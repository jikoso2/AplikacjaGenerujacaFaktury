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


    @FXML private TextField[] Amount;
    @FXML private TextField[] Item;
    @FXML private TextField[] Price;

    public void initialize() {
        Amount = new TextField[] {Amount1,Amount2,Amount3,Amount4,Amount5};
        Item = new TextField[] {Item1,Item2,Item3,Item4,Item5};
        Price = new TextField[] {Price1,Price2,Price3,Price4,Price5};
    }


    public void onGenerateClicked() throws IOException, DocumentException {

        initialize();

        PdfTableRow Row1 = setRow(Item[0], Amount[0], Price[0],1);
        PdfTableRow Row2 = setRow(Item[1], Amount[1], Price[1],2);
        PdfTableRow Row3 = setRow(Item[2], Amount[2], Price[2],3);
        PdfTableRow Row4 = setRow(Item[3], Amount[3], Price[3],4);
        PdfTableRow Row5 = setRow(Item[4], Amount[4], Price[4],5);
        PdfTableRow[] Rows = new PdfTableRow[] {Row1,Row2,Row3,Row4,Row5};

        PDFGenerator generator = new PDFGenerator(Integer.parseInt(factureNumberField.getText()),nameField.getText(),streetField.getText(),postalCodeCityField.getText(),payment(),Integer.parseInt(nipField.getText()),Rows);
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

   /* private boolean isCorrectNIPandNumber(){
        return !nipField.getText().equals("") && !factureNumberField.getText().equals("") && nipField.getText().length() <= 11;
    }
    */

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
