export default {

    // message信息弹窗
    message(message) {
        layui.use('layer', function () {
            layui.layer.msg(message);
        });
    },

    // 打开弹窗
    popup(data) {
        data = data + "";
        let div = document.createElement("div");
        let dataList = data.split("\n");

        for (let value of dataList) {
            let d = document.createElement("div");
            d.innerHTML = value + "";
            div.append(d);
        }

        layui.use('layer', function () {
            layui.layer.open({
                title: "",
                content: div.innerHTML
            });
        });
    }

};