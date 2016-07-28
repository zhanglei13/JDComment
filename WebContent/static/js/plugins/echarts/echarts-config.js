var base = {};
base.lineOption =  {
    tooltip: {trigger:'axis'},
    legend: {data:[]},
    toolbox: {show:true, feature:{magicType:{show:true,type:['line','bar','stack','tiled']}}},
    calculable: true,
    xAxis: [{type:'category',boundaryGap:false,data:[]}],
   	yAxis: [{type:'value'}],
    series: []
};

base.pieOption = {
    title: {},
    tooltip: {trigger:'item',formatter:"{a} <br/>{b} : {c} ({d}%)"},
    legend: {orient:'vertical',x:'left',data:[]},
    calculable: true,
    series: []
};