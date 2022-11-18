
package vue.FenetreHandler;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import com.sothawo.mapjfx.offline.OfflineCache;
import controleur.StateController;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import modele.*;
import modele.exception.MauvaisFormatXmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ServiceCoursier;
import service.impl.ServiceLivraisonMockImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;


public class FenetrePrincipaleHandler {

    /**
     * logger for the class.
     */
    private static final Logger logger = LoggerFactory.getLogger(FenetrePrincipaleHandler.class);
    private final String intersectionIcone = "/icons8-pin-24.png";
    private final String entrepotIcone = "/icons8-warehouse-24.png";
    ;
    private static String entropotId;
    private static Coordinate coordCenterWarehouse;
    private static final Coordinate coordWarhouseLyon = new Coordinate(45.74979, 4.87572);
    private StateController stateController;
    private Coordinate coordMin;
    private Coordinate coordMax;


    private List<Coordinate> coordinateList;
    private Extent extentLyon;
    /**
     * default zoom value.
     */
    private static final int ZOOM_DEFAULT = 15;

    /**
     * the markers.
     */
    private Marker markerMinCoord;
    private Marker markerMaxCoord;

    private final Set<Marker> markersIntersections = new HashSet<>();


    /**
     * Le plan
     */
    private Plan plan;
    @FXML
    /** button to set the map's zoom. */
    private Button buttonZoom;

    /**
     * the MapView containing the map
     */
    @FXML
    private MapView mapView;

    /**
     * the box containing the top controls, must be enabled when mapView is initialized
     */
    @FXML
    private HBox topControls;

    /**
     * Slider to change the zoom value
     */
    @FXML
    private Slider sliderZoom;

    /**
     * Accordion for all the different options
     */
    @FXML
    private Pane leftControls;


    /**
     * button to set the map's center
     */
    @FXML
    private Button buttonWarhouse;

    @FXML
    private Button buttonCoursier;

    @FXML
    private Button buttonCalculTournee;

    @FXML
    private Button buttonSupprimerLivraison;

    @FXML
    private Button buttonModifierLivraison;

    @FXML
    private Button buttonAjouterLivraison;


    /**
     * label to display the last event.
     */
    @FXML
    private Label labelEvent;

    /**
     * the first CoordinateLine
     */
    private CoordinateLine trackMagenta;
    private Map<Coursier, List<CoordinateLine>> trackMap = new HashMap<>();

    /**
     * the second CoordinateLine
     */
    private CoordinateLine trackCyan;
    /**
     * Check button for first track
     */
    private CoordinateLine polygonLine;
    /**
     * Check Button for polygon drawing mode.
     */

    @FXML
    private ListView<Livraison> listeLivraisons;

    @FXML
    private ListView<Livraison> listeLivraisonsSurTournee;

    @FXML
    private VBox vBoxLivraison;

    @FXML
    private VBox vBoxTournee;

    private ServiceCoursier serviceCoursier = ServiceCoursier.getInstance();

    @FXML
    private ComboBox comboCoursier;

    private Optional<Coursier> coursierSelectionne = Optional.empty();

    private Parent parent;

    public FenetrePrincipaleHandler() {
    }

    public void initialize(StateController stateController, Plan plan, Parent parent) {
        this.plan = plan;
        this.stateController = stateController;
        this.parent = parent;
    }

