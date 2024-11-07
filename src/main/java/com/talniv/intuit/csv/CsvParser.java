package com.talniv.intuit.csv;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.exceptions.CsvParsingException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvParser {

    private final Logger logger = LoggerFactory.getLogger(CsvParser.class);

    @Value("${com.talniv.intuit.csvPath}")
    private String csvPath;


    public List<Player> parseCsv() throws IOException, CsvParsingException {
        final List<Player> players = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {
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

    private static Player recordToPlayer(CSVRecord csvRecord) {
        return new Player(
                csvRecord.get("playerID"),
                parseInt(csvRecord.get("birthYear")),
                parseInt(csvRecord.get("birthMonth")),
                parseInt(csvRecord.get("birthDay")),
                csvRecord.get("birthCountry"),
                csvRecord.get("birthState"),
                csvRecord.get("birthCity"),
                parseInt(csvRecord.get("deathYear")),
                parseInt(csvRecord.get("deathMonth")),
                parseInt(csvRecord.get("deathDay")),
                csvRecord.get("deathCountry"),
                csvRecord.get("deathState"),
                csvRecord.get("deathCity"),
                csvRecord.get("nameFirst"),
                csvRecord.get("nameLast"),
                csvRecord.get("nameGiven"),
                parseInt(csvRecord.get("weight")),
                parseInt(csvRecord.get("height")),
                csvRecord.get("bats"),
                csvRecord.get("throws"),
                csvRecord.get("debut"),
                csvRecord.get("finalGame"),
                csvRecord.get("retroID"),
                csvRecord.get("bbrefID")
        );
    }

    private static Integer parseInt(String val) {
        if (val.isBlank()) {
            return null;
        }
        return Integer.parseInt(val);
    }

}
