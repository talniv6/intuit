package com.talniv.intuit.service;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.data.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getAll() {
        return playerRepository.findAll();
    }

    public Optional<Player> getById(String id) {
        return playerRepository.findById(id);
    }

}
