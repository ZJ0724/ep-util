(function (component) {
    component.alert = {
        // message信息弹窗
        message(message) {
            layui.use('layer', function(){
                layui.layer.msg(message);
            });
        },

        // 打开弹窗
        popup(data) {
            layui.use('layer', function(){
                layui.layer.open({
                    title: "",
                    content: data
                });
            });
        }
    };
})(window.epUtil.component);