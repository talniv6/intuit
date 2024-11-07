package com.talniv.intuit.controllers;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.service.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path = "/api")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path = "/players")
    public @ResponseBody Page<Player> getPlayers(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        // not ideal, as hibernate use offset-limit pagination. I found libraries implementing cursor-based pagination,
        // but it's not a trivial integration
        Pageable paging = PageRequest.of(page, size);
        return playerService.getAll(paging);
    }

    @GetMapping(path = "/players/{id}")
    public @ResponseBody Optional<Player> getPlayerById(@PathVariable String id) {
        return playerService.getById(id);
    }

}
