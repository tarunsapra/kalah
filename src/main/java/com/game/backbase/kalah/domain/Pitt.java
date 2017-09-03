package com.game.backbase.kalah.domain;

/**
 * Created by tarunsapra on 02/09/2017.
 */
public class Pitt {

    //Number of balls in the pitt
    private Integer size;

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;

    }


    public Pitt(int size) {
        this.size = size;
    }

    public void incrementSize() {
        size = getSize() + 1;
    }

    public void decrementSize() {
       size = getSize() - 1;
    }
}
