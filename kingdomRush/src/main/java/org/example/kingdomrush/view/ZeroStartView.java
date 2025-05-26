package org.example.kingdomrush.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.example.kingdomrush.HelloApplication;
import java.io.File;
import java.io.IOException;

public class ZeroStartView {

    public static Scene video(){
        String videoPath = "C:\\java.code\\finalGame\\finalproject-game-Alirezaharandi-111\\kingdomRush\\src\\main\\resources\\picture\\before.mp4";
        Media media = new Media(new File(videoPath).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);

        mediaView.setFitWidth(1400);
        mediaView.setFitHeight(900);

        Scene scene = new Scene(new StackPane(mediaView));

        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-view.fxml"));
                Scene scene;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                HelloApplication.getStage().setScene(scene);
            }
        });
        mediaPlayer.play();
        return scene;
    }

}
