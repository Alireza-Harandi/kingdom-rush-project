package org.example.kingdomrush.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.kingdomrush.HelloApplication;
import org.example.kingdomrush.controller.PlayerController;

import java.io.IOException;
import java.sql.SQLException;

public class LoginSignupView {

    @FXML
    private Button loginButton;

    @FXML
    private Button signupButton;

    @FXML
    private Text errorField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField userNameField;

    @FXML
    private Button confirm;

    @FXML
    void exitClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    private boolean login = true;

    @FXML
    void loginClicked(ActionEvent event) {
        login = true;
        loginButton.setStyle("-fx-background-color: green;");
        signupButton.setStyle("-fx-background-color: red;");
        confirm.setText("Login");
    }

    @FXML
    void signupClicked(ActionEvent event) {
        login = false;
        signupButton.setStyle("-fx-background-color: green;");
        loginButton.setStyle("-fx-background-color: red;");
        confirm.setText("Signup");
    }

    @FXML
    void finishClicked(ActionEvent event) throws SQLException, IOException {
        if(userNameField.getText().equals("") || passwordField.getText().equals("")) {
            errorField.setText("Fill in your information completely");
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    ae -> errorField.setText("")
            ));
            timeline.play();
        }
        else if(login) {
            boolean result = PlayerController.getPlayerController().login(userNameField.getText(), passwordField.getText());
            if(result){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
                HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
            }
            else {
                errorField.setText("Username or password is wrong");
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> errorField.setText("")
                ));
                timeline.play();
            }
        }
        else {//signup
            boolean result = PlayerController.getPlayerController().signup(userNameField.getText(), passwordField.getText());
            if(result){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
                HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
            }
            else {
                errorField.setText("Duplicate username");
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> errorField.setText("")
                ));
                timeline.play();
            }
        }
    }

}
