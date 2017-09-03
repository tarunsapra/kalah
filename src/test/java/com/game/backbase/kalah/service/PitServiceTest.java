package com.game.backbase.kalah.service;

import com.game.backbase.kalah.domain.Pitt;
import com.game.backbase.kalah.domain.Player;
import com.game.backbase.kalah.utility.Cache;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by tarunsapra on 03/09/2017.
 */
@RunWith(JUnit4.class)
public class PitServiceTest {

    private PitService pitService = new PitService();

    private static Map<String, Player> cache;

    @Before
    public void resetCache() {
        cache = Cache.resetGame();
        cache = Cache.getMapping();
    }

    @Test
    public void testSizeOfThirdPit()  {
        pitService.process("PlayerA", 1, PitServiceTest.cache);
        int sizeOfThirdPit = cache.get("PlayerA").getPitts().get(2).getSize();
        assertEquals(sizeOfThirdPit, 7);
    }

    @Test
    public void testSizeOfKalah()  {
        pitService.process("PlayerA", 1, PitServiceTest.cache);
        int sizeOfKalah = cache.get("PlayerA").getKalah().getSize();
        assertEquals(sizeOfKalah, 1);
    }

    @Test
    public void testVariousScenarios()  {
        //call to final pit (pit counting starts from 0 to 5 )
        pitService.process("PlayerA", 5, PitServiceTest.cache);

        //otherPLayer's pitts get incremented as well.
        int sizeOfFirstPitOfOtherPlayer = cache.get("PlayerB").getPitts().get(0).getSize();
        assertEquals(sizeOfFirstPitOfOtherPlayer, 7);

        //Now, after the below call, pit 5 of playerA should have one only 1 ball as in the first call of this test method we emptied final pit.
        pitService.process("PlayerA", 4, PitServiceTest.cache);

        int sizeOfLastPit = cache.get("PlayerA").getPitts().get(5).getSize();
        assertEquals(sizeOfLastPit, 1);

        //After 2 consecutive calls to last and 4th pit thh Kalah/Store of playerA should have 2 balls and first pit of playerB should have 8 balls
        int sizeOfKalah = cache.get("PlayerA").getKalah().getSize();
        assertEquals(sizeOfKalah, 2);

        //two more balls added to pit 0 of playerB after 2 consecutive playerA calls.
        assertEquals(cache.get("PlayerB").getPitts().get(0).getSize().intValue(), 8);

        pitService.process("PlayerB", 0, PitServiceTest.cache);

        Player playerB = cache.get("PlayerB");
        assertEquals(playerB.getPitts().get(0).getSize().intValue(), 0);

        assertEquals(playerB.getKalah().getSize().intValue(), 1);

        pitService.process("PlayerB", 1, PitServiceTest.cache);

        assertEquals(playerB.getKalah().getSize().intValue(), 2);

        //total of 4 executions - thus 6+4 , result  should be 10 for 3rd Pit of playerB
        assertEquals(playerB.getPitts().get(2).getSize().intValue(), 10);

        //set all pits of playerB to Zero to check the scenario of game ended.
        setPittsToZero(playerB.getPitts());

        assertEquals(true, Cache.checkIfTheGameHasEnded("PlayerB"));



    }

    @Test
    public void testLastSeedInEmptyHouse() {

        Player player = cache.get("PlayerA");


        //Now I am setting pit number 2 as size 1 i.e. having one ball and next pit i.e. pit 3 as empty - thus its the case of last seed sown in empty house
        player.getPitts().get(2).setSize(1);
        player.getPitts().get(3).setSize(0);

        assertEquals(0, player.getKalah().getSize().intValue());

        //Now we do the process call
        pitService.process("PlayerA", 2, PitServiceTest.cache);

        //Since the seed is sown in the last pit thus the sum of pit 3 (1) plus the sum of pit 2 of playerB (opposite pit with 6 balls) gets stored in the playerA kalah.
        assertEquals(7, player.getKalah().getSize().intValue());

    }

    private void setPittsToZero(List<Pitt> pitts) {

        pitts.forEach(p -> p.setSize(0));
    }

}