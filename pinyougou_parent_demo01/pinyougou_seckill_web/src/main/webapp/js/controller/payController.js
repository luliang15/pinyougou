window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //支付页面对象{money:支付金额,out_trade_no:支付单号}
            entity:{money:0,out_trade_no:''}
        },
        methods:{
            //生成二维码
            createNative:function () {
                axios.get("/pay/createNative.do").then(function (response) {
                    //绑定订单号与金额
                    app.entity.out_trade_no = response.data.out_trade_no;
                    app.entity.money = (response.data.total_fee / 100).toFixed(2);
                    //生成二维码
                    var qrcode = new QRCode(document.getElementById("qrcode"), {
                        text: response.data.code_url,
                        width: 200,
                        height: 200,
                        colorDark : "#000000",
                        colorLight : "#ffffff",
                        correctLevel : QRCode.CorrectLevel.H
                    });

                    //生成二维码成功后，马上去查询订单状态
                    app.queryPayStatus();
                })
            },
            //监听订单支付状态
            queryPayStatus:function () {
                axios.get("/pay/queryPayStatus.do?out_trade_no=" + this.entity.out_trade_no).then(function (response) {
                    if(response.data.success){
                        window.location.href = "paysuccess.html?money=" + app.entity.money;
                    }else{
                        if("支付超时" == response.data.message){
                            window.location.href = "paytimeout.html";
                        }else{
                            window.location.href = "payfail.html";
                        }
                    }
                })
            },
            /**
             * 解析一个url中所有的参数
             * @return {参数名:参数值}
             */
            getUrlParam:function() {
                //url上的所有参数
                var paramMap = {};
                //获取当前页面的url
                var url = document.location.href;
                //获取问号后面的参数
                var arrObj = url.split("?");
                //如果有参数
                if (arrObj.length > 1) {
                    //解析问号后的参数
                    var arrParam = arrObj[1].split("&");
                    //读取到的每一个参数,解析成数组
                    var arr;
                    for (var i = 0; i < arrParam.length; i++) {
                        //以等于号解析参数：[0]是参数名，[1]是参数值
                        arr = arrParam[i].split("=");
                        if (arr != null) {
                            paramMap[arr[0]] = arr[1];
                        }
                    }
                }
                return paramMap;
            },
            //获取其它页面传入的金额
            getMoney:function () {
                return this.getUrlParam()["money"];
            }

        },
        created:function () {
            //只有在支付页才会生成二维码
            if(window.location.href.indexOf("pay.html") > -1){
                this.createNative();
            }

        }
    });
}