    private void chargerPlan(Plan plan) {
        initCoordStatic();
        this.plan = plan;
        coordinateList =
                plan.getIntersections().values().stream()
                        .map(intersection -> {
                            if (intersection.isEntrepot()) {
                                FenetrePrincipaleHandler.entropotId = intersection.getId();
                            }
                            return new Coordinate(intersection.getLatitude(), intersection.getLongitude());
                        })
                        .collect(Collectors.toList());
        // TODO: pour les constantes, ca degage d'ici, example "/icons8-pin-24.png", "/icons8-warehouse-24.png"
        coordinateList.stream()
                .map(c -> {
                    String image = this.intersectionIcone;
                    boolean isVisible = false;
                    if (c.equals(coordCenterWarehouse)) {

                        image = this.entrepotIcone;
                        isVisible = true;
                    }
                    return new Marker(getClass().getResource(image), -15, -20).setPosition(c).setVisible(isVisible);
                })
                .forEach(markersIntersections::add);

        extentLyon = Extent.forCoordinates(coordinateList);
        coordMin = extentLyon.getMin();
        coordMax = extentLyon.getMax();
        // a couple of markers using the provided ones
        markerMinCoord = Marker.createProvided(Marker.Provided.BLUE).setPosition(coordMin).setVisible(false);
        markerMaxCoord = Marker.createProvided(Marker.Provided.GREEN).setPosition(coordMax).setVisible(false);
    }

    private static void initCoordStatic() {
        coordCenterWarehouse = new Coordinate(coordWarhouseLyon.getLatitude(), coordWarhouseLyon.getLongitude());
    }

    /**
     * called after the fxml is loaded and all objects are created. This is not called initialize any more,
     * because we need to pass in the projection before initializing.
     *
     * @param projection the projection to use in the map.
     */
    public void initMapAndControls(Projection projection, Plan plan) {
        chargerPlan(plan);
        logger.trace("begin initialize");
        vBoxLivraison.setAlignment(Pos.CENTER);
        vBoxTournee.setAlignment(Pos.CENTER);


        // init MapView-Cache
        final OfflineCache offlineCache = mapView.getOfflineCache();
        final String cacheDir = System.getProperty("java.io.tmpdir") + "/mapjfx-cache";

        // set the custom css file for the MapView
        mapView.setCustomMapviewCssURL(getClass().getResource("/custom_mapview.css"));

        // set the controls to disabled, this will be changed when the MapView is intialized
        setControlsDisable(true);

        // wire up the location buttons
        buttonWarhouse.setOnAction(event -> mapView.setCenter(coordCenterWarehouse));

        buttonCoursier.setOnAction(event -> {
            try {
                this.stateController.allerGestionnaireCoursier();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Coursier toutLesCoursiers = new Coursier("Tout", "Coursier");
        comboCoursier.getItems().add(toutLesCoursiers);
        serviceCoursier.getListeCoursiers().forEach(c -> comboCoursier.getItems().add(c));
        comboCoursier.setOnAction(e -> {
            selectionnerCoursier((Coursier) ((ComboBox) e.getSource()).getValue());
            disableToutChemin();
            coursierSelectionne.ifPresent(c -> {
                if(!c.equals(toutLesCoursiers)) {
                    enableCheminByCoursier(c);
                }
                else {
                    enableToutChemin();
                }
            });
            refreshLivraison();
        });

        logger.trace("location buttons done");

        // wire the zoom button and connect the slider to the map's zoom
        buttonZoom.setOnAction(event -> mapView.setZoom(ZOOM_DEFAULT));
        sliderZoom.valueProperty().bindBidirectional(mapView.zoomProperty());

        logger.trace("options and labels done");

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        setupEventHandlers();

        logger.trace("marker checks done");

        trackMagenta = new CoordinateLine().setColor(Color.MAGENTA).setWidth(7).setVisible(true);

        buttonCalculTournee.setOnAction(event -> this.calculTournee());

        buttonSupprimerLivraison.setOnAction(event -> {
            supprimerLivraison();
        });

        buttonModifierLivraison.setOnAction(event -> {
            modifierLivraison();
        });

        listeLivraisons.setOnMouseClicked(event -> {
            this.stateController.getCurrentState().cliqueLivraison(this.stateController);
        });

        listeLivraisons.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                this.stateController.getCurrentState().cliqueLivraison(this.stateController);
                labelEvent.setText(newValue.toString(plan));
                String intersectionIdSelectionne = newValue.getDestinationLivraison().getId();
                Map<String, Dijkstra> resultatDijkstra =
                        plan.plusCourtChemin(entropotId, Collections.singletonList( intersectionIdSelectionne ));
                // TODO: Duplicated code
                List<Coordinate> cheminLivraison = resultatDijkstra.get(intersectionIdSelectionne).getChemin().stream()
                        .map(Troncon::getOrigine)
                        .map(intersection-> new Coordinate(intersection.getLatitude(), intersection.getLongitude()))
                        .collect(Collectors.toList());
                // Ajouter derniere intersection au chemin
                cheminLivraison.add(new Coordinate(newValue.getDestinationLivraison().getLatitude(), newValue.getDestinationLivraison().getLongitude()));
                mapView.removeCoordinateLine(trackMagenta);
                trackMagenta = new CoordinateLine(cheminLivraison).setColor(Color.MAGENTA).setWidth(7).setVisible(true);
//            Extent tracksExtent = Extent.forCoordinates(trackMagenta.getCoordinateStream().collect(Collectors.toList()));
//            mapView.setExtent(tracksExtent);
                mapView.addCoordinateLine(trackMagenta);
                for(Marker m : markersIntersections) {
                    if(m.getPosition().getLatitude() == newValue.getDestinationLivraison().getLatitude() && m.getPosition().getLongitude() == newValue.getDestinationLivraison().getLongitude()) {
                        m.setVisible(true);
                    } else if(!m.getPosition().getLatitude().equals(coordCenterWarehouse.getLatitude()) || !m.getPosition().getLongitude().equals(coordCenterWarehouse.getLongitude())) {
                        m.setVisible(false);
                    }
                }
            }

        });

        this.parent.setOnMouseClicked(event -> {
            Double x = event.getScreenX();
            Double y = event.getSceneY();

            if (!this.vBoxLivraison.getLayoutBounds().contains(x, y)) {
                this.stateController.getCurrentState().clique(this.stateController);
                trackMagenta.setVisible(false);
                listeLivraisons.getSelectionModel().clearSelection();
            }
        });
        disableLivraisonDisableableComponents();
        // finally initialize the map view
        logger.trace("start map initialization");
        mapView.initialize(Configuration.builder()
                .projection(projection)
                .showZoomControls(false)
                .build());
        this.listeLivraisons.setCellFactory(param -> new ListCell<Livraison>() {
            @Override
            protected void updateItem(Livraison livraison, boolean empty) {
                super.updateItem(livraison, empty);
                //TODO: change the display format (address)
                if (empty || livraison == null || livraison.getDestinationLivraison() == null) {
                    setText(null);
                } else {
                    setText(livraison.afficherIhm(getPlan()));
                }
            }
        });

        this.listeLivraisonsSurTournee.setCellFactory(param -> new ListCell<Livraison>() {
            @Override
            protected void updateItem(Livraison livraison, boolean empty) {
                super.updateItem(livraison, empty);
                //TODO: change the display format (address)
                if (empty || livraison == null || livraison.getDestinationLivraison() == null) {
                    setText(null);
                } else {
                    setText(livraison.toString(getPlan()));
                }
            }
        });

        logger.debug("initialization finished");
    }

