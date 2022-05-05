package org.adp.databus.app.config;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author zzq
 */
@Configuration
public class DataBusConst {

    public static String USER_DIR = FileUtils.getUserDirectoryPath();

    public static final String ID = "id";
    public static final String URL = "url";
    public static final String RESP_LOCAL = "LocalFile";
    public static final String RESP_REMOTE = "remote";
    public static final String RESP_LOCATION_PREFIX = "file:";

    @Value("${databus.appName:DataBus}")
    public String applicationName;

    @Value("${databus.databaseFileName:databus.sqlite}")
    public String dataBusFileName;

    @Value("${databus.configFileName:config.json}")
    public String applicationConfigName;

    @Value("${databus.pluginInstallLocation:PluginFolder}")
    public String pluginInstallLocationFileName;

    @Value("${databus.pluginRespLocation:PluginRepo}")
    public String pluginRespLocationFileName;

    @Value("${databus.repositories:repositories.json}")
    public String pluginRespFolderDefine;

    @Value("${databus.remoteRespUri:http://localhost:3005/DataBus-Etl/PluginRepo/plugins.json}")
    public String remoteRespUri;
}
