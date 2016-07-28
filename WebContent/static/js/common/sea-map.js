 /**
	* 公共方法
	* User: wulewei
	* Date: 15-5-25
	*/
define(function (require, exports) {
	//载入地图组件
	// var mapScript = document.createElement("script");
	// mapScript.src = "http://map.qq.com/api/js?v=2.exp&libraries=drawing&key=FSQBZ-G26AU-ZH2V4-4Z643-ATKXO-65BZC";
	// document.getElementsByTagName("head")[0].appendChild(mapScript);
	//jquery
	var $ = require("jquery");
	//基础组件
	var util = require("util");
	//地图实例
	var map, geocoder, marker, circle, polygons, drawing, markersArray = null;
	
	markersArray = [];

	exports.init = function (opts) {
		var render = opts.render;
		var lat = parseFloat(opts.lat || 39.916527);
		var lng = parseFloat(opts.lng || 116.397128);
		var center = exports.getCenter(lat, lng);
		var polygonOptionsParam = {
	        //多边形是否可点击。
	        clickable: true,
	        //多边形是否可编辑。
	        editable: true,
	        //多边形的填充色，可通过Color对象的alpha属性设置透明度。
	        fillColor: new qq.maps.Color(255, 208, 70, 0.3),
	        //多边形的边框样式。实线是solid，虚线是dash。
	        strokeDashStyle: 'solid',
	        //多边形的边框线宽。
	        strokeWeight: 4,
	        //多边形是否可见。
	        visible: true,
	        //多边形的zIndex值。
	        zIndex: 1000
        }
		
		//地图实例
        map = new qq.maps.Map(document.getElementById(render),
        	{
	        	center: center,
	            zoom: 14
        	}
        );
        polygonOptionsParam.map = map;

        //地图标记
        marker = new qq.maps.Marker({
            map: map,
            position: center
        });

        //绘制功能
        drawing = new qq.maps.drawing.DrawingManager({
            drawingMode: qq.maps.drawing.OverlayType.POLYGON,
            polygonOptions: polygonOptionsParam
        });

        //绑定地图手画绘制事件（绘制完成后触发）
        qq.maps.event.addListener(drawing, 'polygoncomplete', function (polygon) {
        	polygons = polygon
            markersArray.push(polygons);
            drawing.setDrawingMode(null);            
        });
	}




	exports.drawingSetMap = function () {
		drawing.setMap(map);
	}


	exports.getCenter = function (lat, lng) {
		return new qq.maps.LatLng(lat, lng);
	}


	exports.editPolygon = function () {
		drawing.setDrawingMode(null);
	}

	exports.getPolygonPath = function () {
		alert(polygons.getPath().length);
		var rangeArr  = [];
		if (polygons.getPath() && polygons.getPath().length > 0) {
            for (var i = 0; i < polygons.getPath().length; i++) {
                var lng = polygons.getPath().getAt(i).lng.toFixed(6);
                var lat = polygons.getPath().getAt(i).lat.toFixed(6);
                rangeArr.push(lng + "," + lat);
            }
		}
		alert(rangeArr);
	}

});