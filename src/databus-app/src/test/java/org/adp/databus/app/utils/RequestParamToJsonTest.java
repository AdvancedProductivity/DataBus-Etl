package org.adp.databus.app.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class RequestParamToJsonTest {


    @Test
    public void testParameterMapper() {
        assertNull(RequestParamToJson.reqToJson(null, null));
        final MockHttpServletRequest req = MockMvcRequestBuilders.get("/sad")
                .param("asd", "0", "1", "2")
                .param("b", "2")
                .param("c", "")
                .buildRequest(null);
        assertNull(RequestParamToJson.reqToJson(req, null));
        ObjectMapper mapper = new ObjectMapper();
        assertNull(RequestParamToJson.reqToJson(null, mapper));
        final ObjectNode jsonNodes = RequestParamToJson.reqToJson(req, mapper);
        assertNotNull(jsonNodes);
        assertEquals(3, jsonNodes.size());
        assertTrue(jsonNodes.path("asd").isArray());
        assertEquals(3, jsonNodes.path("asd").size());
        assertTrue(jsonNodes.path("b").isTextual());
        assertTrue(StringUtils.isEmpty(jsonNodes.path("c").asText()));
    }

    @Test
    public void testFileToMap() {
        MockMultipartFile file0 = new MockMultipartFile("file0",
                "testContext".getBytes(StandardCharsets.UTF_8));
        MockMultipartFile file1 = new MockMultipartFile("file1",
                "testContext".getBytes(StandardCharsets.UTF_8));
        final MockHttpServletRequest mockHttpServletRequest = MockMvcRequestBuilders.multipart("/api/test")
                .file(file0)
                .file(file1)
                .accept(MediaType.APPLICATION_JSON).buildRequest(null);
        final Map<String, MultipartFile> fileMap = RequestParamToJson.reqFileToMap(mockHttpServletRequest);
        assertNotNull(fileMap);
        assertEquals(2, fileMap.size());
    }

}