package org.adp.databus.app.quartz;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zzq
 */
public class TestJob extends QuartzJobBean {
    private static final Logger log = LoggerFactory.getLogger(TestJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        final JobDataMap jobDataMap = context.getMergedJobDataMap();
        final String a = jobDataMap.getString("a");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final String format = simpleDateFormat.format(new Date());
        log.info("Hello Job, now is: {}, the param a is: {}", format, a);
    }

}
