/*
 Copyright 2015-2020 Peter-Josef Meisch (pj.meisch@sothawo.com)

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package controleur;

import com.sothawo.mapjfx.*;
import com.sothawo.mapjfx.event.MapLabelEvent;
import com.sothawo.mapjfx.event.MapViewEvent;
import com.sothawo.mapjfx.event.MarkerEvent;
import com.sothawo.mapjfx.offline.OfflineCache;
import javafx.animation.AnimationTimer;
import javafx.animation.Transition;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import modele.*;
import modele.exception.MauvaisFormatXmlException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.ServiceLivraison;
import service.impl.ServiceLivraisonMockImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller for the FXML defined code.
 *
 * @author P.J. Meisch (pj.meisch@sothawo.com).
 */
public class Controller {

    /** logger for the class. */
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    /** some coordinates from around town. */
    // TODO: remove those coordinates .

            //TODO: make entropot id dynamic
    private static final String __ENTROPOT_ID__ = "25303831";
    private static Coordinate coordCenterWarehouse;
    private static final Coordinate coordWarhouseLyon = new Coordinate(45.74979, 4.87572);
    private Coordinate coordMin;
    private Coordinate coordMax;


    private List<Coordinate> coordinateList;
    private Extent extentLyon;
    /** default zoom value. */
    private static final int ZOOM_DEFAULT = 15;

    /** the markers. */
    private Marker markerMinCoord;
    private Marker markerMaxCoord;

    private final Set<Marker> markersIntersections = new HashSet<>();

    /** the labels. */

    /** Le plan */
    private Plan plan;

    @FXML
    /** button to set the map's zoom. */
    private Button buttonZoom;

    /** the MapView containing the map */
    @FXML
    private MapView mapView;

    /** the box containing the top controls, must be enabled when mapView is initialized */
    @FXML
    private HBox topControls;

    /** Slider to change the zoom value */
    @FXML
    private Slider sliderZoom;

    /** Accordion for all the different options */
    @FXML
    private Accordion leftControls;

    /** section containing the location button */
    @FXML
    private TitledPane optionsLocations;

    /** button to set the map's center */
    /** button to set the map's center */
    /** button to set the map's center */
    /** button to set the map's center */
    @FXML
    private Button buttonWarhouse;

    /** for editing the animation duration */
    @FXML
    private TextField animationDuration;

    /** the BIng Maps API Key. */
    @FXML
    private TextField bingMapsApiKey;

    /** Label to display the current center */
    @FXML
    private Label labelCenter;

    /** Label to display the current extent */
    @FXML
    private Label labelExtent;

    /** Label to display the current zoom */
    @FXML
    private Label labelZoom;

    /** label to display the last event. */
    @FXML
    private Label labelEvent;

    /** RadioButton for MapStyle OSM */
    @FXML
    private RadioButton radioMsOSM;

    /** RadioButton for MapStyle Stamen Watercolor */
    @FXML
    private RadioButton radioMsSTW;

    /** RadioButton for MapStyle Bing Roads */
    @FXML
    private RadioButton radioMsBR;

    /** RadioButton for MapStyle Bing Roads - dark */
    @FXML
    private RadioButton radioMsCd;

    /** RadioButton for MapStyle Bing Roads - grayscale */
    @FXML
    private RadioButton radioMsCg;

    /** RadioButton for MapStyle Bing Roads - light */
    @FXML
    private RadioButton radioMsCl;

    /** RadioButton for MapStyle Bing Aerial */
    @FXML
    private RadioButton radioMsBA;

    /** RadioButton for MapStyle Bing Aerial with Label */
    @FXML
    private RadioButton radioMsBAwL;

    /** RadioButton for MapStyle WMS. */
    @FXML
    private RadioButton radioMsWMS;

    /** RadioButton for MapStyle XYZ */
    @FXML
    private RadioButton radioMsXYZ;

    /** ToggleGroup for the MapStyle radios */
    @FXML
    private ToggleGroup mapTypeGroup;

    /** Check button for harbour marker */
    @FXML
    private CheckBox checkIntersectionsMarkers;
    /** the first CoordinateLine */
    private CoordinateLine trackMagenta;
    /** Check button for first track */
    @FXML
    private CheckBox checkTrackMagenta;

