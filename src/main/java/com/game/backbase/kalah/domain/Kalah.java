package com.game.backbase.kalah.domain;

/**
 * Created by tarunsapra on 02/09/2017.
 */
public class Kalah {

    //number of balls in Kalah
    private Integer size;

    public Kalah(int size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void incrementSize() {
        this.setSize(size + 1);
    }

    public void decrementSize() {
        setSize(size - 1);
    }

    public void addSize(Integer size) {
        this.setSize(getSize() + size);
    }
}
