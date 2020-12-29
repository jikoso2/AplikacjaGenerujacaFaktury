package utils;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Main;

public class Dialogs {


    public static void userInfo(String ContentText, Alert.AlertType Type){
        Alert alert = new Alert(Type);
        alert.setTitle("Aplikacja Generująca Fakture");
        alert.setContentText(ContentText);
        alert.setHeaderText(null);
        addIcon(alert);
        alert.showAndWait();
    }

    private static void addIcon(Alert alert){
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        Image image = new Image(Main.class.getResourceAsStream("Pictures/Icon.png"));
        stage.getIcons().add(image);
    }


}
