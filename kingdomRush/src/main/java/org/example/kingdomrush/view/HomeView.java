package org.example.kingdomrush.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.example.kingdomrush.HelloApplication;
import org.example.kingdomrush.controller.PlayerController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeView implements Initializable {

    @FXML
    private ImageView levelImage;

    @FXML
    private Line line2;

    @FXML
    private Line line3;

    @FXML
    private Line line4;

    @FXML
    private Text numberGem;

    @FXML
    private ImageView station1;

    @FXML
    private ImageView station2;

    @FXML
    private ImageView station3;

    @FXML
    private ImageView station4;

    @FXML
    void bagClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("shop-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    @FXML
    void logoutClicked(MouseEvent event) throws IOException {
        PlayerController.getPlayerController().setPlayer(null);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("start-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    @FXML
    void settingClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("setting-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    @FXML
    void station1Clicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("map1-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    @FXML
    void station2Clicked(MouseEvent event) throws IOException {
        if(PlayerController.getPlayerController().getPlayer().getLevel()>=2){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("map2-view.fxml"));
            HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
        }
    }

    @FXML
    void station3Clicked(MouseEvent event) throws IOException {
        if(PlayerController.getPlayerController().getPlayer().getLevel()>=3){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("map3-view.fxml"));
            HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
        }
    }

    @FXML
    void station4Clicked(MouseEvent event) throws IOException {
        if(PlayerController.getPlayerController().getPlayer().getLevel()>=4){
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("map4-view.fxml"));
            HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image open = new Image("file:src/main/resources/picture/stage.png");
        Image level2 = new Image("file:src/main/resources/picture/two.png");
        Image level3 = new Image("file:src/main/resources/picture/three.png");
        Image level4 = new Image("file:src/main/resources/picture/four.png");
        int playerLevel = 0;
        if(PlayerController.getPlayerController().getPlayer()!=null)
            playerLevel = PlayerController.getPlayerController().getPlayer().getLevel();

        if(playerLevel==2){
            station2.setImage(open);
            line2.setStroke(Paint.valueOf("ff2b00e4"));
            levelImage.setImage(level2);
        }
        else if(playerLevel==3){
            station2.setImage(open);
            station3.setImage(open);
            line2.setStroke(Paint.valueOf("ff2b00e4"));
            line3.setStroke(Paint.valueOf("ff2b00e4"));
            levelImage.setImage(level3);
        }
        else if(playerLevel==4){
            station2.setImage(open);
            station3.setImage(open);
            station4.setImage(open);
            line2.setStroke(Paint.valueOf("ff2b00e4"));
            line3.setStroke(Paint.valueOf("ff2b00e4"));
            line4.setStroke(Paint.valueOf("ff2b00e4"));
            levelImage.setImage(level4);
        }

        if(PlayerController.getPlayerController().getPlayer()!=null)
            numberGem.setText(String.valueOf(PlayerController.getPlayerController().getPlayer().getGem()));
    }
}
