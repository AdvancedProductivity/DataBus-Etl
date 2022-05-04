package org.adp.databus.app.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasToString;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestHomeTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Resource
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        //使用上下文构建mockMvc
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test()
    public void testTestController(){
        assertNotNull(mockMvc, "Spring Context not null");
        assertDoesNotThrow(() -> {
            final MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.get("/api/test")
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();
            final MockHttpServletResponse response = mvcResult.getResponse();
            final String contentAsString = response.getContentAsString();
            final JsonNode jsonNode = objectMapper.readTree(contentAsString);
            assertTrue(jsonNode.has("data"));
            assertTrue(jsonNode.path("data").isTextual());
            assertEquals(jsonNode.path("data").asText().length(), 10);
        });
    }

    @Test()
    public void testPostFile() {
        assertDoesNotThrow(() -> {
            MockMultipartFile file0 = new MockMultipartFile("file0",
                    "testContext".getBytes(StandardCharsets.UTF_8));
            MockMultipartFile file1 = new MockMultipartFile("file1",
                    "testContext".getBytes(StandardCharsets.UTF_8));
            final MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.multipart("/api/test")
                                    .file(file0)
                                    .file(file1)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            final MockHttpServletResponse response = mvcResult.getResponse();
            final String contentAsString = response.getContentAsString();
            final JsonNode jsonNode = objectMapper.readTree(contentAsString);
            assertEquals(2, jsonNode.path("count").asInt());
        });
    }


    @Test()
    public void testQuery() {
        assertNotNull(mockMvc, "Spring Context not null");
        assertDoesNotThrow(() -> {
            final LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<String, String>();
            linkedMultiValueMap.put("a", Collections.singletonList("10"));
            linkedMultiValueMap.put("b", Arrays.asList("1,2,3", "po"));
            final MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.get("/api/test/query")
                                    .queryParams(linkedMultiValueMap)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                    .andReturn();

            final MockHttpServletResponse response = mvcResult.getResponse();
            final String contentAsString = response.getContentAsString();
            final JsonNode jsonNode = objectMapper.readTree(contentAsString);
            assertEquals(2, jsonNode.path("count").asInt());
        });
    }

    @Test()
    public void testCors() {
        assertNotNull(mockMvc, "Spring Context not null");
        String origin = "http://localhost:4200";
        assertDoesNotThrow(() -> {
            final MvcResult mvcResult = mockMvc.perform(
                            MockMvcRequestBuilders.options("/api/test")
                                    .header("Access-Control-Request-Method", "GET")
                                    .header("Origin", origin)
                                    .header("Referer", origin)
                                    .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Origin", origin))
                    .andExpect(MockMvcResultMatchers.header().exists("Access-Control-Allow-Methods"))
                    .andExpect(MockMvcResultMatchers.header().string("Access-Control-Allow-Credentials", "true"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print())
                    .andReturn();
            String headers = mvcResult.getResponse().getHeader("Access-Control-Allow-Methods");
            assertNotNull(headers);
            assertTrue(headers.contains("GET"));
        });
    }
}