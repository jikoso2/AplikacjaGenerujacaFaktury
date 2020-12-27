package sample;

import com.itextpdf.text.DocumentException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Generator faktur");
        primaryStage.setScene(new Scene(root, 780, 440));
        primaryStage.show();

    }

    public static void main(String[] args) throws IOException, DocumentException {
        launch(args);
    }
}
