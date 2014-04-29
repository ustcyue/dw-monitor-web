$(function() {
        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        var calendar = $('#calendar').fullCalendar({
            buttonText: {
                prev: '<i class="icon-chevron-left"></i>',
                next: '<i class="icon-chevron-right"></i>'
            },

            header: {
                left: 'prev,next today',
                center:  'title',
                right: 'month,agendaWeek,agendaDay'
            },
            events: function(start, end,callback){
                var start_s = start.Format("yyyy-MM-dd");
                var end_s = end.Format("yyyy-MM-dd");
                $.ajax({
                    url:"rest/sla/getFailEvents/"+start_s+"/"+end_s,
                    dataType: "json",
                    success:function(data){
                        var events = [];
                        events = retriveEvents(data.msg);
                        callback(events);
                    }
                });
            }
        });
})

 function retriveEvents(data){
    var events = [];
    for (var i=0; i<data.length; i++){
        events[i] = {
            title: data[i].title,
            start: data[i].start_time
        }
     }
     return events;
}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}