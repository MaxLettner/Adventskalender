package at.htl.adventskalender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

record FileContent(List<Boolean> doorStates, String bgName) {}

public class AdventskalenderApplication extends Application {
    private Stage primaryStage;
    private static boolean menuState;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        menuState = false;

        loadStage("adventskalender");
    }

    public void loadStage(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdventskalenderApplication.class.getResource(name + "-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        AdventskalenderController controller = fxmlLoader.getController();

        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.C) {
                controller.toggleCheat();
            }
            if(e.getCode() == KeyCode.R) {
                controller.reset();
            }
            if(e.getCode() == KeyCode.ESCAPE & !menuState) {
                menuState = true;
                controller.showMenu();
            }
        });


        primaryStage.setTitle(name);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image(AdventskalenderApplication.class.getResourceAsStream("/images/icon.jpeg")));
        primaryStage.show();

        //TODO: add music or sound effects
        //TODO: add a menu
        //TODO: Comment
    }

    public static void resetMenuState() {
        menuState = false;
    }
}
