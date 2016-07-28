<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
	<head>
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
	  <script type="text/javascript" src="<%= basePath%>static/js/common/jquery-2.1.1.min.js"></script>
	  
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
		<input type="hidden" value="${product.productId}" class="idinput"/>
			<ul>
				<li><a href="<%= basePath%>product/detail?productId=${product.productId}">数据罗盘</a></li>
				<li><a href="<%= basePath%>product/allComment?productId=${product.productId}">全部</a></li>
				<li><a href="<%= basePath%>product/goodComment?productId=${product.productId}">优质评论</a></li>
				<li><a href="<%= basePath%>product/picComment?productId=${product.productId}">晒图评价</a></li>
				<li class="current"><a href="<%= basePath%>product/badComment?productId=${product.productId}">差评</a></li>
				
			</ul>
		</div>
			

		<div style="width: 1194px; margin-top: 10px;">
			<div align="left" style="margin: 0px 0px 0px 0px; float: right; width: 970px; overflow: hidden;">
			<script>
					var productId = $('.idinput').val();
						$.ajax({
				        	type:'get',
				        	data:{productId: productId},
				        	url:"<%= basePath%>comment/lowComments",
				        	success:function(data){
				        		var content = "";
				        		for (var i=0;i<data.length;i++) {
				        			content += "<div class='all-one'><div class='all-user'>";
				        			content += "<img src='<%= basePath%>static/image/photo.jpg' width='100' height='100' class='mb20'>";
				        			content += "<p>"+data[i].userId+"</p>";
				        			content += "<p>"+data[i].commentTime+"</p></div><div class='all-word'>";
				        			content += "<p class='mb15'>"+data[i].commnetText+"</p>";
				        			content += "<img src='<%= basePath%>static/image/good1.jpg' width='100' height='100' style='border:1px solid #ccc;'></div>";
				        			content += "<div class='all-star'><p class='f16 mt15'>用户评价</p>";
				        			var s = (data[i].commentScore+"").substring(0,1);
				        			content += "<div class='comment-star mt15'><span style='width:"+parseInt(s)/5 * 100+"%'></span></div>";
				        			content += "<p class='f16 mt15'>用户信誉：</p>";
				        			content += "<div class='comment-star mt15'><span style='width:90%'></span></div>";
				        			content += "</div><div style='clear:both'></div></div>";
								}
				        		
				        		$('#test').html(content);
				        	}
				        });
					</script>
			
				<div style="width: 970px;" id="test"></div>
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