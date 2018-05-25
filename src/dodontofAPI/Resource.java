package dodontofAPI;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;


import java.util.HashMap;


public class Resource extends Tab {
    private HashMap<String,TextField> formMap;
    private HashMap<String,Button> buttonMap;
    private boolean damageIsSetted,optionIsSetted;

    public static final int SUCCESS_SET_STATUS = 1;
    public static final int INVALID_ARRAY_SIZE = 2;
    public static final int INVALID_STATUS = 3;

    public static final int STATUS_SIZE = 8;

    Resource(GridPane gridPane, EventHandler<Event> eventHandler, EventHandler<ActionEvent> actionEventEventHandler) {
        super("resource",gridPane);
        formMap = new HashMap<>();
        buttonMap = new HashMap<>();
        ObservableList<Node> list = gridPane.getChildren();
        for(Node node:list) {
            String id = node.getId();
            if (id == null || id.isEmpty()) continue;
            if (node.getClass().getName().indexOf("TextField") >= 0) {
                formMap.put(id, (TextField) node);
            } else if (node.getClass().getName().indexOf("Button") >= 0) {
                buttonMap.put(id,(Button) node);
                ((Button) node).setOnAction(actionEventEventHandler);
            }
        }
        switch(this.setStatus(new int[STATUS_SIZE])){
            case SUCCESS_SET_STATUS:
                System.out.println("success_set_status");
                break;
            case INVALID_ARRAY_SIZE:
                System.out.println("invalid array size");
                break;
            case INVALID_STATUS:
                System.out.println("invalid status");
                break;
        }
        this.reset();
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

    public int setStatus(int[] arrayStatus) {
        if (arrayStatus.length + 3 != formMap.size()) return INVALID_ARRAY_SIZE;
        int i = 0;
        for (String key : formMap.keySet()) {
            if ("resourceName".equals(key) || "damage".equals(key) || "option".equals(key))continue;
            int status = arrayStatus[i];
            if (status < 0) return INVALID_STATUS;
            formMap.get(key).setText(String.valueOf(status));
            if (i >= arrayStatus.length) {
                arrayStatus[i] = 0;
            } else {
                i++;
            }
        }
        return SUCCESS_SET_STATUS;
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
        if (!damageIsSetted) {
            formMap.get("damage").setText(String.valueOf(damage));
        }
    }
    public void setOption(int option) {
        if (!optionIsSetted) {
            formMap.get("option").setText(String.valueOf(option));
        }
    }
    public void settlementDamage() {
        damageIsSetted = true;
    }
    public void settlementOption() {
        optionIsSetted = true;
    }
    public void reset() {
        formMap.get("damage").setText("0");
        formMap.get("option").setText("0");
        damageIsSetted = false;
        optionIsSetted = false;
    }
}
