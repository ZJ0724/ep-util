(function () {
    // 注册<navigation>组件
    Vue.component("navigation", {
        template: `<div id="head">
            <div class="dropdown">
                <div class="item" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    报关单回执
                </div>
                <ul class="dropdown-menu">
                    <li><a href="/formCusResult/tongXunFormCusResult">上传通讯回执</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="">上传业务回执</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="">一次性上传</a></li>
                </ul>
            </div>

            <div class="dropdown">
                <div class="item" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    修撤单回执
                </div>
                <ul class="dropdown-menu">
                    <li><a href="">QP回执</a></li>
                    <li role="separator" class="divider"></li>
                    <li><a href="">业务回执</a></li>
                </ul>
            </div>
        </div>`
    });
})();