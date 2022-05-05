package org.adp.databus.app.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.pf4j.update.PluginInfo;
import org.pf4j.update.UpdateManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Pf4jSpringConfigTest {

    @Resource
    private UpdateManager updateManager;

    @Resource
    private SpringPluginManager springPluginManager;

    @Test
    public void testGetPlugin() {
        assertNotNull(updateManager);
        final List<PluginInfo> availablePlugins = updateManager.getAvailablePlugins();
        System.out.println(availablePlugins.size());

        assertNotNull(availablePlugins);
        final List<PluginInfo> plugins = updateManager.getPlugins();
        assertNotNull(plugins);

        for (PluginInfo plugin : plugins) {
            final PluginWrapper plugin1 = springPluginManager.getPlugin(plugin.id);
            System.out.println(plugin1.getPluginState().toString());
        }
        System.out.println(plugins.size());
        final List<PluginInfo> updates = updateManager.getUpdates();
        assertNotNull(updates);
        System.out.println(updates.size());


    }

}