    /** the second CoordinateLine */
    private CoordinateLine trackCyan;
    /** Check button for first track */
    private CoordinateLine polygonLine;
    /** Check Button for polygon drawing mode. */

    /** Check Button for constraining th extent. */
    @FXML
    private CheckBox checkConstrainXmlFile;

    @FXML
    private ListView<?> listeLivraisons;

    /** params for the WMS server. */
    private WMSParam wmsParam = new WMSParam()
        .setUrl("http://ows.terrestris.de/osm/service?")
        .addParam("layers", "OSM-WMS");

//    https://server.arcgisonline.com/arcgis/rest/services/World_Topo_Map/MapServer/tile/10/350/530
    private XYZParam xyzParams = new XYZParam()
        .withUrl("https://server.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer/tile/{z}/{y}/{x})")
        .withAttributions(
            "'Tiles &copy; <a href=\"https://services.arcgisonline.com/ArcGIS/rest/services/World_Topo_Map/MapServer\">ArcGIS</a>'");

    // TODO: handle exceptions
    public Controller() throws MauvaisFormatXmlException, IOException {
        //chargerPlan("src/test/resources/smallMap.xml");
    }

    private void chargerPlan(String path) throws MauvaisFormatXmlException, IOException {
        initCoordStatic();
        Parser parser = new Parser();
        this.plan = parser.lirePlan(path);
        coordinateList =
                plan.getIntersections().values().stream()
                        .map(intersection -> new Coordinate(intersection.getLatitude(), intersection.getLongitude()))
                        .collect(Collectors.toList());
        // TODO: pour les constantes, ca degage d'ici, example "/icons8-pin-24.png", "/icons8-warehouse-24.png"
        coordinateList.stream()
                .map(c -> {
                    String imageUrl = "/icons8-pin-24.png";
                    if (c.equals(coordCenterWarehouse)) {
                        imageUrl = "/icons8-warehouse-24.png";
                    }
                    return new Marker(getClass().getResource(imageUrl), -15, -20).setPosition(c).setVisible(false);
                })
//                 .map(c-> Marker.createProvided(Marker.Provided.BLUE).setPosition(c).setVisible(false))
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
     * @param projection
     *     the projection to use in the map.
     */
    public void initMapAndControls(Projection projection, String path) throws MauvaisFormatXmlException, IOException {
        chargerPlan(path);
        logger.trace("begin initialize");



        // init MapView-Cache
        final OfflineCache offlineCache = mapView.getOfflineCache();
        final String cacheDir = System.getProperty("java.io.tmpdir") + "/mapjfx-cache";
//        logger.info("using dir for cache: " + cacheDir);
//        try {
//            Files.createDirectories(Paths.get(cacheDir));
//            offlineCache.setCacheDirectory(cacheDir);
//            offlineCache.setActive(true);
//        } catch (IOException e) {
//            logger.warn("could not activate offline cache", e);
//        }

        // set the custom css file for the MapView
        mapView.setCustomMapviewCssURL(getClass().getResource("/custom_mapview.css"));

        leftControls.setExpandedPane(optionsLocations);

        // set the controls to disabled, this will be changed when the MapView is intialized
        setControlsDisable(true);

        // wire up the location buttons
        buttonWarhouse.setOnAction(event -> mapView.setCenter(coordCenterWarehouse));

//        buttonAllLocations.setOnAction(event -> mapView.setExtent(extentAllLocations));
        logger.trace("location buttons done");

        // wire the zoom button and connect the slider to the map's zoom
        buttonZoom.setOnAction(event -> mapView.setZoom(ZOOM_DEFAULT));
        sliderZoom.valueProperty().bindBidirectional(mapView.zoomProperty());

        // add a listener to the animationDuration field and make sure we only accept int values
        animationDuration.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                mapView.setAnimationDuration(0);
            } else {
                try {
                    mapView.setAnimationDuration(Integer.parseInt(newValue));
                } catch (NumberFormatException e) {
                    animationDuration.setText(oldValue);
                }
            }
        });
        animationDuration.setText("500");

        // bind the map's center and zoom properties to the corresponding labels and format them
        labelCenter.textProperty().bind(Bindings.format("center: %s", mapView.centerProperty()));
        labelZoom.textProperty().bind(Bindings.format("zoom: %.0f", mapView.zoomProperty()));
        logger.trace("options and labels done");

        // watch the MapView's initialized property to finish initialization
        mapView.initializedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                afterMapIsInitialized();
            }
        });

        // observe the map type radiobuttons
        mapTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            logger.debug("map type toggled to {}", newValue.toString());
            MapType mapType = MapType.OSM;
            if (newValue == radioMsOSM) {
                mapType = MapType.OSM;
            } else if (newValue == radioMsBR) {
                mapType = MapType.BINGMAPS_ROAD;
            } else if (newValue == radioMsCd) {
                mapType = MapType.BINGMAPS_CANVAS_DARK;
            } else if (newValue == radioMsCg) {
                mapType = MapType.BINGMAPS_CANVAS_GRAY;
            } else if (newValue == radioMsCl) {
                mapType = MapType.BINGMAPS_CANVAS_LIGHT;
            } else if (newValue == radioMsBA) {
                mapType = MapType.BINGMAPS_AERIAL;
            } else if (newValue == radioMsBAwL) {
                mapType = MapType.BINGMAPS_AERIAL_WITH_LABELS;
            } else if (newValue == radioMsWMS) {
                mapView.setWMSParam(wmsParam);
                mapType = MapType.WMS;
            } else if (newValue == radioMsXYZ) {
                mapView.setXYZParam(xyzParams);
                mapType = MapType.XYZ;
            }
            mapView.setBingMapsApiKey(bingMapsApiKey.getText());
            mapView.setMapType(mapType);
        });
        mapTypeGroup.selectToggle(radioMsOSM);

        setupEventHandlers();

        // add the graphics to the checkboxes

        checkIntersectionsMarkers.setGraphic(
                new ImageView(new Image(markerMaxCoord.getImageURL().toExternalForm(), 16.0, 16.0, true, true))
        );
        // bind the checkboxes to the markers visibility
        markersIntersections.forEach(marker-> {
            checkIntersectionsMarkers.selectedProperty().bindBidirectional(marker.visibleProperty());
        });
        logger.trace("marker checks done");

        // load two coordinate lines
