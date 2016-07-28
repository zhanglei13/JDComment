$.ajax({
    url:"../admin/file3",
    type:"get",
    success:function(data){
        var chartData = [];
        var firstDate = new Date();
        firstDate.setDate(firstDate.getDate() - 60);
        
        for (var j =0; j<20; j++) {
       //     var newDate = new Date(firstDate);
            var newDate = new Date(firstDate.valueOf() + j*3*24*60*60*1000);

            var visits = data[j];

            chartData.push({
                date: newDate,
                jindongvisits: visits
            });

        }
        
        console.log(chartData);
        var chart = AmCharts.makeChart("minichartdiv", {
            "type": "serial",
            "theme": "none",
            "dataProvider": chartData,
            "graphs": [{
                "bulletSize": 3,
                "balloonText": "[[value]]",
                 "bullet": "round",
                 "bulletBorderAlpha": 1,
                "valueField": "jindongvisits"
            }],
            "marginTop": 5,
            "marginRight": 2,
            "marginLeft": 30,
            "marginBottom": 9,
            "chartCursor": {graphBulletSize:1},
            "autoMargins": false,
            "dataDateFormat": "YYYY-MM-DD",
            "categoryField": "date",
            "categoryAxis": {
                "parseDates": true,
                "axisAlpha": 0,
                "gridAlpha": 0,
                "inside": true,
                "tickLength": 0
            }
        });

    }
})
