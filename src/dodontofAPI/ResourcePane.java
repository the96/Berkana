package dodontofAPI;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResourcePane implements Initializable{
    private ArrayList<Resource> resourceArrayList;

    public void addResource(Resource resource) {
        resourceArrayList.add(resource);
    }

    public void resourceRenamed(javafx.event.ActionEvent actionEvent) {
        System.out.println(actionEvent.getClass());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resourceArrayList = new ArrayList<>();
    }
}
