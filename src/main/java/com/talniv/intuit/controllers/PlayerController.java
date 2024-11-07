package com.talniv.intuit.controllers;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.service.PlayerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/api")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path = "/players")
    public @ResponseBody List<Player> getPlayers() {
        return playerService.getAll();
    }

    @GetMapping(path = "/players/{id}")
    public @ResponseBody Optional<Player> getPlayerById(@PathVariable String id) {
        return playerService.getById(id);
    }

}
