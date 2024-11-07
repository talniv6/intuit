package com.talniv.intuit.csv;

import com.talniv.intuit.data.Player;
import com.talniv.intuit.data.PlayerRepository;
import com.talniv.intuit.exceptions.CsvLoadException;
import com.talniv.intuit.service.AppStarter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class CsvLoaderTest {

    @Autowired
    private CsvLoader csvLoader;

    @MockBean
    private PlayerRepository playerRepository;

    @MockBean
    private AppStarter appStarter;

    @Value("${com.talniv.intuit.csvPath}")
    private String csvPath;

    @Captor
    private ArgumentCaptor<List<Player>> captor;

    @Test
    void testBatches() throws IOException {
        csvLoader.loadCsv(csvPath, 2);

        Mockito.verify(playerRepository, Mockito.times(3)).saveAll(captor.capture());

        assertThat(captor.getAllValues().size()).isEqualTo(3);

        // first batch
        assertThat(captor.getAllValues().get(0).size()).isEqualTo(2);
        assertThat(captor.getAllValues().get(0).get(0).getPlayerId()).isEqualTo("aardsda01");
        assertThat(captor.getAllValues().get(0).get(1).getPlayerId()).isEqualTo("aaronha01");

        // second batch
        assertThat(captor.getAllValues().get(1).size()).isEqualTo(2);
        assertThat(captor.getAllValues().get(1).get(0).getPlayerId()).isEqualTo("aaronto01");
        assertThat(captor.getAllValues().get(1).get(1).getPlayerId()).isEqualTo("aasedo01");

        // third batch
        assertThat(captor.getAllValues().get(2).size()).isEqualTo(1);
        assertThat(captor.getAllValues().get(2).get(0).getPlayerId()).isEqualTo("abadan01");
    }

    @Test
    void testBatchSizeBiggerThenTable() throws IOException {
        csvLoader.loadCsv(csvPath, 10);

        Mockito.verify(playerRepository).saveAll(captor.capture());

        assertThat(captor.getAllValues().size()).isEqualTo(1);
        assertThat(captor.getAllValues().get(0).size()).isEqualTo(5);
    }

    @Test
    void testBadBatchSize() {
        Assertions.assertThrows(CsvLoadException.class, () -> csvLoader.loadCsv(csvPath, 0));
    }
}