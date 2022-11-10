package org.example.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login  implements Initializable{
    private static boolean isLoginWindow=true;
    @FXML
    TextField loginField;
    @FXML
    TextField passwordField;
    @FXML
    private Button logIn;
    public boolean checkLogin() {
       if(loginField.getText().equals("")||passwordField.getText().equals("")){
           isLoginWindow=true;
       }else {
           isLoginWindow = false;
       }
        return isLoginWindow;
    }
    public static boolean getIsLoginWindow() {
        return isLoginWindow;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logIn.setOnAction(event->{
            logIn.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root,HelloApplication.getSceneHeight(),HelloApplication.getSceneWidth()));
            if (checkLogin()==false){
                stage.show();
            }else{
                new Alert(Alert.AlertType.WARNING,"Enter login correctly",ButtonType.OK).showAndWait();
            }


        });
    }
}
