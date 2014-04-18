/**
 * Created by xiaoning.yue on 2014/4/18.
 */
$(function () {
    var g = new JustGage({
        id: "slaclk",
        value: 70,
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
});