package org.adp.databus.app.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.io.FileUtils;
import org.pf4j.spring.SpringPluginManager;
import org.pf4j.update.UpdateManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Pf4j Spring Config
 *
 * @author zzq
 */
@Configuration
@DependsOn(value = "dataSource")
public class Pf4jSpringConfig {
    private static Logger logger = LoggerFactory.getLogger(Pf4jSpringConfig.class);

    @Resource
    private DataBusConst dataBusConst;

    @Resource
    private ObjectMapper objectMapper;

    private static SpringPluginManager MANAGER;

    @Bean
    public SpringPluginManager pluginManager() {
        checkFile();
        logger.info("the remote plugin uri is: {}", dataBusConst.remoteRespUri);
        logger.info("add pf4j spring manager");
        if (MANAGER == null) {
            MANAGER = new SpringPluginManager(
                    FileUtils.getFile(
                            DataBusConst.USER_DIR,
                            dataBusConst.applicationName,
                            dataBusConst.pluginInstallLocationFileName
                    ).toPath()
            );
        }
        return MANAGER;
    }

    private void checkFile() {
        final File file = FileUtils.getFile(
                DataBusConst.USER_DIR,
                dataBusConst.applicationName,
                dataBusConst.pluginInstallLocationFileName
        );
        if (!file.exists()) {
            logger.info("create plugin folder");
            file.mkdirs();
        }
        final File pluginRepoDefineFile = FileUtils.getFile(
                DataBusConst.USER_DIR,
                dataBusConst.applicationName,
                dataBusConst.pluginRespFolderDefine
        );
        if (!pluginRepoDefineFile.exists()) {
            logger.info("create plugin repo json config file");
            try {
                final ArrayNode arrayNode = objectMapper.createArrayNode();
                arrayNode.add(genResp(DataBusConst.RESP_REMOTE, dataBusConst.remoteRespUri));
                arrayNode.add(genResp(DataBusConst.RESP_LOCAL,
                                DataBusConst.RESP_LOCATION_PREFIX
                                        + DataBusConst.USER_DIR
                                        + File.separator
                                        + dataBusConst.applicationName
                                        + File.separator
                                        + dataBusConst.pluginRespLocationFileName
                                        + File.separator
                        )
                );
                FileUtils.write(pluginRepoDefineFile, arrayNode.toPrettyString(), StandardCharsets.UTF_8);
            } catch (Exception e) {
                logger.error("create plugin repo json config file error", e);
            }
        }
    }

    private JsonNode genResp(String id, String url) {
        return objectMapper
                .createObjectNode()
                .put(DataBusConst.ID, id)
                .put(DataBusConst.URL, url);
    }

    @Bean
    @DependsOn("pluginManager")
    public UpdateManager updateManager() {
        logger.info("add pf4j update manager");
        return new UpdateManager(MANAGER,
                FileUtils.getFile(
                        DataBusConst.USER_DIR,
                        dataBusConst.applicationName,
                        dataBusConst.pluginRespFolderDefine
                ).toPath()
        );
    }

    @PreDestroy
    public void cleanup() {
        MANAGER.stopPlugins();
    }

}
