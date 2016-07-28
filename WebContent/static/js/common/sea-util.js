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
    var jQuery = $ = require("jquery");
    var dialog = require("dialog");


    /**
     * 加载提示层
     * @param target 插入目标位置
     * @return id
     */
    function loading (target) {
        var target = target ? exports.paramJquery(target) : $("body");
        var loadingId = "loadingLayer" + new Date().getTime();
        if (!$(".loading-layer").size()) {
            var loadingHtml = "";
            loadingHtml += '<div class="loading-layer">';
            loadingHtml += '<div class="loading-tips">数据加载中...</div>';
            loadingHtml += '<div class="loading-mask"></div></div>';
            target.append(loadingHtml);
        }
        $(".loading-layer").show();
    }


    /**
     * 关闭加载提示
     */
    function unLoading () {
        $(".loading-layer").hide();
    }



    /***********************************************************************
     * 下面方法为调用层
     */



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
        };
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
     exports.isShow = function (obj){
        var obj = exports.paramJquery(obj);
        if (obj.is(":hidden")) {
            return false;
        }
        return true;
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
        } else {
            dialog.alert("error:util.paramJquery,msg:不是一个有效类型");
        }
        return paramObj;
    };


    /* 异步post请求，用于表单提交
     * @param url 请求地址
     * @param data 参数
     * @param callback 回调函数
     */
    exports.ajaxPost = function (url, param, callback, target) {
        var target = target ? target : $("body");
        loading(target);
        $.ajax({
            type: "POST",
            url: url,
            data: param,
            cache: false,
            success: function (result) {
                if(callback) callback(result);
            },
            error: function (xhr, error) {
                if(callback) callback(error);
            },
            complete : function (xhr,ts) {
                unLoading();
            }
        });
    };

    /* 异步post请求，用于表单提交
     * @param url 请求地址
     * @param data 参数
     * @param callback 回调函数
     */
    exports.ajaxGet = function (url, param, callback, target) {
        var target = target ? target : $("body");
        loading(target);
        $.ajax({
            type: "GET",
            url: url,
            data: param,
            cache: false,
            success: function (result) {
                if(callback) callback(result);
            },
            error: function (xhr, error) {
                if(callback) callback(error);
            },
            complete : function (xhr,ts) {
                unLoading();
            }
        });
    };

    /* 异步post请求，用于表单提交
     * @param url 请求地址
     * @param data 参数
     * @param callback 回调函数
     */
    exports.ajaxGetToJson = function (url, param, callback, target) {
        var target = target ? target : $("body");
        loading(target);
        $.ajax({
            type: "GET",
            dataType: "JSON",
            url: url,
            data: param,
            cache: false,
            success: function (result) {
                if(callback) callback(result);
            },
            error: function (xhr, error) {
                if(callback) callback(error);
            },
            complete : function (xhr,ts) {
                unLoading();
            }
        });
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
        exports.ajaxGet(url, param, function (result) {
            partId.html(result);
            if (callback) callback();
        }, partId);
    };


    /**
     * 选项卡
     * @param id
     * @param callback 回调函数
     */
    exports.tab = function (id, callback) {
        var oPar = exports.paramJquery(id);
        var tabNav = oPar.find(".tab-nav").children("li");
        var tabPanel = oPar.find(".tab-body").children(".panel");
        var tarId = "";
        tabNav.unbind("click").bind("click", function(){
            $(this).addClass("active").siblings().removeClass("active");
            tabPanel.eq($(this).index()).show().siblings().hide();
            if (callback) {
                tarId = tabPanel.eq($(this).index()).attr("tar-id") ? 
                        tabPanel.eq($(this).index()).attr("tar-id") : 
                        "index-" + $(this).index();
                callback(tarId);
            }
        });
        if (!oPar.find(".active").size()) {
            tabNav.eq(0).click();  
        }
    }
});