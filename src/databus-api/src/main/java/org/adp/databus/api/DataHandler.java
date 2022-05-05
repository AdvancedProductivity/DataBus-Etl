package org.adp.databus.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.adp.databus.api.operation.OperationHelper;
import org.pf4j.ExtensionPoint;

/**
 * Handle the data
 *
 * @author zzq
 */
public interface DataHandler extends ExtensionPoint, OperationHelper {

    /**
     * handle the data from the structure to another structure
     *
     * @param param config from page
     * @param data  the data wait for handle
     * @return data after handle
     */
    JsonNode handler(JsonNode data, JsonNode param);

}
