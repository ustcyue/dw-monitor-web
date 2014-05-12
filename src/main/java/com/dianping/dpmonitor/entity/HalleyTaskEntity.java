package com.dianping.dpmonitor.entity;

/**
 * Created by xiaoning.yue on 2014/5/12.
 */
public class HalleyTaskEntity {
    private Integer taskId;
    private String taskName;
    private String taskOwner;
    public boolean isCovered = false;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskOwner() {
        return taskOwner;
    }

    public void setTaskOwner(String taskOwner) {
        this.taskOwner = taskOwner;
    }
}
