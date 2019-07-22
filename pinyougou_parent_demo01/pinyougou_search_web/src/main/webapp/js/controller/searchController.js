window.onload=function () {
    var app = new Vue({
        el:"#app",
        data:{
            //查询结果集
            resultMap:{},
            //搜索条件集{keywords: 关键字, category: 商品分类, brand: 品牌,
            //          spec: {'网络'：'移动4G','机身内存':'64G',price:价格区间,pageNo:当前页,pageSize:查询条数}
            searchMap:{keyword:'',category:'',brand:'',spec:{},price:'',pageNo:1,pageSize:20},
            //pageLable:[分页标签列表]
            pageLable:[],
            //标识分页插件中是否显示前面的省略号
            firstDot:true,
            //标识分页插件中是否显示后面的省略号
            lastDot:true,
            //用于记录查询按钮点击后searchMap.keyword的值
            searchKeyword:''
        },
        methods:{
            //搜索
            search:function () {
                axios.post("/item/search.do", this.searchMap).then(function (response) {
                    app.resultMap = response.data;
                    //刷新分页标签
                    app.buildPageLabel();
                });
            },
            //构建分页标签
            buildPageLabel:function(){
                //每次重新清空数组
                this.pageLable = [];
                let firstPage = 1;  //开始页码
                let lastPage = this.resultMap.totalPages;  //结束页码
                this.firstDot=true;//前面有点
                this.lastDot=true;//后边有点
                //计算分页逻辑
                //如果总页数 > 5
                if(this.resultMap.totalPages > 5){
                    //如果当前页 <= 3
                    if(this.searchMap.pageNo <= 3){
                        //只显示前5页
                        lastPage = 5;
                        //前面没省略号
                        this.firstDot = false;
                        this.lastDot = true;
                        //如果当页在后3页
                    }else if(this.searchMap.pageNo >= (this.resultMap.totalPages - 2)){
                        //只显示后5页
                        firstPage = this.resultMap.totalPages - 4;
                        //后面没点
                        this.lastDot = false;
                        this.firstDot = true;
                    }else{
                        firstPage = this.searchMap.pageNo - 2;
                        lastPage = this.searchMap.pageNo + 2;

                        //前后都有点
                        this.firstDot = true;
                        this.lastDot = true;
                    }
                }else{
                    //前后都没点
                    this.firstDot = false;
                    this.lastDot = false;
                }

                //此处应该从1开始
                for(let i = firstPage; i <= lastPage; i++){
                    app.pageLable.push(i);
                }
            },
            //跳转页码
            queryByPage:function(pageNo){
                //先把要跳转的页码转成int
                pageNo = parseInt(pageNo);
                if(pageNo < 1 || pageNo > this.resultMap.totalPages){
                    alert("请输入正确的页码！");
                    return;
                }
                this.searchMap.pageNo = pageNo;
                //刷新数据
                this.search();
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