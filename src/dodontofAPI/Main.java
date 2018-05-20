package dodontofAPI;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Paths;

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
