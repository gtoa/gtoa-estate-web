package com.renren.gota.webserver.model;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

import java.util.Date;

/**
 * @Title:
 * @Desc:
 * @Author xiaoxue.wang
 * @Date 2015-09-23 12:12.
 */
public class CalendarEvent {
    private String id;

    private Date createTime;
    private String summery;
    private Event.Creator creator;
    private String desc;

    private Date startTime;
    private Date endTime;


    public CalendarEvent(Event event) {
        if(event == null)
            return ;
        DateTime dt = event.getCreated();
        this.createTime = new Date(dt.getValue());
        creator = event.getCreator();
        summery = event.getSummary();
        desc = event.getDescription();

        EventDateTime edts = event.getStart();
        EventDateTime edte = event.getEnd();
        if(edts != null)
            startTime = new Date(edts.getDateTime().getValue());
        if(edte != null)
            endTime = new Date(edte.getDateTime().getValue());
        this.id = event.getId();

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummery() {
        return summery;
    }

    public void setSummery(String summery) {
        this.summery = summery;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Event.Creator getCreator() {
        return creator;
    }

    public void setCreator(Event.Creator creator) {
        this.creator = creator;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "id='" + id + '\'' +
                ", createTime=" + createTime +
                ", summery='" + summery + '\'' +
                ", creator=" + creator +
                ", desc='" + desc + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public static void main(String[] args) {
        DateTime dt = new DateTime(new Date().getTime());
        System.out.println(new Date(dt.getValue()));
    }
}
