import documentUtil from "../util/documentUtil.js";

export default {

    id: "ep-util-loading",

    // 打开加载
    open() {
        if (this.isOpen()) {
            return;
        }

        let div = `
            <div id="${this.id}" class="center" style="width: 100%;height: 100%;background-color: #0c0c0c52;position: fixed;top: 0;z-index: 99999;">
                <i class="layui-icon layui-icon-loading-1 layui-icon layui-anim layui-anim-rotate layui-anim-loop" style="font-size: 30px;"></i>
            </div>
        `;

        document.getElementById("main").append(documentUtil.parseDocument(div));
    },

    // 关闭
    close() {
        if (!this.isOpen()) {
            return;
        }

        document.getElementById(this.id).remove();
    },

    // 加载层是否打开
    isOpen() {
        return document.getElementById(this.id) != null;
    }

}