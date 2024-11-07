package com.talniv.intuit.csv;

import com.talniv.intuit.data.AppConfig;
import com.talniv.intuit.data.AppConfigRepository;
import com.talniv.intuit.data.Player;
import com.talniv.intuit.data.PlayerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.List;


@Component
public class CsvLoader implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(CsvLoader.class);

    private final CsvParser csvParser;
    private final PlayerRepository playerRepository;
    private final AppConfigRepository appConfigRepository;

    @Value("${com.talniv.intuit.csvPath}")
    private String csvPath;

    public CsvLoader(CsvParser csvParser, PlayerRepository playerRepository, AppConfigRepository appConfigRepository) {
        this.csvParser = csvParser;
        this.playerRepository = playerRepository;
        this.appConfigRepository = appConfigRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String contentHash = getFileMD5();

        AppConfig appConfig = appConfigRepository.findTopBy().orElseGet(AppConfig::new);
        if (contentHash.equals(appConfig.getLatestContentHash())) {
            logger.info("Csv content didn't changed- skip loading");
            return;
        }

        logger.info("last content hash different from current (or doesn't exist). new hash: {}", contentHash);
        appConfig.setLatestContentHash(contentHash);
        appConfigRepository.save(appConfig);

        // the parse and load should be in batching.

        // alternatively, can use native postgres csv load, but apparently it's not easy to match the header names in
        // the csv (which are camel case), to the column names hibernate generates (snake case).

        logger.info("parsing csv");
        List<Player> players = csvParser.parseCsv(csvPath);

        logger.info("loading csv content to DB");
        playerRepository.saveAll(players);
    }

    private String getFileMD5() throws Exception {
        byte[] data = Files.readAllBytes(Paths.get(csvPath));
        byte[] hash = MessageDigest.getInstance("MD5").digest(data);
        return new BigInteger(1, hash).toString(16);
    }
}
