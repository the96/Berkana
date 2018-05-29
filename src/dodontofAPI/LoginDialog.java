package dodontofAPI;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginDialog implements Initializable{
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    interface RefCallBack {
        public void setLoginInfo(String login,String password);
    }
    RefCallBack ref;
    public void setReference(RefCallBack reference) {
        ref = reference;
    }
    @FXML
    TextField roomField;
    @FXML
    TextField passwordField;
    @FXML
    public void login() {
        ref.setLoginInfo(roomField.getText(),passwordField.getText());
    }
}
