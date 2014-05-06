package com.dianping.dpmonitor.action;

import com.dianping.dpmonitor.service.SlaService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created with IntelliJ IDEA.
 * User: yxn
 * Date: 14-3-21
 * Time: 下午5:40
 * To change this template use File | Settings | File Templates.
 */
@Repository("BillingAction")
@Path("/bill")
public class BillingAction {
    @Autowired
    private SlaService slaService;
    private JSONObject jsonObject;

    @GET
    @Path("/testBilling")
    public JSONObject  testBilling(){

        return null;
    }
}
