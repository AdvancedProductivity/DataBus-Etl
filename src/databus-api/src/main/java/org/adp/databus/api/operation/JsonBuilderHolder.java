package org.adp.databus.api.operation;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * holder the json builder
 *
 * @author zzq
 */
public class JsonBuilderHolder {

    private static ObjectMapper mapper = null;

    public static ObjectMapper getMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper();
        }
        return mapper;
    }
}
