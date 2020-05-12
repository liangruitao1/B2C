var goodsController = new Vue({
    mixins: [base],
    el: "#goodsView",//绑定的视图对象
    data: {
        pic  : null ,
        itemImage : {
            color : "",
            url : ""
        },
        basePath: "/seller/goods",
        itemCatList1: [],// 一级分类列表
        itemCatList2: [],// 二级分类列表
        itemCatList3: [],// 三级分类列表
        brandList: [],
        specUionList: [],// 规格选项联合实体类列表
        entity: {
            goods: {
                brandId: 0,
                category1Id: 0,// 一级分类id
                category2Id: 0,// 二级分类id
                category3Id: 0,// 三级分类id
                typeTemplateId: 0

            },
            goodsDesc: {// 提交给goodsDesc表
                itemImages : [],// 图片信息列表
                specificationItems: [],// 规格选项列表
                customAttributeItems: [],// 自定义属性列表

            },
            itemList : [],// SKU列表
        }
    },
    created: function () {
        //进入页面首先初始化一级分类信息
        this.findItemCatsByParentId(0, "itemCatList1");

    },
    methods: {

        selectFile : function(event) {
            this.pic = event.target.files[0];
        },
        initImage : function() {
            this.itemImage = {
                color : "",
                url : ""
            };
        },
        uploadImage : function() {
            var formData = new FormData();// 构造表单对象
            var self = this;
            formData.append("pic", this.pic);
            axios.post("/seller/upload", formData, {
                headers : {
                    "content-Type" : "multipart/form-data"
                }
            }).then(function(res) {
                console.debug(res.data.code);
                self.itemImage.url = res.data.msg;
                console.debug(self.itemImage.url);
            });
        },
        addImage : function() {
            this.entity.goodsDesc.itemImages.push(this.itemImage);
        },
        removeImage : function(index) {
            this.entity.goodsDesc.itemImages.splice(index, 1);
        },
        createItemListStructure : function() {
            var self = this;
            var structure = [ {// 初始化保存结构
                spec : {},
                price : 0,
                num : 999,
                status : "0",
                isDefault : "0"
            } ];
            this.entity.goodsDesc.specificationItems.forEach(function(item,
                                                                      index, arr) {
                // 添加spec中的列，也就是给spec增加一个键值对
                structure = self.addColumn(structure, item.attributeName,
                    item.attributeValue);
            });
            // console.debug(structure);
            this.$set(this.entity, "itemList", structure);
        },
        // 添加spec中的列
        addColumn : function(list, columnName, columnValues) {
            var newList = [];
            list.forEach(function(item, index, arr) {
                columnValues.forEach(function(columnValue, ind, ar) {
                    // 必须以JSON的方式进行克隆
                    // 直接修改item的话，会覆盖之前push进去的数据
                    var row = JSON.parse(JSON.stringify(item));
                    row.spec[columnName] = columnValue;
                    newList.push(row);
                });
            });
            // 如果不判断length，会导致createItemListStructure中的循环结束。
            if (newList.length == 0)
                return list;
            return newList;
        },
        findSpecUnionsByTypeTemplateId: function (typeTemplateId) {
            var self = this;
            var uri = "/seller/typeTemplate/specUnions/" + typeTemplateId;
            axios.get(uri).then(function (res) {
                // 创建specificationItems的保存结构
                self.createSpecificationItemsStructure(res);
                self.specUionList = res.data;
            });
        },
        // 创建specificationItems的保存结构
        createSpecificationItemsStructure: function (res) {
            // this.entity.goodsDesc.specificationItems = [];
            var self = this;
            res.data.forEach(function (item, index, arr) {
                // 设置响应式属性
                // 如果没有设置，则用户选择的规格信息无法更新到specificationItems中
                self.$set(self.entity.goodsDesc.specificationItems, index, {
                    attributeName: item.spec.specName,
                    attributeValue: []
                });
            });
        },

        findItemCatsByParentId: function (parentId, listFlag) {
            var self = this;
            var uri = "/seller/itemCats/" + parentId;
            axios.get(uri).then(function (res) {
                self.$data[listFlag] = res.data;
            });
        },
        findTypeTemplateByItemCatId: function (itemCatId) {
            var self = this;
            var uri = "/seller/itemCat/" + itemCatId;
            axios.get(uri).then(function (res) {
                self.entity.goods.typeTemplateId = res.data.typeId;
            });
        },
        findBrandIdsAndCustomAttributeItemsByTypeTemplateId: function (typeTemplateId) {
            var self = this;
            var uri = "/seller/typeTemplate/" + typeTemplateId;
            axios.get(uri).then(function (res) {
                self.brandList = JSON.parse(res.data.brandIds);
                self.entity.goodsDesc.customAttributeItems = JSON
                    .parse(res.data.customAttributeItems);
            })
        },
        save: function () {


            console.debug(this.entity);
        }

    },
    watch: {
        //key-需要观察的属性名
        //value-属性发生变化时执行的回调函数

        //val：当前值
        //oldVal：改变之前的值
        "entity.goods.category1Id": function (val, oldVal) {
            this.findItemCatsByParentId(val, "itemCatList2");
        },
        "entity.goods.category2Id": function (val, oldVal) {
            this.findItemCatsByParentId(val, "itemCatList3");
        },
        "entity.goods.category3Id": function (val, oldVal) {
            this.findTypeTemplateByItemCatId(val)
        },
        "entity.goods.typeTemplateId" : function(val, oldVal) {
            this.findBrandIdsAndCustomAttributeItemsByTypeTemplateId(val);
            this.findSpecUnionsByTypeTemplateId(val);
        }
    }
});