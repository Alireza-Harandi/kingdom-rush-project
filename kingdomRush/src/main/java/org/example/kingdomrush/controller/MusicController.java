package org.example.kingdomrush.controller;

import javafx.scene.media.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MusicController {
    private static MusicController musicController;

    private MusicController() {}

    public static MusicController getMusicController(){
        if(musicController==null)
            musicController = new MusicController();
        return musicController;
    }

    private Media media = new Media("https://vgmsite.com/soundtracks/kingdom-rush-soundtrack/ebtlnycjzx/01.%20Main%20Theme.mp3");
    private MediaPlayer mediaPlayer = new MediaPlayer(media);
}

