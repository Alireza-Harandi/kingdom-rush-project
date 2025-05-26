package org.example.kingdomrush.model;

import javafx.scene.image.Image;

public class WizardTower extends Tower {

    public WizardTower(int id) {
        super("file:src/main/resources/picture/Wizard.png", 10, 110, 200, 100, 1, id, new Image("file:src/main/resources/picture/arrowWizard.png"), true);
    }
}