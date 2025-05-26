package org.example.kingdomrush.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Tower extends Image {
    private int power;
    private int updateFee;
    private int rangeFire;
    private int price;
    private int level;
    private int id;
    private Image arrow;
    private boolean air;

    public Tower(String s, int power, int updateFee, int rangeFire, int price, int level, int id, Image arrow, boolean air) {
        super(s);
        this.power = power;
        this.updateFee = updateFee;
        this.rangeFire = rangeFire;
        this.price = price;
        this.level = level;
        this.id = id;
        this.arrow = arrow;
        this.air = air;
    }
}
