Vue.component('paginate',VuejsPaginate);
Vue.component('v-select',VueSelect.VueSelect);
var base={
    data : function () {
        return {//实例属性（业务逻辑中所需的各种属性）
            basePath : "",
            entity :{},
            ids : [],
            pageCount : 0,
            searchParam : {
              pageNum : 1,
              pageSize : 4
            },
            searchResult : {
                total : 0,
                list :[]
            }
        };
    },
    created : function () {
      this.findPage();
    },
    methods : {
        findPage : function () {
            var self = this ;
            //异步请求
            axios.get(this.basePath+"s",{
                params : this.searchParam
            }).then(
                function (res) {
                    self.searchResult.list = res.data.list;
                    self.pageCount = Math.ceil(res.data.total / self.searchParam.pageSize);
                });
        },
        saveOrUpdate:function () {//添加或修改
            var self = this;
            if(this.entity.id){//有id就是修改
                //异步请求修改的方法
                axios.put(this.basePath,this.entity).then(function (res) {
                    console.debug(res.data.code);//打印结果
                    self.findPage();//刷新页面
                })
            }else{//没有id就是添加
                //异步请求添加的方法
                axios.post(this.basePath,this.entity).then(function (res) {
                    console.debug(res.data.code);//打印结果
                    self.findPage();//刷新页面
                })
            }

        },
        findById : function (id) {
            var self = this;
            axios.get(this.basePath+"/"+id).then(
                function (res) {
                    self.entity=res.data;
                });
        },
        deleteByIds : function () {
            var self = this;
            if(this.ids.length>0){
                axios.delete(this.basePath+"/"+this.ids.join(",")).then(function (res) {
                    console.debug(res.data.code);
                    self.findPage();
                });
            }
        },
        jsonToString : function (jstr,key) {
            var jsonArr = JSON.parse(jstr);
            var newArr = [];
            jsonArr.forEach(function (item,index,arr) {
                newArr.push(item[key]);
            });
            return newArr.join(",");
        }
    }
}