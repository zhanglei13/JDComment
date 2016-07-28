            var chartData = generateChartData();
var chart = AmCharts.makeChart("bigchartdiv", {
    "type": "serial",
    "theme": "none",
    "legend": {
        "equalWidths": false,
        "useGraphSettings": true,
        "valueAlign": "left",
        "valueWidth": 120
    },
    "pathToImages": "../amcharts/amcharts_stock/images/",
    "dataProvider": chartData,
    "valueAxes": [{
        "axisAlpha": 0.2,
        "dashLength": 1,
        "position": "left"
    }],
    "mouseWheelZoomEnabled":true,
    "graphs": [{
        // "id":"g1",
        "balloonText": "[[category]]<br/><b><span style='font-size:14px;'>value: [[value]]</span></b>",
        "bullet": "round",
        "bulletBorderAlpha": 1,
        "bulletColor":"#FFFFFF",
        "hideBulletsCount": 50,
        "title": "京东",
        "valueField": "jindongvisits",
        "useLineColorForBulletBorder":true
    },{
        // "id":"g1",
        "balloonText": "[[category]]<br/><b><span style='font-size:14px;'>value: [[value]]</span></b>",
        "bullet": "round",
        "bulletBorderAlpha": 1,
        "bulletColor":"#729C34",
        "hideBulletsCount": 50,
        "title": "淘宝",
        "valueField": "taobaovisits",
        "useLineColorForBulletBorder":true
    }],
    "chartScrollbar": {
        "autoGridCount": true,
        // "graph": "g1",
        "scrollbarHeight": 40
    },
    "chartCursor": {
        "cursorPosition": "mouse"
    },
    "categoryField": "date",
    "categoryAxis": {
        "parseDates": true,
        "axisColor": "#DADADA",
        "dashLength": 1,
        "minorGridEnabled": true
    },
    "exportConfig":{
      menuRight: '20px',
      menuBottom: '30px',
      menuItems: [{
      icon: '../amcharts/amcharts_stock/images/export.png',
      format: 'png'   
      }]  
    }
});

            chart.addListener("rendered", zoomChart);
            zoomChart();
            // this method is called when chart is first inited as we listen for "dataUpdated" event
            function zoomChart() {
                // different zoom methods can be used - zoomToIndexes, zoomToDates, zoomToCategoryValues
                chart.zoomToIndexes(chartData.length - 40, chartData.length - 1);
            }


            // generate some random data, quite different range
            function generateChartData() {
                var chartData = [];
                var firstDate = new Date();
                firstDate.setDate(firstDate.getDate() - 5);

                for (var i = 0; i < 20; i++) {
                    // we create date objects here. In your data, you can have date strings
                    // and then set format of your dates using chart.dataDateFormat property,
                    // however when possible, use date objects, as this will speed up chart rendering.
                    var newDate = new Date(firstDate);
                    newDate.setDate(newDate.getDate() + i);

                    var visits = Math.round(Math.random() * (40 + i / 5)) + 20 + i;
                    var visits2 = Math.round(Math.random() * (40 + i / 5)) + 20 + i;

                    chartData.push({
                        date: newDate,
                        jindongvisits: visits,
                        taobaovisits: visits2

                    });
                }
                return chartData;
            }
