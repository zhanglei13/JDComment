<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>欢迎登录</title>
  <meta name="author" content="DeathGhost" />
  <link rel="stylesheet" type="text/css" href="static/style/business/login/style.css"/>
  <style>
  body{height:100%;background:#16a085;overflow:hidden;}
  canvas{z-index:-1;position:absolute;}
  </style>
  <script src="static/js/business/login/jquery.js"></script>
  <script src="static/js/common/jquery-1.7.2.min.js"></script>
  <script src="static/js/business/login/verificationNumbers.js"></script>
  <script src="static/js/business/login/Particleground.js"></script>
  <script>
  $(document).ready(function() {
    //粒子背景特效
    $('body').particleground({
      dotColor: '#5cbdaa',
      lineColor: '#5cbdaa'
    });
 /*   function checkPhone(){ 
        var phone = $('.user').val();
        if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))){ 
            $('.error').text('手机号码有误，请重填');
            return false; 
        } 
    } 
    $('#submit_btn').click(function(){
      $('.error').text('');
  //    checkPhone();
     
      if($('.user').val()==''){
        $('.error').text('请输入用户名');
      }else if($('.password').val()==''){
        $('.error').text('请输入密码');
      }else{
    	  var userId = $('.user').val();
    	  var password = $('.password').val();
      }
    })*/
    
    
  });
  </script>
</head>
<body>
<form action="ulogin" method="post">
<dl class="admin_login">
 <dt>
  <strong style="color:white;">欢迎登陆</strong>
  <em>Management System</em>
 </dt>
 <dd class="user_icon">
  <input type="text" placeholder="账号" class="login_txtbx user" name="username" />
 </dd>
 <dd class="pwd_icon">
  <input type="password" placeholder="密码" class="login_txtbx password" name="password"/>
 </dd>
 <dd>
  <input type="submit" value="立即登陆" id="submit_btn"/>
 </dd>
 <dd>
  <p class='error'></p>
  <p>JDStar-8</p>
 </dd>
</dl>
</form>
</body>
</html>
