package org.adp.databus.api.event;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.context.ApplicationEvent;

/**
 * @author zzq
 */
public class DataBusEvent extends ApplicationEvent {
    private String topic;

    private JsonNode data;

    public DataBusEvent(Object source, String topic, JsonNode data) {
        super(source);
        this.topic = topic;
        this.data = data;
    }


    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }
}
