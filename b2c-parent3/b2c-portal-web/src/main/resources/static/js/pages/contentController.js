var contentController = new Vue({
    mixins : [base],
    el : "#contentView",//视图对象
    data : {
      basePath : "/portal/content",
      contentList : [],
    },
    created : function () {
        this.findByCategoryId(1);
    },
    methods : {
        findByCategoryId : function (categoryId) {
            var self = this;
            var uri = "/portal/contents/"+categoryId;
            axios.get(uri).then(function (res) {
                self.contentList = res.data;
            });
        }
    }
});