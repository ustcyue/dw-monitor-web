package com.dianping.dpmonitor.entity;

/**
 * Created by xiaoning.yue on 2014/5/6.
 */
public class SlaEventEntity {
    private int eventId;
    private String title;
    private String eventDate;
    private long startTime;
    private long endTime;
    private int eventLevel;
    private int eventType;

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(int eventLevel) {
        this.eventLevel = eventLevel;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }
}
