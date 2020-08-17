import documentUtil from "../util/documentUtil.js";

export default {

    id: "mask",

    open() {
        if (this.isOpen()) {
            return;
        }

        let div = `<div id="${this.id}" class="layui-anim layui-anim-fadein" style="background-color: #0c0c0c38;position: fixed;top: 0;width: 100%; height: 100%;z-index: 100;"></div>`;

        document.getElementById("main").append(documentUtil.parseDocument(div));
    },

    close() {
        if (!this.isOpen()) {
            return;
        }

        document.getElementById(this.id).remove();
    },

    isOpen() {
        return document.getElementById(this.id) != null;
    }
}