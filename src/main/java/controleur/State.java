package controleur;

public interface State {
    public void doubleCliquePlan(Controller controller);

    public void valider(Controller controller);

    public void modifierLivraison(Controller controller);

    public void ajouterCoursier(Controller controller);

    public void chargerLivraison(Controller controller);
}
