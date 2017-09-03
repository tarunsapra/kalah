package com.game.backbase.kalah.service;

import com.game.backbase.kalah.domain.Pitt;
import com.game.backbase.kalah.domain.Player;
import com.game.backbase.kalah.utility.Cache;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by tarunsapra on 02/09/2017.
 */
@Service
public class PitService {

    public void process(String playerName, int currentPitt, Map<String, Player> mapping) {

        Player player = mapping.get(playerName);
        Player otherPlayer = Cache.getOtherPlayer(playerName);
        List<Pitt> otherPlayerPitts = otherPlayer.getPitts();
        Pitt startingPitt = player.getPitts().get(currentPitt);
        int startingPittSize = startingPitt.getSize();
        List<Pitt> pitts = player.getPitts();
        int otherPlayerPittCounter = 0;
        boolean isLastSeedInKalah = false;
        boolean isLastSeedInEmptyHouse = false;
        for (int i = 0; i < startingPittSize; i++) {
            currentPitt++;
            if (currentPitt == pitts.size()) {
                player.getKalah().incrementSize();
                isLastSeedInKalah = true;
            } else {
                //If All the pits of other players are sowed, now time to loop-back in current player's set of pits.
                if (otherPlayerPittCounter >= 6) {
                    currentPitt = 0;
                }
                //Normal flow, sow the next pit
                if (currentPitt < pitts.size()) {
                    Pitt incrementPitt = pitts.get(currentPitt);
                    incrementPitt.incrementSize();
                    //last seed sowing
                    if (i == (startingPittSize - 1) && pitts.get(currentPitt).getSize() == 1) {
                        isLastSeedInEmptyHouse = true;
                    }
                }

            }
            //Sow the pits of other player
            if (currentPitt > pitts.size() && otherPlayerPittCounter < 6) {
                otherPlayerPitts.get(otherPlayerPittCounter).incrementSize();
                otherPlayerPittCounter++;
            }

        }
        startingPitt.setSize(0);
        //Last sown seed is in empty house on player's side thus currentSeed and seeds in the opposite house of player are captured in the Kalah/house of the current player
        if (isLastSeedInEmptyHouse) {

            Pitt lastSownSeedPit = pitts.get(currentPitt);
            Pitt oppositePitOfPlayer = otherPlayerPitts.get((otherPlayerPitts.size() - currentPitt) - 1);
            player.getKalah().addSize(lastSownSeedPit.getSize() + oppositePitOfPlayer.getSize());
            lastSownSeedPit.setSize(0);
            oppositePitOfPlayer.setSize(0);
        }

        //Check if the game has ended
        boolean hasTheGameEnded = Cache.checkIfTheGameHasEnded(playerName);
        //Move all the pits balls/sizes oppositePlayer into his kalah
        if (hasTheGameEnded) {
            otherPlayer.getKalah().addSize(otherPlayer.getPitts().stream().mapToInt(p -> p.getSize()).sum());
            otherPlayer.getPitts().forEach( p -> p.setSize(0));
        }
        //Last seed sown is in the Kalah/Store of the current player will result in free move.
        if (isLastSeedInKalah && otherPlayerPittCounter == 0) {
            Cache.getMapping().put("nextMove", player);
        } else {
            Cache.getMapping().put("nextMove", otherPlayer);
        }
    }
}
