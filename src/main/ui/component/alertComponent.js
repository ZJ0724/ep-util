export default {

    // message信息弹窗
    message(message) {
        layui.use('layer', function () {
            layui.layer.msg(message);
        });
    },

    // 打开弹窗
    popup(data) {
        let div = document.createElement("div");

        // list
        if (data instanceof Array) {
            for (let item of data) {
                let d = document.createElement("div");
                d.innerHTML = item + "";
                d.style.marginTop = "20px";
                div.append(d);
            }
        } else {
            // \n
            data = data + "";
            let dataList = data.split("\n");
            for (let value of dataList) {
                let d = document.createElement("div");
                d.innerHTML = value + "";
                div.append(d);
            }
        }

        layui.use('layer', function () {
            layui.layer.open({
                title: "",
                content: div.innerHTML
            });
        });
    }

};