package org.example.kingdomrush.model;

import javafx.scene.image.Image;

public class MortalTower extends Tower {
    public MortalTower(int id) {
        super("file:src/main/resources/picture/artillery.png", 10, 110, 200, 125, 1, id, new Image("file:src/main/resources/picture/arrowMortal.png"), false);
    }
}
