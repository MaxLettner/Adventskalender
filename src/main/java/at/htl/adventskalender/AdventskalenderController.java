package at.htl.adventskalender;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point3D;
import javafx.scene.control.Alert;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.awt.font.ImageGraphicAttribute;
import java.io.File;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdventskalenderController {
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
    private ImageView imageOpeningAnimation;

    @FXML
    private Pane pane;

    HashMap<ImageView, Door> doors = new HashMap<>();
    Image doom = getImageFromName("doom");
    boolean cheat = false;


    public void initialize() {

        doors.put(image1,new Door("door1","duck", true));
        doors.put(image2,new Door("door2","duck", true));
        doors.put(image3,new Door("door3","duck", true));
        doors.put(image4,new Door("door4","duck", true));
        doors.put(image5,new Door("door5","duck", true));
        doors.put(image6,new Door("door6","duck", true));
        doors.put(image7,new Door("door7","duck", true));
        doors.put(image8,new Door("door8","duck", true));
        doors.put(image9,new Door("door9","duck", true));
        doors.put(image10,new Door("door10","duck", true));
        doors.put(image11,new Door("door11","duck", true));
        doors.put(image12,new Door("door12","duck", true));
        doors.put(image13,new Door("door13","duck", true));
        doors.put(image14,new Door("door14","duck", true));
        doors.put(image15,new Door("door15","duck", true));
        doors.put(image16,new Door("door16","duck", true));
        doors.put(image17,new Door("door17","duck", true));
        doors.put(image18,new Door("door18","duck", true));
        doors.put(image19,new Door("door19","duck", true));
        doors.put(image20,new Door("door20","duck", true));
        doors.put(image21,new Door("door21","duck", true));
        doors.put(image22,new Door("door22","duck", true));
        doors.put(image23,new Door("door23","duck", true));
        doors.put(image24,new Door("door24","duck", true));

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.95);
        pane.setEffect(bloom);


        setImagesToDoor();


        BackgroundImage bgImage = new BackgroundImage(getImageFromName("bg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        pane.setBackground(new Background(bgImage));

    }

    private void setImagesToDoor() {
        for(ImageView i : doors.keySet()) {
            if (doors.get(i).getIsClosed()) {
                i.setImage(doors.get(i).getImageClosed());
            }else {
                i.setImage(doors.get(i).getImageOpen());
            }
        }
    }

    private Image getImageFromName(String name) {
        return new Image(getClass().getResource("/images/" + name + ".jpeg").toExternalForm());
    }

    public void handleClick(MouseEvent event) {


        ImageView imageView = (ImageView) event.getSource();

        if(checkDate(imageView)) {
            openDoorAnimation(imageView);

            imageView.setImage(doors.get(imageView).getImageOpen());


        }else{
            Image image = imageView.getImage();
            imageView.setImage(doom);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You are not allowed to see this yet, aren't you?");
            alert.showAndWait();
            imageView.setImage(image);
        }



            imageView.setImage(doors.get(imageView).getImageOpen());
    }

    public void openDoorAnimation(ImageView imageView) {
        imageOpeningAnimation.setLayoutX(imageView.getLayoutX());
        imageOpeningAnimation.setLayoutY(imageView.getLayoutY());
        imageOpeningAnimation.setImage(imageView.getImage());
        imageOpeningAnimation.setVisible(true);
        imageOpeningAnimation.toFront();

        imageOpeningAnimation.getTransforms().clear();

        imageOpeningAnimation.setTranslateX(0);
        Rotate rotate = new Rotate(0, 0, imageOpeningAnimation.getFitHeight() / 2, 0, Rotate.Y_AXIS);
        imageOpeningAnimation.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(2000), new KeyValue(rotate.angleProperty(), -90))
        );

        timeline.play();
    }

    public boolean checkDate(ImageView imageView) {
        if(cheat) return true;

        LocalDate date = LocalDate.now();
        IO.println(date.toString());

        String s = imageView.getId().toString();
        int numDoor = Integer.parseInt(s.replace("image", ""));

        LocalDate dateDoor = LocalDate.of(date.getYear(), Month.DECEMBER, numDoor);
        IO.println(dateDoor.toString());

        return !date.isBefore(dateDoor);
    }


}
