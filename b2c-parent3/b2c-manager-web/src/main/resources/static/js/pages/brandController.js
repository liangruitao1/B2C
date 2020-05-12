var brandController = new Vue({
    mixins : [ base ],//引入base这个混入对象
    el : "#brandView",// 绑定的视图对象
    data : {
        basePath : "/manager/brand"
    }
});