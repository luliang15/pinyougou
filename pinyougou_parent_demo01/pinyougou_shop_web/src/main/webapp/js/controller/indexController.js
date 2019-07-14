window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //登录名
            loginName:""
        },
        methods:{
            getLoginInfo:function () {
                axios.get("/login/info.do").then(function (response) {
                    app.loginName = response.data.loginName;
                })
            }
        },
        created:function () {
            this.getLoginInfo();
        }
    });
}