package at.htl.adventskalender;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class Door {
    private Image _imageClosed;
    private Image _imageOpen;
    private boolean _isClosed;

    Door(String nameClosed, String nameOpen, boolean isClosed) {
        _imageClosed = getImageFromName(nameClosed);
        _imageOpen = getImageFromName(nameOpen);
        _isClosed = isClosed;
    }

    private Image getImageFromName(String name) {
        return new Image(new File("src/main/resources/images/" +  name + ".jpeg").toURI().toString());
    }

    public Image getImageClosed() {
        return _imageClosed;
    }
    public Image getImageOpen() {
        return _imageOpen;
    }
    public boolean getIsClosed() { return _isClosed;}
}
