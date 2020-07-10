(function (util) {
    util.documentUtil = {
        // 将字符串转换成document元素
        parseDocument(string) {
            let div = document.createElement("div");
            div.innerHTML = string;
            return div.children[0];
        }
    };
})(window.epUtil.util);