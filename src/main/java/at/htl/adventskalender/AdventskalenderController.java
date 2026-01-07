package at.htl.adventskalender;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.transform.Rotate;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

public class AdventskalenderController {
    @FXML private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12, image13, image14, image15, image16, image17, image18, image19, image20, image21, image22, image23, image24;

    @FXML private Pane pane;

    private Stage stage;

    private HashMap<ImageView, Door> doors = new LinkedHashMap<>();

    private final Image doom = getImageFromName("doom");
    private String bgName = "bg1";

    private boolean cheat = false;

    private FileHandler fh = new FileHandler();


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
            FileContent fc = fh.readFromFile();
            fileContent = fc.doorStates();
            bgName = fc.bgName();
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
        setBackground(bgName);

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
        return new Image(AdventskalenderController.class.getResourceAsStream("/images/" + name + ".jpeg"));
    }

    public void handleClick(MouseEvent event) {
        ImageView imageView = (ImageView) event.getSource();
        if(doors.get(imageView).getIsClosed()) {
            if(checkDate(imageView)) {
                openDoorAnimation(imageView);

                imageView.setImage(doors.get(imageView).getImageOpen());
                doors.get(imageView).setIsClosed(false);

                writeToFile();

            }else{
                imageView.setImage(doom);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.initModality(Modality.WINDOW_MODAL);
                alert.setContentText("You are not allowed to see this yet, aren't you?");
                alert.showAndWait();
                imageView.setImage(doors.get(imageView).getImageClosed());
            }
        }


    }

    private void writeToFile() {
        List<Boolean> states = new ArrayList<>();
        doors.forEach((ImageView,Door) -> {states.add(Door.getIsClosed());});

        fh.writeToFile(new FileContent(states, bgName));
    }

    public void openDoorAnimation(ImageView origView) {
        ImageView animationImageView = new ImageView(origView.getImage());

        animationImageView.setFitWidth(origView.getFitWidth());
        animationImageView.setFitHeight(origView.getFitHeight());

        animationImageView.setLayoutX(origView.getLayoutX());
        animationImageView.setLayoutY(origView.getLayoutY());

        animationImageView.setVisible(true);

        pane.getChildren().add(animationImageView);

        animationImageView.setTranslateX(0);

        Rotate rotate = new Rotate(
                0,
                0,
                animationImageView.getFitHeight() / 2,
                0,
                Rotate.Y_AXIS
        );

        animationImageView.getTransforms().clear();
        animationImageView.getTransforms().add(rotate);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(rotate.angleProperty(), 0)),
                new KeyFrame(Duration.millis(2000), new KeyValue(rotate.angleProperty(), -90))
        );

        timeline.setOnFinished(e -> {
            pane.getChildren().remove(animationImageView);
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

    public void showMenu() {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.initOwner(stage);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.setTitle("Menu");
        alert.setHeaderText("Menu");

        ButtonType back = new ButtonType("Back");
        ButtonType cheat = new ButtonType("Toggle Cheats");
        ButtonType clear = new ButtonType("Clear");
        ButtonType bg = new ButtonType("Change Background");
        ButtonType quit = new ButtonType("Quit");

        alert.getButtonTypes().setAll(back, cheat, bg, clear, quit);

        Optional<ButtonType> result = alert.showAndWait();

        AdventskalenderApplication.resetMenuState();
        if (result.isEmpty() | result.get() == back) return;

        if (result.get() == cheat) {
            toggleCheat();
        } else if (result.get() == clear) {
            reset();
        } else if(result.get() == bg) {
            cycleBackgrounds();
        } else if (result.get() == quit) {
            Platform.exit();
        }
    }

    public void reset() {
        fh.delete();
        doors.forEach((imageView, door) -> {door.setIsClosed(true);});
        setImagesToDoor();
        bgName = "bg1";
        setBackground(bgName);
    }

    private void setBackground(String name) {
        BackgroundImage bgImage = new BackgroundImage(
                getImageFromName(name),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        pane.setBackground(new Background(bgImage));
    }

    public void toggleCheat() {
        cheat = !cheat;
    }

    public void cycleBackgrounds() {
        IO.println("test");
        switch(bgName) {
            case "bg1": bgName = "bg2";break;
            case "bg2": bgName = "bg3";break;
            case "bg3": bgName = "bg1";
        }

        setBackground(bgName);
        writeToFile();
    }

    public String getBgName() {
        return bgName;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


}
