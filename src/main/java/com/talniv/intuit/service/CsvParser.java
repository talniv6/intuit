package com.talniv.intuit.service;

import com.talniv.intuit.data.Player;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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

    @Value("${com.talniv.intuit.csvPath}")
    private String csvPath;


    public List<Player> parseCsv() throws IOException {
        final List<Player> players = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(csvPath), StandardCharsets.UTF_8))) {
            CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withTrim());

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();
            for (CSVRecord csvRecord : csvRecords) {
                Player player = new Player(
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
                players.add(player);
            }
        }
        return players;
    }

    private static Integer parseInt(String val) {
        if (val.isBlank()) {
            return null;
        }
        return Integer.parseInt(val);
    }

}
