window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //查询结果集
            resultMap:{},
            //搜索条件集{keywords: 关键字, category: 商品分类, brand: 品牌,
            //          spec: {'网络'：'移动4G','机身内存':'64G',price:价格区间}
            searchMap:{keyword:'',category:'',brand:'',spec:{},price:''}
        },
        methods:{
            search:function () {
                axios.post("/item/search.do", this.searchMap).then(function (response) {
                    app.resultMap = response.data;
                });
            },
            /**
             * 添加搜索项
             * @param key 操作的属性名:category,brand,spec
             * @param value 记录值
             */
            addSearchItem:function (key,value) {
                if(key == "category" || key == "brand" || key == "price"){
                    //this.searchMap[key] = value;
                    //动态对象构建，使用$set
                    app.$set(this.searchMap, key, value);
                }else{
                    app.$set(this.searchMap.spec,key,value);
                }
                //刷新数据
                this.search();
            },
            /**
             * 撤销搜索项
             * @param key
             */
            deleteSearchItem:function (key) {
                if(key == "category" || key == "brand" || key=="price"){
                    //this.searchMap[key] = value;
                    //动态对象构建，使用$set
                    app.$set(this.searchMap, key, '');
                }else{
                    //app.$set(this.searchMap.spec,key,value);
                    //与$set一样$delete
                    //delete this.searchMap.spec[key];
                    app.$delete(this.searchMap.spec,key);
                }

                //刷新数据
                this.search();
            }
        },
        created:function () {
            //初始化查询所有商品
            this.search();
        }
    });
}