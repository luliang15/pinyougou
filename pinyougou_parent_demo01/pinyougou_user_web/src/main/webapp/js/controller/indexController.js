window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //登录名
            loginName:""
        },
        methods:{
            //获取登录用户的信息
            getUserInfo:function () {
                axios.get("/login/info.do").then(function (response) {
                    app.loginName = response.data.loginName;
                })
            }
        },
        created:function () {
           this.getUserInfo();
        }
    });
}