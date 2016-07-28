<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
	<head>
	  <meta charset="UTF-8">
	  <title>评价详情</title>
	  <link rel="stylesheet" href="<%= basePath%>static/style/common/base.v2.0.css">
	  <link rel="stylesheet" href="<%= basePath%>static/style/common/block.v2.0.css">
	  <link rel="stylesheet" href="<%= basePath%>static/style/business/index/index.css">
	  <link rel="stylesheet" href="<%= basePath%>static/style/business/index/details.css">
	  <link rel="stylesheet" href="<%= basePath%>static/style/business/index/Style.css" type="text/css" />
	  <link rel="stylesheet" href="<%= basePath%>static/style/business/index/headfoot.css" type="text/css" />
	  <link rel="stylesheet" type="text/css" href="<%= basePath%>static/style/business/index/pro-detail.css">
	  <link rel="stylesheet" type="text/css" href="<%= basePath%>static/style/business/index/duibi.css">

	  <script type="text/javascript" src="<%= basePath%>static/amcharts/amcharts_stock/amcharts.js"></script>
	  <script type="text/javascript" src="<%= basePath%>static/amcharts/amcharts_stock/serial.js"></script>
	  <script type="text/javascript" src="<%= basePath%>static/amcharts/amcharts_charts/pie.js"></script>
	
	  <script type="text/javascript" src="<%= basePath%>static/amcharts/miniChart.js"></script>
	  <script type="text/javascript" src="<%= basePath%>static/amcharts/PieChart.js"></script>
	</head>
	<body class='eva'>
		<div class="head w">
			<div class="margin">
				<div class="fl">
					<a href="<%= basePath%>"><img src="<%= basePath%>static/image/logo.png" alt="八戒" width="70" height="70" class="iblock"></a>
				</div>
				<div class="fl mt30 white">八戒SHOPPING</div>
				<div class="fr mt30">
				<% HttpSession sessions=request.getSession(); 
				Object sessionValues=sessions.getAttribute("username"); %>
					<% 
					if(sessionValues==null){
					%>
					<a href="<%= basePath%>login" class="button bg-gray ">登陆</a>
					<%
					}else
					{%>
					<span style="color:white;" class="f14"><a href="<%= basePath%>index"><%=sessionValues %></a>,欢迎登陆！</span>
						<a href="<%= basePath%>logout" class="button bg-gray ">注销</a>
				<%}%>
				</div>
			</div>	
		</div>
		<div class="margin">
			<div class="pro-detail-box" >
			<div class="title"><h1>${product.productName}</h1></div>		
			<div class="pro-detail-img">
				<img width="278" height="278" src="<%= basePath%>${product.productImg}">
			</div>
			<div class="pro-detail-info" style="height:250px;">
				<div class="pro-detail-price">
					<small>当前价格：</small>
					<em>￥</em>
					<span>${product.productPrice}</span>
				</div>
				<p class="xl">
				<div class="cs">
					<span class="cs-name">暂无简介</span>
				</div>
				<div class="rank"></div>
				<div class="pro-detail-btn">
					<img src="<%= basePath%>static/image/buy.png">
				</div>

				<div class="clear"></div>
			</div>	
			<div class="pro-detail-trend">
				<table>
					<tr>
						<td class="fb">商品评价：</td>
						<td class="pl50 mt10 mb10">
							<div class="grade clearfix">
								<span class="score tc">4.1</span>
								<span class="stars">
								<em style="width:91%"></em>
								</span>
							</div>
							<p class="comment-num tc pl50"><b>166</b>人点评</p>
						</td>
					</tr>
					<tr>
						<td class="fb">商家评价：</td>
						<td class="pl50 mt10 mb10">
							<div class="grade clearfix">
								<span class="score tc">4.5</span>
								<span class="stars">
								<em style="width:95%"></em>
								</span>
							</div>
							<p class="comment-num tc pl50"><b>166</b>人点评</p>
						</td>
					</tr>
					<tr>
						<td class="fb">物流评价：</td>
						<td class="pl50 mt10 mb10">
							<div class="grade clearfix">
								<span class="score tc">4.3</span>
								<span class="stars">
								<em style="width:93%"></em>
								</span>
							</div>
							<p class="comment-num tc pl50"><b>166</b>人点评</p>
						</td>
					</tr>
				</table>
			</div>				
		</div>

		<div class="pro-nav-box">
			<ul>
				<li><a href="<%= basePath%>product/detail?productId=${product.productId}">数据罗盘</a></li>
				<li><a href="<%= basePath%>product/allComment?productId=${product.productId}">全部</a></li>
				<li><a href="<%= basePath%>product/goodComment?productId=${product.productId}">优质评论</a></li>
				<li class="current"><a href="<%= basePath%>product/picComment?productId=${product.productId}">晒图评价</a></li>
				<li><a href="<%= basePath%>product/badComment?productId=${product.productId}">差评</a></li>
				
			</ul>
		</div>
			

		<div style="width: 1194px; margin-top: 10px;">
			<div align="left" style="margin: 0px 0px 0px 0px; float: right; width: 970px; overflow: hidden;">
				<div style="width: 970px;">
					<div class="all-one">
						<div class="all-user">
							<img src="<%= basePath%>static/image/photo.jpg" width="100" height="100" class="mb20">
							<p>用户1312399721</p>
							<p>2016/7/28 12:00:00</p>
						</div>
						<div class="all-word">
							<p class="mb15">1.运行苹果的ios系统，拥有最为完善的生态系统，各类高质量的软件app应有尽有，而且绝对适配 2、摄像头已达到平板的主流水准，日常使用完全OK 3、7.9英寸的机身以及较为轻薄的机身十分便携。</p>
							<img src="<%= basePath%>static/image/good1.jpg" width="100" height="100" style="border:1px solid #ccc;">
						</div>
						<div class="all-star">
							<p class="f16 mt15">用户评价</p>
							<div class="comment-star mt15"><span style="width:75%"></span></div>
							<p class="f16 mt15">用户信誉：</p>
							<div class="comment-star mt15"><span style="width:90%"></span></div>
						</div>
						<div style="clear:both"></div>
					</div>
					<div class="all-one">
						<div class="all-user">
							<img src="<%= basePath%>static/image/photo.jpg" width="100" height="100" class="mb20">
							<p>用户1312399721</p>
							<p>2016/7/28 12:00:00</p>
						</div>
						<div class="all-word">
							<p class="mb15">价格相对便宜，而且苹果的做工很不错，电池支撑时间很长，可以连续耍8个小时，屏幕色彩还原度高，很薄，便携性强</p>
							<img src="<%= basePath%>static/image/good3.jpg" width="100" height="100" style="border:1px solid #ccc;">
						</div>
						<div class="all-star">
							<p class="f16 mt15">用户评价</p>
							<div class="comment-star mt15"><span style="width:90%"></span></div>
							<p class="f16 mt15">用户信誉：</p>
							<div class="comment-star mt15"><span style="width:43%"></span></div>
						</div>
						<div style="clear:both"></div>
					</div>
					<div class="all-one">
						<div class="all-user">
							<img src="<%= basePath%>static/image/photo.jpg" width="100" height="100" class="mb20">
							<p>用户1312399721</p>
							<p>2016/7/28 12:00:00</p>
						</div>
						<div class="all-word">
							<p class="mb15">Z团购买的新机哦，为什么会出现“文件管理安装错误，无法正常使用，请重新安装”... 而且，同时听歌+玩游戏《忍者水果》竟然卡了两回机~~~ 同样500W像素，效果勉强赶上我的海信HS-U8(老机子老照片)~~~ 使用寿命，待测</p>
							<img src="<%= basePath%>static/image/good6.jpg" width="100" height="100" style="border:1px solid #ccc;">
						</div>
						<div class="all-star">
							<p class="f16 mt15">用户评价</p>
							<div class="comment-star mt15"><span style="width:35%"></span></div>
							<p class="f16 mt15">用户信誉：</p>
							<div class="comment-star mt15"><span style="width:60%"></span></div>
						</div>
						<div style="clear:both"></div>
					</div>
				</div>
			</div>

			<div style="width: 218px; float: left; text-align: left;">
				<!-- 分栏 -->
				<div class="titlehead210" style="margin-bottom: 10px;">
					<div class="spleftdi"> 相似产品推荐 </div>
					<div style="margin: 2px;">
						<!-- one element -->
						<div class="topKoubeiAll" style="padding: 15px 0px;">
							<div class="topKoubieLeft">
								<a target="_blank" href="#"><img width="60" border="0"src="<%= basePath%>static/image/good1.jpg" alt=""></a>
							</div>
							<div class="topKoubeiRight">
								<a class="f12sc" target="_blank" href="<%= basePath%>product/detail?productId=1">特洁恩(Tejien)TJN-FLT-T10净水器家</a>
								<br>价格：<span class="topKoubiePrice">￥299</span>
							</div>
						</div>
					    <div class="clear"></div>
						<div class="divline"></div>
						<!-- element end  -->
						<div class="topKoubeiAll" style="padding: 15px 0px;">
							<div class="topKoubieLeft">
								<a target="_blank" href="#"><img width="60" border="0"src="<%= basePath%>static/image/good2.jpg" alt=""></a>
							</div>
							<div class="topKoubeiRight">
								<a class="f12sc" target="_blank" href="<%= basePath%>product/detail?productId=1">特洁恩(Tejien)TJN-FLT-T10净水器家</a>
								<br>价格：<span class="topKoubiePrice">￥299</span>
							</div>
						</div>
					    <div class="clear"></div>
						<div class="divline"></div>

					</div>

				</div>
				
			</div>
			<!-- left end -->
			<div class="clear"></div>
		</div>
		</div>
		<div id = "footernews2">
			<div id="ftinfo">
				JDStar编程马拉松
			</div>
		</div>

		
	</body>
</html>