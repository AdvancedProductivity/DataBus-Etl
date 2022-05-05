package org.adp.databus.api;

import com.fasterxml.jackson.databind.JsonNode;
import org.adp.databus.api.operation.OperationHelper;
import org.pf4j.ExtensionPoint;

/**
 * provide the data to handle
 *
 * @author zzq
 */
public interface DataSupplier extends ExtensionPoint, OperationHelper {

    /**
     * supplier the data by the param
     *
     * @param param param form the web config
     * @return the data
     */
    JsonNode get(JsonNode param);
}
