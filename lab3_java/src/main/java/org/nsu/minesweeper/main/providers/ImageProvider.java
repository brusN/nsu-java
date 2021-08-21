package org.nsu.minesweeper.main.providers;

import javafx.scene.image.Image;
import org.nsu.minesweeper.main.commandExecutor.CommandFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class ImageProvider {
    public static HashMap<String, Image> getImages(String propertiesFilePath) {
        HashMap<String, Image> images = new HashMap<>();
        Properties properties = new Properties();
        try {
            properties.load(CommandFactory.class.getClassLoader().getResourceAsStream(propertiesFilePath));
            images = new HashMap<>();
            for (var nextProperty : properties.entrySet()) {
                String imageName = nextProperty.getKey().toString();
                Image image = new Image(ImageProvider.class.getClassLoader().getResourceAsStream("img/" + nextProperty.getValue().toString()));
                images.put(imageName, image);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return images;
    }
}
