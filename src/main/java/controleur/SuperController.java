package controleur;

public class SuperController {

    private Controller controller;
    private ControllerPageAccueil controllerPageAccueil;

    public SuperController() {

    }

    public String getXmlPath() {
        return controllerPageAccueil.getXmlPath();
    }
}
