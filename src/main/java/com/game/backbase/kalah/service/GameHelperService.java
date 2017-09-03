package com.game.backbase.kalah.service;

import com.game.backbase.kalah.domain.Player;
import com.game.backbase.kalah.domain.Result;
import com.game.backbase.kalah.utility.Cache;
import com.game.backbase.kalah.utility.GameStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * Created by tarunsapra on 02/09/2017.
 */
@Service
public class GameHelperService {

    public Map<String, Player> initializeGame() {
        return Cache.getMapping();
    }

    public Map<String, Player> resetGame() {
        return Cache.resetGame();
    }

    public Result populateResult(Map<String, Player> mapping, String playerName) {
        Result result = new Result();
        StringBuilder resultBuilder = new StringBuilder();
        if (Cache.getGameStatus().equals(GameStatus.Ended)) {
            Player player = mapping.get(playerName);
            Player otherPlayer = Cache.getOtherPlayer(playerName);
            if (player.getKalah().getSize() > otherPlayer.getKalah().getSize()) {
                resultBuilder.append("Game ended, winner is" + player.getName());
            } else {
                resultBuilder.append("Game ended, winner is" + otherPlayer.getName());
            }
            result.setMessage(resultBuilder.toString());
        }
        if (mapping.get("nextMove") != null) {
            result.setMessage("Next move by player " + mapping.get("nextMove").getName());
        }
        for(String player : mapping.keySet()) {
            if(player.contains("Player"))
             result.getPlayers().add(mapping.get(player));
        }

        return result;
    }

    //check for right player, as last seed sown in kalah results in free move
    public boolean isValidCall(String player) {
        if (Cache.getMapping().get("nextMove") != null) {
            if (!Cache.getMapping().get("nextMove").getName().equalsIgnoreCase(player)) {
                return false;
            }
        }

        return true;
    }

    public Result freeMoveViolation() {
        Result result = new Result();
        result.setMessage("Violation of free move");
        return result;
    }
}
