/**
 * Created by xiaoning.yue on 2014/5/9.
 */
/**
 * Created by xiaoning.yue on 2014/4/23.
 */
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
                {"sTitle": "SLA价值"},
                {"sTitle": "直接前驱任务列表"}
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
        url: "rest/stab/getUntractedSla",  //请求搜索的路径
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
    row[0] =  slaObj.slaId;
    row[1] =  slaObj.slaName;
    row[2] = typedesc;
    row[3] =  Math.round(slaObj.jobValue*100)/100;
    row[4] = slaObj.keyTaskId;
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

