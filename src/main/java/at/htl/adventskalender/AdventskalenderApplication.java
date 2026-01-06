package at.htl.adventskalender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;

public class AdventskalenderApplication extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;

        loadStage("adventskalender");
    }

    public void loadStage(String name) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdventskalenderApplication.class.getResource(name + "-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.C) {
                AdventskalenderController.toggleCheat();
            }
        });
        primaryStage.setTitle(name);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();

        //TODO: randomize ImageView ids in adventskalender-view
        //TODO: FileHandler -> make a clear file method
        //TODO: add music or sound effects
        //TODO: add a menu
        //TODO: fix the broken images and find new ones
        //TODO: Comment
    }
}
