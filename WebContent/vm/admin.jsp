<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
	<head>
		<title>审批</title>
	 
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
		
		<link rel="stylesheet" href="<%= basePath%>/static/style/common/base.v2.0.css">
		<link rel="stylesheet" href="<%= basePath%>/static/style/common/block.v2.0.css">
		<link rel="stylesheet" href="<%= basePath%>/static/style/business/index/index.css">
		<link rel="stylesheet" href="<%= basePath%>/static/style/business/index/details.css">
		<script src="<%= basePath%>/static/js/sea/sea.js"></script>
	</head>
	
	<body>
		<div class="admin-menu">
			<p class="admin-title">管理员</p>
			<ul>
				<li><span></span>评价审核</li>
			</ul>
		</div>
		<div class="admin-main">
			<div class="admin-list">
				<div class="all-one">
					<div class="all-user">
						<img src="<%= basePath%>/static/image/photo.jpg" width="100" height="100" class="mb20">
						<p>用户1312399721</p>
						<p>2016/7/28 12:00:00</p>
					</div>
					<div class="all-word">
						<p class="mb15">1.运行苹果的ios系统，拥有最为完善的生态系统，各类高质量的软件app应有尽有，而且绝对适配 2、摄像头已达到平板的主流水准，日常使用完全OK 3、7.9英寸的机身以及较为轻薄的机身十分便携。</p>
						<img src="<%= basePath%>/static/image/good1.jpg" width="100" height="100" style="border:1px solid #ccc;">
					</div>
					<div class="all-star">
						<p class="f16 mt15"><input type="button" class="button bg-green pass" value="通过"></p>
						<p class="f16 mt15"><input type="button" class="button bg-red reback" value="驳回"></p>
					</div>
					<div style="clear:both"></div>
				</div>
			</div>
		</div>
		<script>
		seajs.use(['jquery','dialog'],function($,dialog){
			$(function(){
				$('.pass').click(function(){
					dialog.confirm('是否确认通过？',function(){window.location.reload();});
				})
				$('.reback').click(function(){
					dialog.confirm('是否确认驳回？',function(){window.location.reload();})
				})
			})
		})
		</script>
		
		
	</body>
	
</html>