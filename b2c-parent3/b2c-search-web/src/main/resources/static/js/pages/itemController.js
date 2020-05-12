var itemController = new Vue({
    mixins : [base],
    el : "#itemView",
    data : {
      basePath : "/search/item",
      searchParam : {
         pageNum : 1,
          pageSize : 10,
          keyword : "",
      } ,
    },
});