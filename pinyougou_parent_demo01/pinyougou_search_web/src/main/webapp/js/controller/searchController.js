window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //查询结果集
            resultMap:{},
            //搜索条件集{keyword:关键字}
            searchMap:{keyword:''}
        },
        methods:{
            search:function () {
                axios.post("/item/search.do", this.searchMap).then(function (response) {
                    app.resultMap = response.data;
                });
            }
        },
        created:function () {
            //初始化查询所有商品
            this.search();
        }
    });
}