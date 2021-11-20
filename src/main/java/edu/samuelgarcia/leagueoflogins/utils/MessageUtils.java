package edu.samuelgarcia.leagueoflogins.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class MessageUtils {
    public static void showError(String message, Stage parent) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.initModality(Modality.WINDOW_MODAL);
        error.initOwner(parent);
        error.setTitle("Error");
        error.setHeaderText(message);
        ((Stage) error.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        error.showAndWait();
    }

    public static void showError(String header, String content, Stage parent) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.initModality(Modality.WINDOW_MODAL);
        error.initOwner(parent);
        error.setTitle("Error");
        error.setHeaderText(header);
        error.setContentText(content);
        ((Stage) error.getDialogPane().getScene().getWindow()).setAlwaysOnTop(true);
        error.showAndWait();
    }

    public static Optional<ButtonType> showConfirmation(String message, Stage parent) {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.initModality(Modality.WINDOW_MODAL);
        confirmation.initOwner(parent);
        confirmation.setHeaderText(message);
        return confirmation.showAndWait();
    }

    public static void showInfo(String message, Stage parent) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.initModality(Modality.WINDOW_MODAL);
        info.initOwner(parent);
        info.setTitle("Info");
        info.setHeaderText(message);
        info.showAndWait();
    }
}
