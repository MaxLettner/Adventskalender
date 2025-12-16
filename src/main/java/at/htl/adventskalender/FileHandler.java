package at.htl.adventskalender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public String path = getClass().getResource("/data/states.adv").toExternalForm();

    public void writeToFile() {
        try {
            File file = new File(path);
            FileWriter writer = new FileWriter(path);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
