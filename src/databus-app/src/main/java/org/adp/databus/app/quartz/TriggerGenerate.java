package org.adp.databus.app.quartz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class TriggerGenerate {

    private static final Logger log = LoggerFactory.getLogger(TriggerGenerate.class);

    private static final AtomicInteger jobCount = new AtomicInteger(0);
    
    @Resource
    private StdScheduler quartzScheduler;

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ObjectMapper objectMapper;

    public ObjectNode addQuartzJob(String cron) {
        final ObjectNode result = objectMapper.createObjectNode();
        if (quartzScheduler == null) {
            return result.put("error", "have no scheduler");
        }
        CronTrigger trigger = createCronTrigger(
                "job_" + jobCount.incrementAndGet()
                , new Date()
                , cron
                , CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING
        );
        final JobDetail job = createJob(TestJob.class, true, applicationContext, "testJobName", "testGroup");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            final Date date = quartzScheduler.scheduleJob(job, trigger);
            log.info("create date: {}", date);
            return result.put("date", simpleDateFormat.format(date))
                    .put("id", job.getKey().toString());
        } catch (Exception e) {
            log.error("create job error", e);
            return result.put("error", e.toString());
        }
    }

    public CronTrigger createCronTrigger(String triggerName,
                                         Date startTime,
                                         String cronExpression,
                                         int misFireInstruction) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setName(triggerName);
        factoryBean.setStartTime(startTime);
        factoryBean.setCronExpression(cronExpression);
        factoryBean.setMisfireInstruction(misFireInstruction);
        try {
            factoryBean.afterPropertiesSet();
        } catch (ParseException e) {
            log.error("create cron trigger error: {}", e.getMessage(), e);
        }
        return factoryBean.getObject();
    }

    public JobDetail createJob(
            Class<? extends QuartzJobBean> jobClass,
            boolean isDurable,
            ApplicationContext context,
            String jobName,
            String jobGroup
    ) {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(jobClass);
        factoryBean.setDurability(isDurable);
        factoryBean.setApplicationContext(context);
        factoryBean.setName(jobName);
        factoryBean.setGroup(jobGroup);

        // Set job data map
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(jobName + jobGroup, jobClass.getName());
        jobDataMap.put("a", RandomStringUtils.random(10, true, false));
        factoryBean.setJobDataMap(jobDataMap);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    public boolean deleteJon(String id) {
        if (StringUtils.isEmpty(id)) {
            log.error("have no id to delete");
            return false;
        }
        final String[] split = StringUtils.split(id, ".");
        if (split.length != 2) {
            log.error("id must have two part");
            return false;
        }
        JobKey key = new JobKey(split[1], split[0]);
        try {
            final boolean b = quartzScheduler.checkExists(key);
            if (!b) {
                log.error("the id:{} not exist", id);
                return false;
            }
            final boolean b1 = quartzScheduler.deleteJob(key);
            return b1;
        } catch (Exception e) {
            log.error("error happens while delete job with id:{}", id);
            return false;
        }
    }
}
