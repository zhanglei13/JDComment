<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>评价系统</title>
  <link rel="stylesheet" href="static/style/common/base.v2.0.css">
  <link rel="stylesheet" href="static/style/common/block.v2.0.css">
  <link rel="stylesheet" href="static/style/business/index/index.css">
  <script src="static/js/sea/sea.js"></script>
</head>
<body class='eva'>
	<div class="head w">
		<div class="margin">
			<div class="fl">
				<a href="demo.jsp"><img src="static/image/logo.png" alt="八戒" width="70" height="70" class="iblock"></a>
			</div>
			<div class="fl mt30 white">八戒SHOPPING</div>
			<% HttpSession sessions=request.getSession(); 
				Object sessionValues=sessions.getAttribute("username"); %>
			<div class="fr mt30">
				<span style="color:white;" class="f14"><a href="index"><%=sessionValues %></a>,欢迎登陆！</span>
				<a href="logout" class="button bg-gray ">注销</a>
			</div>
		</div>	
	</div>
	<div class="user margin">
		<img src="./static/image/photo.jpg" alt="照片太帅" class="fl ml100 mr20" width="70" height="70">
		<table style="margin:15px;">
			<tr>
				<td>用户名：</td>
				<td><%=sessionValues %></td>
				<td style="width:300px;"></td>
				<td>信誉度:</td>
				<td class="comments-level"><span class="back"><span class="has" style="width:80px">80%</span></span></td>
			</tr>		
		</table>
		<a href="demo.jsp" class="button bg-green fr go">进入商城</a>
		<div style="clear:both;"></div>
	</div>


	<div class="wrapper">
		<div class="nav-tab clearfix">
			<span class="active">待评价</span>
			<a href="oldindex">已评价</a>
		</div>
		
		
		<script>
		seajs.use('jquery',function($){
			var content="";
			$.ajax({
	        	type:'get',
	        	data:{username:"zhanglei"},
	        	url:"trade/alcom",
	        	success:function(data){
	        		for (var i=0;i<data.length;i++) {
						content += "<div class=\"w wait\"><img src=\""+data[i].productImg+"\" class=\"fl ml10 mr50\" width=\"100\" height=\"100\">"
						content += "<div>";
						content += "<p class=\"f20 fb\"><a href=\"product/detail?productId="+data[i].productId+"\" style=\"cursor:pointer;\">商品名称</a></p>";
						content += "<p>"+data[i].productName+"</p>";
						content += "<p class=\"mt20\">";
						content += "<input type=\"button\" class=\"button bg-red mr20 assess\" value=\"评价\" data-id=\""+data[i].productId+"\">";
						content += "</p></div></div>";
					}
	        		$('#test').html(content);
	        	}
	        });
		})
			
		</script>
		
		<div id = "test"></div>
		<!--  
		<div class="w wait">
			<img src="static/image/good1.jpg" class="fl ml10 mr50" width="100" height="100">
			<div>
				<p class="f20 fb"><a href="details-data?id=123" style="cursor:pointer;">商品名称</a></p>
				<p>卡西欧 CASIO 数码相机 ZR3600 （1210万有效像素 12.5倍光学变焦） 紫色</p>
				<p class="mt20">
				<input type="button" class="button bg-red mr20 assess" value="评价" data-id="123">
				</p>
			</div>
		</div>
		<div class="w wait">
			<img src="static/image/good2.jpg" class="fl ml10 mr50" width="100" height="100">
			<div>
				<p class="f20 fb"><a href="details-data?id=123" style="cursor:pointer;">商品名称</a></p>
				<p>卡西欧 CASIO 数码相机 ZR3600 （1210万有效像素 12.5倍光学变焦） 紫色</p>
				<p class="mt20">
				<input type="button" class="button bg-red mr20 assess" value="评价" data-id="123">
				</p>
			</div>
		</div>
		<div class="w wait">
			<img src="static/image/good3.jpg"  class="fl ml10 mr50" width="100" height="100">
			<div>
				<p class="f20 fb"><a href="details-data?id=123" style="cursor:pointer;">商品名称</a></p>
				<p>卡西欧 CASIO 数码相机 ZR3600 （1210万有效像素 12.5倍光学变焦） 紫色</p>
				<p class="mt20">
				<input type="button" class="button bg-red mr20 assess" value="评价" data-id="123">
				</p>
			</div>
		</div>
		-->
		
	</div>
	<div id = "footernews2">
		<div id="ftinfo">
			JDStar编程马拉松
		</div>
	</div>
	<script>
		seajs.use(['jquery','dialog'],function($,dialog){
			$(function(){
				var productId=0;
				$('.assess').die().live('click',function(){
					productId = $(this).attr('data-id');
					dialog.openUrlContent({
						id:"hel",
						title:"评价",
						url:"form",
						width:550,
						height:250,
						callback:function(id){
							/* alert("heehehe"); */
							//var scorearray = $("input[type='hidden']").attr("value");
							//alert($("input[name='goods']").val());

							$(".goodsId").attr("value",productId);
							$("#helform").submit();
							
							console.log(params);
							$.ajax({
								url:"/comment/commentadd",
								data: params
							});
						}
					})
				});
				$('.see').click(function(){
					var id = $(this).attr('data-id');
					window.location.href= "details-data.html?id="+id;
				});
			})
		})
	</script>

</body>
</html>