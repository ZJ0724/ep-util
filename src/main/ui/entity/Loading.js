export default class loading {

    element;
    loadingElement;

    constructor(element) {
        this.element = element;
    }

    // 打开加载
    openLoading() {
        let div = document.createElement("div");
        let i = document.createElement("i");

        div.setAttribute("class", "center loading");
        i.setAttribute("class", "layui-icon layui-icon-loading-1 layui-icon layui-anim layui-anim-rotate layui-anim-loop");
        i.style.fontSize = "50px";
        div.appendChild(i);

        this.element.style.position = "relative";
        this.element.appendChild(div);
        this.loadingElement = div;
    }

    // 关闭加载
    closeLoading() {
        this.loadingElement.remove();
        this.element.style.position = "";
    }

}