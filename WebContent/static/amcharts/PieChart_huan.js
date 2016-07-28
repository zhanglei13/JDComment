var chart = AmCharts.makeChart("piechart", {
    "type": "pie",
    "theme": "none",
    "dataProvider": [{
        "title": "好评",
        "value": 4852
    }, {
        "title": "差评",
        "value": 9899
    }],
    "titleField": "title",
    "valueField": "value",
    "labelRadius": 5,
	
    "radius": "42%",
    "innerRadius": "60%",
    "labelText": "[[title]]"
});
chart.outlineAlpha = 0.8;
chart.outlineThickness = 2;
chart.colorField = "color";
chart.pulledField = "pulled";
