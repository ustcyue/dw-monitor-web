package com.dianping.dpmonitor.action;

import com.dianping.dpmonitor.entity.HalleyTaskEntity;
import com.dianping.dpmonitor.entity.SlaJobEntity;
import com.dianping.dpmonitor.job.StabJob;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by xiaoning.yue on 2014/5/9.
 */
@Controller("StabAction")
@Scope("prototype")
@Path("/stab")
public class StabAction {
    @Autowired
    StabJob stabJob;
    private JSONObject jsonObject;
    @GET
    @Path("/getCoverRate")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getCoverRate(
            @Context HttpServletRequest request
    ){
        double stabRate = stabJob.calcuCoverRate();
        jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("rate",stabRate);
        return jsonObject;
    }

    @GET
    @Path("/getHighPrioCoverRate")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getHighPrioCoverRate(
            @Context HttpServletRequest request
    ){
        double stabRate = stabJob.calcuHighPrioCoverRate();
        jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("rate",stabRate);
        return jsonObject;
    }

    @GET
    @Path("/refreshCache")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject refreashCache(
            @Context HttpServletRequest request
    ){
        stabJob.refresh();
        jsonObject = new JSONObject();
        jsonObject.put("code",200);
        return jsonObject;
    }
    @GET
    @Path("/getUntractedSla")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUntractedSla(
            @Context HttpServletRequest request
    ){
        List<SlaJobEntity> uncoverJobs =  stabJob.getUntractedSla();
        JSONArray jArray = new JSONArray();
        jArray.addAll(uncoverJobs);
        jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("msg",jArray);
        return jsonObject;
    }
    @GET
    @Path("/getUntractedHigh")
    @Produces(MediaType.APPLICATION_JSON)
    public JSONObject getUntractedHigh(
            @Context HttpServletRequest request
    ){
        List<HalleyTaskEntity> uncoverJobs =  stabJob.getUntractedHigh();
        JSONArray jArray = new JSONArray();
        jArray.addAll(uncoverJobs);
        jsonObject = new JSONObject();
        jsonObject.put("code",200);
        jsonObject.put("msg",jArray);
        return jsonObject;
    }

}
