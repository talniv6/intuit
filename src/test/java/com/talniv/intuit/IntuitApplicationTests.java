package com.talniv.intuit;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talniv.intuit.data.Player;
import com.talniv.intuit.testutils.GetAllResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class IntuitApplicationTests {

    private static final  ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void beforeAll() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

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
        Set<String> expectedIds = Set.of("aardsda01", "aaronha01", "aaronto01", "aasedo01", "abadan01");
        Set<String> expectedNames = Set.of("David", "Hank", "Tommie", "Don", "Andy");

        ResultActions resultActions = mockMvc.perform(get("/api/players"));
        String contentAsString = resultActions.andReturn().getResponse().getContentAsString();
        GetAllResponse getAllResponse = mapper.readValue(contentAsString, GetAllResponse.class);

        Set<String> actualIds = getAllResponse.getContent().stream().map(Player::getPlayerId).collect(Collectors.toSet());
        Set<String> actualNames = getAllResponse.getContent().stream().map(Player::getNameFirst).collect(Collectors.toSet());

        assertThat(getAllResponse.getContent().size()).isEqualTo(5);
        assertThat(actualIds).isEqualTo(expectedIds);
        assertThat(actualNames).isEqualTo(expectedNames);
    }

}
