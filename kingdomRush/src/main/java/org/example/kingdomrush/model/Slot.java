package org.example.kingdomrush.model;

import javafx.scene.image.ImageView;
import lombok.Getter;
import lombok.Setter;


public class Slot{
    @Setter
    @Getter
    private int level;
    @Setter
    @Getter
    private ImageView imageView;
    private int id;

    public Slot(ImageView imageView) {
        this.level = 0;
        this.id = 0;
        this.imageView = imageView;
    }

    public int get(){
        return id;
    }

    public void set(int id){
        this.id = id;
    }

}
