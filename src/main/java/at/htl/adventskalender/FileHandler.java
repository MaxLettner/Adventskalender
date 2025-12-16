package at.htl.adventskalender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
    public static String path = FileHandler.class.getResource("/data/states.adv").toExternalForm();

    public static void writeToFile() {
        try {
            File file = new File(path);
            if(!file.exists()) {
                file.createNewFile();
            }

            FileWriter writer = new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
