window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //广告列表
            contentList:[]
        },
        methods:{
            //加载首页广告列表
            findContentList:function () {
                //加载轮播图广告
                axios.get("/content/findByCategoryId.do?categoryId=1").then(function (response) {
                    //app.contentList[1] = response.data;
                    app.$set(app.contentList,1,response.data);
                })
            }
        },
        created:function () {
            this.findContentList();
        }
    });
}