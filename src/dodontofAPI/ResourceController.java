package dodontofAPI;

import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResourceController extends GridPane implements Initializable{
    private ArrayList<Resource> resourceArrayList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceArrayList = new ArrayList<>();
    }

    public void addResource(Resource resource) {
        resourceArrayList.add(resource);
    }

    public void resourceRenamed(ActionEvent e) {
        System.out.println(e);
    }
}
