package org.adp.databus.app.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.pf4j.PluginWrapper;
import org.pf4j.spring.SpringPluginManager;
import org.pf4j.update.PluginInfo;
import org.pf4j.update.UpdateManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Pf4jSpringConfigTest {

    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private Pf4jSpringConfig pf4jSpringConfig;

    @Resource
    private UpdateManager updateManager;

    @Resource
    private SpringPluginManager springPluginManager;

    @Resource
    private DataBusConst dataBusConst;

    @Test
    public void testRespFileGen() {
        final File file = FileUtils.getFile(
                DataBusConst.USER_DIR,
                dataBusConst.applicationName,
                dataBusConst.pluginRespFolderDefine
        );
        assertNotNull(file);
        assertTrue(file.exists());
        assertDoesNotThrow(() -> {
            final JsonNode jsonNode = objectMapper.readTree(file);
            assertTrue(jsonNode.isArray());
            assertEquals(2, jsonNode.size());
            for (JsonNode node : jsonNode) {
                assertTrue(node.has(DataBusConst.ID));
                assertTrue(node.has(DataBusConst.URL));
            }
        });
    }

    @Test
    public void testGetValue() {
        assertNotNull(pf4jSpringConfig);
        assertDoesNotThrow(() -> {
            Field field = Pf4jSpringConfig.class.getDeclaredField("remoteUri");
            field.setAccessible(true);
            final Object o = field.get(pf4jSpringConfig);
            assertNotNull(o);
            assertTrue(o instanceof String);
            assertEquals("local", o);
        });
    }

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
            assertNull(plugin1);
        }
        System.out.println(plugins.size());
        final List<PluginInfo> updates = updateManager.getUpdates();
        assertNotNull(updates);
        System.out.println(updates.size());
    }

}