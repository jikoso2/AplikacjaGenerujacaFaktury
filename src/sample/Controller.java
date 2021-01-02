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

    @FXML private TextField Amount1,Amount2,Amount3;
    @FXML private TextField Item1,Item2,Item3;
    @FXML private TextField Price1,Price2,Price3;

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
    private int count = 3;

    public void initialize() {
        Amount = new TextField[] {Amount1,Amount2,Amount3};
        Item = new TextField[] {Item1,Item2,Item3};
        Price = new TextField[] {Price1,Price2,Price3};

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
        ControllerUtils.coloringNeutralChecked(clientInfo);
        PdfTableRow[] Rows = ischeckRows();

        if (ControllerUtils.fieldChecker(clientInfo,isSelected)) {
            PDFGenerator generator = new PDFGenerator(Integer.parseInt(factureNumberField.getText()), nameField.getText(), streetField.getText(), postalCodeCityField.getText(), ControllerUtils.payment(selectedPayment), nipField.getText(), Rows, isSelected);
            generator.finalGenerator();
            Dialogs.userInfo("Udało się pomyślnie wygenerować fakture", Alert.AlertType.INFORMATION);
        }
    }


    private PdfTableRow[] ischeckRows() {
        PdfTableRow[] Rows = new PdfTableRow[count];
        int num=1;
        for (int i = 0; i < Rows.length; i++) {
           Rows[i]  = checkRows(Item[i], Amount[i], Price[i],num);
            num++;
        }
        return Rows;
    }


    private PdfTableRow checkRows(TextField item, TextField amount, TextField price, int number){
        if(item.getText().length() != 0 && amount.getText().length() != 0 && price.getText().length() != 0) {
            return new PdfTableRow(item.getText(), amount.getText(), price.getText(),number);
        }
        else
            return new PdfTableRow(null,"0","0",1);
    }

    public void testValue() {
        initializeTEST();
    }

    public void isClear() {
        for (int i = 0; i < Item.length; i++) {
            Item[i].clear();
            Amount[i].clear();
            Price[i].clear();
        }
        for (TextField textField : clientInfo) {
            textField.clear();
        }
    }

    public void addItem() {
        if (count <= 16) {

            if (count == 3)
            initialize();

            TextField newItem = new TextField();
            newItem.setPrefWidth(320);
            TextField newAmount = new TextField();
            newAmount.setPrefWidth(60);
            TextField newPrice = new TextField();
            newPrice.setPrefWidth(90);

            Amount = add_element(Amount, new TextField(), count);
            Item = add_element(Item, new TextField(), count);
            Price = add_element(Price, new TextField(), count);


            centerGridPane.add(Item[count], 1, count + 2);
            centerGridPane.add(Amount[count], 2, count + 2);
            centerGridPane.add(Price[count], 3, count + 2);
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
