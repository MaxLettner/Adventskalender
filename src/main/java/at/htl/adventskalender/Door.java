package at.htl.adventskalender;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Door {
    private Image _imageClosed;
    private Image _imageOpen;
    private Image _imageDoom;
    private ImageView _imageView;

    Door(ImageView imageView,String nameClosed, String nameOpen) {
        _imageView = imageView;
        _imageClosed = getImageFromName(nameClosed);
        _imageOpen = getImageFromName(nameOpen);
        _imageDoom = getImageFromName("doom");
    }

    private Image getImageFromName(String name) {
        return new Image(new File("src/main/resources/images/" +  name + ".jpeg").toURI().toString());
    }
}
