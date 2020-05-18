export default {

    // 打开加载
    openLoading(element) {
        let div = document.createElement("div");
        let i = document.createElement("i");

        element.style.position = "relative";
        div.style.position = "absolute";
        div.style.width = "100%";
        div.style.height = "100%";
        div.style.left = "0";
        div.style.top = "0";
        div.style.backgroundColor = "#00000036";
        div.setAttribute("class", "center");
        i.setAttribute("class", "layui-icon layui-icon-loading-1 layui-icon layui-anim layui-anim-rotate layui-anim-loop");
        i.style.fontSize = "50px";
        div.appendChild(i);

        element.appendChild(div);
    }

}