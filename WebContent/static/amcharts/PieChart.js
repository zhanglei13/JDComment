var m=[];
$.ajax({
	url:"../admin/file2",
	type:"get",
	success:function(data){
		for(var i in data){
			var obj={};
			obj.label = i;
			obj.value = parseInt(data[i]);
			m.push(obj);
		}
		var chart = AmCharts.makeChart("piechartdiv", {
		    "type": "pie",
			"theme": "none",
		    "dataProvider": m,
		    "valueField": "value",
		    "titleField": "label",
		    "radius": "25%"
		});
	}
})
