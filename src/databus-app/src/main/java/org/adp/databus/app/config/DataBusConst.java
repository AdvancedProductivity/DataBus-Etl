package org.adp.databus.app.config;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author zzq
 */
@Configuration
public class DataBusConst {

    public static String USER_DIR = FileUtils.getUserDirectoryPath();

    @Value("${databus.appName:DataBus}")
    public String applicationName;

    @Value("${databus.databaseFileName:databus.sqlite}")
    public String dataBusFileName;

    @Value("${databus.configFileName:config.json}")
    public String applicationConfigName;

    @Value("${databus.pluginLocation:PluginFolder}")
    public String pluginInstallLocationFileName;

    @Value("${databus.repositories:repositories.json}")
    public String pluginRespFolderLocation;

    @Value("${databus.remoteRespUri:http://localhost:3005/DataBus-Etl/PluginRepo/plugins.json}")
    public String remoteRespUri;
}
