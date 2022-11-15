package controleur;

// TODO: est-ce qu'on remove ce gars la?
public class SuperController {

    private Controller controller;
    private ControllerPageAccueil controllerPageAccueil;

    public SuperController() {

    }

    public String getXmlPath() {
        return controllerPageAccueil.getXmlPath();
    }
}
