package sample;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import utils.ControllerUtils;
import utils.Dialogs;

import java.io.IOException;

public class Controller {

    public GridPane centerGridPane;
    @FXML private TextField streetField;
    @FXML private TextField nameField;
    @FXML private TextField postalCodeCityField;
    @FXML private TextField nipField;
    @FXML private TextField factureNumberField;

    @FXML private TextField Amount1,Amount2,Amount3,Amount4,Amount5;
    @FXML private TextField Item1,Item2,Item3,Item4,Item5;
    @FXML private TextField Price1,Price2,Price3,Price4,Price5;

    @FXML private TextField[] Amount;
    @FXML private TextField[] Item;
    @FXML private TextField[] Price;
    @FXML private TextField[] clientInfo;
    @FXML boolean isSelected;
    @FXML RadioButton selectedPayment;

    @FXML
    private void closeButtonAction() {
        Platform.exit();
    }

    @FXML ToggleGroup paymentType;
    @FXML public RadioButton personalFVAT;
    private int count = 6;

    public void initialize() {
        Amount = new TextField[] {Amount1,Amount2,Amount3,Amount4,Amount5};
        Item = new TextField[] {Item1,Item2,Item3,Item4,Item5};
        Price = new TextField[] {Price1,Price2,Price3,Price4,Price5};
        clientInfo = new TextField[] {nipField,factureNumberField,postalCodeCityField,nameField,streetField};
        isSelected = personalFVAT.isSelected();
        selectedPayment = (RadioButton) paymentType.getSelectedToggle();
    }

    public void initializeTEST() {
        nipField.setText("6233333233");
        streetField.setText("Stokrotek");
        postalCodeCityField.setText("41-400 Katowice");
        factureNumberField.setText("1");
        nameField.setText("Jarosław Czerniak");
        Item1.setText("Szybkie buty");
        Price1.setText("309");
        Amount1.setText("2");
    }



    public void onGenerateClicked() throws IOException, DocumentException {
        initialize();
        ControllerUtils.coloringNeutralChecked(clientInfo);
        PdfTableRow[] Rows = ischeckRows();

        if (ControllerUtils.fieldChecker(clientInfo,isSelected)) {
            PDFGenerator generator = new PDFGenerator(Integer.parseInt(factureNumberField.getText()), nameField.getText(), streetField.getText(), postalCodeCityField.getText(), ControllerUtils.payment(selectedPayment), nipField.getText(), Rows, isSelected);
            generator.finalGenerator();
            System.out.println("Wygenerowano");
            Dialogs.userInfo("Udało się pomyślnie wygenerować fakture", Alert.AlertType.INFORMATION);
        }
    }


    private PdfTableRow[] ischeckRows() {
        PdfTableRow Row1 = checkRows(Item[0], Amount[0], Price[0],1);
        PdfTableRow Row2 = checkRows(Item[1], Amount[1], Price[1],2);
        PdfTableRow Row3 = checkRows(Item[2], Amount[2], Price[2],3);
        PdfTableRow Row4 = checkRows(Item[3], Amount[3], Price[3],4);
        PdfTableRow Row5 = checkRows(Item[4], Amount[4], Price[4],5);
        return new PdfTableRow[] {Row1,Row2,Row3,Row4,Row5};
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

    public void isClear() {
        for (int i = 0; i < 5; i++) {
            Item[i].clear();
            Amount[i].clear();
            Price[i].clear();
            clientInfo[i].clear();
        }
    }

    public void addItem() {
        if (count <= 16) {

            TextField newItem = new TextField();
            newItem.setPrefWidth(320);
            TextField newAmount = new TextField();
            newAmount.setPrefWidth(60);
            TextField newPrice = new TextField();
            newPrice.setPrefWidth(90);

            Amount = add_element(Amount, newAmount, count);
            Item = add_element(Amount, new TextField(), count);
            Price = add_element(Amount, new TextField(), count);


            centerGridPane.add(Item[count], 1, count + 1);
            centerGridPane.add(Amount[count], 2, count + 1);
            centerGridPane.add(Price[count], 3, count + 1);
            count++;
        }

    }

    public static TextField[] add_element(TextField[] myArray, TextField element, int count)
    {
        TextField[] newArray = new TextField[count+1];
        for (int i = 0; i< myArray.length; i++) {
            newArray[i] = myArray[i];
        }
        newArray[count] = element;

        return newArray;
    }

    public void subItem() {
        //centerGridPane.setVisible(false);
    }
}
