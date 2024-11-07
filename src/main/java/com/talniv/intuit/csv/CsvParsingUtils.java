package com.talniv.intuit.csv;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.exceptions.CsvParsingException;
import org.apache.commons.csv.CSVRecord;

public class CsvParsingUtils {

    private CsvParsingUtils() {
    }

    static Player recordToPlayer(CSVRecord csvRecord) {
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

    static Integer getIntValue(CSVRecord csvRecord, String col) {
        String val = csvRecord.get(col);
        if (val.isBlank()) {
            return null;
        }

        try {
            return Integer.parseInt(val);
        } catch (NumberFormatException e) {
            throw new CsvParsingException("Invalid value for column " + col + " (expecting int)", e);
        }
    }

}
