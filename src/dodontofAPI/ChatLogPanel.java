package dodontofAPI;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ChatLogPanel {
    private Label label;
    private Button button;
    private ChatMessageDataLog chatMessageDataLog;
    ChatLogPanel(ChatMessageDataLog chatMessageDataLog,EventHandler<ActionEvent> eventHandler) {
        label = new Label(chatMessageDataLog.toString());
        button  = new Button("Get");
        button.setOnAction(eventHandler);
        this.chatMessageDataLog =chatMessageDataLog;
    }
    public Label getLabel(){
        return label;
    }
    public Button getButton() {
        return button;
    }
    public double getMilliTime() { return chatMessageDataLog.getMilliTime();}
    public int getColor() {return chatMessageDataLog.getColor();}
    public int getChannel() {return chatMessageDataLog.getChannel();}
    public String getMessage() {return chatMessageDataLog.getMessage();}
    public String getSenderName() {return chatMessageDataLog.getSenderName();}
    public String getUniqueId() {return chatMessageDataLog.getUniqueId();}
    public String toString() {return chatMessageDataLog.toString();}

}
