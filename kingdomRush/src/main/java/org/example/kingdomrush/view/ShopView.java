package org.example.kingdomrush.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.kingdomrush.HelloApplication;
import org.example.kingdomrush.controller.PlayerController;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ShopView implements Initializable {

    @FXML
    private Rectangle buyButton;

    @FXML
    private Text buyCoin;

    @FXML
    private Text buyFreeze;

    @FXML
    private Text buyHealth;

    @FXML
    private Text buyLittleBoy;

    @FXML
    private Rectangle confirmButton;

    @FXML
    private Text numberCoin;

    @FXML
    private Text numberFreeze;

    @FXML
    private Text numberGem;

    @FXML
    private Text numberHealth;

    @FXML
    private Text numberLittleBoy;

    @FXML
    private Text resultField;

    @FXML
    private Text buySum;

    //Health,Freeze,Coin,LittleBoy
    private List<Integer> buySpell;

    @FXML
    void buyCoinClicked(MouseEvent event) {
        buySpell.set(2,buySpell.get(2)+1);
        buyCoin.setText(    String.valueOf(Integer.parseInt(buyCoin.getText())+1)   );
        buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())+850)    );
    }

    @FXML
    void buyFreezeClicked(MouseEvent event) {
        buySpell.set(1,buySpell.get(1)+1);
        buyFreeze.setText(    String.valueOf(Integer.parseInt(buyFreeze.getText())+1)   );
        buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())+250)    );
    }

    @FXML
    void buyHealthClicked(MouseEvent event) {
        buySpell.set(0,buySpell.getFirst()+1);
        buyHealth.setText(    String.valueOf(Integer.parseInt(buyHealth.getText())+1)   );
        buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())+350)    );
    }

    @FXML
    void buyLittleBoyClicked(MouseEvent event) {
        buySpell.set(3,buySpell.get(3)+1);
        buyLittleBoy.setText(    String.valueOf(Integer.parseInt(buyLittleBoy.getText())+1)   );
        buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())+999)    );
    }

    @FXML
    void coinDecreaseClicked(MouseEvent event) {
        if(Integer.parseInt(buyCoin.getText())>0) {
            buySpell.set(2, buySpell.get(2) - 1);
            buyCoin.setText(String.valueOf(Integer.parseInt(buyCoin.getText()) - 1));
            buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())-850)    );
        }
    }

    @FXML
    void freezeDecreaseClicked(MouseEvent event) {
        if(Integer.parseInt(buyFreeze.getText())>0) {
            buySpell.set(1, buySpell.get(1) - 1);
            buyFreeze.setText(String.valueOf(Integer.parseInt(buyFreeze.getText()) - 1));
            buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())-250)    );
        }
    }

    @FXML
    void healthDecreaseClicked(MouseEvent event) {
        if(Integer.parseInt(buyHealth.getText())>0) {
            buySpell.set(0, buySpell.getFirst() - 1);
            buyHealth.setText(String.valueOf(Integer.parseInt(buyHealth.getText()) - 1));
            buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())-350)    );
        }
    }

    @FXML
    void littleBoyDecreaseClicked(MouseEvent event) {
        if(Integer.parseInt(buyLittleBoy.getText())>0) {
            buySpell.set(3, buySpell.get(3) - 1);
            buyLittleBoy.setText(String.valueOf(Integer.parseInt(buyLittleBoy.getText()) - 1));
            buySum.setText(    String.valueOf(Integer.parseInt(buySum.getText())-999)    );
        }
    }

    @FXML
    void confirmClicked(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("home-view.fxml"));
        HelloApplication.getStage().setScene(new Scene(fxmlLoader.load()));
    }

    @FXML
    void buyClicked(MouseEvent event) throws SQLException {
        if(!(buySpell.get(0)==0 & buySpell.get(1)==0 & buySpell.get(2)==0 & buySpell.get(3)==0)) {
            boolean result = PlayerController.getPlayerController().buySpell(buySpell);
            if (result) {
                buySum.setText("0");
                buySpell = new ArrayList<>(Arrays.asList(
                        0, 0, 0, 0
                ));
                resultField.setText("done successfully");
                resultField.setFill(Color.web("#2fd4d4cc"));
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> resultField.setText("")
                ));
                timeline.play();

                numberHealth.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[0]);
                numberFreeze.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[1]);
                numberCoin.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[2]);
                numberLittleBoy.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[3]);
                numberGem.setText(String.valueOf(PlayerController.getPlayerController().getPlayer().getGem()));
                buyHealth.setText("0");
                buyFreeze.setText("0");
                buyCoin.setText("0");
                buyLittleBoy.setText("0");
            } else {
                resultField.setText("not enough gem");
                resultField.setFill(Color.web("#ad0000cc"));
                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.seconds(3),
                        ae -> resultField.setText("")
                ));
                timeline.play();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buySpell = new ArrayList<>(Arrays.asList(
                0,0,0,0
        ));

        numberHealth.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[0]);
        numberFreeze.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[1]);
        numberCoin.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[2]);
        numberLittleBoy.setText(PlayerController.getPlayerController().getPlayer().getBag().split(",")[3]);
        numberGem.setText(String.valueOf(PlayerController.getPlayerController().getPlayer().getGem()));
    }
}
