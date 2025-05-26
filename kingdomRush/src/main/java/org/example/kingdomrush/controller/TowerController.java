package org.example.kingdomrush.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.kingdomrush.model.*;

import java.util.*;

public class TowerController {
    private static TowerController towerController;

    private TowerController(){}

    public static TowerController getTowerController(){
        if(towerController==null)
            towerController = new TowerController();
        return towerController;
    }

    public int createTower(int coin, Tower tower){
        return coin-tower.getPrice();
    }

    public int updateTower(int coin, Tower tower){
        tower.setPower(tower.getPower()+20);
        return coin-tower.getUpdateFee();
    }

    public int sellTower(int coin, Tower tower){
        int count = tower.getPrice() + ((tower.getLevel()-1)*tower.getUpdateFee());
        return (count/2)+coin;
    }

    public void sendSton(AnchorPane ap, Node node, Slot slot, List<Tower> towers) {
        Optional<Tower> desiredTower = towers.stream().filter(t -> t.getId() == slot.get()).findFirst();

        if (!desiredTower.isPresent()) {
            return;
        }

        ImageView ston = new ImageView(desiredTower.get().getArrow());
        ston.setFitWidth(20);
        ston.setFitHeight(20);

        double slotCenterX = slot.getImageView().getBoundsInParent().getCenterX();
        double slotCenterY = slot.getImageView().getBoundsInParent().getCenterY();

        ston.setLayoutX(slotCenterX - ston.getFitWidth() / 2);
        ston.setLayoutY(slotCenterY - ston.getFitHeight() / 2);

        Platform.runLater(() -> {
            ap.getChildren().add(ston);
        });

        Timeline timeline = new Timeline();

        double nodeCenterX = node.getBoundsInParent().getCenterX();
        double nodeCenterY = node.getBoundsInParent().getCenterY();

        double shiftX = nodeCenterX - slotCenterX;
        double shiftY = nodeCenterY - slotCenterY;

        KeyValue keyValueX = new KeyValue(ston.translateXProperty(), shiftX);
        KeyValue keyValueY = new KeyValue(ston.translateYProperty(), shiftY);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), keyValueY, keyValueX);
        timeline.getKeyFrames().add(keyFrame);

        timeline.setCycleCount(1);
        timeline.play();
        timeline.setOnFinished(e -> {
            Platform.runLater(() -> {
                ap.getChildren().remove(ston);
                Raider r = (Raider) node;
                if (r.getHealth() < 0) {
                    ap.getChildren().remove(node);
                    r.getTimelineAnimation().stop();
                }
            });
        });
    }


    public void towerWakeUp(AnchorPane ap, Slot slot, Tower tower, List<Tower> towwers, Text coinNumber) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    boolean shotFired = false;
                    List<Node> raiders = new ArrayList<>(ap.getChildren());

                    for (Node node : raiders) {
                        if (node instanceof Raider) {

                            if (tower instanceof MortalTower) {
                                Raider raider = (Raider) node;
                                if (raider.isAir())
                                    continue;
                            }

                            Raider raider = (Raider) node;
                            double tempDistance = Math.sqrt(
                                    Math.pow(slot.getImageView().getBoundsInParent().getCenterY() - node.getBoundsInParent().getCenterY() - 5, 2) +
                                            Math.pow(slot.getImageView().getBoundsInParent().getCenterX() - node.getBoundsInParent().getCenterX() - 5, 2)
                            );

                            if (tempDistance < tower.getRangeFire()) {
                                sendSton(ap, node, slot, towwers);
                                raider.setHealth(raider.getHealth() - tower.getPower());

                                if (!(tower instanceof MortalTower))
                                    shotFired = true;
                            }

                            if (raider.getHealth() < 0) {
                                coinNumber.setText(String.valueOf(Integer.parseInt(coinNumber.getText()) + raider.getLoot()));
                                ap.getChildren().remove(node);
                                raider.getTimelineAnimation().stop();
                            }

                            if (shotFired) {
                                break;
                            }
                        }
                    }
                });
            }
        }, 1000, 1000);
    }




}
