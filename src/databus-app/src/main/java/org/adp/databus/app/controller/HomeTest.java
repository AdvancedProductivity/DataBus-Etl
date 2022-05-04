package org.adp.databus.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
