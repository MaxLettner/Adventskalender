package at.htl.adventskalender;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileHandler {

    String dirPath = System.getProperty("user.home") + File.separator + "adv";
    String filePath = System.getProperty("user.home") + File.separator + "adv" + File.separator + "states.adv";

    FileHandler() {
        File directory = new File(dirPath);
        if(!directory.exists()) {
            directory.mkdir();
        }
    }

    public void writeToFile(List<Boolean> states) {

        try {
            File file = new File(filePath);
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            for(Boolean state : states) {
                if(state) {
                    writer.write("1" + System.lineSeparator());
                }else {
                    writer.write("0" + System.lineSeparator());
                }
            }
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Boolean> readFromFile() {

        List<Boolean> states = new ArrayList<>();
        try{
            File file = new File(filePath);
            if(!file.exists()) {
                return null;
            }
            FileReader reader = new FileReader(file);
            String fileContent = reader.readAllAsString();
            String[] fileContentInLines = fileContent.split(System.lineSeparator());

            for(int i = 0;i < 24;i++) {
                states.add(Objects.equals(fileContentInLines[i], "1"));

            }

            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }


        return states;
    }

    public boolean checkFileIntegrity() {
        try {
            File file = new File(filePath);
            if(!file.exists()) {
                return false;
            }
            FileReader reader = new FileReader(file);
            String fileContent = reader.readAllAsString();
            String[] fileContentInLines = fileContent.split(System.lineSeparator());
            if(fileContentInLines.length != 24) {
                return false;
            }
            for(String line : fileContentInLines) {
                if(line.length() != 1) {
                    return false;
                }
                if(!line.equals("1") & !line.equals("0")) {
                    return false;
                }
            }
        } catch (Exception e) {
            IO.println(e.getMessage());
            return false;
        }
        return true;
    }
}
