(function () {

    // 添加js
    function addJs(path) {
        document.write(`<script src="${path}"></script>`);
    }

    // 添加css
    function addCss(path) {
        document.write(`<link rel="stylesheet" type="text/css" href="${path}" />`);
    }

    // 样式
    addCss("/style/base.css");
    addCss("/style/a.css");
    addCss("/style/head.css");
    addCss("/style/body.css");
    addCss("/style/panel.css");

    // layui
    addCss("/frame/layui/css/layui.css");
    addJs("/frame/layui/layui.all.js");

    // jquery
    addJs("/frame/jquery-3.5.1.min.js");

    // bootstrap
    addCss("/frame/bootstrap-3.3.7-dist/css/bootstrap.css");
    addJs("/frame/bootstrap-3.3.7-dist/js/bootstrap.js");

    // vue
    addJs("/frame/vue.min.js");

})();