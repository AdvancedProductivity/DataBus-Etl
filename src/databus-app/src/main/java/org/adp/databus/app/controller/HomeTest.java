package org.adp.databus.app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adp.databus.app.utils.RequestParamToJson;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author test Controller
 */
@RestController
@RequestMapping("api/test")
public class HomeTest {

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping
    public ObjectNode get() {
        return objectMapper
                .createObjectNode().put("data", RandomStringUtils.random(10, true, false));
    }

    public static class QueryParam extends ObjectNode {
        private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        public QueryParam() {
            super(OBJECT_MAPPER.getNodeFactory());
        }

    }

    @GetMapping("/query")
    public ObjectNode getQueryParam(HttpServletRequest request, QueryParam query) {
        return objectMapper
                .createObjectNode().put("count", query.size()).set("content", query);
    }

    @PostMapping
    public ObjectNode postFile(HttpServletRequest request) {
        final Map<String, MultipartFile> stringMultipartFileMap = RequestParamToJson.reqFileToMap(request);
        return objectMapper
                .createObjectNode().put("count", stringMultipartFileMap.size());
    }
}