//        private static final Coordinate coordWarhouseLyon = new Coordinate(45.74979, 4.87572);

        // TODO: delete afterwards, this is just a test
        Intersection warehouse = plan.getIntersections().get("25303831");
        Intersection i1 = plan.getIntersections().get("25321689");
        Intersection i2 = plan.getIntersections().get("25321687");
        Intersection i3 = plan.getIntersections().get("459797860");
        Intersection i4 = plan.getIntersections().get("251047560");
        Intersection i5 = plan.getIntersections().get("25321447");

        Map<String, Dijkstra> resultat = plan.plusCourtChemin(warehouse.getId(), Stream.of("25336178").collect(Collectors.toList()));
//        List<Coordinate> chemin = Stream.of(
//                new Coordinate(warehouse.getLatitude(), warehouse.getLongitude()),
//                new Coordinate(i1.getLatitude(), i1.getLongitude()),
//                new Coordinate(i2.getLatitude(), i2.getLongitude()),
//                new Coordinate(i3.getLatitude(), i3.getLongitude()),
//                new Coordinate(i4.getLatitude(), i4.getLongitude()),
//                new Coordinate(i5.getLatitude(), i5.getLongitude())
//        ).collect(Collectors.toList());
        //TODO: duplicated code
        List<Coordinate> chemin = resultat.get("25336178").getChemin().stream()
                    .map(Troncon::getOrigine)
                    .map(intersection-> new Coordinate(intersection.getLatitude(), intersection.getLongitude()))
                    .collect(Collectors.toList());
        trackMagenta = new CoordinateLine().setColor(Color.MAGENTA).setWidth(7).setVisible(true);
        trackCyan = new CoordinateLine(chemin).setColor(Color.CYAN).setWidth(7);
        logger.trace("tracks loaded");
        checkTrackMagenta.selectedProperty().bindBidirectional(trackMagenta.visibleProperty());
