package dodontofAPI;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Properties;

public class Controller implements Initializable, LoginDialog.RefCallBack{
    Stage stage;
    Stage loginStage;
    @FXML
    private Label label;
    @FXML
    private Label roomLabel;
    @FXML
    private Button getChatButton;

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane logPane;
    @FXML
    private TabPane resourceTabPane;
    private ArrayList<ChatLogPane> chatLogPane;
    private ArrayList<ResourcePane> resourcePanes;
    private HashMap<Integer, ChatLogPane> chatLogPaneMap;

    private ChatController chatController;
    private ChatLogButtonHandler chatLogButtonHandler;
    private ServerInfo serverInfo;

    private Properties properties;

    private String serverUrl;
    private int roomNum;
    private String password;

    private int rowCount =0;
    private int diceSum;

    private int attackState;
    private int optionState;
    private static final int VOID_SELLECTED = 0;
    private static final int ATTACK_SELLECTED = 1;
    private static final int MAGIC_SELLECTED = 2;
    private static final int PIERCING_SELLECTED = 3;
    private static final int PROTECT_SELLECTED = 1;
    private static final int HEAL_SELLECTED = 2;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatController = new ChatController();
        serverInfo = new ServerInfo("official1");
        chatLogPane = new ArrayList<>();
        scrollPane.setContent(logPane);
        chatLogButtonHandler = new ChatLogButtonHandler();
        chatLogPaneMap = new HashMap<>();
        logPane.getStyleClass().setAll("log-pane-background");
        scrollPane.getStyleClass().setAll("scroll-pane");
        resourceTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        resourcePanes = new ArrayList<>();
        loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.initOwner(stage);
        getChatButton.setDisable(true);
        properties = new Properties();
        try {
            InputStream inputStream = new FileInputStream("serverurl.xml");
            properties.loadFromXML(inputStream);
            System.out.println(properties.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidPropertiesFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        diceSum = 0;
        attackState = 0;
        optionState = 0;
        roomNum = -1;
        serverUrl = "";
        addTab();
    }

    public void setStage (Stage stage) {
        this.stage = stage;
    }

    public void fetchServerInfo() {
        serverInfo.reloadBusyInfo(serverUrl);
        label.setText(serverInfo.getLoginCount() + " / " + serverInfo.getMaxLoginCount() + "\r\n" +
                        serverInfo.getVersion());
    }

    @FXML
    public void openLoginDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginDialog.fxml"));
            GridPane gridPane = loader.load();
            LoginDialog loginDialog = loader.getController();
            loginDialog.setReference(this::setLoginInfo);
            ArrayList<String> serverList = new ArrayList<>();
            for (Object key: properties.keySet()) {
                serverList.add(key.toString());
            }
            loginDialog.setServerName(serverList);
            loginStage.setScene(new Scene(gridPane));
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void readChatLog() {
        if (roomNum < 0) return;
        chatController.reloadChatLog(serverUrl, roomNum, password);
        //chatController.testReloadChatLog();
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
        ChatLogPane clp = new ChatLogPane(chat,chatLogButtonHandler);
        chatLogPane.add(clp);
        chatLogPaneMap.put(clp.getHashCode(),clp);
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
                Resource resource = new Resource(gridPane,new TabChangeHandler(),new ResourceTabButtonHandler());
                resourcePane.setResource(resource);
                resourcePanes.add(resourcePane);
                resourceTabPane.getTabs().add(resource);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        );
    }

    public void clearCheckbox() {
        for (Integer key : chatLogPaneMap.keySet()) {
            chatLogPaneMap.get(key).setSelected(false);
        }
    }

    public Resource getSelectedResource() {
        return (Resource)resourceTabPane.getSelectionModel().getSelectedItem();
    }

    @Override
    public void setLoginInfo(String serverName, String room, String password) {
        if (serverName.isEmpty())return;
        this.serverUrl = properties.getProperty(serverName).toString();
        this.fetchServerInfo();
        if (room.isEmpty()) return;
        this.roomNum = Integer.parseInt(room);
        this.password = password;
        this.loginStage.close();
        this.roomLabel.setText("room No." + roomNum);
        this.getChatButton.setDisable(this.roomNum < 0);
        this.readChatLog();
    }

    class ChatLogButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event){
            ChatLogPane clp = chatLogPaneMap.get(event.getSource().hashCode());
            String message = clp.getMessage();
            // このメッセージがダイスログなら結果を取り出す
            Pattern p = Pattern.compile("[→] [0-9]+$");
            Matcher m = p.matcher(message);
            if(m.find()) {
                int dice = Integer.parseInt(m.group().substring(2));
                diceSum += clp.isSelected()?dice:dice*-1;
                Resource resource = (Resource) resourceTabPane.getSelectionModel().getSelectedItem();
                resource.setDamage(diceSum);
                resource.setOption(diceSum);
            }
        }
    }

    class TabChangeHandler implements EventHandler<Event> {
        @Override
        public void handle(Event event) {
            if(event.getEventType() == Tab.SELECTION_CHANGED_EVENT) {
                Resource resource = getSelectedResource();
                resource.setDamage(diceSum);
                resource.setOption(diceSum);
            }
        }
    }

    class ResourceTabButtonHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Node node = (Node)event.getSource();
            String sourceId = node.getId();
            Resource resource = getSelectedResource();
            switch (sourceId) {
                case "attackButton":
                    setAttack(resource,sourceId);
                    attackState = ATTACK_SELLECTED;
                    break;
                case "magicButton":
                    setAttack(resource,sourceId);
                    attackState = MAGIC_SELLECTED;
                    break;
                case "piercingButton":
                    setAttack(resource,sourceId);
                    attackState = PIERCING_SELLECTED;
                    break;
                case "protectButton":
                    setOption(resource,sourceId);
                    optionState = PROTECT_SELLECTED;
                    break;
                case "healButton":
                    setOption(resource,sourceId);
                    optionState = HEAL_SELLECTED;
                    break;
                case "calcButton": // calcは計算後にresetを実行する
                    calc(attackState,optionState,resource);
                case "resetButton":
                    attackState = VOID_SELLECTED;
                    resource.reset();
                    clearCheckbox();
                    break;
            }
        }

        private void setAttack(Resource resource, String sourceId) {
            resource.settlementDamage();
            resource.setOption(0);
            diceSum = 0;
            clearCheckbox();
            resource.selectedButton(sourceId);
        }

        private void setOption(Resource resource, String sourceId) {
            resource.settlementOption();
            resource.setDamage(0);
            diceSum = 0;
            clearCheckbox();
            resource.selectedButton(sourceId);
        }

        private void calc(int attackState, int optionState, Resource resource) {
            int damage = resource.getDamage();
            int opotion = resource.getOption();
            int hp = resource.getHP();
            int maxhp = resource.getMaxHP();
            switch (attackState) {
                case ATTACK_SELLECTED:
                    damage -= resource.getDef();
                    break;
                case MAGIC_SELLECTED:
                    damage -= resource.getMagicDef();
                    break;
            }
            switch (optionState) {
                case HEAL_SELLECTED:
                    damage -= opotion;
                    break;
                case PROTECT_SELLECTED:
                    damage -= opotion;
                default:
                    if(damage < 0)damage = 0;
                    break;
            }
            hp -= damage;
            hp = Math.min(hp,maxhp);
            resource.setHP(hp);
        }
    }
}

