package com.talniv.intuit.service;

import com.talniv.intuit.csv.CsvLoader;
import com.talniv.intuit.data.AppConfig;
import com.talniv.intuit.data.AppConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.Optional;

@SpringBootTest
class AppStarterTest {

    @MockBean
    private CsvLoader csvLoader;

    @MockBean
    private AppConfigRepository appConfigRepository;

    @Value("${com.talniv.intuit.csvPath}")
    private String csvPath;

    @Value("${com.talniv.intuit.csvBatchSize}")
    private int csvBatchSize;

    @Autowired
    private AppStarter appStarter;

    @BeforeEach
    void setUp() {
        Mockito.clearInvocations(csvLoader, appConfigRepository);
    }

    @Test
    void testFileChanged() throws Exception {
        Optional<AppConfig> appConfig = Optional.of(new AppConfig(123L, "previous hash"));
        Mockito.when(appConfigRepository.findTopBy()).thenReturn(appConfig);

        appStarter.afterPropertiesSet();

        Mockito.verify(appConfigRepository).save(appConfig.get());
        Mockito.verify(csvLoader).loadCsv(csvPath, csvBatchSize);
    }

    @Test
    void testFileUnchanged() throws Exception {
        String contentHash = DigestUtils.getFileMD5(csvPath);
        Optional<AppConfig> appConfig = Optional.of(new AppConfig(123L, contentHash));
        Mockito.when(appConfigRepository.findTopBy()).thenReturn(appConfig);

        appStarter.afterPropertiesSet();

        Mockito.verify(appConfigRepository, Mockito.never()).save(Mockito.any());
        Mockito.verifyNoInteractions(csvLoader);
    }

    @Test
    void testAppConfigNotExists() throws Exception {
        AppConfig expectedAppConfig = new AppConfig();
        expectedAppConfig.setLatestContentHash(DigestUtils.getFileMD5(csvPath));
        Mockito.when(appConfigRepository.findTopBy()).thenReturn(Optional.empty());

        appStarter.afterPropertiesSet();

        Mockito.verify(appConfigRepository).save(expectedAppConfig);
        Mockito.verify(csvLoader).loadCsv(csvPath, csvBatchSize);
    }
}