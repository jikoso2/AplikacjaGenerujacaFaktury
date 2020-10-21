package sample;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {

    @FXML private RadioButton cardField;
    @FXML private RadioButton moneyField;
    @FXML private TextField streetField;
    @FXML private TextField nameField;
    @FXML private TextField postalCodeCityField;
    @FXML private TextField nipField;
    @FXML private TextField factureNumberField;
    
    public void onGenerateClicked() throws IOException, DocumentException {
        PDFGenerator generator = new PDFGenerator(Integer.parseInt(factureNumberField.getText()),nameField.getText(),streetField.getText(),postalCodeCityField.getText(),payment(),Integer.parseInt(nipField.getText()));
        if(isCorrectNIPandNumber() && (moneyField.isSelected() || cardField.isSelected())) {
            generator.finalGenerator();
            System.out.println("Generowanie");
        } else
        System.out.println("Czegoś brakuje");


        /*
        PDFGenerator gen = new PDFGenerator(123,"Jarosław Czerniak", "ul. Szkolna 25/10","41-400 Mysłowice", false, 1234567);
        gen.finalGenerator();
        System.out.println("Wygenerowano");

         */
    }

    private boolean isCorrectNIPandNumber(){
        return !nipField.getText().equals("") && !factureNumberField.getText().equals("") && nipField.getText().length() <= 11;
    }

    @FXML
    private void closeButtonAction() {
        Platform.exit();
    }

    private Boolean payment(){
        if (moneyField.isSelected() && cardField.isSelected())
            return null;
        else if (moneyField.isSelected())
            return false;
        else if (cardField.isSelected())
            return true;
        else
            System.out.println("Tu bd error");
        return null;
    }
}
