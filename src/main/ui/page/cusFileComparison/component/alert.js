(function (component) {
    component.alert = {
        // message信息弹窗
        message(message) {
            layui.use('layer', function(){
                layui.layer.msg(message);
            });
        }
    };
})(window.epUtil.component);