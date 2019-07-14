//indexController
window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            loginName:""
        },
        methods:{
            getLoginInfo:function () {
                axios.get("../login/info.do").then(function (response) {
                    app.loginName = response.data.loginName;
                })
            }
        },
        //Vue对象初始化后，调用此逻辑
        created:function () {
            //获取登录用户信息
            this.getLoginInfo();
        }
    });
}
