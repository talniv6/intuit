package com.talniv.intuit.service;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.data.PlayerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Page<Player> getAll(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    public Optional<Player> getById(String id) {
        return playerRepository.findById(id);
    }

}
