import routerConfig from "../config/routerConfig.js";
import documentCommon from "./documentCommon.js";

export default {

    // 加载
    load() {
        let hash = window.location.hash;
        let $template = $("*[template]");
        let templateElement = $template[0];

        for (let value of routerConfig) {
            if (value.hash === hash) {
                $template.load(value.template, null, function () {
                    // 重新加载input.js
                    documentCommon.loadJs("/style/input.js");
                });
                return;
            }
        }

        // 未找到配置路由清空template
        templateElement.innerHTML = "";
    }

}