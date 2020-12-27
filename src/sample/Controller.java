package sample;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

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

    @FXML
    private void closeButtonAction() {
        Platform.exit();
    }

    public void initialize() {
        Amount = new TextField[] {Amount1,Amount2,Amount3,Amount4,Amount5};
        Item = new TextField[] {Item1,Item2,Item3,Item4,Item5};
        Price = new TextField[] {Price1,Price2,Price3,Price4,Price5};
    }

    public void initializeTEST() {
        nipField.setText("123333323");
        streetField.setText("Stokrotek");
        postalCodeCityField.setText("41-400 Katowice");
        factureNumberField.setText("1");
        nameField.setText("Jarosław Czerniak");
        Item1.setText("Spiderosy");
        Price1.setText("309");
        Amount1.setText("2");
    }



    public void onGenerateClicked() throws IOException, DocumentException {

        initialize();

        PdfTableRow Row1 = checkRows(Item[0], Amount[0], Price[0],1);
        PdfTableRow Row2 = checkRows(Item[1], Amount[1], Price[1],2);
        PdfTableRow Row3 = checkRows(Item[2], Amount[2], Price[2],3);
        PdfTableRow Row4 = checkRows(Item[3], Amount[3], Price[3],4);
        PdfTableRow Row5 = checkRows(Item[4], Amount[4], Price[4],5);
        PdfTableRow[] Rows = new PdfTableRow[] {Row1,Row2,Row3,Row4,Row5};

        if (alertPayment()) {
            PDFGenerator generator = new PDFGenerator(Integer.parseInt(factureNumberField.getText()), nameField.getText(), streetField.getText(), postalCodeCityField.getText(), payment(), Integer.parseInt(nipField.getText()), Rows);
            generator.finalGenerator();
            System.out.println("Wygenerowano");
            alertGenerate();
        }


    }

    private boolean alertPayment() {
        if (payment() == PaymentType.noSelected) {
            alertPaymentMethod();
            return false;
        } else
            return true;
    }

   /* private boolean isCorrectNIPandNumber(){
        return !nipField.getText().equals("") && !factureNumberField.getText().equals("") && nipField.getText().length() <= 11;
    }
    */



    private PaymentType payment(){
        if (moneyField.isSelected() && cardField.isSelected())
            return PaymentType.both;
        else if (moneyField.isSelected())
            return PaymentType.money;
        else if (cardField.isSelected())
            return PaymentType.card;
        else
            return PaymentType.noSelected;
    }


    private PdfTableRow checkRows(TextField item, TextField amount, TextField price, int number){
        if(item.getText().length() != 0 && amount.getText().length() != 0 && price.getText().length() != 0) {
            return new PdfTableRow(item.getText(), amount.getText(), price.getText(),number);
        }
        else
            return new PdfTableRow(null,"0","0",5);
    }

    public void testValue() {
        initializeTEST();
    }

    private void alertPaymentMethod(){
        Alert alert1 = new Alert((Alert.AlertType.WARNING));
        alert1.setTitle("Brak Metody Płatności");
        alert1.setContentText("Wybierz metode płatności karta/gotówka");
        alert1.setHeaderText(null);
        addIcon(alert1);
        alert1.showAndWait();
    }

    private void alertGenerate(){
        Alert alert2 = new Alert((Alert.AlertType.INFORMATION));
        alert2.setTitle("Aplikacja Generująca Fakture");
        alert2.setContentText("Udało się pomyślnie wygenerować fakture");
        alert2.setHeaderText(null);
        addIcon(alert2);
        alert2.showAndWait();
    }

    private void addIcon(Alert alert){
        Stage stage = (Stage)alert.getDialogPane().getScene().getWindow();
        Image image = new Image(getClass().getResourceAsStream("Pictures/Icon.png"));
        stage.getIcons().add(image);
    }

}
