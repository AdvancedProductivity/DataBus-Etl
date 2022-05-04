package org.adp.databus.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zzq
 */
public class RequestParamToJson {

    /**
     * parser the req parameter value to json node
     *
     * @param request      req
     * @param objectMapper json builder
     * @return parameter json
     */
    public static ObjectNode reqToJson(HttpServletRequest request, ObjectMapper objectMapper) {
        if (objectMapper == null || request == null) {
            return null;
        }
        final Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap == null || parameterMap.size() == 0) {
            return objectMapper.createObjectNode();
        }
        final ObjectNode result = objectMapper.createObjectNode();
        for (String s : parameterMap.keySet()) {
            final String[] strings = parameterMap.get(s);
            if (strings == null || strings.length == 0) {
                result.set(s, NullNode.getInstance());
            } else if (strings.length == 1) {
                result.put(s, strings[0]);
            } else {
                final ArrayNode arrayNode = objectMapper.createArrayNode();
                for (String string : strings) {
                    arrayNode.add(string);
                }
                result.set(s, arrayNode);
            }
        }
        return result;
    }

    /**
     * parser the req file to map node
     *
     * @param request req
     * @return parameter json
     */
    public static Map<String, MultipartFile> reqFileToMap(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest r = (MultipartHttpServletRequest) request;
            final Map<String, MultipartFile> fileMap = r.getFileMap();
            return fileMap;
        }
        return null;
    }
}
