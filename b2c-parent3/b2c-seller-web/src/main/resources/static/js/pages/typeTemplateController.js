var typeTemplateController = new Vue({
   mixins : [base],
   el : "#typeTemplateView",
   data : {
       basePath : "/manager/typeTemplate",
       brandList : [],
       specificationList : [],
   },
   created : function () {
     this.$set(this.entity, "customAttributeItems",[]);
     this.findBrands();
     this.findSpecifications();
   },
    methods : {
       initSelectAndRows : function () {
           this.entity.id = null;
           this.entity.name = "";
           this.entity.specIds = [];
           this.entity.brandIds = [];
           this.entity.customAttributeItems = [];
       },
        addRow : function () {
            this.entity.customAttributeItems.push({});
        },
        deleteRow : function (index) {
            this.entity.customAttributeItems.splice(index,1);
        },
        findBrands : function () {
            var self = this ;
            axios.get("/manager/brands2").then(function (res) {
                self.brandList = res.data;
            });
        },
        findSpecifications : function () {
            var self = this;
            axios.get("/manager/specifications2").then(function (res) {
                self.specificationList = res.data;
            });
        },
        findById : function (id) {
            var self =this;
            axios.get(this.basePath+"/"+id).then(
                function (res) {
                    self.entity = res.data;
                    self.entity.brandIds = JSON.parse(self.entity.brandIds);
                    self.entity.specIds = JSON.parse(self.entity.specIds);
                    self.entity.customAttributeItems = JSON.parse(self.entity.customAttributeItems);
                }
            );
        }

    }

});