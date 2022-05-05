package org.adp.databus.app.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.boot.jdbc.metadata.HikariDataSourcePoolMetadata;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * @author zzq
 */
@Configuration
public class PrepareWork {
    private static final Logger logger = LoggerFactory.getLogger(PrepareWork.class);

    @Bean
    public DataSource dataSource() {
        initFileBeforeDataSourceBuild();
        logger.info("init data source");
        HikariConfig config = new HikariConfig();
        String jdbcUrl = "jdbc:sqlite://" + FileUtils.getUserDirectoryPath() + File.separator + DataBusConst.APPLICATION_NAME + File.separator + DataBusConst.DATA_BUS_FILE_NAME;
        logger.info("connect the jdbc: {}", jdbcUrl);
        config.setJdbcUrl(jdbcUrl);
        config.setDriverClassName("org.sqlite.JDBC");
        final HikariDataSource hikariDataSource = new HikariDataSource(config);
        initTable(hikariDataSource);
        return hikariDataSource;
    }

    private void initTable(HikariDataSource hikariDataSource) {
        try {
            final Connection connection = hikariDataSource.getConnection();
            final Statement statement = connection.createStatement();
            List<String> sqlBatch = Arrays.asList(TableCreate.TEST_TABLE);
            for (String batch : sqlBatch) {
                statement.addBatch(batch);
            }
            final int[] ints = statement.executeBatch();
            for (int i = 0; i < sqlBatch.size(); i++) {
                logger.info("execute sql:{} result: {}", sqlBatch.get(i), ints[i]);
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            logger.error("init the database's table error", e);
        }
    }

    public void initFileBeforeDataSourceBuild() {
        logger.info("zzq see application start");
        final String userDirectoryPath = FileUtils.getUserDirectoryPath();
        logger.info("zzq see the path is {}", userDirectoryPath);
        File databaseFile = FileUtils.getFile(userDirectoryPath, DataBusConst.APPLICATION_NAME, DataBusConst.DATA_BUS_FILE_NAME);
        if (!databaseFile.exists()) {
            createDatabaseFile(userDirectoryPath, databaseFile);
        } else {
            logger.info("database file have exist,do not need created new");
        }
    }

    private void createDatabaseFile(String userDirectoryPath, File databaseFile) {
        try {
            FileUtils.write(databaseFile, "", StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.error("create database file error", e);
        }
    }
}

