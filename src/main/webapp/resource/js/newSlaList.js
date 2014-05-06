/**
 * Created by xiaoning.yue on 2014/4/23.
 */
var monitor_center_cdate = getformattedDate();
$(document).ready( function () {
    var tableData = getSlaData();
    var table = $('#sla-dataTable').DataTable(
        {
            "aaData":tableData
            ,
            "aoColumns":[
                {"sTitle": "SLA ID"},
                {"sTitle": "SLA名称"},
                {"sTitle": "SLA类型"},
                {"sTitle": "（预计）完成时间"},
                {"sTitle": "当前状态"},
                {"sTitle": "当前瓶颈"},
                {"sTitle": "瓶颈任务状态"}
            ],
            "iDisplayLength":100
        }
    );
} );


function getSlaData(){
    var slaArray = new Array();
    $.ajax({
        async: false,
        cache: false,
        type: 'get',
        dataType: "json",
        url: "rest/sla/getSlaJobs/"+monitor_center_cdate,  //请求搜索的路径
        timeout: 5000,
        error: function () {              //请求失败处理函数
            alert("获取数据出错！");
        },
        success: function (data) {
            var responseObj = data;
            if(responseObj.code == 200){
                slaArray = dataPresent(responseObj.msg);
            }
            else{
                alert("无SLA数据");
            }
        }
    });
    return slaArray;
}

function dataPresent(datas) {
    var jArray = datas;
    var tableContent = "";
    var rowData = "";
    jArray.sort(sortSLA);
    var jobNum = jArray.length;
    var slaArray = new Array();
    for(var i=0; i < jArray.length; i++){
        var slaObj = jArray[i];
        slaArray[i] = createRow(slaObj);
    }
    return slaArray;
}

function createRow(slaObj) {
    var row = new Array();
    var addClass = "";
    var statdesc = "";
    var status = slaObj.reportStatus;
    switch(status){
        case 0:
            addClass = "info";
            statdesc = "<span class='label label-info'>运行中</span>";
            break;
        case 1:
            addClass = "success";
            statdesc = "<span class='label label-success'>成功完成</span>";
            break;
        case 2:
            addClass = "warning";
            statdesc = "<span class='label label-warning'>已完成(延迟)</span>";
            break;
        case 3:
            addClass = "info";
            statdesc = "<span class='label label-info'>运行中</span>";
            break;
        case -1:
            addClass = "error";
            statdesc = "<span class='label label-danger'>预计延迟</span>";
            break;
    }
    var type = slaObj.slaType;
    var typedesc = "";
    switch (type){
        case 0:
            typedesc = "自定义"
            break;
        case 1:
            typedesc = "北斗报表"
            break;
        case 2:
            typedesc = "邮件任务"
            break;
        case 3:
            typedesc = "dashboard"
            break;
        case 4:
            typedesc = "线上应用"
            break;
    }
    if(slaObj.keyPreStatus == null){
        slaObj.keyPreStatus = 'NA'
    }
    if((slaObj.keyPreStatus == -1 || slaObj.keyPreStatus == 3 || slaObj.keyPreStatus == 7)&& status !=1 && status !=2){
        slaObj.finishTime = "暂时未知";
    }
    row[0] =  slaObj.slaId;
    row[1] =  slaObj.slaName;
    row[2] = typedesc;
    row[3] = slaObj.finishTime;
    row[4] = statdesc;
    row[5] = slaObj.keyPreTaskName;
    row[6] = statusMap(slaObj.keyPreStatus);
    return row;
}

function getformattedDate(){
    var now=new Date()
    y=now.getFullYear()
    m=now.getMonth()+1
    d=now.getDate()
    m=m<10?"0"+m:m
    d=d<10?"0"+d:d
    return y+"-"+m+"-"+d
}


function statusMap(status){
    switch (status){
        case 0:
            return "init";
        case -1:
            return "fail";
        case 1:
            return "success";
        case 0:
            return "init";
        case 2:
            return "running";
        case 3:
            return "suspend";
        case 4:
            return "init error";
        case 5:
            return "wait";
        case 6:
            return "ready";
        case 7:
            return "timeout";
        default:
            return status+" ";
    }
}


function sortSLA(a,b){
    var aStat = a.reportStatus;
    var bStat = b.reportStatus;
    if(aStat == 2){
        aStat = -0.5;
    }
    if(bStat == 2){
        bStat = -0.5;
    }
    if(aStat != bStat){
        return aStat - bStat;
    }
    else{
        return a.slaId - b.slaId;
    }
}
