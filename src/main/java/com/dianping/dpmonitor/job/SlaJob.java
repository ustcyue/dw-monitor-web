package com.dianping.dpmonitor.job;

import com.dianping.dpmonitor.entity.SlaEventEntity;
import com.dianping.dpmonitor.entity.SlaJobEntity;
import com.dianping.dpmonitor.mapper.SlaMapper;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiaoning.yue on 2014/4/28.
 */
@Service("slaJob")
public class SlaJob {
    @Autowired
    private SlaMapper slaMapper;
    private static Logger logger = Logger.getLogger(SlaJob.class);
    public void refreshing() {
        logger.info("refreshing");
        String today = new DateTime().toString("yyyy-MM-dd");
        slaMapper.deleteEvent(today, 1);
        List<SlaJobEntity> slaJobs = slaMapper.getSlaJobs(today);
        double delayValue = 0;
        for (SlaJobEntity slaJob : slaJobs) {
            if (slaJob.getReportStatus().equals(-1) || slaJob.getReportStatus().equals(2)) {
                delayValue += slaJob.getJobValue();
            }
        }
        String failLevel =
                delayValue < 100 ? "NA" :
                        delayValue < 200 ? "P5" :
                                delayValue < 400 ? "P4" :
                                        delayValue < 600 ? "P3" :
                                                delayValue < 800 ? "P2" :
                                                        "P1";
        if (!failLevel.equals("NA")) {
            String title = "稳定性" + failLevel + "故障";
            int eventLevel = failLevel.compareTo("P3") >= 0 ? 1 : 2;
            long startTime = new DateTime().getMillis()/1000;
            long endTIme = startTime;
            int eventType = 1;
            SlaEventEntity event = new SlaEventEntity();
            event.setTitle(title);
            event.setEventDate(today);
            event.setStartTime(startTime);
            event.setEndTime(endTIme);
            event.setEventLevel(eventLevel);
            event.setEventType(eventType);
            slaMapper.insertSlaEvent(event);
            logger.info("insert new Event:" + title);
        }
    }
    public void refreshingHis() {
        logger.info("refreshing");
        for (int i = 1; i <= 80; i++) {
            String today = new DateTime().minusDays(i).toString("yyyy-MM-dd");
            List<SlaJobEntity> slaJobs = slaMapper.getSlaJobs(today);
            double delayValue = 0;
            for (SlaJobEntity slaJob : slaJobs) {
                if (slaJob.getReportStatus().equals(-1) || slaJob.getReportStatus().equals(2)) {
                    delayValue += slaJob.getJobValue();
                }
            }
            String failLevel =
                    delayValue < 100 ? "NA" :
                            delayValue < 200 ? "P5" :
                                    delayValue < 400 ? "P4" :
                                            delayValue < 600 ? "P3" :
                                                    delayValue < 800 ? "P2" :
                                                            "P1";
            if (!failLevel.equals("NA")) {
                String title = "稳定性" + failLevel + "故障";
                int eventLevel = failLevel.compareTo("P3") >= 0 ? 1 : 2;
                slaMapper.deleteEvent(today, 1);
                long startTime = new DateTime().minusDays(i).getMillis() / 1000;
                long endTIme = startTime;
                int eventType = 1;
                SlaEventEntity event = new SlaEventEntity();
                event.setTitle(title);
                event.setEventDate(today);
                event.setStartTime(startTime);
                event.setEndTime(endTIme);
                event.setEventLevel(eventLevel);
                event.setEventType(eventType);
                slaMapper.insertSlaEvent(event);
                logger.info("insert new Event:" + title);
            }
        }
    }
}
