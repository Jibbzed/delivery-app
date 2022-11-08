module vue {
    requires javafx.controls;
    requires jdom2;
    requires org.slf4j;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.sothawo.mapjfx;

    requires javafaker;
    exports vue;

    opens vue to javafx.fxml, javafx.graphics;
    exports controleur;
    opens controleur to javafx.fxml, javafx.graphics;
    exports controleur.state;
    opens controleur.state to javafx.fxml, javafx.graphics;
}