 /**
  * 弹不框组件
  * User: wulewei
  * Date: 15-5-25
  */
define(function (require, exports) {
    
    /**
     * jquery
     * 获取包装后对象和方法
     */
    var jQuery = $ = require("jquery");
    

    /**
     * 弹出对话框底层方法
     * @param paramsObj
     * title 标题
     * content 弹出层内容：来源取决于type
     * type 类型两位数字
     * 第一位标识content取值：1 直接显示content内容 ，2 content内容为url，3 content内容为url并放在frame中，4 制定ID内容
     * 第二位标识取值：1生成确认和取消按钮 ，2生成关闭按钮
     * width
     * height
     * callback 按钮回调函数
     */
    function open(params) {
        if (!params.title) params.title = "信息";
        buildDialog(initOpenParam(params));
    };


    /**
     * 初始化弹出框入参
     * @param params
     * @returns {{id: *, title: *, content: *, type: *, width: *, height: *, callback: (*|callback|g._listeners.callback), isClose: boolean}}
     */
    function initOpenParam(params) {
        params = typeof(params) == 'object' ? params : {};
        return {
            id: params.id ? params.id : new Date().getTime(),
            title: params.title ? params.title : "提示",
            content: params.content ? params.content : "还没有内容！",
            type: params.type ? params.type : "12",
            width: params.width,
            height: params.height,
            target: params.target || $("body"),
            callback: params.callback,
            btnTxt : params.btnTxt || "确定"
        }
    };


    /**
     * 生成弹出框内容并绑定事件
     * @param paramsObj
     * @returns {html string}
     */
    var maskSize = 0;
    function buildDialog(paramsObj) {
        var type = Math.floor(paramsObj.type.charAt(0));
        switch (type) {
            case 1: buildContent(paramsObj); break;
            case 2: buildContentByUrl(paramsObj); break;
            case 3: buildFrameByUrl(paramsObj); break;
            case 4: buildContentById(paramsObj); break;
            default:;
        }
        if(!maskSize){
            paramsObj.target.append('<div class="dialog-mask" onSelectStart="return false"></div>');
            maskSize = 1;
        }
        $(".dialog-mask").show();
    };


    /**
     * 当前页面弹出层，内容为paramsObj.content
     * @param paramsObj
     */
    function buildContent(paramsObj){
        var containerHtml = buildCommonContent(paramsObj);
        paramsObj.target.append(containerHtml);
    };


    /**
     * 当前页面弹出层，内容为指定url异步获取
     * @param paramsObj
     */
    function buildContentByUrl(paramsObj) {
        var containerHtml,url,dialogId;
        url = paramsObj.content;
        dialogId = "dialog" + paramsObj.id;
        paramsObj.content = "<span>数据加载中...</span>";
        containerHtml = buildCommonContent(paramsObj);
        paramsObj.target.append(containerHtml);
        $.ajax({
            type: "GET",
            url: url,
            cache: false,
            success: function (result) {
                if (result && result.result && result.result.retMsg) {
                    paramsObj.content = result.result.retMsg;
                } else {
                    paramsObj.content = result;
                }
            },
            error: function (xhr, error) {
                paramsObj.content = "请求出现错误" + error;
            },
            complete : function (xhr,ts) {
                $("#" + dialogId).find(".dialog-content").html(paramsObj.content);
            }
        });
    };


    /**
     * 当前页面弹出层，内容为指定url异步获取
     * @param paramsObj
     */
    function buildFrameByUrl(paramsObj) {
        var containerHtml,url;
        url = paramsObj.content;
        paramsObj.content = '';
        paramsObj.content += '<iframe align="middle" marginwidth="0" marginheight="0" frameborder="no" ';
        paramsObj.content += 'width="100%" height="100%" src="';
		paramsObj.content += url;
        paramsObj.content += '"></iframe>';
        containerHtml = buildCommonContent(paramsObj);
        paramsObj.target.append(containerHtml);
    };

   
    /**
     * 当前页面弹出层，内容为指定id下内容
     * @param paramsObj
     */
    function buildContentById(paramsObj) {
        var containerHtml = "";
        var content = $("#" + paramsObj.content).html();
        paramsObj.content = content;
        containerHtml = buildCommonContent(paramsObj);
        paramsObj.target.append(containerHtml);
    };


    /**
     * 构建弹出层基础架构
     * @param paramsObj     
     */
    function buildCommonContent(paramsObj) {
        var mainarr = [],
        id = paramsObj.id,
        width = paramsObj.width ? Math.floor(paramsObj.width) : 300,
        height = paramsObj.height ? paramsObj.height  : 100,
        left = ($(window).width() - width - 20) / 2 > 10 ? ($(window).width() - width - 20) / 2 : 10,
        top = ($(window).height() - height - 140) / 2 > 10 ? ($(window).height() - height - 140) / 2 : 10,
        wrapStyle = 'left:' + left + 'px;' + 'top:' + top + 'px',
        contentStyle = 'width:' + width + 'px; height:' + height + 'px;',
        operateType = paramsObj.type.charAt(1),
        dialogId = 'dialog' + id,
        confirmId = 'confirm' + id,
        cancelId = 'cancel' + id;

        mainarr.push('<div class="dialog" id="' + dialogId + '"');
        mainarr.push(' style="' + wrapStyle + '">');
        mainarr.push('<h2>' + paramsObj.title + '</h2>');
        mainarr.push('<div class="dialog-content" style="' + contentStyle + '">');
        mainarr.push(paramsObj.content);
        mainarr.push('</div><div class="dialog-foot">');
        if (operateType == 1) {
            mainarr.push('<button id="' + confirmId + '" class="button bg-blue mr10">'+paramsObj.btnTxt+'</button>');
            mainarr.push('<button id="' + cancelId + '" class="button bg-blue mr10">取 消</button>');
        } else if (operateType == 2) {
            mainarr.push('<button id="' + cancelId + '" class="button bg mr10">关 闭</button>');
        }
        mainarr.push('</div></div>');

        $("#" + confirmId).die("click").live("click", function (){
            if (paramsObj.callback) {
                if (paramsObj.type.charAt(2)) {
                    textarea = $(this).parents(".dialog").find("textarea").val();
                    paramsObj.callback(dialogId,textarea);
                } else {
                    paramsObj.callback(dialogId);
                }
            }
        });

        $("#" + cancelId).die("click").live("click", function (){
            exports.close(dialogId);
        });

        return mainarr.join("");
    };



    /***********************************************************************
     * 下面方法为调用层
     * confirm 询问层
     * openUrl 引入url内容
     * openFrame 引入url内容到iframe(外部系统调用，内部带交互不要使用此方法)
     */


    /**
     * 关闭弹出框
     * @param id 弹出框id;
     */
    exports.close = function (id, callback) {
        $("#" + id).remove();
        if (!$(".dialog").size()) $(".dialog-mask").hide();
        if (callback)  callback();
    };


    /**
     * 提示框
     * @param id 内容
     * @param callback 确认回调函数
     */
    exports.loading = function () {
        var params = {};
        params.title = "提示";
        params.type = "12";
        params.content = content;
        params.width = "300";
        open(params);
    };


    /**
     * 基本
     * @param content 内容
     * @param callback 确认回调函数
     */
    exports.alert = function (content, target) {
        var params = {};
        params.title = "提示";
        params.type = "12";
        params.content = content;
        params.width = "220";
        params.height = "40";
        if (target) params.target = target;
        open(params);
    };


    /**
     * 提示框
     * @param content 辅助文字
     * @param callback 确认回调函数
     */
    exports.prompt = function (content,callback,target) {
        var params = {};
        var textarea = '<textarea rows="5" cols="40" class="mt10"></textarea>';
        params.title = "提示";
        params.type = "111";
        params.content = content;
        params.width = "300";
        if (typeof params.content === "function") {
            params.callback = params.content;
            params.content = textarea;
        } else {
            params.content = '<p>' + content +'</p>' + textarea;
            params.callback = callback;
        }
        if (target) params.target = target;
        open(params);
    };


    /**
     * 询问层确认层
     * @param content 内容
     * @param callback 确认回调函数
     */
    exports.confirm = function (content, callback ,target) {
        var params = {};
        params.title = "提示";
        params.type = "11";
        params.content = content;
        params.width = "300";
        if (arguments.length == 3) {
            if ( typeof callback === "function") {
                params.callback = callback;
                params.target = target;
            } else {
                params.callback = target;
                params.target = callback;
            }              
        } else if (typeof callback === "function") {
            params.callback = callback;
        } else {
            params.target = callback;
        }
        open(params);
    };


    /**
     * 请求URL内容
     * @param title 标题
     * @param url 请求地址
     * @param callback 确认回调函数
     * @param target 插入目标位置
     */
    exports.openUrlContent = function (params) {
        params.content = params.url;
        params.target = params.target;
        params.type = "21";
        params.url = null;
        open(params);
    };


    /**
     * 引入iframe
     * @param title 标题
     * @param url 请求地址
     * @param callback 确认回调函数
     */
    exports.openFrameContent = function (params) {
        params.content = params.url;
        params.target = params.target;
        params.type = params.type || "31";
        params.url = null;
        open(params);
    };


    /**
     * 引入Id内容
     * @param title 标题
     * @param id 请求地址
     * @param callback 确认回调函数
     */
    exports.openIdContent  = function (params) {
        params.content = params.content;
        params.target = params.target;
        params.type = "41";
        params.width = params.width || 400;
        params.height = params.height || 160;
        params.id = params.id;
        open(params);
    };


    /**
     * 弹出框可拖拽
    */
    $("body").on("mousedown",".dialog h2",function(ev){
        var ev,dialog,dashedBox,oldX,oldY,nowX,nowY,oW,oh,oMl;
        ev = ev || window.event;
        dialog = $(this).parent();
        oW = dialog.width();
        oH = dialog.height();
        oMl = Math.floor(dialog.css("margin-left").replace("px",""));
        dashedBox = $('<div class="dialog-dashed"></div>')
        oldX = ev.clientX - (dialog.offset().left - oMl);
        oldY = ev.clientY - dialog.offset().top;
        dialog.parent().append(dashedBox);

        $(document).unbind("mousemove").bind("mousemove", function(ev){
            if (ev.clientX < 20 || ev.clientX > $(window).width() - 20 || ev.clientY < 20 || ev.clientY > $(window).height() - 20) {
                $(document).unbind("mousemove");
                $(document).unbind("mouseup"); 
                dashedBox.remove();             
            }
            nowX = ev.clientX - oldX;
            nowY = ev.clientY - oldY;
            if (nowX < (10 - oMl)) nowX = 10 - oMl;
            if (nowY < 10) nowY = 10;
            if (nowX > $(window).width() - (oW + 10) - oMl) nowX = $(window).width() - (oW + 10) - oMl;
            if (nowY > $(window).height() - oH  - 10) nowY = $(window).height() - oH - 10;
            dashedBox.css({
                width : oW,
                height : oH,
                marginLeft : oMl,
                left: nowX,
                top: nowY                      
            });
            return false;
        });

        $(document).unbind("mouseup").bind("mouseup", function(ev){
            $(document).unbind("mousemove");
            $(document).unbind("mouseup");
            dashedBox.remove();
            dialog.css({"left":nowX,"top":nowY});
        });
        return false;
    });

});