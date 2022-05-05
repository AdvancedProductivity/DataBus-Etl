package org.adp.databus.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.adp.databus.api.operation.OperationHelper;
import org.pf4j.ExtensionPoint;

/**
 * persistence the data
 *
 * @author zzq
 */
public interface DataConsumer extends ExtensionPoint, OperationHelper {

    /**
     * save data
     *
     * @param data  the data wait for save
     * @param param the param from the web config
     */
    void persistence(JsonNode data, JsonNode param);

}
