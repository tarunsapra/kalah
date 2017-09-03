package com.game.backbase.kalah.utility;

import com.game.backbase.kalah.domain.Kalah;
import com.game.backbase.kalah.domain.Pitt;
import com.game.backbase.kalah.domain.Player;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by tarunsapra on 02/09/2017.
 */
public class Cache {

    public static final String PLAYER = "Player";
    //Declaring in-memory cache for game execution
    private static Map<String, Player> cache =  new WeakHashMap<>();
    private static GameStatus gameStatus = GameStatus.Active;

    //private default constructor in order to ensure non-instantiability
    private Cache (){}

    public static Map<String, Player> getMapping() {
        if(cache.isEmpty()) {
            synchronized (Cache.class) {
                if(cache.isEmpty()) {
                    cache.put("PlayerA", new Player("PlayerA", initializePitts(), new Kalah(0)));
                    cache.put("PlayerB", new Player("PlayerB", initializePitts(), new Kalah(0)));
                }
            }
        }
        return cache;
    }

    private static List<Pitt> initializePitts() {
        List<Pitt> pitts = new ArrayList<>();
        IntStream.range(0,6).forEach(i -> pitts.add(i, new Pitt(6)));
        return pitts;
    }

    public static Map<String,Player> resetGame() {
        cache = new WeakHashMap<>();
        gameStatus = GameStatus.Active;
        return cache;
    }

    public static Player getOtherPlayer(String playerName) {
        for(String player : cache.keySet()) {
            if(player.contains(PLAYER)) {
                if (!player.equalsIgnoreCase(playerName)) {
                    return cache.get(player);
                }
            }
        }
        return new Player();

    }

    public static boolean checkIfTheGameHasEnded(String playerName) {
        Player player = cache.get(playerName);
        int sumOfPits = player.getPitts().stream().mapToInt(p -> p.getSize()).sum();
        //All pits are empty
        if (sumOfPits == 0) {
            gameStatus = GameStatus.Ended;
            return true;
        }

        return false;
    }

    public static GameStatus getGameStatus() {
        return gameStatus;
    }
}
