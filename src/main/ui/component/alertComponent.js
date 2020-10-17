export default {
    // message信息弹窗
    message(message) {
        layui.use('layer', function () {
            layui.layer.msg(message);
        });
    },

    // 打开弹窗
    popup(data, isEnlarge) {
        let div = document.createElement("div");
        let area = isEnlarge ? ["50%", "70%"] : ["auto", "auto"];
        // list
        if (data instanceof Array) {
            for (let item of data) {
                let d = document.createElement("div");
                d.innerHTML = item + "";
                d.style.marginTop = "20px";
                div.append(d);
            }
        }
        // \n
        else if (data.indexOf("\n") !== -1) {
            // \n
            data = data + "";
            let dataList = data.split("\n");
            for (let value of dataList) {
                let d = document.createElement("div");
                d.innerHTML = value + "";
                div.append(d);
            }
        }
        else {
            div.innerHTML = data;
        }

        layui.use('layer', function () {
            layui.layer.open({
                title: "",
                area: area,
                content: div.innerHTML
            });
        });
    }
};