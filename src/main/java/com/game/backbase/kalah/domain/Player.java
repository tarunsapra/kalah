package com.game.backbase.kalah.domain;

import java.util.List;

/**
 * Created by tarunsapra on 02/09/2017.
 */

public class Player {

    private  String name;

    private  List<Pitt> pitts;

    private  Kalah kalah;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPitts(List<Pitt> pitts) {
        this.pitts = pitts;
    }

    public Kalah getKalah() {
        return kalah;
    }

    public void setKalah(Kalah kalah) {
        this.kalah = kalah;
    }

    public Player() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equals(player.name);

    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public Player(String name, List<Pitt> pitts, Kalah kalah) {
        this.name = name;
        this.pitts = pitts;
        this.kalah = kalah;

    }

    public List<Pitt> getPitts() {
        return pitts;
    }


}
