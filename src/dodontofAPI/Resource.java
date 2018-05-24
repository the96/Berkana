package dodontofAPI;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


import java.util.HashMap;


public class Resource extends Tab {
    HashMap<String,TextField> formMap;

    Resource(GridPane gridPane, EventHandler<Event> eventHandler) {
        super("resource",gridPane);
        formMap = new HashMap<>();
        ObservableList<Node> list = gridPane.getChildren();
        for(Node node:list) {
            String id = node.getId();
            if (id == null || id.isEmpty()) continue;
            formMap.put(id, (TextField) node);
        }
        this.setOnSelectionChanged(eventHandler);
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
    public String getName()  {
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
    public int getDamage() {
        return parseInt(formMap.get("damage").getText());
    }
    public int getOption() {
        return parseInt(formMap.get("option").getText());
    }

    public void setHP(int hp) {
        formMap.get("hp").setText(String.valueOf(hp));
    }
    public void setMaxHP(int maxHP) {
        formMap.get("maxhp").setText(String.valueOf(maxHP));
    }
    public void setMP(int mp) {
        formMap.get("mp").setText(String.valueOf(mp));
    }
    public void setMaxMP(int maxMP) {
        formMap.get("maxmp").setText(String.valueOf(maxMP));
    }
    public void setDef(int def) {
        formMap.get("def").setText(String.valueOf(def));
    }
    public void setMagicDef(int magicDef) {
        formMap.get("magicDef").setText(String.valueOf(magicDef));
    }
    public void setInitiative(int initiative) {
        formMap.get("initiative").setText(String.valueOf(initiative));
    }
    public void setMov(int mov) {
        formMap.get("mov").setText(String.valueOf(mov));
    }
    public void setDamage(int damage) {
        formMap.get("damage").setText(String.valueOf(damage));
    }
    public void setOption(int option) {
        formMap.get("option").setText(String.valueOf(option));
    }
}
