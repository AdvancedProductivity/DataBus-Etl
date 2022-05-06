package org.adp.databus.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.adp.databus.app.quartz.TriggerGenerate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author zzq
 */
@RestController
@RequestMapping("/api/job")
public class JobController {
    @Resource
    private ObjectMapper objectMapper;

    @Resource
    private TriggerGenerate triggerGenerate;

    @PostMapping
    public ObjectNode addJob(@RequestBody ObjectNode cronInfo) {
        final ObjectNode result = objectMapper.createObjectNode();
        final String cron = cronInfo.path("cron").asText();
        if (StringUtils.isEmpty(cron)) {
            return result.put("result", false);
        }
        final ObjectNode b = triggerGenerate.addQuartzJob(cron);
        return result.put("result", true).set("data", b);

    }

    @DeleteMapping
    public ObjectNode removeJob(@RequestParam String id) {
        final ObjectNode result = objectMapper.createObjectNode();
        boolean b = triggerGenerate.deleteJon(id);
        return result.put("result", b);
    }
}
