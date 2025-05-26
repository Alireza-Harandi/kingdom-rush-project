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
public class GoblinRaider extends Raider {
    private List<Image> raiderWalking;

    public GoblinRaider(PointF[] points) {
        super(100, 30, 3, points, false);
        raiderWalking = new ArrayList<>(Arrays.asList(
                new Image("file:src/main/resources/picture/goblin1.png"),
                new Image("file:src/main/resources/picture/goblin2.png"),
                new Image("file:src/main/resources/picture/goblin3.png"),
                new Image("file:src/main/resources/picture/goblin4.png")
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
