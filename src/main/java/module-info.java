module at.htl.adventskalender {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.htl.adventskalender to javafx.fxml;
    exports at.htl.adventskalender;
}