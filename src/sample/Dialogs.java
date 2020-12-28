package sample;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Dialogs {

    public static void alertPaymentMethod(){
        Alert alert1 = new Alert((Alert.AlertType.WARNING));
        alert1.setTitle("Brak Metody Płatności");
        alert1.setContentText("Wybierz metode płatności karta/gotówka");
        alert1.setHeaderText(null);
        addIcon(alert1);
        alert1.showAndWait();
    }

    public static void alertGenerate(){
        Alert alert2 = new Alert((Alert.AlertType.INFORMATION));
        alert2.setTitle("Aplikacja Generująca Fakture");
        alert2.setContentText("Udało się pomyślnie wygenerować fakture");
        alert2.setHeaderText(null);
        addIcon(alert2);
        alert2.showAndWait();
    }

    private static void addIcon(Alert alert){
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        Image image = new Image(Dialogs.class.getResourceAsStream("Pictures/Icon.png"));
        stage.getIcons().add(image);
    }

}
