package org.example.kingdomrush.model;

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class Raider extends ImageView {
    private int health;
    private int speed;
    private int loot;
    private PointF[] points;
    private boolean air;
    private Timeline timelineAnimation;

    public Raider(int health, int speed, int loot, PointF[] points, boolean air) {
        this.health = health;
        this.speed = speed;
        this.loot = loot;
        this.points = points;
        this.air = air;
        timelineAnimation = new Timeline();
    }
}

