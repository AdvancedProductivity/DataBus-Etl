package org.adp.databus.plugins.randomdata.gen;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adp.databus.api.DataSupplier;
import org.adp.databus.api.operation.JsonBuilderHolder;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Generate
 *
 * @author zzq
 */
public final class RandomDataGenerate implements DataSupplier {
    private String location = null;
    private static final String HEADER = "header";
    private static final String ROW_COUNT = "rowCount";

    @Override
    public JsonNode get(JsonNode param) {
        final ObjectMapper mapper = JsonBuilderHolder.getMapper();
        final ArrayNode arrayNode = mapper.createArrayNode();
        final int rowCount = param.path(ROW_COUNT).asInt();
        for (int i = 0; i < rowCount; i++) {
            final ObjectNode objectNode = mapper.createObjectNode();
            final ArrayNode header = (ArrayNode) param.path(HEADER);
            for (JsonNode jsonNode : header) {
                objectNode.put(jsonNode.asText(), RandomStringUtils.random(10, true, true));
            }
            arrayNode.add(objectNode);
        }
        return null;
    }

    @Override
    public void validate(JsonNode param) throws Exception {
        if (param == null) {
            throw new Exception("Param Must Exist");
        }
        if (!param.isObject()) {
            throw new Exception("Param Must Be a Json Object");
        }
        if (!param.path(ROW_COUNT).isInt()) {
            throw new Exception("Config param not have rowCount field");
        }
        if (param.path(ROW_COUNT).asInt() <= 0) {
            ((ObjectNode) param).put(ROW_COUNT, 5);
        }
        if (!param.path(HEADER).isArray() || param.path(HEADER).size() <= 0) {
            final ObjectMapper mapper = JsonBuilderHolder.getMapper();
            final ArrayNode arrayNode = mapper.createArrayNode();
            arrayNode.add("A").add("B").add("C").add("D").add("E");
            ((ObjectNode) param).set(HEADER, arrayNode);
        }
    }

    @Override
    public String getConfigExample() {
        return null;
    }

    @Override
    public void setBaseFileDir(String fileDir) {
        this.location = fileDir;
    }

    @Override
    public String getComponentName() {
        return "Generate Random Grid Data";
    }
}
