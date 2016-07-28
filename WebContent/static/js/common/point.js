 /**
  * hello
  * User: wangkun
  * Date: 16-01-08
  */
 /**
  * 埋点对应关系
  * @param  {[type]} id [description]
  * @return {[type]}    [description]
  */

     function bindPoint() {
         $(".clstag").each(function () {
             var id = $(this).attr('dataId');
             var clstag = getNo(id);
             $(this).attr("clstag", clstag);
         });
     }

     function getNo(id) {
         var mapping = {
             //菜单栏
             "370": "pageclick|keycount|menu_201601071|1",
             "371": "pageclick|keycount|menu_201601071|2",
             "1090": "pageclick|keycount|menu_201601071|3",
             "973": "pageclick|keycount|menu_201601071|4",
             "532": "pageclick|keycount|menu_201601071|5",
             "533": "pageclick|keycount|menu_201601071|6",
             "a": "pageclick|keycount|menu_201601071|7",
             "979": "pageclick|keycount|menu_201601071|8",
             "982": "pageclick|keycount|menu_201601071|9",
             "482": "pageclick|keycount|menu_201601071|10",
             "b": "pageclick|keycount|menu_201601071|11",
             "672": "pageclick|keycount|menu_201601071|12",
             "673": "pageclick|keycount|menu_201601071|13",
             "674": "pageclick|keycount|menu_201601071|14",
             "1690": "pageclick|keycount|menu_201601071|15",
             "677": "pageclick|keycount|menu_201601071|16",
             "1906": "pageclick|keycount|menu_201601071|17",
             "1015": "pageclick|keycount|menu_201601071|18",
             "491": "pageclick|keycount|menu_201601071|19",
             "492": "pageclick|keycount|menu_201601071|20",
             "742": "pageclick|keycount|menu_201601071|21",
             "553": "pageclick|keycount|menu_201601071|22",
             "495": "pageclick|keycount|menu_201601071|23",
             "496": "pageclick|keycount|menu_201601071|24",
             "1696": "pageclick|keycount|menu_201601071|25",
             "1699": "pageclick|keycount|menu_201601071|26",
             "1033": "pageclick|keycount|menu_201601071|27",
             "1284": "pageclick|keycount|menu_201601071|28",
             "970": "pageclick|keycount|menu_201601071|29",
             "1029": "pageclick|keycount|menu_201601071|30",
             "1902": "pageclick|keycount|menu_201601071|31",
             "1351": "pageclick|keycount|menu_201601071|32",
             "1352": "pageclick|keycount|menu_201601071|33",
             "1873": "pageclick|keycount|menu_201601071|34",
             "c": "pageclick|keycount|menu_201601071|35",
             "menuLogo": "pageclick|keycount|menu_201601071|99",
             //首页
             "guideUser":"pageclick|keycount|home_201601083|1",
             "showUser":"pageclick|keycount|home_201601083|2",
             "storeAdmin":"pageclick|keycount|home_201601083|3",
             "onSale":"pageclick|keycount|home_201601083|4",
             "adminStation":"pageclick|keycount|home_201601083|5",
             //订单中心-拣货小助手-商超订单
             "export":"pageclick|keycount|order_pick_201601087|1",
             "pick":"pageclick|keycount|order_pick_201601087|2",
             "statistics":"pageclick|keycount|order_pick_201601087|3",
             "alldelivery":"pageclick|keycount|order_pick_201601117|1",
             //订单中心-拣货小助手-服务订单+全部
             "exportOrder":"pageclick|keycount|initall_201601114|1",
             "allorders":"pageclick|keycount|service_order_201601116|1",
             "allservice":"pageclick|keycount|service_order_201601116|2",
             "alldeal":"pageclick|keycount|service_order_201601116|3",
             //商家管理-短信平台设置
             "newmessage":"pageclick|keycount|storeNotice_201601086|1",
             "messagesubmit":"pageclick|keycount|storeNotice_201601086|2",
             // 密码修改(直接引用)
             "keyword":"pageclick|keycount|key_201601112|1",
             //商家管理-门店管理
             "settingRadius":"pageclick|keycount|store_list_201601085|1",
             //数据中心-商家经营管理
             "seven_day":"pageclick|keycount|data_store_201601088|1",
             "thirty_day":"pageclick|keycount|data_store_201601088|2",
             "DIY_time":"pageclick|keycount|data_store_201601088|3",
             "cjzje":"pageclick|keycount|data_store_201601088|4",
             "ddjj":"pageclick|keycount|data_store_201601088|5",
             "xddl":"pageclick|keycount|data_store_201601088|6",
             "wcddl":"pageclick|keycount|data_store_201601088|7",
             "qxddl":"pageclick|keycount|data_store_201601088|8",
             "fks":"pageclick|keycount|data_store_201601088|9",
             "pjjhsj":"pageclick|keycount|data_store_201601088|10",
             "store_operat":"pageclick|keycount|data_store_201601088|11",
             "store_turnover":"pageclick|keycount|data_store_201601088|12",
             "store_customer":"pageclick|keycount|data_store_201601088|13",
             "store_pv":"pageclick|keycount|data_store_201601088|14",
             "data_store":"pageclick|keycount|djdata_201601141|1",
         //  数据中心-门店经营管理
             "station_seven":"pageclick|keycount|data_station_201601089|1",
             "station_thirty":"pageclick|keycount|data_station_201601089|2",
             "station_diy":"pageclick|keycount|data_station_201601089|3",
             "station_xddl":"pageclick|keycount|data_station_201601089|4",
             "station_wcddl":"pageclick|keycount|data_station_201601089|5",
             "station_cjje":"pageclick|keycount|data_station_201601089|6",
             "station_fks":"pageclick|keycount|data_station_201601089|7",
             "station_zhl":"pageclick|keycount|data_station_201601089|8",
             "station_pjjhsj":"pageclick|keycount|data_station_201601089|9",
             "station_ddjj":"pageclick|keycount|data_station_201601089|10"

         };
         return mapping[id] || "";
     }
