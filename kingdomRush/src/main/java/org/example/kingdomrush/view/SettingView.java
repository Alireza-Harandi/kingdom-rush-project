package org.example.kingdomrush.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.kingdomrush.HelloApplication;
import org.example.kingdomrush.controller.MusicController;
import org.example.kingdomrush.controller.PlayerController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SettingView implements Initializable {

    @FXML
    private ImageView audioImage;

    @FXML
    private Text errorField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField usernameField;

    @FXML
    void audioClicked(MouseEvent event) {
        Image play = new Image("file:src/main/resources/picture/volume.png");
        Image pause = new Image("file:src/main/resources/picture/mute.png");

        if(!PlayerController.getPlayerController().getPlayer().getMute()){
            PlayerController.getPlayerController().getPlayer().setMute(true);
            audioImage.setImage(pause);
            MusicController.getMusicController().getMediaPlayer().setMute(true);
        }
        else {
            PlayerController.getPlayerController().getPlayer().setMute(false);
            audioImage.setImage(play);
            MusicController.getMusicController().getMediaPlayer().setMute(false);
        }
    }

    @FXML
    void confirmClicked(ActionEvent event) throws SQLException, IOException {
        if(usernameField.getText().equals("") || passwordField.getText().equals("")) {
            errorField.setText("Fill in your information completely");
            Timeline timeline = new Timeline(new KeyFrame(
                    Duration.seconds(3),
                    ae -> errorField.setText("")
            ));
            timeline.play();
        }
        else{
            boolean result = PlayerController.getPlayerController().editeProfile(usernameField.getText(), passwordField.getText());
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

    @FXML
    void exitClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(PlayerController.getPlayerController().getPlayer()!=null) {
            usernameField.setText(PlayerController.getPlayerController().getPlayer().getUsername());
            passwordField.setText(PlayerController.getPlayerController().getPlayer().getPassword());
        }
    }
}
