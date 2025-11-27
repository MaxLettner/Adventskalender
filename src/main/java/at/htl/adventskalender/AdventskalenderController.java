package at.htl.adventskalender;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class AdventskalenderController {
    @FXML
    private VBox root;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private ImageView image6;
    @FXML
    private ImageView image7;
    @FXML
    private ImageView image8;
    @FXML
    private ImageView image9;
    @FXML
    private ImageView image10;
    @FXML
    private ImageView image11;
    @FXML
    private ImageView image12;
    @FXML
    private ImageView image13;
    @FXML
    private ImageView image14;
    @FXML
    private ImageView image15;
    @FXML
    private ImageView image16;
    @FXML
    private ImageView image17;
    @FXML
    private ImageView image18;
    @FXML
    private ImageView image19;
    @FXML
    private ImageView image20;
    @FXML
    private ImageView image21;
    @FXML
    private ImageView image22;
    @FXML
    private ImageView image23;
    @FXML
    private ImageView image24;

    @FXML
    private Pane pane;


    public void initialize() {
        setImage(image1, "door1");
        setImage(image2, "door2");
        setImage(image3, "door3");
        setImage(image4, "door4");
        setImage(image5, "door5");
        setImage(image6, "door6");
        setImage(image7, "door7");
        setImage(image8, "door8");
        setImage(image9, "door9");
        setImage(image10, "door10");
        setImage(image11, "door11");
        setImage(image12, "door12");
        setImage(image13, "door13");
        setImage(image14, "door14");
        setImage(image15, "door15");
        setImage(image16, "door16");
        setImage(image17, "door17");
        setImage(image18, "door18");
        setImage(image19, "door19");
        setImage(image20, "door20");
        setImage(image21, "door21");
        setImage(image22, "door22");
        setImage(image23, "door23");
        setImage(image24, "door24");

        File file = new File("src/main/resources/images/bg.jpg");
        Image image = new Image(file.toURI().toString());
        BackgroundImage bgImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        pane.setBackground(new Background(bgImage));

    }

    private void setImage(ImageView imageview, String name) {
        File file = new File("src/main/resources/images/" +  name + ".jpeg");
        Image image = new Image(file.toURI().toString());
        imageview.setImage(image);
    }

    public void handleClick(MouseEvent event) {
        LocalDate date = LocalDate.now();

        IO.println(date.toString());

        ImageView imageView = (ImageView) event.getSource();

        setImage(imageView, "duck");
    }


}
