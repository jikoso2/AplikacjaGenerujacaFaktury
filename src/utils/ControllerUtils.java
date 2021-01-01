package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import sample.PaymentType;


public class ControllerUtils {

    static Paint colorRed = Paint.valueOf("#FF0000");
    static Paint colorTransparent = Paint.valueOf("#FFFFFF");


    public static void coloringNeutralChecked(TextField[] clientInfo) {
        for (TextField textField : clientInfo) {
            coloringNeutral(textField);
        }
    }



    public static boolean fieldChecker(TextField[] clientInfo,boolean isPersonal) {
        for (TextField textField : clientInfo) {
            coloring(textField);
            if(isPersonal)
                coloringNeutral(clientInfo[0]);
        }

        if (checker(clientInfo[0]) && (clientInfo[0].getLength() == 10 || isPersonal) && checker(clientInfo[1]) && checker(clientInfo[2]) && checker(clientInfo[3]) && checker(clientInfo[4]))
            return true;
        else {
            if(clientInfo[0].getLength() != 10 && !isPersonal)
                Dialogs.userInfo("Niepoprawna długość NIP (jest: "+clientInfo[0].getLength() + " oczekiwano: 10)", Alert.AlertType.WARNING);
            else
                Dialogs.userInfo("Proszę wypełnić brakujące pola", Alert.AlertType.WARNING);
            return false;
        }
    }

    private static boolean checker(TextField Field){
        return !Field.getText().equals("");
    }

    private static void coloring(TextField Field){
        if(Field.getText().equals("")) {
            Field.setStyle("-fx-control-inner-background: #" + colorRed.toString().substring(2));
        }
    }
    private static void coloringNeutral(TextField Field){
        Field.setStyle("-fx-control-inner-background: #" + colorTransparent.toString().substring(2));
    }

    public static PaymentType payment(RadioButton selectedPayment){
        switch(selectedPayment.getText()){
            case "Karta":
                return PaymentType.card;
            case "Gotówka":
                return PaymentType.money;
            case "Karta/Gotówka":
                return PaymentType.both;
            default:
                return PaymentType.noSelected;
        }
    }

}