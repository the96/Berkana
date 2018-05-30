package dodontofAPI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginDialog implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    interface RefCallBack {
        public void setLoginInfo(String serverName, String login, String password);
    }
    RefCallBack ref;
    public void setReference(RefCallBack reference) {
        ref = reference;
    }
    public void setServerName(ArrayList<String> serverList) {
        serverField.getItems().addAll(serverList);
    }
    public String getServerName() {
        return serverField.getValue().toString();
    }
    @FXML
    ComboBox<String> serverField;
    @FXML
    TextField roomField;
    @FXML
    PasswordField passwordField;
    @FXML
    public void login() {
        String serverName = serverField.getSelectionModel().getSelectedItem().toString();
        ref.setLoginInfo(serverName, roomField.getText(), passwordField.getText());
    }
}
