package com.talniv.intuit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talniv.intuit.data.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class IntuitApplicationTests {

    private static final  ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPlayerByIdTest() throws Exception {
        final Player expectedPlayer = new Player("aardsda01", 1981, 12,
                27, "USA", "CO", "Denver",
                null, null, null, "", "", "",
                "David", "Aardsma", "David Allan", 215, 75,
                "R", "R", "2004-04-06", "2015-08-23",
                "aardd001", "aardsda01"
        );

        ResultActions resultActions = mockMvc.perform(get("/api/players/aardsda01"));
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        Player actualPlayer = mapper.readValue(contentAsString, Player.class);

        assertThat(actualPlayer).isEqualTo(expectedPlayer);
    }

    @Test
    void getAllTest() throws Exception {
        final Player expectedPlayer1 = new Player("aardsda01", 1981, 12,
                27, "USA", "CO", "Denver",
                null, null, null, "", "", "",
                "David", "Aardsma", "David Allan", 215, 75,
                "R", "R", "2004-04-06", "2015-08-23",
                "aardd001", "aardsda01"
        );

        final Player expectedPlayer2 = new Player("aaronha01", 1934, 2,
                5, "USA", "AL", "Mobile",
                null, null, null, "", "", "",
                "Hank", "Aaron", "Henry Louis", 180, 72,
                "R", "R", "1954-04-13", "1976-10-03",
                "aaroh101", "aaronha01"
        );

        ResultActions resultActions = mockMvc.perform(get("/api/players"));
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        List<Player> actualPlayers = mapper.readValue(contentAsString, new TypeReference<>() {});

        assertThat(new HashSet<>(actualPlayers)).isEqualTo(new HashSet<>(List.of(expectedPlayer1, expectedPlayer2)));
    }
}
