package zti.projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application{

    /** run app **/
    public static void main(String... args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainLayout.fxml"));
        Parent root = loader.load();
        GUI gui = loader.getController();
        gui.init();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/style.css");

        primaryStage.setScene(scene);
        primaryStage.setTitle("OKE2018 Challenge - Task 1");
        primaryStage.show();
    }

}
