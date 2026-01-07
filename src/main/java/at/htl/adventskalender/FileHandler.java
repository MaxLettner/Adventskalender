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

    public void writeToFile(FileContent fileContent) {

        try {
            File file = new File(filePath);
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            for(Boolean state : fileContent.doorStates()) {
                if(state) {
                    writer.write("1" + System.lineSeparator());
                }else {
                    writer.write("0" + System.lineSeparator());
                }
            }
            writer.write(fileContent.bgName() + System.lineSeparator());
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileContent readFromFile() {

        List<Boolean> states = new ArrayList<>();
        String bgName = null;
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

            bgName = fileContentInLines[24];

            reader.close();
        }catch(Exception e) {
            e.printStackTrace();
        }


        return new FileContent(states, bgName);
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
            if(fileContentInLines.length != 25) {
                return false;
            }
            for(int i = 0; i < 24;i++) {
                if(fileContentInLines[i].length() != 1) {
                    return false;
                }
                if(!fileContentInLines[i].equals("1") & !fileContentInLines[i].equals("0")) {
                    return false;
                }
            }
            if(!fileContentInLines[24].equals("bg1") & !fileContentInLines[24].equals("bg2") & !fileContentInLines[24].equals("bg3")) {
                return false;
            }
        } catch (Exception e) {
            IO.println(e.getMessage());
            return false;
        }
        return true;
    }

    public void delete() {
        try {
            File file = new File(filePath);
            if(file.exists()) {
                file.delete();
            }
        }catch(Exception e){
            IO.println(e.getMessage());
        }
    }
}
