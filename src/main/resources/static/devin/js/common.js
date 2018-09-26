/**
 * 通用方法封装处理
 * Copyright (c) 2018 devin
 */

$(function () {
    // 复选框事件绑定
    if ($.fn.select2 !== undefined) {
        $("select.form-control:not(.noselect2)").each(function () {
            $(this).select2().on("change", function () {
                $(this).valid();
            })
        })
    }
    if ($(".i-checks").length > 0) {
        $(".i-checks").iCheck({
            checkboxClass: "icheckbox_square-green",
            radioClass: "iradio_square-green",
        })
    }
    if ($(".radio-box").length > 0) {
        $(".radio-box").iCheck({
            checkboxClass: "icheckbox_square-green",
            radioClass: "iradio_square-green",
        })
    }

    if ($(".select-time").length > 0) {
         var lang =  getCookie('lang');
         if(lang == 'zh_CN' || !lang){
            lang = 'cn'
         }else {
             lang = 'en'
         }
        layui.use('laydate', function () {
            var laydate = layui.laydate;
            var start = laydate.render({
                elem: '#startTime',
                theme: 'molv',
                type: 'datetime',
                lang: lang,
                istime: true,
                istoday: false,
                done: function (value,dates) {
                    end.config.min ={
                        year:dates.year,
                        month:dates.month-1, //关键
                        date: dates.date,
                        hours: dates.hours,
                        minutes: dates.minutes,
                        seconds : dates.seconds
                    };
                }
            });
            var end = laydate.render({
                elem: '#endTime',
                theme: 'molv',
                type: 'datetime',
                lang: lang,
                istime: true,
                istoday: false,
                done: function (value,dates) {
                    start.config.max= {
                        year: dates.year,
                        month: dates.month-1,//关键
                        date: dates.date,
                        hours: dates.hours,
                        minutes: dates.minutes,
                        seconds : dates.seconds-1
                    }
                }
            });
            // laydate.render(start)
            // laydate.render(end)
        });
    }
});

/** 创建选项卡 */
function createMenuItem(dataUrl, menuName) {
    dataIndex = $.common.random(1, 100),
        flag = true;
    if (dataUrl == undefined || $.trim(dataUrl).length == 0) return false;
    var topWindow = $(window.parent.document);
    // 选项卡菜单已存在
    $('.menuTab', topWindow).each(function () {
        if ($(this).data('id') == dataUrl) {
            if (!$(this).hasClass('active')) {
                $(this).addClass('active').siblings('.menuTab').removeClass('active');
                $('.page-tabs-content').animate({marginLeft: ""}, "fast");
                // 显示tab对应的内容区
                $('.mainContent .Devin_iframe', topWindow).each(function () {
                    if ($(this).data('id') == dataUrl) {
                        $(this).show().siblings('.Devin_iframe').hide();
                        return false;
                    }
                });
            }
            flag = false;
            return false;
        }
    });
    // 选项卡菜单不存在
    if (flag) {
        var str = '<a href="javascript:;" class="active menuTab" data-id="' + dataUrl + '">' + menuName + ' <i class="fa fa-times-circle"></i></a>';
        $('.menuTab', topWindow).removeClass('active');

        // 添加选项卡对应的iframe
        var str1 = '<iframe class="Devin_iframe" name="iframe' + dataIndex + '" width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
        $('.mainContent', topWindow).find('iframe.Devin_iframe').hide().parents('.mainContent').append(str1);

        // 添加选项卡
        $('.menuTabs .page-tabs-content', topWindow).append(str);
    }
    return false;
}

/** 设置全局ajax超时处理 */
$.ajaxSetup({
    complete: function (XMLHttpRequest, textStatus) {
        if (textStatus == "parsererror") {
            $.modal.confirm("登陆超时！请重新登陆！", function () {
                window.location.href = ctx + "login";
            })
        }
    }
});
var formatMoney = function (value, type) {
    if (value == '0' || value == 0) {
        return 0;
    }
    if (value == null || value == '') {
        return '';
    }
    if (type == null || type == '') {
        type = 2;
    }
    var num = value.toFixed(type).replace(/(\d)(?=(\d{3})+\.)/g, '$1,');
    return num;
};

function timestampToTime(timestamp) {
    var date = new Date(timestamp * 1000);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
    var D = date.getDate() < 10 ? '0'+date.getDate() + ' ':date.getDate() + ' ';
    var h = date.getHours() < 10 ? '0'+date.getHours() + ':':date.getHours() + ':';
    var m = date.getMinutes() < 10 ? '0'+date.getMinutes() + ':':date.getMinutes() + ':';
    var s = date.getSeconds() < 10 ? '0'+date.getSeconds():date.getSeconds();
    return Y + M + D + h + m + s;
}

function numberToIp(number) {
    var ip = "";
    if (number <= 0) {
        return ip;
    }
    var ip3 = (number << 0) >>> 24;
    var ip2 = (number << 8) >>> 24;
    var ip1 = (number << 16) >>> 24;
    var ip0 = (number << 24) >>> 24

    ip += ip3 + "." + ip2 + "." + ip1 + "." + ip0;

    return ip;
}
function getCookie(cookieName) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for(var i = 0; i < arrCookie.length; i++){
        var arr = arrCookie[i].split("=");
        if(cookieName == arr[0]){
            return arr[1];
        }
    }
    return "";
}