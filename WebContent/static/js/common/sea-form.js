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
    //基础方法模块
    var util = require("util");
    //弹窗模块
    var dialog = require("dialog");
    //*存页面元素
    var elem = {};


    //获取页面基础元素
    function getElem (pageid) {
        elem.pageObj = util.paramJquery(pageid);
        //搜索模块
        elem.oSearch = elem.pageObj.find(".search");
        //检索按钮
        elem.query = elem.oSearch.find(".query");
        //列表模块
        elem.oTable = elem.pageObj.find(".table");
        //列表标题
        elem.oThead = elem.oTable.find("thead");
        //列表内容
        elem.oTbody = elem.oTable.find("tbody");
        //列表行
        elem.alists = elem.oTbody.find("tr");
        //全选
        elem.allCheck = elem.pageObj.find(".check-all");
        //复选框
        elem.checks = elem.pageObj.find(".check");
        //左右列表模块
        elem.dblist = elem.pageObj.find(".dblist");
        elem.moves = elem.dblist.find(".move");
        elem.allmove = elem.dblist.find(".allmove");
    }


    //绑定事件
    function bindEvent() {
        //查询操作
        elem.query.unbind("click").bind("click",function(){
            var oParent = elem.pageObj;
            var oSearch = oParent.find(".search");
            var oSearchForm = oSearch.find("form");
            var action = oSearchForm.attr("action");
            var params = oSearchForm.serialize();
            var oTable = oParent.find(".table");
            util.ajaxRefPart(oTable, action, params);
        });

        //单击行操作
        elem.alists.die().live("click",function(ev){
            var targetObj, checkContainer, checkSize, targetid, allCheck;
            var ev = ev || window.event;
            var oTarget = ev.target || ev.srcElement;
            var check = $(this).find("input[type='checkbox']");
            if (oTarget.tagName.toLowerCase() != "input" && oTarget.tagName.toLowerCase() != "a") {
                if (check.prop('checked')) {
                    check.attr('checked', false);
                } else {
                    check.attr('checked', true);
                    if ($(this).parents(".check-container").attr("data-type") == "only") {
                        check.parents("tr").siblings().find("input[type='checkbox']").attr('checked',false);
                    }
                }
                targetObj = $(this).parents(".check-container");
                if (targetObj.size() > 0) {
                    targetid = targetObj.attr("data-container");
                    allCheck = elem.pageObj.find(".check-all[data-target=" + targetid + "]");
                    checkContainer = elem.pageObj.find(".check-container[data-container=" + targetid + "]");
                    checkSize = elem.pageObj.find(".check-size[data-container=" + targetid + "]");
                    exports.eachCheck(checkContainer, allCheck, checkSize);
                }
                return false;
            }
        });

        //全选操作
        elem.allCheck.die().live("click",function() {
            var checks = null;
            var targetid = $(this).attr("data-target");
            if (targetid) {
                checks = elem.pageObj.find("[data-container=" + targetid + "]").find("input[type='checkbox']");
            } else {
                checks = elem.pageObj.find("input[type='checkbox']");
            }
            checks.attr("checked", $(this).prop("checked"));
            if ($(this).prop("checked")) {
                elem.pageObj.find(".check-size[data-container=" + targetid + "]").html(checks.size());
            } else {
                elem.pageObj.find(".check-size[data-container=" + targetid + "]").html(0);
            }
        });

        //多选操作
        elem.checks.die().live("click",function(){
            var targetObj, checkContainer ,targetid, allCheck;
            if ($(this).parents("table").size()) {
                if ($(this).parents(".check-container").attr("data-type") == "only") {
                    $(this).parents("tr").siblings().find("input[type='checkbox']").attr('checked', false);
                }
            }
            targetObj = $(this).parents(".check-container");
            if (targetObj.size() > 0) {
                targetid = targetObj.attr("data-container");
                allCheck = elem.pageObj.find(".check-all[data-target=" + targetid + "]");
                checkContainer = elem.pageObj.find(".check-container[data-container=" + targetid + "]");
                checkSize = elem.pageObj.find(".check-size[data-container=" + targetid + "]");
                exports.eachCheck(checkContainer, allCheck, checkSize);
                exports.eachCheck(checkContainer, allCheck);
            }
        });

        //检索收起展开
        elem.oSearch.off().on("click",".more",function(){
            if ($(this).attr("data-status") == "hide"){
                $(this).attr("data-status","show");
                $(this).html("收起");
                $(this).parent().find("tr").each(function(){
                    if ($(this).index() > 0) {
                        $(this).attr("class","");
                        $(this).find("input[type='text'],select").val("");
                    }
                });
            } else {
                $(this).attr("data-status","hide");
                $(this).html("显示更多");
                $(this).parent().find("tr").each(function(){
                    if ($(this).index() > 0) {
                        $(this).attr("class","none"); 
                    }
                });
            }
        });

        //左右移动
        elem.moves.die().live("click",function(){
            if ($(this).parents(".userlist").hasClass("db-l")) {
                if (elem.dblistR.find("tr").eq(0).size()) {
                    $(this).html("&#xf0112;").parents("tr").insertBefore(elem.dblistR.find("tr").eq(0));
                } else {
                    elem.dblistR.append($(this).html("&#xf0112;").parents("tr"));
                }
            } else {
                if (elem.dblistL.find("tr").eq(0).size()) {
                    $(this).html("&#xf0114;").parents("tr").insertBefore(elem.dblistL.find("tr").eq(0));
                } else {
                    elem.dblistL.append($(this).html("&#xf0114;").parents("tr"));
                }
            }
            if (exports.dbMoveFn) exports.dbMoveFn();
            return false;
        });

        //全部移动 批量移动
        elem.allmove.die().live("click",function(){
            var dblist = $(this).parents(".dblist ");
            var dblistL = dblist.find(".db-l").find("tbody");
            var dblistR = dblist.find(".db-r").find("tbody");
            if ($(this).parents(".userlist").hasClass("db-l")) {
                if (dblistR.find("tr").size()) {
                    dblistL.find(".move").html("&#xf0112;");
                    dblistL.find("input:checked").parents("tr").insertBefore(dblistR.find("tr").eq(0));
                } else {
                    dblistL.find(".move").html("&#xf0112;");
                    dblistR.append(dblistL.find("input:checked").parents("tr"));
                }
            } else {
                if (dblistL.find("tr").size()) {
                    dblistR.find(".move").html("&#xf0114;");
                    dblistR.find("input:checked").parents("tr").insertBefore(dblistL.find("tr").eq(0));
                } else {
                    dblistR.find(".move").html("&#xf0114;");
                    dblistL.append(dblistR.find("input:checked").parents("tr"));
                }
            }
            exports.eachCheck($(".db-l").find("table"));
            exports.eachCheck($(".db-r").find("table"));
            return false;
        });
    }


    //初始化search
    function searchInit () {
        if (elem.oSearch.find(".none").size()){
            elem.oSearch.append('<div class="more" data-status="hide">显示更多</div>');   
        }
    }


    //初始化table
    function tableInit () {
        elem.oTable.each(function(){
            if ($(this).attr("data-type") == "only") {
                $(this).find(".allcheck").hide();
            }
        });
    }


    //初始化分页
    function pageInit() {
        elem.pageObj.find(".pageCssDefault a").die().live("click", function() {
            var url = $(this).attr("href");
            var target = $(this).parents(".table");
            util.ajaxRefPart(target,url);
            return false;
        });
    }


    /***********************************************************************
     * 下面方法为调用层
     */

    //列表检索
    exports.searchQuery = function (){
        elem.query.click();
    }


    //页面初始化
    exports.init = function (pageid) {
        //获取页面元素
        getElem(pageid);
        //绑定事件
        bindEvent(elem);
        //初始化search模块
        searchInit();
        //初始化table模块
        tableInit();
        //初始化分页模块
        pageInit();
    }

    //遍历checkbox
    exports.eachCheck = function (oParent, allCheck, checkSizeObj) {
        var total = oParent.find(".check[type='checkbox']").size();
        var checkSize = oParent.find(".check:checked").size();
        if (total && checkSize == total) {
            allCheck.attr("checked",true);
        } else {
            allCheck.attr("checked",false);
        }
        if (checkSizeObj) checkSizeObj.html(checkSize);
    }

    //获取选中行
    exports.getAllSelect = function () {
        var allSelRows = elem.pageObj.find("tbody").find("input:checked").parents("tr");
        return allSelRows;
    }

    //获指定行
    exports.getSelect = function (index) {
        var allSelRows = elem.oTbody.find("tr");
        var selRows = allSelRows.eq(index);
        return selRows;
    }


    //分页初始化
    exports.visitToAjax = function(pageid) {
        $(function(){
            var pageObj = util.paramJquery(pageid);
            var oTable = pageObj.find(".table")
            oTable.find(".pageCssDefault a").each(function() {
                var url = $(this).attr("href");
                $(this).attr("href", "javascript:void(0);");
                $(this).bind("click", function() {
                   util.ajaxRefPart(oTable,url);
                });
            });
       });
    }


});