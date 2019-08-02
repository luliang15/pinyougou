window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //购物车列表
            cartList:[],
            //统计数据
            totalValue:{totalNum:0,totalMoney:0.0}
        },
        methods:{
            //查询当前用户的购物车列表
            findCartList:function () {
                axios.get("/cart/findCartList.do").then(function (response) {
                    app.cartList = response.data;
                     //每次查询重新计算金额与数量
                    app.totalValue={totalNum:0,totalMoney:0.00};
                    //重新计算统计数据
                    for(let i = 0; i < app.cartList.length; i++){
                        let cart = app.cartList[i];
                        for(let j = 0; j < cart.orderItemList.length; j++){
                            //读取购物车明细,统计数量与金额
                            let orderItem=cart.orderItemList[j];
                            //计算数量与金额
                            app.totalValue.totalNum += orderItem.num;
                            app.totalValue.totalMoney += orderItem.price*orderItem.num;
                            /*app.totalValue.totalNum += cart.orderItemList[j].num;
                            app.totalValue.totalMoney += cart.orderItemList[j].totalFee;*/
                        }
                    }
                });
            },
            /**
             * 购物车操作逻辑
             * @param itemId 操作的商品
             * @param num 修改的数量
             */
            addGoodsToCartList:function (itemId,num) {

                axios.get("/cart/addGoodsToCartList.do?itemId=" + itemId + "&num=" + num)
                    .then(function (response) {
                    if(response.data.success){
                        //刷新数据
                        app.findCartList();
                    }else{
                        alert(response.data.message);
                    }
                })
            }
        },
        created:function () {
            this.findCartList();
        }
    });
}