package dodontofAPI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

public class ChatLogPanel {
    static int panelNumber = 0;
    private Label label;
    private Button button;
    private GridPane pane;
    private ChatMessageDataLog chatMessageDataLog;
    ChatLogPanel(ChatMessageDataLog chatMessageDataLog,EventHandler<ActionEvent> eventHandler) {
        label = new Label(chatMessageDataLog.toString());
        button  = new Button("Get");
        button.setOnAction(eventHandler);
        button.setMinWidth(35);
        this.chatMessageDataLog =chatMessageDataLog;
        pane = new GridPane();
        pane.addRow(0,label,button);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.ALWAYS);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(39);
        pane.getColumnConstraints().addAll(column1,column2);
        pane.getStyleClass().setAll("log-pane" + panelNumber);
        panelNumber = ++panelNumber % 2;
    }
    public Label getLabel(){
        return label;
    }
    public Button getButton() {
        return button;
    }
    public GridPane getPane() {
        return pane;
    }
    public double getMilliTime() { return chatMessageDataLog.getMilliTime();}
    public int getColor() {return chatMessageDataLog.getColor();}
    public int getChannel() {return chatMessageDataLog.getChannel();}
    public String getMessage() {return chatMessageDataLog.getMessage();}
    public String getSenderName() {return chatMessageDataLog.getSenderName();}
    public String getUniqueId() {return chatMessageDataLog.getUniqueId();}
    public String toString() {return chatMessageDataLog.toString();}

}
