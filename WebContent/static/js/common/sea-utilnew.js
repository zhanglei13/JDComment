/**
 * 公共方法
 * User: wulewei
 * Date: 15-5-25
 */
define(function (require, exports) {

    /**
     * jquery
     * 获取包装后对象和方法
     */
    var $ = require("jquery");

    /**
     * 加载提示层
     * @param target 插入目标位置
     * @return id
     */
    function loading(target, tips) {
        var tips = tips || "请稍等...";
        var target = target ? exports.paramJquery(target) : $("body");
        var loadingId = "loadingLayer" + new Date().getTime();
        if (!$(".loading-layer").size()) {
            var loadingHtml = "";
            loadingHtml += '<div class="loading-layer">';
            loadingHtml += '<div class="loading-tips">' + tips + '</div>';
            loadingHtml += '<div class="loading-mask"></div></div>';
            target.append(loadingHtml);
        }
        $(".loading-layer").show();
    }
    exports.loading = loading;

    /**
     * 关闭加载提示
     */
    function unLoading() {
        $(".loading-layer").hide();
    }
    exports.unLoading = unLoading;

    /**
     * 为必填写添加星号
     */
    exports.addStar = function () {
        $(".req").each(function () {
            $th = $(this).parent().prev();
            if (!$th.text().startWith("\\*")) {
                $th.prepend("<span class=\"red\">* </span>");
            }
        });
    };


    /**
     * 防拷贝
     */
    exports.noCopy = function () {
        $(".nocopy").each(function (i) {
            $(this).css({
                color: "darkgray"
            });
            $(this).bind("selectstart", function () {
                event.returnValue = false;
            });
        });
    };


    /**
     * 回车自动跳转聚焦，backspace失效处理
     */
    exports.keydownEvent = function () {
        exports.enterEvent();
        $(document).bind("keydown", function (event) {
            exports.backspaceForbiden(event);
        });
    };


    /**
     * 字符串长度截取
     */
    exports.splitStr = function (str, length) {
        if (str.length > 0) {
            return str.substr(0, length) + "...";
        }
        return this;
    };

    /**
     * 数组中插入元素
     * 插入位置下标从0开始
     * @param array
     * @param element
     * @param position
     * @return array
     */
    exports.insert = function(array, elem, index) {
        if (typeof(index) == "undefined" || isNaN(index)) {
            return array.push(elem);
        }
        var arr = [];
        for (var i = 0; i < index; i++) {
            arr.push(array[i]);
        }
        arr.push(elem);
        for (var i = index; i < array.length; i++) {
            arr.push(array[i]);
        }
        return arr;
    };

    /**
     * 回车键特殊处理
     */
    exports.enterEvent = function () {
        var $input = $("input,select");
        $input.live("keypress", function (e) {
            var keyCode = exports.getEventKeyCode(e);
            if (keyCode == 13 && this.form) {
                for (var i = 0; i < this.form.elements.length; i++) {
                    if (this == this.form.elements[i]) break;
                }
                i = (i + 1) % this.form.elements.length;
                this.form.elements[i].focus();
                return false;
            } else {
                return true;
            }
        });
    };


    /**
     * backspace键特殊处理
     * @return
     */
    exports.backspaceForbiden = function (event) {
        var keyCode = exports.getEventKeyCode(event);
        //判断按键为backSpace键
        if (keyCode == 8) {
            //获取按键按下时光标做指向的element
            var elem = event.srcElement || event.target;
            //判断是否需要阻止按下键盘的事件默认传递
            var name = elem.nodeName;
            if (name != 'INPUT' && name != 'TEXTAREA') {
                return _stopIt(event);
            }
            var type_e = elem.type.toUpperCase();
            if (name == 'INPUT' && (type_e != 'TEXT' && type_e != 'TEXTAREA' && type_e != 'PASSWORD' && type_e != 'FILE')) {
                return _stopIt(event);
            }
            if (name == 'INPUT' && (elem.readOnly == true || elem.disabled == true)) {
                return _stopIt(event);
            }
        }
        function _stopIt(e) {
            if (e.returnValue) {
                e.returnValue = false;
            }
            if (e.preventDefault) {
                e.preventDefault();
            }
            return false;
        }
    };


    /**
     * 获得事件源的keycode
     * @param event
     * @returns {Number}
     */
    exports.getEventKeyCode = function (event) {
        return event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
    };




    /**
     * 获取可视区宽高
     */
    exports.clientWidth = function () {
        return $(window).width();
    };
    exports.clientHeight = function () {
        return $(window).height();
    };


    /**
     * 判断对象是否隐藏
     * @param obj
     * @returns boolean
     */
    exports.isShow = function (obj) {
        var obj = exports.paramJquery(obj);
        if (obj.is(":hidden")) {
            return false;
        }
        return true;
    };


    /**
     * 序列化当前对象下参数
     * @param name iframe name
     * @return iframe document
     */
    exports.serialize = function (id) {
        try {
            var $id = exports.paramJquery(id);
            if ($id.find("form").size()) {
                return $id.find("form").serialize();
            }
            var $form = $('<form></form>');
            $form.html($id.html());
            return $form.serialize();
        } catch (e) {
        }
    };


    exports.getQueryString = function (str, name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = str.match(reg);
        if (r != null) return (r[2]);
        return "";
    };


    /**
     * 添加cookie
     * @param name key
     * @param value value
     * @param iDay 过期时间
     * @returns boolean
     */
    exports.addCookie = function (name, value, iDay) {
        var date = new Date();
        var iDay = iDay;
        date.setTime(date.getTime() + iDay * 24 * 3600 * 1000);
        document.cookie = name + '=' + value + ';expires=' + date.toGMTString() + ';path=/';
    };


    /**
     * 获取cookie
     * @param name
     */
    exports.getCookie = function (name) {
        var arr = document.cookie.split('; ');
        for (var i = 0; i < arr.length; i++) {
            var arr2 = arr[i].split('=');
            if (arr2[0] == name) return arr2[1];
        }
        return "";
    };


    /**
     * 删除cookie
     * @param name
     */
    exports.removeCookie = function (name) {
        exports.addCookie(name, 1, -1);
    };


    /**
     * 函数参数初始化（对象和id兼容初始化）
     * @param param
     * @returns {*}
     */
    exports.paramJquery = function (param) {
        var paramObj;
        if (typeof(param) == 'object' && param instanceof jQuery) {
            paramObj = param;
        } else if (typeof(param) == 'object') {
            paramObj = $(param);
        } else if (typeof(param) == 'string') {
            paramObj = $("#" + param);
        }
        return paramObj;
    };


    /* 异步post请求，用于表单提交
     * @param url 请求地址
     * @param data 参数
     * @param callback 回调函数
     */
    exports.ajaxPost = function (url, param, callback) {
        var opts = {
            type: "POST",
            cache: false,
            url: url,
            data: param,
            callback: callback
        };
        exports.ajax(opts);
    };


    /* 异步get请求，用于表单提交
     * @param url 请求地址
     * @param data 参数
     * @param callback 回调函数
     */
    exports.ajaxGet = function (url, param, callback) {
        var opts = {
            type: "GET",
            cache: false,
            url: url,
            data: param,
            callback: callback
        };
        exports.ajax(opts);
    };


    /**
     * 局部ajax请求
     * @param id 要刷新内容id
     * @param url
     * @param data 参数
     * @param callback 回调函数
     */
    exports.ajaxRefPart = function (id, url, param, callback) {
        var partId = exports.paramJquery(id);
        exports.ajax({
            url: url,
            type:'GET',
            data: param,
            callback: function (result) {
                partId.html(result);
                if (callback) callback();
            }
        });
    };


    /* ajax请求
     * @param url 请求地址
     * @param data 参数
     * @param callback 回调函数
     */
    var showLoadingNum = 0;
    exports.ajax = function (opts, showLoading) {
        var showLoading = showLoading == undefined ? true : showLoading;
        if (showLoading) {
            loading();
            showLoadingNum ++;
        }
        var defaults = {
            type: "POST",
            cache: false,
            timeout: 60000
        };
        var ajaxParam = $.extend(defaults, opts);
        ajaxParam.success = function (result) {
            if (this.callback) this.callback(result);
            if (showLoadingNum > 1) {
                showLoadingNum--;
            } else {
                showLoadingNum = 0;
                unLoading();
            }
        };
        ajaxParam.error = function (xhr, error) {
            if (this.callback) this.callback(error);
            if (showLoadingNum > 1) {
                showLoadingNum--;
            } else {
                showLoadingNum = 0;
                unLoading();
            }
        };
        ajaxParam.complete = function (xhr, status) {
            if (status == 'timeout') {
                ajaxTimeout.abort();
                showLoadingNum = 0;
                unLoading();
            }
        };
        var ajaxTimeout = $.ajax(ajaxParam);
    };


    /**
     * 局部ajax请求
     * @param id 要刷新内容id
     * @param url
     * @param data 参数
     * @param callback 回调函数
     */
    exports.refPart = function (opts, callback) {
        var $target = exports.paramJquery(opts.render);
        opts.id = null;
        opts.type = 'get';
        var ajaxParam = $.extend({
            callback: function (result) {
                $target.html(result);
                if (callback) callback();
            }
        }, opts);
        exports.ajax(ajaxParam);
    };


    /**
     * 获取iframe对象
     * @param name iframe name
     * @return iframe document
     */
    exports.getFrameElem = function (id) {
        return exports.paramJquery(id).find("iframe").contents();
    };


    /**
     * 数字格式转换成千分位
     * @param num
     * @returns {string}
     */
    exports.commafy = function (num) {
        var oldNum = num;
        try{
            if($.trim((num+""))=="") return '';
            if(isNaN(num)) return oldNum;
            num = num + '';
            if(/^.*\..*$/.test(num)) {
                var pointIndex = num.lastIndexOf('.');
                var intPart = num.substring(0, pointIndex);
                var pointPart = num.substring(pointIndex + 1, num.length);
                intPart = intPart + '';
                var re =/(-?\d+)(\d{3})/;
                while(re.test(intPart)) {
                    intPart = intPart.replace(re,'$1,$2');
                }
                num = intPart + '.' + pointPart;
            }else{
                num = num + '';
                var re = /(-?\d+)(\d{3})/;
                while(re.test(num)){
                    num =num.replace(re,'$1,$2');
                }
            }
            return num;
        }catch (e){
            return oldNum;
        }
    };


    /**
     * 获取对象data-属性
     * @param param 要获取的key
     * @return value
     */
    exports.getAttribute = function (object, key) {
        var arr = [];
        object.each(function () {
            var val = $(this).attr(key);
            if (val) arr.push(val);
        });
        return arr.join(",");
    };


    /**
     * 获取url参数
     * @param  参数名称
     * @return 参数值
     */
    exports.getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return (r[2]);
        return "";
    };


    /**
     * 当前页存入cookie
     * @param page
     */
    exports.addCurPageNo = function (page) {
        var pageNo = page;
        if (!pageNo) pageNo = 1;
        exports.addCookie('JD_UTIL_CURPAGENO', pageNo, 999);
    };

    /**
     * 获取当前页
     * @param page
     * @returns {*}
     */
    exports.getCurPageNo = function (page) {
        var pageNo = exports.getCookie('JD_UTIL_CURPAGENO');
        if (!pageNo) pageNo = 1;
        exports.removeCookie('JD_UTIL_CURPAGENO');
        return pageNo;
    };

    /**
     * 获取当前日期
     * @return string
     */
    exports.getDateString = function (day) {
        var day = day || 0;
        var oDate = new Date();
        oDate.setDate(oDate.getDate() + day);
        var y = oDate.getFullYear();
        var m = oDate.getMonth() + 1;
        var d = oDate.getDate();
        return y + '-' + exports.padNum(m) + '-' + exports.padNum(d);
    };

    /**
     * 补0
     * @param  num
     * @return string
     */
    exports.padNum = function (num) {
        var n = parseInt(num, 10);
        if (isNaN(n)) return num;
        return n > 9 ? '' + n : '0' + n;
    };


    /**
     * 获取当前日期
     * @return string
     */
    exports.getDateString = function (day) {
        var day = day || 0;
        var oDate = new Date();
        oDate.setDate(oDate.getDate() + day);
        var y = oDate.getFullYear();
        var m = oDate.getMonth() + 1;
        var d = oDate.getDate();
        return y + '-' + exports.padNum(m) + '-' + exports.padNum(d);
    };


    /**
     * 判断年份是否为润年
     * @param {Number} year
     */
    exports.isLeapYear = function (year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    };


    /**
     * 获取某一年份的某一月份的天数
     * @param year
     * @param month
     * @return date
     */
    exports.getMonthDays = function (year, month) {
        return [31, null, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31][month] || (exports.isLeapYear(year) ? 29 : 28);
    };


    /**
     * 获取某年的某天是第几周
     * @param y
     * @param m
     * @param d
     * @return weekNumber
     */
    exports.getWeekNumber = function gwn(y, m, d) {
        var oDate = new Date(y, m - 1, d),
            year = oDate.getFullYear(),
            month = oDate.getMonth(),
            days = oDate.getDate();
        for (var i = 0; i < month; i++) {
            days += exports.getMonthDays(year, i);
        }
        var yearFirstDay = new Date(year, 0, 1).getDay() || 7;
        var week = null;
        var afDays = days - (7 - yearFirstDay + 1);
        if (yearFirstDay > 4) {
            week = Math.ceil(afDays / 7);
        } else {
            week = Math.ceil(afDays / 7) + 1 ;
        }
        return week;
    };


    /**
     * 获取输入日期是本周第一或最后一天
     * @param  {[type]} y [description]
     * @param  {[type]} m [description]
     * @param  {[type]} d [description]
     * @return {[type]}   [description]
     */
    exports.getWeekDays = function (y, m, d) {
        var oDate = new Date(y, m - 1, d);
        var curDay = oDate.getDay();
        var weeks = "1234560";
        var f = 0 - weeks.indexOf(curDay);
        var l = 6 - weeks.indexOf(curDay);
        oDate.setDate(oDate.getDate() + f);
        var firstDate = oDate.getFullYear() + '-' + exports.padNum(oDate.getMonth() + 1) + '-' + exports.padNum(oDate.getDate());
        var oDate = new Date(y, m - 1, d);
        oDate.setDate(oDate.getDate() + l);
        var lastDate = oDate.getFullYear() + '-' + exports.padNum(oDate.getMonth() + 1) + '-' + exports.padNum(oDate.getDate());
        return {
            firstDate: firstDate,
            lastDate: lastDate
        }
    };


    exports.minDate = function(d1,d2){
        var n1 = parseInt(d1.replace(new RegExp(/-/g),''), 10);
        var n2 = parseInt(d2.replace(new RegExp(/-/g),''), 10);
        return n1 < n2 ? d1 : d2;
    };


    /**
     * 选项卡
     * @param id
     * @param callback 回调函数
     */
    exports.tab = function (render, callback) {
        var oParent = exports.paramJquery(render);
        var tabNav = oParent.find(".tab-nav").children("li");
        var tabPanel = oParent.find(".tab-body").children(".panel");
        var panelSize = tabPanel.size();
        var tarId = "";
        tabNav.unbind("click").bind("click", function () {
            $(this).addClass("active").siblings().removeClass("active");
            if (panelSize>1){
                tabPanel.eq($(this).index()).show().siblings().hide();
            } else {
                tabPanel.show();
            }
            if (callback) {
                tarId = tabPanel.eq($(this).index()).attr("tar-id") ? tabPanel.eq($(this).index()).attr("tar-id") : $(this).index();
                callback(tarId);
            }
        });
        if (!oParent.find(".tab-nav").find(".active").size()) {
            tabNav.eq(0).click();
        }
    };
});
