package org.adp.databus.plugins.randomdata.gen;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.adp.databus.api.event.DataBusEventTopic;
import org.adp.databus.api.event.EventProducer;
import org.adp.databus.api.event.EventProducerHolder;
import org.adp.databus.api.operation.JsonBuilderHolder;
import org.pf4j.Plugin;
import org.pf4j.PluginWrapper;

/**
 * @author zzq
 */
public class DateGeneratePlugin extends Plugin {

    public DateGeneratePlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
        super.start();
        final EventProducer holder = EventProducerHolder.getHolder();
        if (holder != null) {
            final ObjectMapper mapper = JsonBuilderHolder.getMapper();
            holder.produceEvent(DataBusEventTopic.PLUGIN_START,
                    mapper.createObjectNode()
                            .put("pluginId", getWrapper().getPluginId())
            );
        }
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void delete() {
        super.delete();
    }
}
