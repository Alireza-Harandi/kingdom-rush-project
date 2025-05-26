package org.example.kingdomrush.model;

import javafx.scene.image.Image;

public class ArcherTower extends Tower {
    public ArcherTower(int id) {
        super("file:src/main/resources/picture/archer.png", 200, 110, 200, 70, 1, id, new Image("file:src/main/resources/picture/arrowArcher.png"), true);
    }
}
