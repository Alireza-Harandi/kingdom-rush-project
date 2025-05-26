package org.example.kingdomrush.model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class DarkknightRaider extends Raider {
    private List<Image> raiderWalking;

    public DarkknightRaider(PointF[] points) {
        super(200, 30, 4, points, false);
        raiderWalking = new ArrayList<>();
        raiderWalking.add(new Image("file:src/main/resources/picture/dark1.png "));
        raiderWalking.add(new Image("file:src/main/resources/picture/dark2.png"));
        setImage(raiderWalking.getFirst());
        setFitHeight(50);
        setFitWidth(50);
        makeTimeLine();
    }

    private void makeTimeLine(){
        getTimelineAnimation().setCycleCount(Timeline.INDEFINITE);
        int frame = 0;

        for (int i = 0; i < raiderWalking.size(); i++) {
            int finalI = i;
            getTimelineAnimation().getKeyFrames().addAll(new KeyFrame(Duration.millis(frame), ev -> {
                setImage(raiderWalking.get(finalI));
            }));
            frame+=100;
        }
    }
}
