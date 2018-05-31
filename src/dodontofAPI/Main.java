package dodontofAPI;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gui.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setHeight(615);
        primaryStage.setMinWidth(500);
        primaryStage.show();
        Controller controller = loader.getController();
        controller.setStage(primaryStage);
        final ObservableList<String> css = scene.getStylesheets();
        if (css != null) {
            css.clear();
        }
        Application.setUserAgentStylesheet("MODENA");
        css.addAll(Paths.get("src/dodontofAPI/style.css").toUri().toString());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
