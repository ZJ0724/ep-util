export default {

    main: document.getElementById("main"),

    mask: null,

    // 打开main遮罩层
    openMainMask() {
        let mask = document.createElement("div");
        mask.style.position = "absolute";
        mask.style.width = "100%";
        mask.style.height = "100%";
        mask.style.backgroundColor = "#0000005c";
        this.main.appendChild(mask);
        this.mask = mask;
    },

    // 关闭遮罩层
    closeMainMask() {
        if (this.mask !== null) {
            this.mask.remove();
            this.mask = null;
        }
    }

}