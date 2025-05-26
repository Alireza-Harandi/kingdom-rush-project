package org.example.kingdomrush.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import org.example.kingdomrush.HelloApplication;
import org.example.kingdomrush.controller.MusicController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartView implements Initializable {

    @FXML
    void exitClicked(MouseEvent event) {
        Platform.exit();
    }

    @FXML
    void startClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginSignup-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MusicController.getMusicController().getMediaPlayer().play();
        MusicController.getMusicController().getMediaPlayer().setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                MusicController.getMusicController().getMediaPlayer().seek(MusicController.getMusicController().getMediaPlayer().getStartTime());
                MusicController.getMusicController().getMediaPlayer().play();
            }
        });
    }
}
