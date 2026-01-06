package at.htl.adventskalender;

import javafx.scene.image.Image;


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
        return new Image(getClass().getResource("/images/" + name + ".jpeg").toExternalForm());
    }

    public Image getImageClosed() {
        return _imageClosed;
    }
    public Image getImageOpen() {
        return _imageOpen;
    }
    public boolean getIsClosed() { return _isClosed;}
    public void setIsClosed(boolean isClosed) {
        _isClosed = isClosed;
    }
}