    /**
     * initializes the event handlers.
     */
    private void setupEventHandlers() {
        // add an event handler for singleclicks, set the click marker to the new position when it's visible
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            event.consume();
            final Coordinate newPosition = event.getCoordinate().normalize();
            Intersection intersection = plan.getIntersectionProche(newPosition.getLatitude(), newPosition.getLongitude());
            //set the marker from markerIntersections of the intersection visible
            if (intersection != null) {
                for (Marker marker : markersIntersections) {
                    if (marker.getPosition().getLatitude() == intersection.getLatitude() && marker.getPosition().getLongitude() == intersection.getLongitude()) {
                        marker.setVisible(true);
                        System.out.println("le marker choisi" + marker.toString());
                    } else {
                        //set marker invisible except for the warehouse
                        if (!marker.getPosition().equals(coordCenterWarehouse)) {
                            marker.setVisible(false);
                        }
                    }
                }
                labelEvent.setText(plan.listerTronconsParIntersection(intersection));
            }
        });

        // add an event handler for MapViewEvent#MAP_EXTENT and set the extent in the map
        mapView.addEventHandler(MapViewEvent.MAP_EXTENT, event -> {
            event.consume();
            mapView.setExtent(event.getExtent());
        });


        mapView.addEventHandler(MapViewEvent.MAP_RIGHTCLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: map right clicked at: " + event.getCoordinate());
        });
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            event.consume();
            Coordinate coordSelectionne = event.getMarker().getPosition();
            String intersectionIdSelectionne =
                    plan.getIntersections().values().stream()
                            .filter(i -> i.getLatitude() == coordSelectionne.getLatitude()
                                    && i.getLongitude() == coordSelectionne.getLongitude())
                            .map(Intersection::getId)
                            .findAny().orElse("");
            Map<String, Dijkstra> resultatDijkstra =
                    plan.plusCourtChemin(entropotId, Collections.singletonList(intersectionIdSelectionne));

            List<Coordinate> chemin = resultatDijkstra.get(intersectionIdSelectionne).getChemin().stream()
                    .map(Troncon::getOrigine)
                    .map(intersection -> new Coordinate(intersection.getLatitude(), intersection.getLongitude()))
                    .collect(Collectors.toList());
            // Ajouter derniere intersection au chemin
            chemin.add(coordSelectionne);
            mapView.removeCoordinateLine(trackMagenta);
            trackMagenta = new CoordinateLine(chemin).setColor(Color.MAGENTA).setWidth(7).setVisible(true);
            for (Intersection i : plan.getIntersections().values()) {
                if (i.getLatitude() == coordSelectionne.getLatitude() && i.getLongitude() == coordSelectionne.getLongitude()) {
                    labelEvent.setText(plan.listerTronconsParIntersection(i));
                    break;
                }
            }
        });

        mapView.addEventHandler(MarkerEvent.MARKER_DOUBLECLICKED, event -> {
            event.consume();
            Coordinate coordSelectionne = event.getMarker().getPosition();

            //retrouver l'id de l'intersection depuis les coordonnées du marker
            String intersectionIdSelectionne =
                    plan.getIntersections().values().stream()
                            .filter(i -> i.getLatitude() == coordSelectionne.getLatitude()
                                    && i.getLongitude() == coordSelectionne.getLongitude())
                            .map(Intersection::getId)
                            .findAny().orElse("");

            //obtenir l'intersection depuis son id
            Intersection intersection = plan.getIntersections().get(intersectionIdSelectionne);

            if(!intersection.isEntrepot()){
                this.stateController.doubleCliquePlan(intersection);
            }
        });

        mapView.addEventHandler(MarkerEvent.MARKER_RIGHTCLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: marker right clicked: " + event.getMarker().getId());
        });

        mapView.addEventHandler(MapLabelEvent.MAPLABEL_CLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: label clicked: " + event.getMapLabel().getText());
        });
        mapView.addEventHandler(MapLabelEvent.MAPLABEL_RIGHTCLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: label right clicked: " + event.getMapLabel().getText());
        });

        logger.trace("map handlers initialized");
    }

    @FXML
    private void handleKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.Z) {
            stateController.undo();
            refreshLivraison();
        }
    }

    /**
     * enables / disables the different controls
     *
     * @param flag if true the controls are disabled
     */
    private void setControlsDisable(boolean flag) {
        topControls.setDisable(flag);
        leftControls.setDisable(flag);
    }

    /**
     * finishes setup after the mpa is initialzed
     */
    private void afterMapIsInitialized() {
        logger.trace("map intialized");
        logger.debug("setting center and enabling controls...");
        // start at the harbour with default zoom
        mapView.setZoom(ZOOM_DEFAULT);
        mapView.setCenter(coordCenterWarehouse);
        // add the markers to the map - they are still invisible
        mapView.addMarker(markerMinCoord);
        mapView.addMarker(markerMaxCoord);
        markersIntersections.forEach(mapView::addMarker);
        // add the tracks
        mapView.addCoordinateLine(trackMagenta);
        // now enable the controls
        setControlsDisable(false);
    }

    private void calculTournee() {
        // On récupère la liste de livraisons existantes et on les groupe par coursier
        Map<Optional<Coursier>, List<Livraison>> listeLivraisonByCoursier = ServiceLivraisonMockImpl.getInstance().afficherToutesLivraisons()
                .stream()
                .filter(livraison -> livraison.getCoursierLivraison().isPresent())
                .collect(groupingBy(Livraison::getCoursierLivraison));
        //  on calcule tournee et la groupe par coursier.
        Map<Coursier, Tournee> tourneeParCoursier = new HashMap<>();
        listeLivraisonByCoursier.keySet().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                //TODO: remove this filter after filtering the courtier in the creating livraison vue handler.
                .filter(coursier -> !coursier.getPlanifie())
                .forEach(
                        coursier -> {
                            Map<String, Livraison> livraisons = new HashMap<>();
                            listeLivraisonByCoursier.get(Optional.of(coursier)).forEach(
                                    livraison -> {
                                        livraisons.put(livraison.getDestinationLivraison().getId(), livraison);
                                    }
                            );
                            //TODO: Try catch here for the tounee with NullPointerException.
                            Tournee tournee = new CalculTournee(this.plan, plan.getIntersections().get(entropotId), livraisons).calculerTournee();
                            tourneeParCoursier.put(coursier, tournee);
                            tournee.getLivraisons().forEach(
                                    l -> ServiceLivraisonMockImpl.getInstance().ajouterLivraison(l)
                            );
                            coursier.setPlanifie(true);
                            ServiceCoursier.getInstance().modifierCoursier(coursier);
                        }
                );
        listeLivraisons.getItems().forEach(
                livraison -> {
                    ServiceLivraisonMockImpl.getInstance().supprimerLivraison(livraison);
                }
        );
        refreshLivraison();

        // On récupère les intersections en groupant par coursier
        Map<Coursier, List<Troncon>> listTronconsOrderedByCourtier = new HashMap<>();
        tourneeParCoursier.forEach(
                (c , t) ->
                    listTronconsOrderedByCourtier.put(
                            c,
                            t.getLivraisons()
                                    .stream()
                                    .flatMap(livraison -> livraison.getParcoursLivraison().stream())
                                    .collect(Collectors.toList()))
        );



        mapView.removeCoordinateLine(trackMagenta);
        tourneeParCoursier.forEach(
                (c, tournee) -> {
                    final double r =Math.abs(Math.random());
                    final double g = Math.abs(Math.random());
                    final double b = Math.abs(Math.random());
                    List<CoordinateLine> coordinateLinesForTournee = new ArrayList<>();
                    tournee.getLivraisons().forEach(
                            livraison -> {
                                List<Coordinate> coordonnesSurLivraison = new ArrayList<>();
                                livraison.getParcoursLivraison().stream().forEach(
                                        parcours -> {
                                            coordonnesSurLivraison.add(new Coordinate(parcours.getOrigine().getLatitude(), parcours.getOrigine().getLongitude()));
                                            coordonnesSurLivraison.add(new Coordinate(parcours.getDestination().getLatitude(), parcours.getDestination().getLongitude()));
                                        }
                                );

                                float opacity = ((float) tournee.getLivraisons().indexOf(livraison) + 1) / ((float)tournee.getLivraisons().size());
                                CoordinateLine coordinateLineForLivraison =  new CoordinateLine(coordonnesSurLivraison)
                                        .setColor(Color.color(r, g, b, opacity))
                                        .setWidth(7)
                                        .setVisible(true);
                                coordinateLinesForTournee.add(coordinateLineForLivraison);
                            }
                    );
                    trackMap.put(c, coordinateLinesForTournee);
                    coordinateLinesForTournee.forEach(mapView::addCoordinateLine);

                    this.coursierSelectionne.ifPresent(
                            coursierSelectionner -> {
                                if(coursierSelectionner.equals( new Coursier("Tout", "Coursier"))) {
                                    enableToutChemin();
                                } else {
                                    disableToutChemin();
                                    enableCheminByCoursier(coursierSelectionner);
                                }

                            }

                    );
                }
        );
    }

    private void enableCheminByCoursier(Coursier c) {
        if(this.trackMap.containsKey(c)) {
            this.trackMap.get(c).forEach(cor-> cor.setVisible(true));
        }
    }

    private void disableToutChemin() {
        this.trackMap.forEach(
                (coursier, chemin) -> {
                    chemin.forEach(coordinateLine -> coordinateLine.setVisible(false));
                }
        );
    }

    private void enableToutChemin() {
        this.trackMap.forEach(
                (coursier, chemin) -> {
                    chemin.forEach(coordinateLine -> coordinateLine.setVisible(true));
                }
        );
    }

    /**
     * load a coordinateLine from the given uri in lat;lon csv format
     *
     * @param url url where to load from
     * @return optional CoordinateLine object
     * @throws NullPointerException if uri is null
     */
    private Optional<CoordinateLine> loadCoordinateLine(URL url) {
        try (
                Stream<String> lines = new BufferedReader(
                        new InputStreamReader(url.openStream(), StandardCharsets.UTF_8)).lines()
        ) {
            return Optional.of(new CoordinateLine(
                    lines.map(line -> line.split(";")).filter(array -> array.length == 2)
                            .map(values -> new Coordinate(Double.valueOf(values[0]), Double.valueOf(values[1])))
                            .collect(Collectors.toList())));
        } catch (IOException | NumberFormatException e) {
            logger.error("load {}", url, e);
        }
        return Optional.empty();
    }

    public void refreshLivraison() {
        ObservableList<Livraison> listLivraisonObeservable = FXCollections.observableArrayList();
        ObservableList<Livraison> listLivraisonSurTourneeObeservable = FXCollections.observableArrayList();

        listeLivraisons.getItems().removeAll(listeLivraisons.getItems());
        listeLivraisonsSurTournee.getItems().removeAll(listeLivraisonsSurTournee.getItems());

        listLivraisonObeservable.addAll(
                ServiceLivraisonMockImpl.getInstance().afficherToutesLivraisons()
                        .stream().filter(l -> l.getParcoursLivraison().isEmpty()).collect(Collectors.toList())
        );
        listLivraisonSurTourneeObeservable.addAll(
                ServiceLivraisonMockImpl.getInstance().afficherToutesLivraisons()
                        .stream()
                        .filter(l -> !l.getParcoursLivraison().isEmpty())
                        .filter(l -> {
                            if (coursierSelectionne.isPresent()) {
                                return l.getCoursierLivraison().equals(coursierSelectionne);
                            }
                            // when there is no selected courtier, list all deliveries that are in a tournee
                            return true;
                        })
                        .collect(Collectors.toList())
        );
        listeLivraisons.getItems().addAll(listLivraisonObeservable);
        listeLivraisonsSurTournee.getItems().addAll(listLivraisonSurTourneeObeservable);
        labelEvent.setText("Liste livraison modifiée");
    }

    public void supprimerLivraison() {
        Livraison livraisonASupprimer = this.listeLivraisons.getSelectionModel().getSelectedItem();
        stateController.supprimerLivraison(livraisonASupprimer);
        refreshLivraison();
    }

    public void modifierLivraison() {
        Livraison livraisonAModifier = this.listeLivraisons.getSelectionModel().getSelectedItem();
        stateController.cliqueModifierLivraison(livraisonAModifier);
        refreshLivraison();
    }


    public Plan getPlan() {
        return plan;
    }

    public void disableLivraisonDisableableComponents() {
        this.buttonSupprimerLivraison.setDisable(true);
        this.buttonModifierLivraison.setDisable(true);
    }

    public void enableLivraisonDisableableComponents() {
        this.buttonSupprimerLivraison.setDisable(false);
        this.buttonModifierLivraison.setDisable(false);
    }

    public void selectionnerCoursier(Coursier coursier) {
        this.coursierSelectionne = Optional.of(coursier);
    }
}
