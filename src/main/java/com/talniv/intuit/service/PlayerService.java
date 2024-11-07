package com.talniv.intuit.service;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.data.PlayerRepository;
import com.talniv.intuit.exceptions.DBFetchingError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final Logger logger = LoggerFactory.getLogger(PlayerService.class);

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Page<Player> getAll(Pageable pageable) {
        try {
            return playerRepository.findAll(pageable);
        } catch (Exception e) {
            logger.error("Error fetching all players from DB: {}", e.getMessage());
            throw new DBFetchingError("Error fetching all players from DB: " + e.getMessage(), e);
        }
    }

    public Optional<Player> getById(String id) {
        try {
            return playerRepository.findById(id);
        } catch (Exception e) {
            logger.error("Error fetching player with id {} from DB: {}", id, e.getMessage());
            throw new DBFetchingError("Error fetching player with id " +id + " from DB: " + e.getMessage(), e);
        }
    }

}
