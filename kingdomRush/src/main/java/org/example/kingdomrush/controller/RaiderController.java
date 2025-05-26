package org.example.kingdomrush.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.kingdomrush.model.MapGame;
import org.example.kingdomrush.model.Raider;

public class RaiderController {
    private static RaiderController raiderController;

    private RaiderController(){}

    public static RaiderController getRaiderController(){
        if(raiderController==null)
            raiderController = new RaiderController();
        return raiderController;
    }

    public void move(Raider raider, AnchorPane anchorPane, Text healthNumber, Text resultText, Button resultButton, Rectangle resultRectangle, ImageView start){
        Timeline timeline = new Timeline();

        float initialX = raider.getPoints()[0].x + 30;
        float initialY = raider.getPoints()[0].y + 20;

        int num = raider.getPoints().length;

        Duration duration = Duration.seconds(5);

        for (int i = 1; i < num; i++) {
            double shiftX = raider.getPoints()[i].x - initialX/*-25*/;
            double shiftY = raider.getPoints()[i].y - initialY/*-25*/;

            KeyValue keyValueX = new KeyValue(raider.translateXProperty(), shiftX);
            KeyValue keyValueY = new KeyValue(raider.translateYProperty(), shiftY);

            KeyFrame keyFrame = new KeyFrame(duration.multiply(i),  keyValueY,keyValueX);
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setCycleCount(1);
        timeline.play();

        timeline.setOnFinished(e -> {
            if (raider.getHealth() > 0) {
                int currentHealth = Integer.parseInt(healthNumber.getText());
                healthNumber.setText(String.valueOf(currentHealth - 1));
                anchorPane.getChildren().remove(raider);
                raider.getTimelineAnimation().stop();
                currentHealth = Integer.parseInt(healthNumber.getText());
                if(currentHealth==0){
                    for (Node node: anchorPane.getChildren()){
                        node.setMouseTransparent(true);
                        node.setFocusTraversable(false);
                    }

                    MapGame.attack = false;

                    anchorPane.getChildren().remove(start);

                    anchorPane.getChildren().remove(resultButton);
                    anchorPane.getChildren().remove(resultRectangle);
                    anchorPane.getChildren().remove(resultText);

                    anchorPane.getChildren().add(resultRectangle);
                    anchorPane.getChildren().add(resultButton);
                    anchorPane.getChildren().add(resultText);

                    resultRectangle.setVisible(true);

                    resultButton.setVisible(true);
                    resultButton.setFocusTraversable(true);
                    resultButton.setMouseTransparent(false);

                    resultText.setVisible(true);
                }
            }
        });
    }


}
