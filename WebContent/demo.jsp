<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>评价系统</title>
  <script src="static/js/business/login/jquery.js"></script>
  <script src="static/js/common/jquery-1.7.2.min.js"></script>
  <link rel="stylesheet" href="static/style/common/base.v2.0.css">
  <link rel="stylesheet" href="static/style/common/block.v2.0.css">
  <link rel="stylesheet" href="static/style/business/index/index.css">
  <script src="static/js/sea/sea.js"></script>
</head>

<body class='eva'>
	<div class="head w">
		<div class="margin">
			<div class="fl">
				<a href="#" style="display:inline;"><img src="static/image/logo.png" alt="八戒" width="70" height="70" style="display:inline;"></a>
			</div>
			<div class="fl mt30 white">八戒SHOPPING</div>
			<div class="fr mt30">
				<span style="color:white;" class="f14">
				
				<% HttpSession sessions=request.getSession(); 
				Object sessionValues=sessions.getAttribute("username"); %> 
				
				<% 
					if(sessionValues==null){
					%>
					<a href="login" class="button bg-gray ">登陆</a>
					<%
					}else
					{%>
					<a href="index"><%=sessionValues %> </a>,欢迎登陆!
					<a href="logout" class="button bg-gray ">注销</a>
				<%}%>

				</span>
			</div>
			<div style="clear:both;"></div>
		</div>	

	</div>
	<div class="margin pig-goods">
		<script>
			$.ajax({
	        	type:'get',
	        	url:"product/list",
	        	success:function(data){
	        		var content = "";
	        		for (var i=0;i<data.length;i++) {
	        			content += "<div class=\"fl goods\">";
	        			content += "<a href=\"product/detail?productId="+data[i].productId+"\" style=\"cursor:pointer;\"><img src=\""+data[i].productImg+"\" width=\"200\" height=\"200\"style=\"border-bottom:1px solid #ccc;margin-bottom:10px;\"></a>";
	        			content += "<p class=\"f16 fb ml20\" >" +data[i].productName+ "<span class=\"red fb ml20\">￥"+data[i].productPrice+"</span></p>"
						content += "<p class=\"ml20 f12\">商品数："+data[i].productNum+"</p>";
						content += "<p class=\"ml20 mr10 f12 red\">评价数："+data[i].count+"</p>";
						content += "</div>";
					}
	        		
	        		$('.pig-goods').html(content);
	        	}
	        });
		</script>
	     
		<%--<c:forEach var="item" items="${list}">--%>
		    <%--<div class="fl goods">--%>
		    	<%--<a href="details-data.html?id=123" style="cursor:pointer;"><img src="+<c:out value="${item.productImg}" />+" width="200" height="200"style="border-bottom:1px solid #ccc;margin-bottom:10px;"></a>--%>
				<%--<p class="f16 fb ml20" ><c:out value="${item.productName}" /><span class="red fb ml20">￥<c:out value="${item.productPrice}" /> </span></p>--%>
				<%--<p class="ml20 f12">商品数：<c:out value="${item.productNum}" /></p>--%>
				<%--<p class="ml20 mr10 f12 red">评价数：<c:out value="${item.count}" /></p>--%>
		    <%--</div>--%>
		<%--</c:forEach>--%>
	
	<!--  	
		<div class="fl goods">
			<a href="details-data.html?id=123" style="cursor:pointer;"><img src="static/image/good4.jpg" width="200" height="200"style="border-bottom:1px solid #ccc;margin-bottom:10px;">
			</a>
			<p class="f16 fb ml20" >商品名称<span class="red fb ml20">￥4899.00 </span></p>
			<p class="ml20 f12">北京移动官方旗舰店</p>
			<p class="ml20 mr10 f12 red">评价数：2000</p>
		</div>
		<div class="fl goods">
			<a href="details-data.html?id=123" style="cursor:pointer;"><img src="static/image/good4.jpg" width="200" height="200"style="border-bottom:1px solid #ccc;margin-bottom:10px;">
			</a>
			<p class="f16 fb ml20" >商品名称<span class="red fb ml20">￥4899.00 </span></p>
			<p class="ml20 f12">北京移动官方旗舰店</p>
			<p class="ml20 mr10 f12 red">评价数：2000</p>
		</div>
		<div class="fl goods">
			<a href="details-data.html?id=123" style="cursor:pointer;"><img src="static/image/good4.jpg" width="200" height="200"style="border-bottom:1px solid #ccc;margin-bottom:10px;">
			</a>
			<p class="f16 fb ml20" >商品名称<span class="red fb ml20">￥4899.00 </span></p>
			<p class="ml20 f12">北京移动官方旗舰店</p>
			<p class="ml20 mr10 f12 red">评价数：2000</p>
		</div>
		-->
		
	</div>
	
</body>
</html>