package org.example.kingdomrush.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MapGame {
    private final List<Slot> slots;
    private final PointF[] points;
    private final int waves;
    private final int coin;
    public static boolean attack = false;
    private final PointF[] points2;

    public MapGame(List<Slot> slots, PointF[] points, int waves, int coin, PointF[] points2) {
        this.slots = slots;
        this.points = points;
        this.waves = waves;
        this.coin = coin;
        this.points2 = points2;
        attack = false;
    }
}
