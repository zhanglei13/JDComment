/**
 * 公共方法
 * User: wulewei
 * Date: 15-5-25
 */
define(function (require, exports) {

	//jquery
	var $ = require("jquery");

	//基础方法模块
	var util = require("util");
    var dialog = require("dialog");

	//check组件
	var check = require("check");


   /**
	* 表格组件
	* @param id
	* @param url
	* @param tools
	*/
	exports.init = function (opts) {
		return new Grid(opts);
	};

	function Grid (opts) {
		this.config = opts;
		this.config.queryStatus = "normal";
		this.config.pn = null;
		this.config.ps = null;
		this.config.sign = this.config.sign == undefined ? true : this.config.sign;
		this.$render = util.paramJquery(this.config.render) || $("body");
		this.$grid = this.$render.find(".grid");
		this.$query = this.$grid.find(".query");
		this.$search = this.$grid.find(".search");
		this.$table = this.$grid.find(".table");
		this.type = this.$grid.attr("select-type");
		this.config.tools ? this.createTools() : "";
		this.type == "only" ? this.$table.find(".check-all").hide() : this.$table.find(".check-all").show()
		this.bindEvent();
		this.pageInit();
	}
	Grid.prototype = {
		//检索数据
		queryData : function (pn, ps) {
			var url = this.config.url;
			var param = util.serialize(this.$search);
			this.config.pn = null;
			this.config.ps = null;	
			if (!isNaN(parseFloat(pn)) && !isNaN(parseFloat(ps))) {
				this.config.pn = pn;
				this.config.ps = ps;
			}
			this.config.queryStatus = status || "0";
			if (this.config.pn && this.config.ps) {
				param += "&pageSize=" + this.config.ps + "&currentPage=" + this.config.pn;
			}
			util.refPart({
				render : this.$table,
				url : url,
				data : param
			});
		},

		//创建工具栏
		createTools : function () {
			if (this.config.tools.length) {
				if (this.$grid.find(".tools").size()) {
					this.$tools = this.$grid.find(".tools");
				} else {
					this.$tools = $('<div class="tools"></div>');
					this.$tools.insertBefore(this.$table);
				}
				var html = [];
				var toolsBtns = this.config.tools;
				var len = toolsBtns.length;
				for (var i = 0; i < toolsBtns.length; i++) {
					var text = toolsBtns[i].text;
					if (!toolsBtns[i].id) toolsBtns[i].id = new Date().getTime();
					toolsBtns[i].id = "tools" + toolsBtns[i].id;
					var id = toolsBtns[i].id;
					html.push('<input class="button bg-blue mr10" id="');
					html.push(id);
					html.push('" type="button" value="');
					html.push(text);
					html.push('">');
				}
				this.$tools.append('<div class="tools-buttons">' + html.join("") + '</div>').show();
			}
		},

		//创建工具栏
		getSelRows : function () {
			var rows = this.$table.find("tbody").find("input:checked").parents("tr");
			return rows;
		},

		//绑定事件
		bindEvent : function () {
			var self = this;

			//查询操作
			self.$query.unbind("click").bind("click", function () {
				if (self.config.onClickQuery) {
					self.config.onClickQuery.call(self);
				} else {
					self.queryData();
				}
			});

			//单击行选中
			self.$table.find("tbody").find("tr").die().live("click", function (){
	            var ev = ev || window.event;
	            var $target = ev.target || ev.srcElement;
	            var check = $(this).find("input[type='checkbox']");
	            var tagName = $target.tagName.toLowerCase();
	            if (tagName != "input" && tagName != "a") {
	                if (check.prop('checked') && self.type != "only") {
	                    check.attr('checked', false);
	                } else {
	                    check.attr('checked', true);
	                    if (self.type == "only") {
	                        check.parents("tr").siblings().find("input[type='checkbox']").attr('checked',false);
	                    }
	                }
	                self.rowsListener.eachCheckBox();
	            }
	            return false;
			});

			//工具栏按钮回调
			if (self.config.tools) {
				self.$tools.find(".button").click(function() {
					var toolsBtnObj = self.config.tools[$(this).index()];
					toolsBtnObj.callback.call(self, toolsBtnObj.id);
				});
			}

			//绑定操作行回调
			self.rowsListener = check.init({
				render : self.$grid,
				callback: function () {
					if (self.config.sign) self.signRows();
					if (self.config.onClickRow) self.config.onClickRow.call(self);
				}
			});
			
			//绑定栏目编辑
			self.$render.find(".edit").die().live("click", function (ev) {
				var field = $(this);
				var optStr = field.attr("data-param") || "{}";
				var ev = ev || window.event;
            	var otarget = ev.target || ev.srcElement;
            	if (otarget.tagName.toLowerCase() != "input" && !$(otarget).find(".button").size()) {
                    //var oldVal = $(this).text();
					var oldVal = $(this).find(".ed").text();
                    var type = $(this).attr("currenttype");
					var optHtml = '<input type="text" value="';
					//optHtml += oldVal;
					optHtml += '">';
					$(this).html(optHtml);

					$(this).find("input").focus();
					$(this).find("input").blur(function() {
                        var curVal = field.find("input[type='text']").val();
                        if (curVal == oldVal || (!self.config.onClickEdit && !url)) {
                            //field.html(oldVal);
                            field.html("<span class='ed'>"+oldVal+"</span><span style='float:right'><i class='icon iconfont'>&#xf0022;</i></span>");
                            return false;
                        }else{
                            var r = /^-?[0-9]\d*$/;
                            if(!r.test(curVal)){
                                dialog.alert('请输入整数！');
                                field.html("<span class='ed'>"+oldVal+"</span><span style='float:right'><i class='icon iconfont'>&#xf0022;</i></span>");
                                return false;
                            }else if(curVal < 0){
                                dialog.alert("不能为负数");
                                field.html("<span class='ed'>"+oldVal+"</span><span style='float:right'><i class='icon iconfont'>&#xf0022;</i></span>");
                                return false;
                            }
                        }

                        var options =  eval('(' + optStr + ')');
						var url = options.url || "";
						var param = {};
//						var paramStr = JSON.stringify(options.param);
                        var paramStr = options.param;
						if (paramStr) {
//							var paramArr = paramStr.split(",");
//
//							for (var i=0,j=paramArr.length;i<j;i++) {
//								var key = paramArr[i];
//								var dataVal = field.parents("tr").attr("data-" + key);
//								if (dataVal) {
//									param[key] = dataVal;
//								}
//							}
                            for(var i in paramStr){
                                param[i] = paramStr[i];
                            }
						}
                        if(type == 1){
                            options.name ? param[options.name] = curVal : param.currentQty = curVal;
                        }
                        if(type == 2){
                            options.name ? param[options.name] = curVal : param.lockQty = curVal;
                        }
                        if(type == 3){
                            options.name ? param[options.name] = curVal : param.recycleQty = curVal;
                        }
//						options.name ? param[options.name] = curVal : param.value = curVal;
                        paramStr[param] = curVal;
						//设置回调后不处理相关逻辑
						util.ajaxPost(url, param, function (result) {
							if (self.config.onClickEdit) {
								var item = {
									field : field,
									value : curVal,
									oldValue : oldVal,
									result : result
								}
								self.config.onClickEdit.call(self, item);

							} else {
								if (result.code == 0) {
									//field.html(curVal);
                                    field.html("<span class='ed'>"+oldVal+"</span>");
								} else {
									//field.html(oldVal);
                                    field.html("<span class='ed'>"+oldVal+"</span>");
								}
							}
						});
						return false;
					});
				}
			});

			//功能按钮点击
			self.$render.find(".command").find("a").die().live("click", function(){
				if (self.config.onClickCommand) {
					self.config.onClickCommand($(this).attr("data-command"));
				}
				return false;
			});

			//初始化选中高亮
			if (self.config.sign) self.signRows();
		},

		//标记行
		signRows: function () {
			this.$table.find("tr").removeClass("select");
			this.$table.find("tbody").find("input:checked").parents("tr").addClass("select");
		},

		//初始化分页
     	pageInit : function () {
     		var self = this;
	        this.$render.find(".pageCssDefault a").die().live("click", function() {
	            var url = $(this).attr("href");
	            var $target = self.$table;
	            util.refPart({
	            	render : $target,
	            	url : url
	            }, function(){
					if (self.config.sign) self.signRows();
	            });

	            return false;
	        });
    	}
	}
});