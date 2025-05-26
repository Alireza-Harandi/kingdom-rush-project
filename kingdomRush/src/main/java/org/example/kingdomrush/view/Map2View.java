package org.example.kingdomrush.view;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.kingdomrush.HelloApplication;
import org.example.kingdomrush.controller.PlayerController;
import org.example.kingdomrush.controller.RaiderController;
import org.example.kingdomrush.controller.TowerController;
import org.example.kingdomrush.model.*;
import org.example.kingdomrush.model.MapGame;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Map2View implements Initializable {

    @FXML
    private Rectangle resultRectangle;

    @FXML
    private Button resultButton;

    @FXML
    private Text resultText;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Rectangle coin;

    @FXML
    private Text coinNumber;

    @FXML
    private Rectangle freeze;

    @FXML
    private Rectangle health;

    @FXML
    private Text healthNumber;

    @FXML
    private Rectangle littleBoy;

    @FXML
    private Text numberSpellCoin;

    @FXML
    private Text numberSpellFreeze;

    @FXML
    private Text numberSpellHealth;

    @FXML
    private Text numberSpellLittleBoy;

    @FXML
    private ImageView tower1Image;

    @FXML
    private ImageView tower2Image;

    @FXML
    private ImageView tower3Image;

    @FXML
    private ImageView tower4Image;

    @FXML
    private ImageView tower5Image;

    @FXML
    private ImageView tower6Image;

    @FXML
    private Text waveNumber;

    @FXML
    private ImageView mainImage;

    @FXML
    private ImageView start;

    private int wave = 0;

    @FXML
    void resultButtonClicked(){
        Platform.runLater(()->{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
            try {
                HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @FXML
    void startClicked(){
        MapGame.attack = true;

        start.setVisible(false);
        start.setMouseTransparent(true);
        start.setFocusTraversable(false);

        wave++;
        waveNumber.setText(wave+"/2");

        addRaiderToMap(0);

        for (Tower tower : towwers) {
            Optional<Slot> desiredSlot = map.getSlots().stream().filter(s -> s.get() == tower.getId()).findFirst();
            TowerController.getTowerController().towerWakeUp(anchorPane, desiredSlot.get(), tower, towwers, coinNumber);
        }
    }

    @FXML
    void exitMapClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    private void addRaiderToMap(int i) {
        count = false;
        int maxRaiders;
        if(wave == 1)
            maxRaiders=10;
        else
            maxRaiders=15;

        if (i >= maxRaiders) {
            checkAllRaidersDead();
            count = true;
            return;
        }

        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1;

        Raider raider;
        switch (randomNumber) {
            case 1:
                raider = new GhostRaider(map.getPoints());
                break;
            case 2:
                raider = new DarkknightRaider(map.getPoints());
                break;
            case 3:
                raider = new GoblinRaider(map.getPoints());
                break;
            default:
                raider = new ZombieRaider(map.getPoints());
                break;
        }

        raider.getTimelineAnimation().play();
        raider.setLayoutX(raider.getPoints()[0].x);
        raider.setLayoutY(raider.getPoints()[0].y);
        anchorPane.getChildren().add(raider);
        RaiderController.getRaiderController().move(raider, anchorPane, healthNumber, resultText, resultButton, resultRectangle, start);

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> addRaiderToMap(i + 1));
        pause.play();
    }

    private boolean count = false;

    private void checkAllRaidersDead() {
        Timeline checkTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
            boolean allDead = anchorPane.getChildren().stream().noneMatch(node -> node instanceof Raider);
            if (allDead & count) {
                if(wave==2){
                    for (Node node: anchorPane.getChildren()){
                        node.setMouseTransparent(true);
                        node.setFocusTraversable(false);
                    }

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
                    resultText.setText("WIN");
                    resultText.setFill(Color.web("#58f5de"));

                    if(PlayerController.getPlayerController().getPlayer().getLevel()<3)
                        PlayerController.getPlayerController().getPlayer().setLevel(3);
                    PlayerController.getPlayerController().getPlayer().setGem(PlayerController.getPlayerController().getPlayer().getGem()+500);
                    PlayerController.getPlayerController().editDetail();
                    count = false;
                }

                MapGame.attack = false;

                start.setVisible(true);
                start.setMouseTransparent(false);
                start.setFocusTraversable(true);
            }
        }));
        checkTimeline.setCycleCount(Animation.INDEFINITE);
        checkTimeline.play();
    }

    private List<Slot> slots;
    private List<Tower> towwers;
    private PointF[] points;

    private MapGame map;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        towwers = new ArrayList<>();

        points = new PointF[]{
                new PointF(1266,170),
                new PointF(1083,253),
                new PointF(883,253),
                new PointF(842,163),
                new PointF(762,103),
                new PointF(682,143),
                new PointF(652,253),
                new PointF(452,282),
                new PointF(448,387),
                new PointF(592,400),
                new PointF(730,389),
                new PointF(780,459),
                new PointF(720,528),
                new PointF(620,548),
                new PointF(600,683)
        };

        Image littleBoyImage = new Image("file:src/main/resources/picture/littleBoy.png");
        Image freezeImage = new Image("file:src/main/resources/picture/freeze.png");
        Image healthImage = new Image("file:src/main/resources/picture/health.png");
        Image coinImage = new Image("file:src/main/resources/picture/coin.png");
        Image towerLevel0 = new Image("file:src/main/resources/picture/level0.png");
        Image towerLevel1 = new Image("file:src/main/resources/picture/level1.png");
        Image towerLevel2 = new Image("file:src/main/resources/picture/level2.png");
        Image towerLevel3 = new Image("file:src/main/resources/picture/level3.png");
        Image towerLevel4 = new Image("file:src/main/resources/picture/level4.png");
        Image popup = new Image("file:src/main/resources/picture/popup.png");
        Image update = new Image("file:src/main/resources/picture/upgrade.png");
        Image slot = new Image("file:src/main/resources/picture/slot.png");

        littleBoy.setFill(new ImagePattern(littleBoyImage));
        freeze.setFill(new ImagePattern(freezeImage));
        health.setFill(new ImagePattern(healthImage));
        coin.setFill(new ImagePattern(coinImage));

        slots = new ArrayList<>();
        slots.add(new Slot(tower1Image));
        slots.add(new Slot(tower2Image));
        slots.add(new Slot(tower3Image));
        slots.add(new Slot(tower4Image));
        slots.add(new Slot(tower5Image));
        slots.add(new Slot(tower6Image));

        for (int i=0;i<6;++i) {
            slots.get(i).set(i+1);
            slots.get(i).setLevel(0);
        }

        map = new MapGame(slots, points, 2, 2000, points);

        for (Slot slotTower : map.getSlots()){
            Circle selectTower = new Circle(slotTower.getImageView().getLayoutX()+50, slotTower.getImageView().getLayoutY()+41, 32);
            selectTower.setOpacity(0);
            anchorPane.getChildren().add(selectTower);

            selectTower.setOnMouseClicked(e->{
                selectTower.setFocusTraversable(false);
                selectTower.setMouseTransparent(true);

                if(slotTower.getLevel()==0){

                    ImageView pop = new ImageView(popup);
                    pop.setFitWidth(slotTower.getImageView().getFitWidth());
                    pop.setFitHeight(slotTower.getImageView().getFitHeight());
                    pop.setX(slotTower.getImageView().getLayoutX());
                    pop.setY(slotTower.getImageView().getLayoutY());
                    pop.setFocusTraversable(false);
                    pop.setMouseTransparent(true);

                    Rectangle rectangle1 = new Rectangle(slotTower.getImageView().getLayoutX()+3, slotTower.getImageView().getLayoutY()+1, 32, 29);//archer
                    Rectangle rectangle2 = new Rectangle(slotTower.getImageView().getLayoutX()+3, slotTower.getImageView().getLayoutY()+52, 32, 29);//wizard
                    Rectangle rectangle3 = new Rectangle(slotTower.getImageView().getLayoutX()+73, slotTower.getImageView().getLayoutY()+52, 32, 29);//bomb
                    rectangle1.setOpacity(0);   rectangle2.setOpacity(0);   rectangle3.setOpacity(0);
                    anchorPane.getChildren().addAll(pop,rectangle1,rectangle2,rectangle3);

                    rectangle1.setOnMouseClicked(e1->{
                        anchorPane.getChildren().removeAll(pop,rectangle1,rectangle2,rectangle3);
                        selectTower.setFocusTraversable(true);
                        selectTower.setMouseTransparent(false);

                        ArcherTower archerTower = new ArcherTower(0);
                        int result = TowerController.getTowerController().createTower(Integer.parseInt(coinNumber.getText()), archerTower);
                        if(result>0){
                            if(MapGame.attack)
                                TowerController.getTowerController().towerWakeUp(anchorPane, slotTower, archerTower, towwers, coinNumber);

                            coinNumber.setText(String.valueOf(result));

                            archerTower.setId(slotTower.get());

                            towwers.add(archerTower);
                            slotTower.getImageView().setImage(archerTower);
                            ImageView star = setSizeStar(towerLevel1, slotTower);
                            slotTower.setLevel(1);
                            anchorPane.getChildren().add(star);
                        }
                    });

                    rectangle2.setOnMouseClicked(e1->{
                        anchorPane.getChildren().removeAll(pop,rectangle1,rectangle2,rectangle3);
                        selectTower.setFocusTraversable(true);
                        selectTower.setMouseTransparent(false);

                        WizardTower wizardTower = new WizardTower(0);
                        int result = TowerController.getTowerController().createTower(Integer.parseInt(coinNumber.getText()), wizardTower);
                        if(result>0){
                            if(MapGame.attack)
                                TowerController.getTowerController().towerWakeUp(anchorPane, slotTower, wizardTower, towwers, coinNumber);

                            coinNumber.setText(String.valueOf(result));

                            wizardTower.setId(slotTower.get());

                            towwers.add(wizardTower);
                            slotTower.getImageView().setImage(wizardTower);
                            ImageView star = setSizeStar(towerLevel1, slotTower);
                            slotTower.setLevel(1);
                            anchorPane.getChildren().add(star);
                        }
                    });

                    rectangle3.setOnMouseClicked(e1->{
                        anchorPane.getChildren().removeAll(pop,rectangle1,rectangle2,rectangle3);
                        selectTower.setFocusTraversable(true);
                        selectTower.setMouseTransparent(false);

                        MortalTower mortalTower = new MortalTower(0);
                        int result = TowerController.getTowerController().createTower(Integer.parseInt(coinNumber.getText()), mortalTower);
                        if(result>0){
                            if(MapGame.attack)
                                TowerController.getTowerController().towerWakeUp(anchorPane, slotTower, mortalTower, towwers, coinNumber);

                            coinNumber.setText(String.valueOf(result));

                            mortalTower.setId(slotTower.get());

                            towwers.add(mortalTower);
                            slotTower.getImageView().setImage(mortalTower);
                            ImageView star = setSizeStar(towerLevel1, slotTower);
                            slotTower.setLevel(1);
                            anchorPane.getChildren().add(star);
                        }
                    });

                }
                else {
                    ImageView updateImageView = new ImageView(update);
                    updateImageView.setX(slotTower.getImageView().getLayoutX()-14);
                    updateImageView.setY(slotTower.getImageView().getLayoutY()-34);
                    updateImageView.setFitWidth(121);
                    updateImageView.setFitHeight(135);
                    anchorPane.getChildren().add(updateImageView);

                    Optional<Tower> desiredTower = towwers.stream().filter(t -> t.getId() == slotTower.get()).findFirst();

                    Rectangle rectangle1 = new Rectangle(slotTower.getImageView().getLayoutX()+28, slotTower.getImageView().getLayoutY()-28, 32, 40);//upgrade
                    Rectangle rectangle2 = new Rectangle(slotTower.getImageView().getLayoutX()+33, slotTower.getImageView().getLayoutY()+71, 22, 22);//sell
                    rectangle1.setOpacity(0);   rectangle2.setOpacity(0);
                    anchorPane.getChildren().addAll(rectangle1,rectangle2);

                    rectangle1.setOnMouseClicked(e1->{
                        anchorPane.getChildren().removeAll(rectangle1,rectangle2,updateImageView);
                        selectTower.setFocusTraversable(true);
                        selectTower.setMouseTransparent(false);

                        int count = slotTower.getLevel();
                        int playerLevel = PlayerController.getPlayerController().getPlayer().getLevel();

                        if(count < playerLevel) {
                            int result = TowerController.getTowerController().updateTower(Integer.parseInt(coinNumber.getText()), desiredTower.get());

                            if (result > 0) {
                                slotTower.setLevel(slotTower.getLevel()+1);
                                desiredTower.get().setLevel(slotTower.getLevel());

                                coinNumber.setText(String.valueOf(result));

                                ImageView star;
                                if (count == 1)
                                    star = setSizeStar(towerLevel2, slotTower);
                                else if (count == 2)
                                    star = setSizeStar(towerLevel3, slotTower);
                                else /*if (count == 3)*/
                                    star = setSizeStar(towerLevel4, slotTower);

                                anchorPane.getChildren().add(star);
                            }
                        }
                    });

                    rectangle2.setOnMouseClicked(e1->{
                        anchorPane.getChildren().removeAll(rectangle1,rectangle2,updateImageView);
                        slotTower.getImageView().setImage(slot);


                        int result = TowerController.getTowerController().sellTower(   Integer.parseInt(coinNumber.getText()), desiredTower.get()  );
                        coinNumber.setText(String.valueOf(result));

                        towwers.remove(desiredTower.get());

                        ImageView star = setSizeStar(towerLevel0, slotTower);

                        slotTower.setLevel(0);

                        anchorPane.getChildren().add(star);
                        selectTower.setFocusTraversable(true);
                        selectTower.setMouseTransparent(false);
                    });
                }
            });
        }
    }

    private ImageView setSizeStar(Image image,Slot tower){
        ImageView star = new ImageView(image);
        star.setX(tower.getImageView().getLayoutX()+64);
        star.setY(tower.getImageView().getLayoutY()+30);
        star.setFitWidth(23);
        star.setFitHeight(21);
        return star;
    }

}
