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


	exports.area = function (opts) {
		return new AreaSelect(opts);
	};


	//自定义地址下拉框
	function AreaSelect (opts) {
		this.$input = util.paramJquery(opts.render);
		this.$render = this.$input.parent();
		this.items = opts.items;
		this.callback = opts.callback;
		this.filter = opts.filter;
		this.curVal = this.$input.val();
		this.init();
		this.bindEvent();
	}

	//自定义下拉框
	AreaSelect.prototype = {
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
		
		createItems : function (items) {
			if (items) this.items = items;
			var itemsArr = [];
			for (var i=0; i<this.items.length;i++) {
				if (this.curVal == this.items[i].value) {
					this.$container.find(".custom-select-input").val(this.items[i].text);
				}
				itemsArr.push('<li class="custom-select-option">'+this.items[i].text+'</li>');
			}
			return itemsArr.join("");
		},

		bindEvent : function () {
			var self = this;

			//显示隐藏下拉列表
			self.$render.find(".custom-select-input").unbind("click").bind("click", function() {
				$(".custom-select").css({"z-index":"998"});
				$(this).parents(".custom-select").css({"z-index":"999"});
				if (util.isShow($(this).next())) {
					self.hideItems();
					if (!self.$input.val()) {
						self.$render.find(".custom-select-input").val(self.items[0].text);
					}
				} else {
					self.showItems();
				}
				return false;
			});


			//筛选
			self.$render.find(".custom-select-input").unbind("keyup").bind("keyup", function() {
				$(this).parent().find("li").show();
				var val = $.trim($(this).val());
				if (val) {
					$(this).parent().find("li").each(function(){
						if ($(this).text().indexOf(val) > -1) {
							$(this).show();
						} else {
							$(this).hide();
						}
					});
				}
			});

			//点击空白收起
			$("body").click(function(ev){
				var ev = ev || window.event;
				var otarget = ev.target || ev.srcElement;
				if (!$(otarget).parents(".custom-select").size()) {
					if (!self.$input.val()) {
						self.$render.find(".custom-select-input").val(self.items[0].text);
					}
					self.hideItems();
				}
				return false;
			});

			//选择内容
			self.$render.off().on("click",".custom-select-option", function() {
				self.hideItems();
				self.$render.find(".custom-select-input").html($(this).html());
				self.$input.val(self.items[$(this).index()].value);
				self.$render.find(".custom-select-input").val(self.items[$(this).index()].text);
				if (self.callback) self.callback(self.items[$(this).index()]);
				self.$input.blur();
				return false;
			});

			//下拉框选择离开
			self.$render.find(".custom-select-input").blur(function(){
				self.$input.blur();
				return false;
			});

			//输入后离开清空内容
			self.$render.find(".custom-select-input").keydown(function(){
				self.$input.val("");
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
			this.$input.val("");
			this.$container.find(".custom-select-input").val(items[0].text);
			this.$container.find(".custom-select-list").find("ul").html(list);
		}
	}


});