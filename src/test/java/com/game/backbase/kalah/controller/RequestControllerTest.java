package com.game.backbase.kalah.controller;

import com.game.backbase.kalah.domain.Result;
import com.game.backbase.kalah.service.GameHelperService;
import com.game.backbase.kalah.service.PitService;
import com.game.backbase.kalah.utility.Cache;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tarunsapra on 03/09/2017.
 *
 * Testing the controller layer by mocking the service layer
 */
@RunWith(SpringRunner.class)
@WebMvcTest(RequestController.class)
public class RequestControllerTest {

    @MockBean
    PitService pitService;

    @MockBean
    GameHelperService gameHelperService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void initilizeTest() throws Exception {
        when(gameHelperService.initializeGame()).thenReturn(Cache.getMapping());
        mockMvc.perform(get("/initialize")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).
        andExpect(content().contentType("application/json;charset=UTF-8")).andExpect(jsonPath("$.PlayerB.name",is("PlayerB")));

    }

    @Test
    public void inValidTest() throws Exception {
        when(gameHelperService.isValidCall(anyString())).thenReturn(false);
        Result result = new Result();
        result.setMessage("Violation of free move");
        when(gameHelperService.freeMoveViolation()).thenReturn(result);
        mockMvc.perform(get("/players/PlayerA/pitts/2")).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk()).
                andExpect(jsonPath("$.message",is("Violation of free move")));

    }


}