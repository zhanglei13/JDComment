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
	//弹窗模块
	var dialog = require("dialog");



   /**
	* 表格组件
	* @param id
	* @param url
	* @param tools
	*/
	exports.grid = function (opts) {
		try{return new Grid(opts)}catch(e){}
	};

	function Grid (opts) {
		this.config = opts;
		this.render = this.config.render;
		this.$grid = util.paramJquery(this.render).find(".grid");
		this.$query = this.$grid.find(".query");
		this.$search = this.$grid.find(".search");
		this.$table = this.$grid.find(".table");
		if (this.config.tools) this.createTools();
		if (this.config.check) {
			var self = this;
			this.check = exports.check({
				render : this.$table,
				callback : function (items) {
					if (self.config.check.sign) {
						self.$table.find("tr").removeClass("select")
						items.each(function() {
							$(this).parents("tr").addClass("select");
						});
					}
					self.config.check.callback(items);
				}
			});
		}
		this.bindEvent();
	}

	Grid.prototype = {
		queryData : function () {
			this.$query.click();
		},
		
		createTools : function () {
			var self = this; 
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
					html.push('<div class="tools-buttons">');
					html.push('<input class="button bg-blue" id="');
					html.push(id);
					html.push('" type="button" value="');
					html.push(text);
					html.push('">');;
				}
				this.$tools.append(html.join("")).show();
				this.$tools.find(".button").click(function() {
					var toolsBtnObj = toolsBtns[$(this).index()];
					toolsBtnObj.callback.call(self, toolsBtnObj.id);
				});
			}
		},

		getChecks : function () {
			return this.$table.find("tbody").find("input:checked");
		},

		bindEvent : function () {
			var self = this; 
			self.$query.unbind("click").bind("click", function () {
				var url = self.config.url;
				var param = util.serialize(self.$search);
				util.refPart({
					render : self.$table,
					url : url,
					data : param
				});
			});

			self.$table.find("tbody").find("tr").die().live("click", function (){
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
	                self.check.eachCheck();
	                return false;
	            }
			});

		}
	}




	exports.customSel = function (opts) {
		try{new CustomSel(opts)}catch(e){}
	};


	//自定义下拉框
	function CustomSel (opts) {
		this.$render = util.paramJquery(opts.render);
		this.$input = this.$render.find("input");
		this.items = opts.items;
		this.callback = opts.callback;
		this.filter = opts.filter;
		this.curVal = this.$input.val();
		this.init();
		this.bindEvent();
	}

	//自定义下拉框
	CustomSel.prototype = {
		init : function () {
			this.$container = $('<div class="custom-select"></div>');
			this.$container.append('<input class="custom-select-input" onSelectStart="return false"><div class="custom-select-list"></div>');
			if (this.filter) {
				this.$container.find(".custom-select-list").append('<div class="custom-select-filter"><input type="text"><input class="button bg-green ml10" type="button" value="查询"></div>');
			}
			var list = this.createItems();
			this.$container.find(".custom-select-list").append('<ul>' + list + '</ul>');
			this.$render.append(this.$container);
		},
		
		createItems : function (opts) {
			if (opts) this.items = opts;
			var itemsArr = [];
			for (var i=0; i<this.items.length;i++) {
				if (this.curVal == this.items[i].value) {
					this.$input.val(this.items[i].value);
					this.$container.find(".custom-select-input").text(this.items[i].text);
				}
				itemsArr.push('<li class="custom-select-option">'+this.items[i].text+'</li>');
			}
			return itemsArr.join("");
		},

		bindEvent : function () {
			var self = this;
			//显示隐藏下拉列表
			self.$render.find(".custom-select-input").die().live("click", function(){
				if (util.isShow($(this).next())) {
					self.hideItems();
				} else {
					self.showItems();
				}
				return false;
			});

			//点击空白收起
			$("body").click(function(ev){
				var ev = ev || window.event;
				var otarget = ev.target || ev.srcElement;
				if (!$(otarget).parents(".custom-select").size()) {
					self.hideItems();
				}
			});

			//选择内容
			self.$render.find(".custom-select-option").die().live("click", function(){
				var $wrap = $(this).parents(".custom-select");
				self.hideItems();
				$wrap.find(".custom-select-input").html($(this).html());
				self.$input.val(self.items[$(this).index()].value);
				$wrap.next().val($(this).attr("data-val"));
				if (self.callback) self.callback(self.items[$(this).index()]);
				return false;
			});

			//检索
			self.$render.find(".custom-select-filter").find("input[type='button']").die().live("click", function(){
				var val = $(this).prev().val();
				for (var i=0; i<self.items.length;i++) {
					if (self.items[i].text.indexOf(val) > -1) {
						self.$render.find(".custom-select-option").eq(i).show();
					} else {
						self.$render.find(".custom-select-option").eq(i).hide();
					}
				}
			});

		},
		
		//隐藏列表
		hideItems : function () {
			this.$render.find(".custom-select-filter").find("input[type='text']").val("");
			this.$render.find("ul").scrollTop(0);
			this.$render.find(".custom-select-list").hide();
			this.$render.find(".custom-select-option").show();
		},

		//显示列表
		showItems : function () {
			$(".custom-select-list").hide();
			this.$render.find(".custom-select-list").show();
		},

		refresh : function (items) {
			var list = this.createItems(items);
			this.$container.find(".custom-select-list").find("ul").html(list);
		}
	}

	// 创建分页
	exports.page = function (opts) {
		return new Page(opts);
	}

	function Page (opts) {
		this.$render = util.paramJquery(opts.render);
		this.pn = opts.pn;
		this.total = opts.total;
		this.callback = opts.callback;
		this.init();
	}

	Page.prototype = {
		init : function () {
			if (this.total < 1) this.$render.html("").hide();
			this.createTemplate();
			this.bindEvent();
		},

		createTemplate : function () {
			var total = Math.floor(this.total);
			var pn = Math.floor(this.pn);
		   	var pageHtml = [];
	        if (pn != 1) {
	            pageHtml.push('<a hidefocus href="javascript:;">上一页</a>');
	        } else {
	            pageHtml.push('<a hidefocus href="javascript:;" class="disabled">上一页</a>');
	        }
	        pageHtml.push('<a hidefocus href="javascript:;" ');
	        pageHtml.push(pn == 1 ? 'class="current"' : 'class=""');
	        pageHtml.push('>1</a>');
	        if (pn > 4) {
	        	pageHtml.push('<span class="point">...</span>');
	        }
	        var startNum = pn >= 4 ? (pn - 2) : 2;
	        var endNum = pn + 2 >= total ? total - 1 : pn + 2;
	        for (var i = startNum; i <= endNum; i++) {
	            if (pn == i) {
	               pageHtml.push('<a hidefocus href="javascript:;" class="current">' + i + '</a>');
	            } else {
	               pageHtml.push('<a hidefocus href="javascript:;" class="">' + i + '</a>');
	            }
	        }
	        if (pn + 3 < total) {
	        	pageHtml.push('<span class="point">...</span>');
	        }
	        if (total != 1) {
	        	pageHtml.push('<a hidefocus href="javascript:;" ');
	        	pageHtml.push(pn == total ? 'class="current">' : 'class="">');
	        	pageHtml.push(total);
	        	pageHtml.push('</a>');
	        }
	        if (pn != total){
	            pageHtml.push('<a hidefocus href="javascript:;">下一页</a>');
	        } else {
	            pageHtml.push('<a hidefocus href="javascript:;" class="disabled">下一页</a>');
	        }
	    	this.$render.html('<div class="page style-red">' + pageHtml.join("") + '</div>');
		},

		bindEvent : function () {
			var self = this;
			var $pageWrap = this.$render.find(".page");
			$pageWrap.find("a").die().live("click", function () {
				if (!$(this).hasClass("disabled")) {
					var pn = $(this).text();
					if (pn == "上一页") pn = self.pn - 1;
					if (pn == "下一页") pn = Math.floor(self.pn) + 1;
					self.callback.call(self, pn);
				}
			});
		},

		refresh : function (pn, total) {
			this.pn = pn;
			if (total) this.total = total;
			this.createTemplate();
		}
	}


	exports.check = function (opts) {
		return new Check(opts);
	}

	function Check (opts) {
		this.$render = opts.render ? util.paramJquery(opts.render) : $("body");
		this.$checkAll = $(this.$render).find(".check-all");
		this.$checks = $(this.$render).find(".check");
		this.size = this.$checks.size();
		this.callback = opts.callback;
		this.bindEvent();
	}

	Check.prototype = {
		bindEvent : function (){
			var self = this;
			//全选操作
			self.$checkAll.die().live("click", function () {
				self.$checks.attr("checked", $(this).prop("checked"));
				self.getChecks();
			});

			//单选遍历
			self.$checks.die().live("click", function (ev) {
				var e = ev || window.event;
				e.cancelBubble ? e.cancelBubble : e.stopPropagation();
				self.eachCheck();
			});
		},

		eachCheck : function () {
			if (this.size == $(this.$render).find(".check:checked").size()) {
				this.$checkAll.attr("checked", true);
			} else {
				this.$checkAll.attr("checked", false);
			}
			this.getChecks();
		},

		getChecks : function () {
			this.callback($(this.$render).find(".check:checked"));
		}
	}

});