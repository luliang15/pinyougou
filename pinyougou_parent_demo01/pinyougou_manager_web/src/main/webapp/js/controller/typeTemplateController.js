//页面初始化完成后再创建Vue对象
window.onload=function () {
    //创建Vue对象
    var app = new Vue({
        //接管id为app的区域
        el:"#app",
        data:{
            //声明数据列表变量，供v-for使用
            list:[],
            //总页数
            pages:1,
            //当前页
            pageNo:1,
            //声明对象
            entity:{brandIds:[],specIds:[],customAttributeItems:[]},
            //将要删除的id列表
            ids:[],
            //搜索包装对象
            searchEntity:{},
            //品牌列表
            brandList:[],
            //规格列表
            specList:[]
        },
        methods:{
            //查询所有
            findAll:function () {
                axios.get("../typeTemplate/findAll.do").then(function (response) {
                    //vue把数据列表包装在data属性中
                    app.list = response.data;
                }).catch(function (err) {
                    console.log(err);
                });
            },
            //分页查询
            findPage:function (pageNo) {
                axios.post("../typeTemplate/findPage.do?pageNo="+pageNo+"&pageSize="+10,this.searchEntity)
                    .then(function (response) {
                        app.pages = response.data.pages;  //总页数
                        app.list = response.data.rows;  //数据列表
                        app.pageNo = pageNo;  //更新当前页
                    });
            },
            //让分页插件跳转到指定页
            goPage:function (page) {
                app.$children[0].goPage(page);
            },
            //新增
            add:function () {
                var url = "../typeTemplate/add.do";
                if(this.entity.id != null){
                    url = "../typeTemplate/update.do";
                }
                axios.post(url, this.entity).then(function (response) {
                    if (response.data.success) {
                        //刷新数据，刷新当前页
                        app.findPage(app.pageNo);
                    } else {
                        //失败时显示失败消息
                        alert(response.data.message);
                    }
                });
            },
            //跟据id查询
            getById:function (id) {
                axios.get("../typeTemplate/getById.do?id="+id).then(function (response) {
                    app.entity = response.data;
                    //把品牌json字符串转换为数组对象
                    app.entity.brandIds = JSON.parse(app.entity.brandIds);
                    //把规格json字符串转换为数组对象
                    app.entity.specIds = JSON.parse(app.entity.specIds);
                    //把扩展属性son字符串转换为数组对象
                    app.entity.customAttributeItems = JSON.parse(app.entity.customAttributeItems);
                })
            },
            //批量删除数据
            dele:function () {
                axios.get("../typeTemplate/delete.do?ids="+this.ids).then(function (response) {
                    if(response.data.success){
                        //刷新数据
                        app.findPage(app.pageNo);
                        //清空勾选的ids
                        app.ids = [];
                    }else{
                        alert(response.data.message);
                    }
                })
            },
            //查询所有品牌
            findBrandList:function () {
                axios.get("/brand/findAll.do").then(function (response) {
                    for(let i = 0; i < response.data.length; i++){
                        delete response.data[i]["firstChar"];
                        delete response.data[i]["name"];
                    }
                    app.brandList = response.data;
                })
            },
            //查询所有规格
            findSpecList:function () {
                axios.get("/specification/findAll.do").then(function (response) {
                    for(let i = 0; i < response.data.length; i++){
                        delete response.data[i]["specName"];
                    }
                    app.specList = response.data;
                })
            },
            //添加表格行
            addTableRow:function () {
                this.entity.customAttributeItems.push({});
            },
            //删除表格行
            deleteTableRow:function (index) {
                //splice(删除开始下标,删除的个数)
                this.entity.customAttributeItems.splice(index, 1);
            },
            /**
             * 把json数组字符串格式化输出
             * @param jsonString 要转换的字符串
             * @param key 提取的属性
             * @return 提取的结果
             */
            jsonToString:function (jsonString,key) {
                let list = JSON.parse(jsonString);
                let result = "";
                for(let i = 0; i < list.length; i++){
                    if(i != 0){
                        result += ",";
                    }
                    result += list[i][key];
                }
                return result;
            }
        },
        //Vue对象初始化后，调用此逻辑
        created:function () {
            //调用用分页查询，初始化时从第1页开始查询
            this.findPage(1);

            this.findBrandList();
            this.findSpecList();
        }
    });
}