//        checkTrackCyan.selectedProperty().bindBidirectional(trackCyan.visibleProperty());
        logger.trace("tracks checks done");
        // get the extent of both tracks
        Extent tracksExtent = Extent.forCoordinates(trackCyan.getCoordinateStream().collect(Collectors.toList()));
        ChangeListener<Boolean> trackVisibleListener =
            (observable, oldValue, newValue) -> mapView.setExtent(tracksExtent);
//        trackMagenta.visibleProperty().addListener(trackVisibleListener);
//        trackCyan.visibleProperty().addListener(trackVisibleListener);

        // add the polygon check handler
        ChangeListener<Boolean> polygonListener =
            (observable, oldValue, newValue) -> {
                if (!newValue && polygonLine != null) {
                    mapView.removeCoordinateLine(polygonLine);
                    polygonLine = null;
                }
            };

        // add the constrain listener
        checkConstrainXmlFile.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue) {
                mapView.constrainExtent(extentLyon);
            } else {
                mapView.clearConstrainExtent();
            }
        }));

        // finally initialize the map view
        logger.trace("start map initialization");
        mapView.initialize(Configuration.builder()
            .projection(projection)
            .showZoomControls(false)
            .build());
        logger.debug("initialization finished");

//        long animationStart = System.nanoTime();
//        new AnimationTimer() {
//            @Override
//            public void handle(long nanoSecondsNow) {
//                if (markerKaSoccer.getVisible()) {
//                    // every 100ms, increase the rotation of the markerKaSoccer by 9 degrees, make a turn in 4 seconds
//                    long milliSecondsDelta = (nanoSecondsNow - animationStart) / 1_000_000;
//                    long numSteps = milliSecondsDelta / 100;
//                    int angle = (int) ((numSteps * 9) % 360);
//                    if (markerKaSoccer.getRotation() != angle) {
//                        markerKaSoccer.setRotation(angle);
//                    }
//                }
//            }
//        }.start();
    }

    /**
     * initializes the event handlers.
     */
    private void setupEventHandlers() {
        // add an event handler for singleclicks, set the click marker to the new position when it's visible
        mapView.addEventHandler(MapViewEvent.MAP_CLICKED, event -> {
            event.consume();
            final Coordinate newPosition = event.getCoordinate().normalize();
            labelEvent.setText("Event: map clicked at: " + newPosition);
//            if (markerClick.getVisible()) {
//                final Coordinate oldPosition = markerClick.getPosition();
//                if (oldPosition != null) {
//                    animateClickMarker(oldPosition, newPosition);
//                } else {
//                    markerClick.setPosition(newPosition);
//                    // adding can only be done after coordinate is set
//                    mapView.addMarker(markerClick);
//                }
//            }
        });

        // add an event handler for MapViewEvent#MAP_EXTENT and set the extent in the map
        mapView.addEventHandler(MapViewEvent.MAP_EXTENT, event -> {
            event.consume();
            mapView.setExtent(event.getExtent());
        });

        // add an event handler for extent changes and display them in the status label
        mapView.addEventHandler(MapViewEvent.MAP_BOUNDING_EXTENT, event -> {
            event.consume();
            labelExtent.setText(event.getExtent().toString());
        });

        mapView.addEventHandler(MapViewEvent.MAP_RIGHTCLICKED, event -> {
            event.consume();
            labelEvent.setText("Event: map right clicked at: " + event.getCoordinate());
        });
        mapView.addEventHandler(MarkerEvent.MARKER_CLICKED, event -> {
            event.consume();
            // TODO: afficher section pour ajouter une livraision
            Coordinate coordSelectionne = event.getMarker().getPosition();
            String intersectionIdSelectionne =
                    plan.getIntersections().values().stream()
                    .filter(i-> i.getLatitude() == coordSelectionne.getLatitude()
                                && i.getLongitude() == coordSelectionne.getLongitude())
                    .map(Intersection::getId)
                    .findAny().orElse("");
            Map<String, Dijkstra> resultatDijkstra =
                    plan.plusCourtChemin(__ENTROPOT_ID__, Collections.singletonList( intersectionIdSelectionne ));
            // TODO: Duplicated code
            List<Coordinate> chemin = resultatDijkstra.get(intersectionIdSelectionne).getChemin().stream()
                    .map(Troncon::getOrigine)
                    .map(intersection-> new Coordinate(intersection.getLatitude(), intersection.getLongitude()))
                    .collect(Collectors.toList());
            // Ajouter derniere intersection au chemin
            chemin.add(coordSelectionne);
            mapView.removeCoordinateLine(trackMagenta);
             trackMagenta = new CoordinateLine(chemin).setColor(Color.MAGENTA).setWidth(7).setVisible(true);
//            Extent tracksExtent = Extent.forCoordinates(trackMagenta.getCoordinateStream().collect(Collectors.toList()));
//            mapView.setExtent(tracksExtent);
            mapView.addCoordinateLine(trackMagenta);

            labelEvent.setText("Event: marker clicked: " + event.getMarker().getId());
        });

        mapView.addEventHandler(MarkerEvent.MARKER_DOUBLECLICKED, event -> {
            event.consume();
            Coordinate coordSelectionne = event.getMarker().getPosition();
            String intersectionIdSelectionne =
                    plan.getIntersections().values().stream()
                            .filter(i -> i.getLatitude() == coordSelectionne.getLatitude()
                                    && i.getLongitude() == coordSelectionne.getLongitude())
                            .map(Intersection::getId)
                            .findAny().orElse("");

            String fxmlFile = "/vue/AjoutLivraison.fxml";
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root;
            try {
                root = fxmlLoader.load(getClass().getResourceAsStream(fxmlFile));

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            final AjoutLivraisonController controller = fxmlLoader.getController();

            controller.initData(plan.getIntersections().get(intersectionIdSelectionne), this);

            Stage stage = new Stage();
            stage.setTitle("Ajout Livraison");
            stage.show();
            stage.setScene(new Scene(root));
            stage.centerOnScreen();
            stage.show();



            if(!stage.isShowing()) {

            }

            labelEvent.setText("Event: marker double clicked: " + event.getMarker().getId());
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

        mapView.addEventHandler(MapViewEvent.MAP_POINTER_MOVED, event -> {
            logger.debug("pointer moved to " + event.getCoordinate());
        });

        logger.trace("map handlers initialized");
    }

    private void animateClickMarker(Coordinate oldPosition, Coordinate newPosition) {
        // animate the marker to the new position
        final Transition transition = new Transition() {
            private final Double oldPositionLongitude = oldPosition.getLongitude();
            private final Double oldPositionLatitude = oldPosition.getLatitude();
            private final double deltaLatitude = newPosition.getLatitude() - oldPositionLatitude;
            private final double deltaLongitude = newPosition.getLongitude() - oldPositionLongitude;

//            {
//                setCycleDuration(Duration.seconds(1.0));
//                setOnFinished(evt -> markerClick.setPosition(newPosition));
//            }

            @Override
            protected void interpolate(double v) {
                final double latitude = oldPosition.getLatitude() + v * deltaLatitude;
                final double longitude = oldPosition.getLongitude() + v * deltaLongitude;
//                markerClick.setPosition(new Coordinate(latitude, longitude));
            }
        };
        transition.play();
    }


    /**
     * enables / disables the different controls
     *
     * @param flag
     *     if true the controls are disabled
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
        // can't add the markerClick at this moment, it has no position, so it would not be added to the map

        // add the fix label, the other's are attached to markers.

        // add the tracks
        mapView.addCoordinateLine(trackMagenta);
//        mapView.addCoordinateLine(trackCyan);


        // now enable the controls
        setControlsDisable(false);
    }

    /**
     * load a coordinateLine from the given uri in lat;lon csv format
     *
     * @param url
     *     url where to load from
     * @return optional CoordinateLine object
     * @throws NullPointerException
     *     if uri is null
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
        ObservableList listLivraisonObeservable = FXCollections.observableArrayList();
        listeLivraisons.getItems().removeAll(listeLivraisons.getItems());
        listLivraisonObeservable.addAll(ServiceLivraisonMockImpl.getInstance().afficherToutLivraisons().stream().map(Livraison::afficherIhm).collect(Collectors.toSet()));
        listeLivraisons.getItems().addAll(listLivraisonObeservable);
    }
}
