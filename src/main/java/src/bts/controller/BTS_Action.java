package src.bts.controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class BTS_Action {
    public static void showMessage(Stage stage, Alert.AlertType alertType, String title, String message) {
        Alert bts_Alert = new Alert(alertType);
        bts_Alert.initOwner(stage);
        bts_Alert.setContentText(message);
        bts_Alert.setTitle(title);
        bts_Alert.showAndWait();
    }
}
