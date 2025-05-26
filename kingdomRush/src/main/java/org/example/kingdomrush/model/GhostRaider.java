package org.example.kingdomrush.model;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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
public class GhostRaider extends Raider {

    private List<Image> raiderWalking;

    public GhostRaider(PointF[] points) {
        super(100, 30, 1, points, true);
        raiderWalking = new ArrayList<>(Arrays.asList(
                new Image("file:src/main/resources/picture/Ghost1.png"),
                new Image("file:src/main/resources/picture/Ghost2.png"),
                new Image("file:src/main/resources/picture/Ghost3.png")
        ));
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
