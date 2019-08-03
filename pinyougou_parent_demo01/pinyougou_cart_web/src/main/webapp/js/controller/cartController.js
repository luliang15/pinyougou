//window.onload=function () {
var app = new Vue({
    el:"#app",
    data:{
        //购物车列表
        cartList:[],
        //统计数据
        totalValue:{totalNum:0,totalMoney:0.0},
        //收件人列表
        addressList:[],
        //用户当前选中的地址
        address:{},
        //订单对象{paymentType:支付方式(默认是1)}
        order:{paymentType:1}
    },
    methods:{
        //查询当前用户的购物车列表
        findCartList:function () {
            axios.get("/cart/findCartList.do").then(function (response) {
                app.cartList = response.data;

                //重新计算统计数据
                for(let i = 0; i < app.cartList.length; i++){
                    let cart = app.cartList[i];
                    for(let j = 0; j < cart.orderItemList.length; j++){
                        //计算数量与金额
                        app.totalValue.totalNum += cart.orderItemList[j].num;
                        app.totalValue.totalMoney += cart.orderItemList[j].totalFee;
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
        },
        //加载收件人列表
        findAddressList:function () {
            axios.get("/address/findListByUserId.do").then(function (response) {
                app.addressList = response.data;
                //读取默认地址
                for(let i = 0; i < app.addressList.length; i++){
                    //找到默认地址
                    if('1' == app.addressList[i].isDefault){
                        app.address = app.addressList[i];
                         break;
                    }
                }
            });
        },
        //修改收件人地址
        selectAddress:function (address) {
            this.address = address;
        },
        //修改支付方式
        selectPayType:function (type) {
            this.order.paymentType = type;
        },
        //保存订单
        submitOrder:function () {
            //把收件人相关信息读取并关联
            this.order.receiverAreaName = this.address.address;
            this.order.receiverMobile = this.address.mobile;
            this.order.receiver = this.address.contact;
            //保存订单
            axios.post("/order/add.do",this.order).then(function (response) {
                alert(response.data.message);
                if(response.data.success){
                    if(app.order.paymentType == 1){
                        window.location.href = "pay.html";
                    }else{
                        window.location.href = "paysuccess.html";
                    }
                }
            })
        }
    },
    created:function () {
        this.findCartList();
        //加载收件人
        this.findAddressList();
    }
});
//}