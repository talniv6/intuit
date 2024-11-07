package com.talniv.intuit.service;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.data.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvLoader implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(CsvLoader.class);

    private final CsvParser csvParser;
    private final PlayerRepository playerRepository;

    public CsvLoader(CsvParser csvParser, PlayerRepository playerRepository) {
        this.csvParser = csvParser;
        this.playerRepository = playerRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // the parse and load should be in batching.

        // alternatively, can use native postgres csv load, but apparently it's not easy to match the header names in
        // the csv (which are camel case), to the column names hibernate generates (snake case).

        logger.info("parsing csv");
        List<Player> players = csvParser.parseCsv();

        logger.info("loading csv content to DB");
        playerRepository.saveAll(players);
    }
}
