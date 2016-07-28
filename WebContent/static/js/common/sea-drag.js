/**
 * 弹不框组件
 * User: wulewei
 * Date: 2015-08-17
 */
define(function (require, exports) {
    //引入jquery模块
    var $ = require("jquery");
    //引入基础方法模块
    var util = require("util");
    // 默认配置

    /**
     * 弹出框可拖拽
    */
    exports.init = function (opts) {
        var defaults = {
            rangeZoom : 0
        }
        var config = $.extend(true, {}, defaults, opts);
        var $drag = config.drag;
        var $move = config.move;
        var $obj = null;
        if (!$move)  {
            $move = $drag;
        }
        $drag.mousedown(function(ev) {
            var ev,oTarget,dashedBox,oldX,oldY,nowX,nowY,oW,oH,rangeZoom;
            ev = ev || window.event;
            oTarget = ev.srcElement || ev.target;
            if (oTarget.tagName == "A") return false;
            oW = $move.width();
            oH = $move.height();
            oldX = ev.clientX - $move.offset().left;
            oldY = ev.clientY - ($move.offset().top - $(window).scrollTop());
            rangeZoom = config.rangeZoom;
            $(document).unbind("mousemove").bind("mousemove", function(ev){
                if (ev.clientX < 10 || ev.clientX > $(window).width() - 10 || ev.clientY < 10 || ev.clientY > $(window).height() - 10) {
                    $(document).unbind("mousemove");
                    $(document).unbind("mouseup");
                }
                nowX = ev.clientX - oldX;
                nowY = ev.clientY - oldY;
                if (nowX > $(window).width() - oW - (10 * rangeZoom)) nowX = $(window).width() - oW - (10 * rangeZoom);
                if (nowY > $(window).height() - oH - (10 * rangeZoom)) nowY = $(window).height() - oH - (10 * rangeZoom);
                if (nowX <= (10 * rangeZoom)) nowX = (10 * rangeZoom);
                if (nowY <= (10 * rangeZoom)) nowY = (10 * rangeZoom);
                $move.css({
                    left: nowX,
                    top: nowY
                });
                return false;
            });
            $(document).unbind("mouseup").bind("mouseup", function(ev){
                $(document).unbind("mousemove");
                $(document).unbind("mouseup");
            });
            return false;
        });            
    };
});