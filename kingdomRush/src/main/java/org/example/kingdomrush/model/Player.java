package org.example.kingdomrush.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player {
    private int id;
    private String username;
    private String password;
    private int level;
    private int gem;
    private String bag;
    private boolean mute;

    public Player(int id, String username, String password, int level, int gem, String bag) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.level = level;
        this.gem = gem;
        this.bag = bag;
        mute = false;
    }

    public Player(){}

    public boolean getMute() {
        return mute;
    }
}

