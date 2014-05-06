package com.dianping.dpmonitor.mapper;

import com.dianping.dpmonitor.entity.BottleneckTaskEntity;
import com.dianping.dpmonitor.entity.SlaEntity;
import com.dianping.dpmonitor.entity.SlaEventEntity;
import com.dianping.dpmonitor.entity.SlaJobEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yxn
 * Date: 14-3-14
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public interface SlaMapper {
    List<SlaJobEntity> getSlaJobs(@Param("timeId") String timeId);

    SlaJobEntity getSlaJobById(@Param("slaId")Integer slaId,@Param("timeId") String timeId);

    List<SlaEntity> getSlas();

    List<String> getUserEmail(@Param("userName") String userName);

    Integer insertSlaDim(@Param("sla") SlaEntity sla);

    Integer insertSlaJob(@Param("sla") SlaEntity sla,@Param("preTaskId") Integer preTaskId);

    Integer insertSlaUser(@Param("sla") SlaEntity sla);

    Integer updateSlaDim(@Param("sla") SlaEntity sla);

    Integer updateSlaJob(@Param("sla") SlaEntity sla,@Param("preTaskId") Integer preTaskId);

    Integer deleteSlaJob(@Param("sla") SlaEntity sla);

    List<BottleneckTaskEntity> getdelayinfo(@Param("timeId") String timeId);
    List<Map<String,Object>> getJobRunHis(@Param("timeId") String timeId, @Param("keyTaskId") List<Integer> keyTaskId);
    List<Map<String,Object>> getSlaHis(@Param("timeId") String timeId);
    List<Map<String, Object>> getSlaEvents(@Param("begin") String begin,@Param("end") String end);
    Integer deleteEvent(@Param("eventDate") String eventDate, @Param("eventType") Integer eventType);
    Integer insertSlaEvent(SlaEventEntity slaEventEntity);
}
