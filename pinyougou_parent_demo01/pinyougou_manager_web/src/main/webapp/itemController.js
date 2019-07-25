window.onload = function () {
    var app = new Vue({
        el: "#app",
        data: {
            //购买数量
            num: 1,
            //记录用户选择的规格{网络：移动4G}
            specificationItems: {},
            //当前将要购买的商品
            sku:{}
        },
        methods: {
            //修改购买数量
            addNum: function (x) {
                if (x < 1) {
                    x = 1;
                }
                this.num = x;
            },
            /**
             * 用户选择规格
             * @param specName 规格名称
             * @param optionName 选项名称
             */
            selectSpecification:function (specName,optionName) {
                //记录用户选中的规格
                app.$set(this.specificationItems,specName,optionName);

                //刷新sku信息
                this.searchSku();
            },
            /**
             * 用于识别当前规格是否选中状态
             * @param specName 规格名称
             * @param optionName 选项名称
             * @return {boolean}
             */
            isSelected:function (specName,optionName) {
                return this.specificationItems[specName] == optionName;
            },
            //加载默认的sku
            loadSku:function () {
                //第一个就是默认的，因为后台排序过
                this.sku = skuList[0];
                //让默认的规格选中,注意这里一定要用深克隆复制信息
                this.specificationItems = JSON.parse(JSON.stringify(this.sku.spec));
            },
            //匹配两个对象的内容是否一致
            matchObject:function(map1,map2){
                for(var k in map1){
                    if(map1[k]!=map2[k]){
                        return false;
                    }
                }
                for(var k in map2){
                    if(map2[k]!=map1[k]){
                        return false;
                    }
                }
                return true;
            },
            //选择规格后，查询相应的sku
            searchSku: function () {
                for (let i = 0; i < skuList.length; i++) {
                    //使用用户选中的规格信息与sku列表的规格信息对比
                    if (this.matchObject(skuList[i].spec, this.specificationItems)) {
                        this.sku = skuList[i];
                        return;
                    }
                }
                //如果没有匹配的
                this.sku = {id: 0, title: '--------', price: 0};
            },
           //添加购物车
            addToCart: function () {
                //先打印出来看看，后续课程使用到
                alert('skuid:' + this.sku.id);
            }
        },
        created: function () {
            this.loadSku();
        }
    });
}