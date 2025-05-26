package org.example.kingdomrush;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.example.kingdomrush.view.ZeroStartView;
import java.io.IOException;

public class HelloApplication extends Application {

    @Getter
    @Setter
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        HelloApplication.stage.setWidth(1300);
        HelloApplication.stage.setHeight(725);
        HelloApplication.stage.setResizable(false);

        HelloApplication.stage.setScene(ZeroStartView.video());

        HelloApplication.stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}