export default {

    // 打开弹窗
    openPopup(data) {
        layui.use('layer', function(){
            layui.layer.open({
                title: "",
                content: data
            });
        });
    },

    // 打开提示型弹窗
    openMsg(data) {
        layui.use('layer', function(){
            layui.layer.msg(data);
        });
    }

}