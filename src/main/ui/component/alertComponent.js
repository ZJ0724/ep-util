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
        let area = ["50%", "70%"];

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
            area = ["auto", "auto"];
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