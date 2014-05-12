package com.dianping.dpmonitor.job;

import com.dianping.dpmonitor.entity.HalleyTaskEntity;
import com.dianping.dpmonitor.entity.SlaJobEntity;
import com.dianping.dpmonitor.mapper.SlaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaoning.yue on 2014/5/9.
 */

@Service("stabJob")
public class StabJob {
    @Autowired
    private volatile SlaMapper slaMapper;
    private volatile Map<Integer, String> taskRelaMap = new HashMap<Integer, String>();
    private volatile Map<Integer, Integer> virgoTaskMap = new HashMap<Integer, Integer>();
    private volatile List<SlaJobEntity> slaList = new ArrayList<SlaJobEntity>();
    private volatile List<HalleyTaskEntity> highPrioTasks = new ArrayList<HalleyTaskEntity>();
    List<Integer> virgoTaskLists = new ArrayList<Integer>();
    public void refresh(){
        taskRelaMap.clear();
        virgoTaskLists = slaMapper.getVirgoTasks();
        for(Integer taskId : virgoTaskLists){
            virgoTaskMap.put(taskId,taskId);
        }
        slaList = slaMapper.getSlaJobWithKeyTasks();
        List<Map<String, Object>> taskRelas = slaMapper.getTaskRelations();
        for(int i=0; i<taskRelas.size();i++){
            Integer taskId = (Integer)taskRelas.get(i).get("task_id");
            String preStr = new String((byte[])taskRelas.get(i).get("task_pre_ids") );
//            String preStr = (String)taskRelas.get(i).get("task_pre_ids");
            taskRelaMap.put(taskId,preStr);
        }
        for(SlaJobEntity slaJob: slaList){
            try {
                String[] tempStr = slaJob.getKeyTaskId().split(",");
                for(String keyPreTask : tempStr){
                    List<Integer> allPreTasks = getAllPreTasks(Integer.valueOf(keyPreTask));
                    slaJob.preTaskList.addAll(allPreTasks);
                }

            }catch(Exception e){
                continue;
            }
            for(Integer taskId: slaJob.preTaskList){
                slaJob.preTaskMap.put(taskId,taskId);
            }
            for(Integer taskId:virgoTaskLists){
                if(slaJob.preTaskMap.containsKey(taskId)){
                    slaJob.isVirgoCoverd = true;
                    break;
                }
            }
        }
        highPrioTasks = slaMapper.getHighPrioTasks();
        for(HalleyTaskEntity task: highPrioTasks){
            if(virgoTaskMap.containsKey(task.getTaskId())){
                task.isCovered = true;
            }
        }
    }

    public double calcuCoverRate(){
        if(slaList.size() == 0){
            this.refresh();
        }
        Integer covered = 0;
        for(SlaJobEntity slaJob: slaList) {
            if(slaJob.isVirgoCoverd)
                covered ++;
        }
        System.out.println(covered);
        return covered/(double)slaList.size();
    }

    public double calcuHighPrioCoverRate(){
        if(highPrioTasks.size() == 0){
            this.refresh();
        }
        Integer covered = 0;
        for(HalleyTaskEntity task: highPrioTasks) {
            if(task.isCovered)
                covered ++;
        }
        return covered/(double)highPrioTasks.size();
    }


    private List<Integer> getAllPreTasks(Integer keyTaskId) {
        List<Integer> pretasks = new ArrayList<Integer>();
        findAllPreTask(keyTaskId, pretasks);
        return pretasks;
    }

    private void findAllPreTask(Integer keyTaskId, List<Integer> pretasks) {
        if(!taskRelaMap.containsKey(keyTaskId)){
            return;
        }else{
            String[] tempStr = taskRelaMap.get(keyTaskId).split(",");
            for(String preTaskIdStr : tempStr){
                Integer preTaskId = Integer.valueOf(preTaskIdStr);
                pretasks.add(preTaskId);
                findAllPreTask(preTaskId,pretasks);
            }
        }
    }

    public List<SlaJobEntity> getUntractedSla() {
        List<SlaJobEntity> returnList = new ArrayList<SlaJobEntity>();
        if(slaList.size() == 0){
            this.refresh();
        }
        for(SlaJobEntity slaJob: slaList){
            if(!slaJob.isVirgoCoverd){
                returnList.add(slaJob);
            }
        }
        return returnList;

    }

    public List<HalleyTaskEntity> getUntractedHigh() {
        List<HalleyTaskEntity> returnList = new ArrayList<HalleyTaskEntity>();
        if(highPrioTasks.size() == 0){
            this.refresh();
        }
        for(HalleyTaskEntity task: highPrioTasks){
            if(!task.isCovered){
                returnList.add(task);
            }
        }
        return returnList;
    }
}
