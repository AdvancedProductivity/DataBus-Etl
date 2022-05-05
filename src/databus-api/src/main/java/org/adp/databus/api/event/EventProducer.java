package org.adp.databus.api.event;


import com.fasterxml.jackson.databind.JsonNode;

/**
 * public event to Spring Application from plugin
 *
 * @author zzq
 */
public interface EventProducer {

    /**
     * make event
     *
     * @param data  the data wait for handler
     * @param topic data type
     * @return result
     */
    boolean produceEvent(String topic, JsonNode data);
}
