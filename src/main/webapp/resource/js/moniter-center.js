/**
 * Created by xiaoning.yue on 2014/4/18.
 */
$(function () {
    var slaHis = getSlaHis();
    var xArray = new Array();
    var stabArray = new Array();
    var accuArray = new Array();
    for(var i = 0; i<slaHis.length; i++){
        xArray[i] = slaHis[i].time_id;
        stabArray[i] = slaHis[i].succ_rate*100;
        accuArray[i] = Math.random()*50+50;
        if(i == slaHis.length -1){
            var curStabValue = Math.round(stabArray[i]*100/100);
            var curAccuValue = Math.round(accuArray[i]*100/100)
        }
    }
    var g = new JustGage({
        id: "slaclk",
        value: curStabValue,
        min: 0,
        max: 100,
        title: "当前按时完成率",
        titleFontColor:"#000000",
            label: "%",
        levelColors:[
            "#FF0002",
            "#F9C802",
            "#A9D70B"

        ]
        ,levelColorsGradient: true
    });
    var g = new JustGage({
        id: "stabclk",
        value: curAccuValue,
        min: 0,
        max: 100,
        title: "当前数据一致率",
        titleFontColor:"#000000",
        label: "%",
        levelColors:[
            "#FF0002",
            "#F9C802",
            "#A9D70B"

        ]
        ,levelColorsGradient: true
    });
    var ctx = $("#myChart").get(0).getContext("2d");

    var line_data = {
        labels : xArray,
        datasets : [
            {
                fillColor : "rgba(170,170,170,0.6)",
                strokeColor : "rgba(220,220,220,1)",
                pointColor : "rgba(220,220,220,1)",
                pointStrokeColor : "#fff",
                data : stabArray
            },
            {
                fillColor : "rgba(151,187,205,0.5)",
                strokeColor : "rgba(151,187,205,1)",
                pointColor : "rgba(151,187,205,1)",
                pointStrokeColor : "#fff",
                data : accuArray
            }
        ]
    }


    new Chart(ctx).Line(line_data, {
        bezierCurve: false
    });

});


function getSlaHis() {
    var slaHis;
    $.ajax({
        async: false,
        cache: false,
        type: 'get',
        dataType: "json",
        url: "rest/sla/getSlaStatusHis",  //请求搜索的路径
        timeout: 5000,
        error: function () {              //请求失败处理函数
            alert("获取数据出错！");
        },
        success: function (data) {
            if(data.code == 200){
                slaHis = data.msg;
            }
            else{

            }
        }
    });
    return slaHis;
}