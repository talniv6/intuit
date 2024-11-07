package com.talniv.intuit.service;

import com.talniv.intuit.csv.CsvLoader;
import com.talniv.intuit.data.AppConfig;
import com.talniv.intuit.data.AppConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AppStarter implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(AppStarter.class);

    private final CsvLoader csvLoader;
    private final AppConfigRepository appConfigRepository;

    @Value("${com.talniv.intuit.csvPath}")
    private String csvPath;

    @Value("${com.talniv.intuit.csvBatchSize}")
    private int csvBatchSize;

    public AppStarter(CsvLoader csvLoader, AppConfigRepository appConfigRepository) {
        this.csvLoader = csvLoader;
        this.appConfigRepository = appConfigRepository;
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        String contentHash = DigestUtils.getFileMD5(csvPath);

        AppConfig appConfig = appConfigRepository.findTopBy().orElseGet(AppConfig::new);
        if (contentHash.equals(appConfig.getLatestContentHash())) {
            logger.info("Csv content didn't changed- skip loading");
            return;
        }

        logger.info("last content hash different from current (or doesn't exist). new hash: {}", contentHash);
        appConfig.setLatestContentHash(contentHash);
        appConfigRepository.save(appConfig);

        csvLoader.loadCsv(csvPath, csvBatchSize);
    }
}
