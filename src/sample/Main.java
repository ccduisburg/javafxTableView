package sample;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/javaFx/sample.fxml"));
        try {
            loader.load();
        } catch (IOException e) {

        }

        Controller controller = loader.getController();
        Scene scene = new Scene(loader.getRoot(), 800, 600);
        primaryStage.setScene(scene);
        controller.initShortcuts();
        primaryStage.setTitle("Kostenträger Prüfkatalog");
        primaryStage.show();
    }




    public static void main(String[] args) {
        launch(args);
    }
}
