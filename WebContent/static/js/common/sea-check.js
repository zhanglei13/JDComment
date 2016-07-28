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
	* checkBox
	* @param id
	* @param url
	* @param tools
	*/
	exports.init = function (opts) {
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
				self.getSelCheckBox();
			});

			//单选遍历
			self.$checks.die().live("click", function (ev) {
				var e = ev || window.event;
				e.cancelBubble ? e.cancelBubble : e.stopPropagation();
				self.eachCheckBox();
			});
		},

		eachCheckBox: function () {
			if (this.size == $(this.$render).find(".check:checked").size()) {
				this.$checkAll.attr("checked", true);
			} else {
				this.$checkAll.attr("checked", false);
			}
			this.getSelCheckBox();
		},

		getSelCheckBox : function () {
			this.callback($(this.$render).find(".check:checked"));
		}
	}

});