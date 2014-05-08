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
            },
            selectable: true,
            editable:true,
            select: function(start, end, allDay) {
                bootbox.prompt("New Event Title:", function(title) {
                    if (title !== null) {
                        $.ajax({
                            type: 'POST',
                            dataType: "json",
                            contentType: 'application/json',
                            url: "rest/sla/InsertEvent",
                            data: {
                                title: title,
                                start: start.getTime()/1000,
                                end: end.getTime()/1000,
                                type:allDay ? 2:3,
                                level:0
                            },
                            error: function () {
                                alertChanged($('#Alert'), "alert-danger", "事件添加失败!");
                                $('#Alert').show();
                                setTimeout(function () {
                                    $('#Alert').hide();
                                }, 3000);
                                return;
                            },
                            success: function(data){
                                var res = data;
                                if ($('#Alert').hasClass('alert-danger')) {
                                    $('#Alert').removeClass('alert-danger');
                                }
                                alertChanged($('#Alert'), "alert-success", "事件添加成功!");
                                $('#Alert').show();
                                //更新提示提示在3s后消失
                                setTimeout(function () {
                                    $('#Alert').hide();
                                }, 3000);
                                calendar.fullCalendar('renderEvent',
                                    {
                                        id : res.id,
                                        title: title,
                                        start: start,
                                        end: end,
                                        allDay: allDay
                                    }
                                );
                            }
                        })
                    }
                });

                calendar.fullCalendar('unselect');
            },
            eventClick: function(calEvent, jsEvent, view) {
                if(calEvent.type == 1) {
                    url = "slaJob-status.html?date=" + calEvent.start.Format("yyyy-MM-dd");
                    window.open(url);
                }
            },
            lazyFetching: false
        });
})

 function retriveEvents(data){
    var events = [];
    for (var i=0; i<data.length; i++){
        var tagClass;
        if(data[i].event_level == 2) {
            tagClass = "label-important";
        }
        else if(data[i].event_level == 0) {
            tagClass = "label-success"
        }
        else{
            tagClass = "label-yellow";
        }
        var startTime = new Date(data[i].start_time*1000);
        var fullday = true;
        if(data[i].event_type == 3)
            fullday = false;
        events[i] = {
            id:data[i].id,
            title: data[i].title,
            start: data[i].start_time,
            end:data[i].end_time,
            className: tagClass,
            allDay: fullday,
            type:data[i].event_type
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