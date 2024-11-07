package com.talniv.intuit.csv;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.data.PlayerRepository;
import com.talniv.intuit.exceptions.CsvLoadException;
import com.talniv.intuit.exceptions.CsvParsingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
public class CsvLoader {

    private final Logger logger = LoggerFactory.getLogger(CsvLoader.class);

    private final PlayerRepository playerRepository;

    public CsvLoader(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Transactional
    public void loadCsv(String csvPath, int csvBatchSize) throws IOException, CsvParsingException {
        // alternatively, can use native postgres csv load, but apparently it's not easy to match the header names in
        // the csv (which are camel case), to the column names hibernate generates (snake case).

        if (csvBatchSize < 1) {
            throw new CsvLoadException("batch size must be at least 1");
        }

        logger.info("loading csv");
        try (FileReader fileReader = new FileReader(csvPath)) {
            CSVParser csvParser = CSVParser.parse(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

            int rowsCounter = 0;
            List<Player> playersBatch = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                Player player;
                try {
                    player = CsvParsingUtils.recordToPlayer(csvRecord);
                } catch (Exception e) {
                    logger.error("Error creating player from record: {} (line {})", csvRecord, rowsCounter);
                    throw new CsvLoadException(e.getMessage(), e);
                }

                playersBatch.add(player);
                rowsCounter++;

                if (rowsCounter % csvBatchSize == 0) {
                    playerRepository.saveAll(playersBatch);
                    playersBatch = new ArrayList<>();
                }
            }

            // last batch
            if (!playersBatch.isEmpty()) {
                playerRepository.saveAll(playersBatch);
            }
        }
        logger.info("done loading csv");
    }
}
