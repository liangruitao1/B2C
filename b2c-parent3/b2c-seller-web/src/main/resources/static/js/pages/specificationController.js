var specificationController = new Vue({
    mixins:[base],
    el:'#specificationView',//绑定的视图对象(作用域)
    data:{//对象的属性
        basePath:"/manager/specification",//基础路径
    },
    created:function () {
        // Vue实例在创建好以后，不能检测到对象属性的添加或删除，需要通过以下方式添加。
        //vue.set(target,key,value);
        this.$set(this.entity,"spec",{});
        this.$set(this.entity,"specOptionList",[]);
    },
    methods:{
        clearRows:function () {//清除规格数据
            this.entity.spec = {};
            this.entity.specOptionList = [];
        },
        addRow:function () {//动态添加一行空的规格明细
            this.entity.specOptionList.push({});//推送
        },
        deleteRow:function (index) {//动态删除一行规格明细
            this.entity.specOptionList.splice(index,1)//参数：下标，长度
        },
        saveOrUpdate:function () {
            var self = this;
            if(this.entity.spec.id){//有id则修改
                //异步调用修改的方法
                axios.put(this.basePath,this.entity).then(function (res) {
                    console.debug(res.data.code);
                    self.findPage();
                })
            }else{//没有id则添加
                //异步调用保存的方法
                axios.post(this.basePath,this.entity).then(function (res) {
                    console.debug(res.data.code);
                    self.findPage();
                })
            }
        }
    }
});