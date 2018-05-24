package dodontofAPI;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;


public class Resource extends Tab {
    HashMap<String,TextField> formMap;

    Resource(GridPane gridPane) {
        super("resource",gridPane);
        formMap = new HashMap<>();
        ObservableList<Node> list = gridPane.getChildren();
        for(Node node:list) {
            String id = node.getId();
            if (id == null || id.isEmpty()) continue;
            formMap.put(id, (TextField) node);
        }
    }

    private int parseInt(String s) {
        int num;
        try {
            num = Integer.parseInt(s);
        } catch (NumberFormatException e){
            System.out.println("NaN");
            num = -1;
        }
        return num;

    }
    public String getName() {
        return formMap.get("resourceName").getText();
    }
    public int getHP() {
        return parseInt(formMap.get("hp").getText());
    }
    public int getMaxHP() {
        return parseInt(formMap.get("maxhp").getText());
    }
    public int getMP() {
        return parseInt(formMap.get("mp").getText());
    }
    public int getMaxMP() {
        return parseInt(formMap.get("maxmp").getText());
    }
    public int getDef() {
        return parseInt(formMap.get("def").getText());
    }
    public int getMagicDef() {
        return parseInt(formMap.get("magicDef").getText());
    }
    public int getInitiative() {
        return parseInt(formMap.get("initiative").getText());
    }
    public int getMov() {
        return parseInt(formMap.get("mov").getText());
    }

}
