package sample;

import com.itextpdf.text.DocumentException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import utils.ControllerUtils;
import utils.Dialogs;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Controller {

    public GridPane centerGridPane;
    public ScrollPane scrollPane;

    @FXML private TextField informationFolderField;
    @FXML private TextField informationNumberField;

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
    @FXML RadioButton selectedPayment;


    @FXML
    private void closeButtonAction() {
        Platform.exit();
    }

    @FXML ToggleGroup paymentType;
    @FXML public RadioButton personalFVAT;
    private int count = 3;
    @FXML

    public void initialize() throws IOException {
        infoActualization();
        Amount = new TextField[] {Amount1,Amount2,Amount3};
        Item = new TextField[] {Item1,Item2,Item3};
        Price = new TextField[] {Price1,Price2,Price3};
        clientInfo = new TextField[] {nipField,factureNumberField,postalCodeCityField,nameField,streetField};
    }

    public void initializeValues() throws IOException {
        nipField.setText("6233333233");
        streetField.setText("ul. Testowa");
        postalCodeCityField.setText("41-400 Testowo");
        factureNumberField.setText("1");
        nameField.setText("Test");
        Item1.setText("Szybkie buty r8");
        Price1.setText("149");
        Amount1.setText("2");
        Item2.setText("Wolne buty r 9.5");
        Price2.setText("259");
        Amount2.setText("1");
        infoActualization();
    }



    public void onGenerateClicked() throws IOException, DocumentException {
        ControllerUtils.coloringNeutralChecked(clientInfo);
        boolean isPersonalSelected = personalFVAT.isSelected();
        selectedPayment = (RadioButton) paymentType.getSelectedToggle();

        boolean checker = ControllerUtils.fieldChecker(clientInfo,isPersonalSelected);
        boolean checker1 =  ControllerUtils.checkItems(Amount,Price);


        if (checker && checker1) {
            PdfTableRow[] Rows = ischeckRows();
            PDFGenerator generator = new PDFGenerator(Integer.parseInt(factureNumberField.getText()), nameField.getText(), streetField.getText(), postalCodeCityField.getText(), ControllerUtils.payment(selectedPayment), nipField.getText(), Rows, isPersonalSelected);
            generator.finalGenerator();
            propertyActualization(factureNumberField.getText());
            infoActualization();
        }
    }

    private void infoActualization() throws IOException {
        Properties defaultProperties = defaultProperties();

        informationNumberField.setText(defaultProperties.getProperty("lastFacture"));
        Path path = Paths.get(defaultProperties.getProperty("url"));
        informationFolderField.setText(path.getFileName().toString());

    }


    private PdfTableRow[] ischeckRows() {
        PdfTableRow[] Rows = new PdfTableRow[count];
        int num=1;
        for (int i = 0; i < Rows.length; i++) {
            Rows[i]  = checkRows(Item[i], Amount[i], Price[i],num);
            if (Rows[i].isGoodItem())
                num++;
        }
        return Rows;
    }


    private PdfTableRow checkRows(TextField item, TextField amount, TextField price, int number){
        if(item.getText().length() != 0 && amount.getText().length() != 0 && price.getText().length() != 0) {
            return new PdfTableRow(item.getText(), amount.getText(), price.getText(),number);
        }
        else
            return new PdfTableRow(null,"0","0",16);
    }

    public void testValue() throws IOException {
        initializeValues();
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

    public void addItem() throws IOException {

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
        System.arraycopy(myArray, 0, newArray, 0, myArray.length);
        newArray[count] = element;

        return newArray;
    }

    public void chooserPath() throws IOException {
        DirectoryChooser dirChooser = new DirectoryChooser();
        File dirchoose = dirChooser.showDialog(centerGridPane.getScene().getWindow());


        try {
            dirchoose.getPath();
            changeProperties(dirchoose);
            infoActualization();
        }
        catch(NullPointerException e)
        {Dialogs.userInfo("Nie wybrano nowego katalogu zapisu", Alert.AlertType.WARNING);
        }

    }

    private void changeProperties(File dirchoose) throws IOException {

        Properties defaultProperties = defaultProperties();

        FileOutputStream out = new FileOutputStream("src/defaultProperties.properties");
        defaultProperties.setProperty("url", dirchoose.getPath());
        defaultProperties.setProperty("lastFacture", defaultProperties.getProperty("lastFacture"));
        defaultProperties.store(out,"");
        out.close();
    }

    private void propertyActualization(String newNumber) throws IOException {
        Properties defaultProperties = defaultProperties();

        FileOutputStream out = new FileOutputStream("src/defaultProperties.properties");
        defaultProperties.setProperty("url", defaultProperties.getProperty("url"));
        defaultProperties.setProperty("lastFacture", newNumber);
        defaultProperties.store(out,"");
        out.close();
    }

    private Properties defaultProperties() throws IOException {
        Properties defaultProperties = new Properties();
        FileInputStream in = new FileInputStream("src/defaultProperties.properties");
        defaultProperties.load(in);
        in.close();
        return defaultProperties;
    }
}
