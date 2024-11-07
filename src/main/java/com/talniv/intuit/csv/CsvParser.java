package com.talniv.intuit.csv;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.exceptions.CsvParsingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParser {

    private final Logger logger = LoggerFactory.getLogger(CsvParser.class);

    public List<Player> parseCsv(String csvPath) throws IOException, CsvParsingException {
        final List<Player> players = new ArrayList<>();
        try (FileReader fileReader = new FileReader(csvPath)) {
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Player player;
                try {
                    player = recordToPlayer(csvRecord);
                } catch (Exception e) {
                    logger.error("Error creating player from record: {}", csvRecord);
                    throw new CsvParsingException(e.getMessage(), e);
                }
                players.add(player);
            }
        }
        return players;
    }

    private Player recordToPlayer(CSVRecord csvRecord) {
        return new Player(
                csvRecord.get("playerID"),
                getIntValue(csvRecord, "birthYear"),
                getIntValue(csvRecord, "birthMonth"),
                getIntValue(csvRecord, "birthDay"),
                csvRecord.get("birthCountry"),
                csvRecord.get("birthState"),
                csvRecord.get("birthCity"),
                getIntValue(csvRecord, "deathYear"),
                getIntValue(csvRecord, "deathMonth"),
                getIntValue(csvRecord, "deathDay"),
                csvRecord.get("deathCountry"),
                csvRecord.get("deathState"),
                csvRecord.get("deathCity"),
                csvRecord.get("nameFirst"),
                csvRecord.get("nameLast"),
                csvRecord.get("nameGiven"),
                getIntValue(csvRecord, "weight"),
                getIntValue(csvRecord, "height"),
                csvRecord.get("bats"),
                csvRecord.get("throws"),
                csvRecord.get("debut"),
                csvRecord.get("finalGame"),
                csvRecord.get("retroID"),
                csvRecord.get("bbrefID")
        );
    }

    private Integer getIntValue(CSVRecord csvRecord, String col) {
        String val = csvRecord.get(col);
        if (val.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            logger.error("Invalid value for column {} (expecting int)", col);
            throw e;
        }
    }

}
