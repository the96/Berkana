package dodontofAPI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(300);
        primaryStage.setMinWidth(374);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
