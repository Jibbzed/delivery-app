package controleur.command;

public interface Command {
    /**
     * Execute the command this
     */
    void doCommand();

    /**
     * Execute the reverse command of this
     */
    void undoCommand();
}
