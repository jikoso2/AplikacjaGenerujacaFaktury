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

    @FXML private TextField Amount1;
    @FXML private TextField Item1;
    @FXML private TextField Price1;

    @FXML private TextField Amount2;
    @FXML private TextField Item2;
    @FXML private TextField Price2;

    @FXML private TextField Amount3;
    @FXML private TextField Item3;
    @FXML private TextField Price3;

    @FXML private TextField Amount4;
    @FXML private TextField Item4;
    @FXML private TextField Price4;

    @FXML private TextField Amount5;
    @FXML private TextField Item5;
    @FXML private TextField Price5;

    
    public void onGenerateClicked() throws IOException, DocumentException {

            PdfTableRow Row1 = setRow(Item1,Amount1,Price1,1);
            PdfTableRow Row2 = setRow(Item2,Amount2,Price2,2);
            PdfTableRow Row3 = setRow(Item3,Amount3,Price3,3);
            PdfTableRow Row4 = setRow(Item4,Amount4,Price4,4);
            PdfTableRow Row5 = setRow(Item4,Amount4,Price4,4);


        PDFGenerator generator = new PDFGenerator(Integer.parseInt(factureNumberField.getText()),nameField.getText(),streetField.getText(),postalCodeCityField.getText(),payment(),Integer.parseInt(nipField.getText()), Row1,Row2,Row3,Row4,Row5);
        generator.finalGenerator();
        System.out.println("Wygenerowano");

/*
        if(isCorrectNIPandNumber() && (moneyField.isSelected() || cardField.isSelected())) {
            generator.finalGenerator();
            System.out.println("Generowanie");
        } else
        System.out.println("Czegoś brakuje");

 */




     /*   PDFGenerator gen = new PDFGenerator(123,"Jarosław Czerniak", "ul. Szkolna 25/10","41-400 Mysłowice", false, 1234567,Row1,Row2,Row3,Row4,Row5);
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

    private PdfTableRow setRow(TextField item, TextField amount, TextField price,int number){
        if(item.getText().length() != 0 && amount.getText().length() != 0 && price.getText().length() != 0) {
            return new PdfTableRow(item.getText(), amount.getText(), price.getText(),number);
        }
        else
            return new PdfTableRow(null,"0","0",5);
    }
}
