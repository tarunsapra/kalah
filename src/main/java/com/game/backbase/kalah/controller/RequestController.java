package com.game.backbase.kalah.controller;

import com.game.backbase.kalah.domain.Player;
import com.game.backbase.kalah.domain.Result;
import com.game.backbase.kalah.service.GameHelperService;
import com.game.backbase.kalah.service.PitService;
import com.game.backbase.kalah.utility.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by tarunsapra on 02/09/2017.
 */
@RestController
@RequestMapping("/")
public class RequestController {

    @Autowired
    GameHelperService gameHelperService;

    @Autowired
    PitService pittService;


    @RequestMapping("/")
    public ResponseEntity<String> getHelloWorld() {
        return new ResponseEntity<String>("Kalah application", HttpStatus.OK);
    }

    @RequestMapping("/initialize")
    public ResponseEntity<Map<String, Player>> initialize() {
        Map<String, Player> players = gameHelperService.initializeGame();
        return new ResponseEntity<Map<String, Player>>(players, HttpStatus.OK);
    }

    @RequestMapping("/reset")
    public Map<String, Player> reset() {
        Map<String, Player> players = gameHelperService.resetGame();
        return players;
    }

    @RequestMapping("/players/{player}/pitts/{pitt}")
    public ResponseEntity<Result> playGame(@PathVariable("player") String player, @PathVariable("pitt") int pitt, HttpServletResponse response) {
        boolean isValidCall = gameHelperService.isValidCall(player);
        if (!isValidCall) {
            Result result = gameHelperService.freeMoveViolation();
            return new ResponseEntity<Result>(result, HttpStatus.OK);
        }
        pittService.process(player, pitt, Cache.getMapping());
        Result result =  gameHelperService.populateResult(Cache.getMapping(), player);
        return new ResponseEntity<Result>(result, HttpStatus.OK);
    }


}
