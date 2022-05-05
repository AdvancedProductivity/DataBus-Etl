package org.adp.databus.api.operation;

import com.fasterxml.jackson.databind.JsonNode;
import org.adp.databus.api.event.EventProducer;

/**
 * provided some help method
 *
 * @author zzq
 */
public interface OperationHelper {

    /**
     * validate the param is right
     *
     * @param param the config json
     * @throws Exception the error message
     */
    void validate(JsonNode param) throws Exception;

    /**
     * get the config example
     *
     * @return json string
     */
    String getConfigExample();


    /**
     * set the event publish
     *
     * @param producer method
     */
    void setEventPublish(EventProducer producer);
}
