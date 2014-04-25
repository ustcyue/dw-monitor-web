package com.dianping.dpmoniter.action;

import com.dianping.dpmoniter.common.CommonUtil;
import com.dianping.dpmoniter.entity.BottleneckTaskEntity;
import com.dianping.dpmoniter.entity.SlaEntity;
import com.dianping.dpmoniter.entity.SlaJobEntity;
import com.dianping.dpmoniter.service.SlaService;
import org.apache.log4j.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: yxn
 * Date: 14-3-14
 * Time: 下午3:36
 * To change this template use File | Settings | File Templates.
 */
@Controller("SlaAction")
@Scope("prototype")
@Path("/sla")
public class SlaAction {
    @Autowired
    private SlaService slaService;

    private JSONObject jsonObject;

    private List<SlaEntity> slaLists = new ArrayList<SlaEntity>();

    private List<SlaEntity> insertLists = new ArrayList<SlaEntity>();

    private List<SlaEntity> updateLists = new ArrayList<SlaEntity>();

    private List<SlaEntity> currentLists = new ArrayList<SlaEntity>();

    public static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

    private static Logger log = Logger.getLogger(SlaAction.class);

    @GET
    @Path("/getSlaJobs/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getSlaJobs(
            @DefaultValue("aa") @PathParam("date") String date,
            @Context HttpServletRequest request
    ) {
        if(date.equals("aa")){
            date = new DateTime().toString("yyyy-MM-dd");
        }
        List<SlaJobEntity> slajobs = slaService.getSlaJobs(date);
        System.out.println(slajobs.size());
        if(slajobs.size() == 0){
            throw new RuntimeException("无活跃SLA任务");
        }
        JSONArray jArray = new JSONArray();
        jArray.addAll(slajobs);
        this.jsonObject = CommonUtil.getPubJson(jArray);
        return jsonObject;
    }

    @GET
    @Path("/getSlaJobFinishTime/{slaId}/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getSlaJobFinishTime(
            @DefaultValue("0") @PathParam("slaId") Integer slaId,
            @DefaultValue("aa") @PathParam("date") String date,
            @Context HttpServletRequest request
    ) {
        if(date.equals("aa")){
            date = new DateTime().toString("yyyy-MM-dd");
        }

        SlaJobEntity targetSla =  slaService.getSlaJobFinishTime(slaId,date);
        if (targetSla == null) {
            throw new RuntimeException("无相关SLA任务");
        }

        JSONObject jsonObj = JSONObject.fromObject(targetSla);
        this.jsonObject = CommonUtil.getPubJson(jsonObj);
        return jsonObject;
    }

    @GET
    @Path("/getBottleNecks/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getBottleNecks(
            @DefaultValue("aa") @PathParam("date") String date,
            @Context HttpServletRequest request
    ) {
        if(date.equals("aa")){
            date = new DateTime().toString("yyyy-MM-dd");
        }
        List<BottleneckTaskEntity> bottleNecks = slaService.generateDiagInfo(date);
        if(bottleNecks.size() == 0){
            throw new RuntimeException("无故障分析数据");
        }
        JSONArray jArray = new JSONArray();
        jArray.addAll(bottleNecks);
        this.jsonObject = CommonUtil.getPubJson(jArray);
        return this.jsonObject;
    }

    @GET
    @Path("/getTaskStatusDetail/{date}")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject  getTaskStatusDetail(
            @DefaultValue("aa") @PathParam("date") String date,
            @Context HttpServletRequest request
    ) {
        if(date.equals("aa")){
            date = new DateTime().toString("yyyy-MM-dd");
        }
        List<Map<String, Object>> taskHis = slaService.getStatusHis(date);
        if(taskHis.size() == 0){
            throw new RuntimeException("无该task详细信息");
        }
        JSONArray jArray = new JSONArray();
        jArray.addAll(taskHis);
        this.jsonObject = CommonUtil.getPubJson(jArray);
        return this.jsonObject;
    }

    @GET
    @Path("/getSlaStatusHis")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getSlaStatusHis(
        @Context HttpServletRequest request
    ){
        List<Map<String, Object>> slaHis = slaService.getSlaStatusHis();
        if(slaHis.size() == 0){
            throw new RuntimeException("sla历史状态信息获取失败");
        }
        System.out.println(slaHis.size());
        JSONArray jArray = JSONArray.fromObject(slaHis.toArray());
        this.jsonObject = CommonUtil.getPubJson(jArray);
        return this.jsonObject;
    }
}
