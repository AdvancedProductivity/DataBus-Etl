package org.adp.databus.app.config;

import org.apache.commons.io.FileUtils;
import org.pf4j.spring.SpringPluginManager;
import org.pf4j.update.UpdateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;
import java.nio.file.Paths;

/**
 * Pf4j Spring Config
 *
 * @author zzq
 */
@Configuration
@DependsOn("dataSource")
public class Pf4jSpringConfig {
    private static Logger logger = LoggerFactory.getLogger(Pf4jSpringConfig.class);

    private static final SpringPluginManager MANAGER;
    private static final String userDirectoryPath;

    static {
        logger.info("start set plugin manager");
        userDirectoryPath = FileUtils.getUserDirectoryPath();
        MANAGER = new SpringPluginManager(
                FileUtils.getFile(userDirectoryPath, DataBusConst.APPLICATION_NAME, DataBusConst.PLUGIN_FILE_LOCATION).toPath()
        );
    }

    @Bean
    public SpringPluginManager pluginManager() {
        logger.info("add pf4j spring manager");
        return MANAGER;
    }

    @Bean
    @DependsOn("pluginManager")
    public UpdateManager updateManager() {
        logger.info("add pf4j update manager");
        return new UpdateManager(MANAGER,
                FileUtils.getFile(userDirectoryPath, DataBusConst.APPLICATION_NAME, DataBusConst.PLUGIN_REPOSITORY_LOCATION).toPath()
        );
    }

    @PreDestroy
    public void cleanup() {
        MANAGER.stopPlugins();
    }

}
