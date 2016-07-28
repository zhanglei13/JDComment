<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
  <title>商品评价</title>
  <link rel="stylesheet" href="static/style/common/base.v2.0.css">
  <link rel="stylesheet" href="static/style/common/block.v2.0.css">
  <link rel="stylesheet" href="static/style/business/index/index.css">
  <script src="static/js/sea/sea.js"></script>
  <script type="text/javascript">
//下面用于图片上传预览功能
function setImagePreview(avalue) {
	var docObj=document.getElementById("doc");

	var imgObjPreview=document.getElementById("preview");
	if(docObj.files &&docObj.files[0])
	{
		//火狐下，直接设img属性
		imgObjPreview.style.display = 'inline';
		imgObjPreview.style.width = '70px';
		imgObjPreview.style.height = '70px';

		//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
		imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
	}
	else
	{
	//IE下，使用滤镜
		docObj.select();
		var imgSrc = document.selection.createRange().text;
		var localImagId = document.getElementById("localImag");
		//必须设置初始大小
		localImagId.style.width = "70px";
		localImagId.style.height = "70px";
		//图片异常的捕捉，防止用户修改后缀来伪造图片
		try{
			localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
			localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
		}
		catch(e)
		{
			alert("您上传的图片格式不正确，请重新选择!");
			return false;
		}
		imgObjPreview.style.display = 'none';
		document.selection.empty();
	}
	return true;
}

</script>
</head>
<body class="form">
	<div class="w">
		<div class="body">
		<form id="helform" method="post" action="comment/commentadd">
		<input type="hidden" name="productId" class="goodsId">
		<table class="w">
			<tr>
				<td class="w100">商品评分：</td>
				<td>
				<span class="rating-star">
				<input type="hidden" name="goods">
				<a href="#">1</a>
		        <a href="#">2</a>
		        <a href="#">3</a>
		        <a href="#">4</a>
		        <a href="#">5</a>
				</span>
				</td>
			</tr>
			<tr>
				<td>商家评分：</td>
				<td>
				<span class="rating-star">
				<input type="hidden" name="stores">
				<a href="#">1</a>
		        <a href="#">2</a>
		        <a href="#">3</a>
		        <a href="#">4</a>
		        <a href="#">5</a>
				</span>
				</td>
			</tr>
			<tr>
				<td>物流评分：</td>
				<td>
				<span class="rating-star">
				<input type="hidden" name="logistics">
				<a href="#">1</a>
		        <a href="#">2</a>
		        <a href="#">3</a>
		        <a href="#">4</a>
		        <a href="#">5</a>
				</span></td>
			</tr>
			<tr>
				<td valign="top">评价：</td>
				<td><textarea name="ctext" id="" style="resize: none;width: 400px;height: 100px;margin-top:10px;" maxlength="140"></textarea></td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="file" name="file" id="doc" style="display:none;" accept=".png,.jpg,.gif,.jpeg"onchange="javascript:setImagePreview();">
					<span id="localImag"><img id="preview" src="static/image/add.png" width="70" height="70" style="display:inline;"></span>
					<input type="checkbox" name="commentAnonymous"> 匿名评价
				</td>
			</tr>
		</table>
		</form>
		</div>
	</div>
	<script>
	seajs.use('jquery',function($){
		// $('.goodsId').attr('value',id);
		$('a').click(function(){
			var obj = $(this).parent();
			obj.find('a').css('background-position', '-90px');
			var score = $(this).html();
			obj.find($('input')).attr('value',score);
			setStar(obj,score);
		})
        function setStar(obj,score) {
			var level = parseInt(score);
			var n;
            if (level ==5) {
                n = '0 -30px';
            } else if (level >=3) {
                n = '0 0';
            } else {
                n = '0 -60px';
            }
            obj.find('a').each(function(){
            	if(parseInt($(this).html())<=level){
            		$(this).css('background-position', n);
            	}else{
            		$(this).css('background-position', '0 -90px');
            	}
            })          
        }

        $('#preview').click(function(){
        	$('#doc').click();
        })
	})
	</script>
</body>
</html>