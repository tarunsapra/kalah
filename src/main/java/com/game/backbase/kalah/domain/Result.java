package com.game.backbase.kalah.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarunsapra on 03/09/2017.
 */
public class Result {

    private String message ;

    private List<Player> players = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}
