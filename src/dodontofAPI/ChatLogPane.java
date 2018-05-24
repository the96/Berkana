package dodontofAPI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ChatLogPane {
    static int panelNumber = 0;
    private Label label;
    private CheckBox checkBox;
    private GridPane pane;
    private ChatMessageDataLog chatMessageDataLog;
    ChatLogPane(ChatMessageDataLog chatMessageDataLog, EventHandler<ActionEvent> eventHandler) {
        label = new Label(chatMessageDataLog.toString());
        checkBox = new CheckBox();
        checkBox.setMinWidth(20);
        checkBox.setOnAction(eventHandler);
        this.chatMessageDataLog =chatMessageDataLog;
        pane = new GridPane();
        pane.addRow(0,label,checkBox);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHalignment(HPos.RIGHT);
        pane.getColumnConstraints().addAll(column1,column2);
        pane.getStyleClass().setAll("log-pane" + panelNumber);
        panelNumber = ++panelNumber % 2;
    }
    public Label getLabel(){return label;}
    public GridPane getPane() {return pane;}
    public CheckBox getCheckBox() {return checkBox;}
    public boolean isSelected() {return checkBox.isSelected();}

    public double getMilliTime() { return chatMessageDataLog.getMilliTime();}
    public int getColor() {return chatMessageDataLog.getColor();}
    public int getChannel() {return chatMessageDataLog.getChannel();}
    public String getMessage() {return chatMessageDataLog.getMessage();}
    public String getSenderName() {return chatMessageDataLog.getSenderName();}
    public String getUniqueId() {return chatMessageDataLog.getUniqueId();}
    public String toString() {return chatMessageDataLog.toString();}
    public Integer getHashCode() {return checkBox.hashCode();}
}
