export default {

    // 加载js
    loadJs(path) {
        let js = document.createElement("script");
        js.src = path;
        document.head.appendChild(js);
        js.remove();
    }

}