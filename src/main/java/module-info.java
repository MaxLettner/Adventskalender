module at.htl.adventskalender {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens at.htl.adventskalender to javafx.fxml;
    exports at.htl.adventskalender;
}