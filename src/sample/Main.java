package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Image image = new Image(getClass().getResourceAsStream("Pictures/Icon.png"));
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Generator faktur");
        primaryStage.setScene(new Scene(root, 780, 440));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
