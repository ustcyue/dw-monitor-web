package com.dianping.dpmoniter.job;

import com.dianping.dpmoniter.entity.SlaEventEntity;
import com.dianping.dpmoniter.mapper.SlaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiaoning.yue on 2014/4/28.
 */
@Service
public class SlaJob {
    @Autowired
    private SlaMapper slaMapper;
    public void testIfexist(){
        List<SlaEventEntity> result = slaMapper.getEventInToday();
    }
}
