package at.htl.adventskalender;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class AdventskalenderController {
    @FXML private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12, image13, image14, image15, image16, image17, image18, image19, image20, image21, image22, image23, image24;

    @FXML private Pane pane;

    private HashMap<ImageView, Door> doors = new LinkedHashMap<>();

    private final Image doom = getImageFromName("doom");
    private static boolean cheat = false;

    FileHandler fh = new FileHandler();


    public void initialize() {
        List<ImageView> images = List.of(                           //temporary List for initalization of doors
                image1, image2, image3, image4, image5, image6,
                image7, image8, image9, image10, image11, image12,
                image13, image14, image15, image16, image17, image18,
                image19, image20, image21, image22, image23, image24
        );


        boolean fileIntegrity = fh.checkFileIntegrity();
        List<Boolean> fileContent = null;

        if(fileIntegrity) {
            fileContent = fh.readFromFile();
        }else {
            IO.println("Could not fetch file!");
        }

        for (int i = 0; i < images.size(); i++) {
            if(fileIntegrity) {
                doors.put(
                        images.get(i),
                        new Door("door" + (i + 1), "duck" + (i + 1), fileContent.get(i))
                );

            }else {
                doors.put(
                        images.get(i),
                        new Door("door" + (i + 1), "duck" + (i + 1), true)
                );

            }
        }


        //bloom effect for makeing the images glow a bit
        Bloom bloom = new Bloom();
        bloom.setThreshold(0.95);
        pane.setEffect(bloom);

        //sets the current images to the ImageView objects
        setImagesToDoor();

        //sets the Background
        BackgroundImage bgImage = new BackgroundImage(
                getImageFromName("bg"),
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
        if(doors.get(imageView).getIsClosed()) {
            if(checkDate(imageView)) {
                openDoorAnimation(imageView);

                imageView.setImage(doors.get(imageView).getImageOpen());
                doors.get(imageView).setIsClosed(false);

                List<Boolean> states = new ArrayList<>();
                doors.forEach((ImageView,Door) -> {states.add(Door.getIsClosed());});

                fh.writeToFile(states);

            }else{
                imageView.setImage(doom);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You are not allowed to see this yet, aren't you?");
                alert.showAndWait();
                imageView.setImage(doors.get(imageView).getImageClosed());
            }
        }


    }

    public void openDoorAnimation(ImageView original) {
        ImageView animationImageView = new ImageView(original.getImage());
        animationImageView.setFitWidth(original.getFitWidth());
        animationImageView.setFitHeight(original.getFitHeight());

        animationImageView.setLayoutX(original.getLayoutX());
        animationImageView.setLayoutY(original.getLayoutY());
        animationImageView.setVisible(true);

        pane.getChildren().add(animationImageView);

        animationImageView.getTransforms().clear();
        animationImageView.setTranslateX(0);

        Rotate rotate = new Rotate(
                0,
                0,
                animationImageView.getFitHeight() / 2,
                0,
                Rotate.Y_AXIS
        );
        animationImageView.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(2000), new KeyValue(rotate.angleProperty(), -90))
        );

        timeline.setOnFinished(e -> {
            pane.getChildren().remove(animationImageView); // cleanup
        });

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



    public static void toggleCheat() {
        cheat = !cheat;
    }




}
