package edu.samuelgarcia.leagueoflogins.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that facilitates the loading of new Scenes.
 */
public class ScreenLoaderUtils {
    private static final String relativePath = "/edu/samuelgarcia/leagueoflogins/";

    /**
     * Loads a Scene from the given resource into the given Stage.
     * @param resource a String containing the name of the resource file.
     * @param stage the Stage where the scene is going to be loaded.
     * @throws IOException if the FXMLLoader can't load the resource.
     * @throws NullPointerException if the resource can't be found.
     */
    public static void loadScene(String resource, Stage stage) throws IOException, NullPointerException {
        Parent view = FXMLLoader.load(
                Objects.requireNonNull(
                        ScreenLoaderUtils.class.getResource(relativePath + resource)
                )
        );
        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }
}
