package dodontofAPI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    @FXML
    private Label label;
    @FXML
    private TextField roomNumber;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane logPane;
    @FXML
    private TabPane resourceTabPane;
    private ArrayList<ChatLogPanel> chatLogPanel;
    private HashMap<Integer,ChatLogPanel> chatLogPanelMap;
    //private ResourcePane resourcePane;

    private ChatController chatController;
    private ChatLogButtonHandler chatLogButtonHandler;

    private static final String[] SERVER_URL = {"https://www.taruki.com/DodontoF_srv1/"};

    private ServerInfo serverInfo;

    private int rowCount =0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatController = new ChatController();
        serverInfo = new ServerInfo("official1");
        chatLogPanel = new ArrayList<>();
        scrollPane.setContent(logPane);
        chatLogButtonHandler = new ChatLogButtonHandler();
        chatLogPanelMap = new HashMap<>();
        logPane.getStyleClass().setAll("log-pane-background");
        scrollPane.getStyleClass().setAll("scroll-pane");
        resourceTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        //resourcePane = new ResourcePane();
        addTab();
    }

    public void fetchServerInfo() {
        serverInfo.reloadBusyInfo(SERVER_URL[0]);
        label.setText(serverInfo.getLoginCount() + " / " + serverInfo.getMaxLoginCount() + "\r\n" +
                        serverInfo.getVersion());
    }

    @FXML
    public void readChatLog() {
        if (roomNumber.getText().isEmpty()) return;
        //chatController.reloadChatLog(SERVER_URL[0], Integer.parseInt(roomNumber.getText()), "");
        chatController.testReloadChatLog();
        int i = rowCount + chatController.getChatlog().size();
        for (ChatMessageDataLog chat : chatController.getChatlog()) {
            addLog(chat);
        }
        new Thread(() -> {
            do {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (i != rowCount);
            Platform.runLater(() -> scrollPane.setVvalue(scrollPane.getVmax()));
        }).start();
    }

    public void addLog(ChatMessageDataLog chat) {
        ChatLogPanel clp = new ChatLogPanel(chat,chatLogButtonHandler);
        chatLogPanel.add(clp);
        chatLogPanelMap.put(clp.getHashCode(),clp);
        Platform.runLater(() -> {
            logPane.addRow(rowCount++, clp.getPane());
        });
    }

    public void addTab() {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ResourcePane.fxml"));
                GridPane gridPane= loader.load();
                ResourcePane resourcePane = loader.getController();
                Resource resource = new Resource(gridPane);
                resourcePane.setResource(resource);
                resourceTabPane.getTabs().add(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
    }

    class ChatLogButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
                ChatLogPanel clp = chatLogPanelMap.get(event.getSource().hashCode());
                System.out.println(clp.getMessage());
        }
    }
}

