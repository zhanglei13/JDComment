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
	// 拖拽模块
	var drag = require("drag");
	// 默认配置
	var defaults = {
		$target: $("body"),
		title: "提示",
		content: "",
		width: 240,
		height: 90,
		type: "1",
		isdrag: true,
		mask : true
	};
	var maskSize = 0;

	function Dialog (opts) {
		this.config = $.extend(true, {}, defaults, opts);
		this.init();
	}

	Dialog.prototype = {

		//初始化弹窗参数
		init: function () {
			var id = this.config.id || Math.round(Math.random() * 100000);
			var self = this;
			this.config.dialogId = this.id = "dialog" + id;
			this.config.dialogObj = $('<div class="dialog" id="' + this.config.dialogId + '"></div>');
			this.build();
			this.bindEvent();
			this.setting();
			this.extend();
		},

		////判断弹窗类型
		//build: function () {
		//	var type = Math.floor(this.config.type);
		//	switch (type) {
		//		case 1: this.buildContent(); break;
		//		case 2: this.buildContentByUrl(); break;
		//		case 3: this.buildFrameByUrl(); break;
		//		case 4: this.buildContentById(); break;
		//		default:;
		//	}
		//	if(!maskSize){
		//		this.config.$target.append('<div class="dialog-mask" onSelectStart="return false"></div>');
		//		maskSize = 1;
		//	}
		//	$(".dialog-mask").show();
		//},
        //判断弹窗类型
        build: function () {
            var type = Math.floor(this.config.type);
            switch (type) {
                case 1: this.buildContent(); break;
                case 2: this.buildContentByUrl(); break;
                case 3: this.buildFrameByUrl(); break;
                case 4: this.buildContentById(); break;
                default:;
            }
            if(!maskSize){
                this.config.$target.append('<div class="dialog-mask" onSelectStart="return false"></div>');
                maskSize = 1;
            }
            $("body").attr("onSelectStart","return false");
            $(".dialog-mask").show();
        },


        //自定义内容
		buildContent: function () {
			this.config.$target.append(this.createTemplate());
		},

		//请求URL内容插入页面
		buildContentByUrl: function () {
			this.config.content = '<span><i class="dialog-foot-loading"><img src="#springUrl()/static/widgets/artDialog/skins/icoms/loading.gif" width="60"></i>数据加载中...</span>';
			this.config.$target.append(this.createTemplate());
			util.ajaxRefPart(this.config.dialogObj.find(".dialog-content"), this.config.url);
		},

		//请求URL内容插入iframe
		buildFrameByUrl: function () {
			this.config.content = '<iframe align=middle marginwidth=0 marginheight=0 frameborder=no width=100% height=100% src="';
			this.config.content += this.config.url;
			this.config.content += '"></iframe>';
			this.config.$target.append(this.createTemplate());
		},

		//请求对象内容
		buildContentById: function () {
			this.config.content = this.config.dialogObj.html();
			this.config.$target.append(this.createTemplate());            
		},

		//创建弹窗模板
		createTemplate: function () {
			var template = [];
			template.push('<h2><span class="dialog-title">');
			template.push(this.config.title);
			template.push('</span><a href="#@" class="close iconfont dialog-btn-close">&#xf00b3;</a></h2>');
			template.push('<div class="dialog-content" style="width:');
			template.push(this.config.width);
			template.push('px; height:');
			template.push(this.config.height);
			template.push('px;">');
			template.push(this.config.content);
			template.push('</div>');
			template.push(this.createBtns());
			template.push('</div>');
			return this.config.dialogObj.html(template.join(""));
		},

		//创建按钮组
		createBtns: function () {
			var template = [];
			if (this.config.buttons) {
				for (var i = 0; i < this.config.buttons.length; i++) {
					template.push('<button class="button bg-blue mr10 dialog-btn-custom">');
					template.push(this.config.buttons[i].text);
					template.push('</button>');
				}
			} else if (this.config.callback) {
				template.push('<button class="button bg-blue mr10 dialog-btn-succ">确定</button>');
				template.push('<button class="button bg-blue mr10 dialog-btn-close">取消</button>');
			} else {
				template.push('<button class="button bg-blue mr10 dialog-btn-close">关闭</button>');
			}
			return template.length ? '<div class="dialog-foot">' + template.join("") + '</div>' : "";
		},

		//绑定事件
		bindEvent: function () {
			var self = this;
			this.config.dialogObj.find(".dialog-btn-custom").click(function () {
				var index = $(this).index();
				self.config.buttons[index].callback.call(self,self.config.dialogId)
				return false;
			});

			this.config.dialogObj.find(".dialog-btn-succ").click(function () {
				self.config.callback.call(self,self.config.dialogId);
				return false;
			});

			this.config.dialogObj.find(".dialog-btn-close").click(function () {
				self.close();
				return false;
			});
		},

		//事件扩展
		extend : function () {
			if (this.config.isdrag) {
				drag.init({
					drag : this.config.dialogObj.find("h2"),
					move : this.config.dialogObj
				});
			}
		},

		////弹出窗属性设置
		//setting: function () {
		//	var self = this;
		//	var l = (util.clientWidth() - this.config.width) / 2;
		//	var t = (util.clientHeight() - this.config.height - 110) / 2;
		//	if (t < 10) t = 10;
		//	self.config.$target.css("overflow","hidden");
		//	self.config.dialogObj.css({ left : l, top: t - 50 > 0 ? t - 50 : t});
		//	if (self.config.type == 3) {
		//		self.config.dialogObj.find("iframe").load(function(){
		//			self.config.dialogObj.stop().animate({top:t},500);
		//		});
		//	} else {
		//		self.config.dialogObj.stop().animate({top:t},200);
		//	}
		//},
        //弹出窗属性设置
        setting: function () {
            var self = this;
            var l = (util.clientWidth() - this.config.width) / 2;
            var t = (util.clientHeight() - this.config.height - 110) / 2;
            if (t < 10) t = 10;
            self.config.$target.css("overflow","hidden");
            self.config.dialogObj.css({ left : l, top: t - 50 > 0 ? t - 50 : t});
            if (self.config.type == 3) {
                self.config.dialogObj.find("iframe").load(function(){
                    self.config.dialogObj.stop().animate({top:t},500, function(){
                        //$("body").attr("onSelectStart","");
                    });
                });
            } else {
                self.config.dialogObj.stop().animate({top:t},200, function () {
                    //$("body").attr("onSelectStart","");
                });
            }
        },


        //关闭弹窗
		close : function () {
			this.config.dialogObj.remove();
			this.config.$target.removeAttr("style");
			if (!$(".dialog").size()) $(".dialog-mask").hide();
		},

		//显示提示信息
		showTips : function (tips) {
			this.hideTips();           
			this.config.dialogObj.find(".dialog-foot").append('<span class="tips fl pl10 red f14">'+tips+'</span>');               
		},

		//关闭提示信息
		hideTips : function () {
			this.config.dialogObj.find(".dialog-foot").find('.tips').remove();
		},

		//底部loading
		loading : function () {
			var tips = '<i class="dialog-foot-loading"><img src="loading.gif" width="60"></i>';
			this.showTips(tips);
		},

		//获取弹窗text
		getText : function () {
			try {return this.config.dialogObj.find("textarea").val();} catch(e) {return "";}
		},

		//获取iframe对象
		getFrameElem : function () {
			return util.getFrameElem(this.config.dialogId);
		}

	};
	
	exports.open = function (opts) {
		return new Dialog(opts);
	};

	/**
	 * 直接显示内容
	 * @param id 弹出窗id ##可选
	 * @param content 弹出内容 ##必填
	 * @param title 标题  ##可选
	 * @param callback 确认回调函数 ##可选
	 */
	exports.openContent = function (opts) {
		opts.type = 1;
		return exports.open(opts);
	};

	/**
	 * 请求URL内容
	 * @param id 弹出窗id ##可选
	 * @param url 弹出内容请求地址 ##必填
	 * @param title 标题  ##可选
	 * @param callback 确认回调函数 ##可选
	 */
	exports.openUrlContent = function (opts) {
		opts.type = 2;
		return exports.open(opts);
	};

	/**
	 * 通过iframe加载页面
	 * @param id 弹出窗id ##可选
	 * @param url 弹出内容请求地址 ##必填
	 * @param title 标题  ##可选
	 * @param callback 确认回调函数 ##可选
	 */
	exports.openIframeContent = function (opts) {
		opts.type = 3;
		return exports.open(opts);
	};

    exports.openFrameContent  = function (opts) {
        opts.type = 3;
        return exports.open(opts);
    };

	/**
	 * 基本
	 * @param content 内容
	 * @param callback 确认回调函数
	 */
	exports.alert = function (content, callback) {
		var opts = initParam(content);
		if (callback) {
			opts.buttons = [
				{
					text : "确定",
					callback : callback
				}
			];
		}
		return exports.openContent(opts);
	};

	/**
	 * 询问层确认层
	 * @param content 内容
	 * @param callback 确认回调函数
	 */
	exports.confirm = function (content, callback) {
		var opts = initParam(content, callback);
		return exports.openContent(opts);
	};

	/**
	 * 提示框
	 * @param content 辅助文字
	 * @param callback 确认回调函数
	 */
	exports.prompt = function (content, callback) {
		var opts = initParam(content, callback);
		opts.callback = callback;
		opts.width = 300;
		opts.height = 140;
		opts.content = '<p>';
		opts.content += content;
		opts.content += '</p><textarea rows="5" cols="40"></textarea>';
		return exports.openContent(opts);
	};


	/**
	 * 提示框
	 * @param content 辅助文字
	 * @param callback 确认回调函数
	 */
	exports.message = function (status, content) {
		var content = content || "";
		var text = status == "success" ? "操作成功" : "操作失败";
		var opts = initParam(content);
		opts.width = 440;
		opts.height = 260;
		opts.content = '<div class="icon ';
		opts.content += status;
		opts.content += '">';
		opts.content += text;
		opts.content += '</div><p class="message">';
		opts.content += content;
		opts.content += '</p>';
		opts.type = 1;
		opts.buttons = [];
		return exports.openContent(opts);
	};


	function initParam (content, callback) {
		var opts = {};
		if (typeof(content) == "string") {
			opts.content = content;
			if (callback) opts.callback = callback;
		} else {
			opts = content;
		}
		return opts;
	}
});