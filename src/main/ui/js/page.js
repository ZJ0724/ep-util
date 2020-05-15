export default {

    // 跳转至报关单回执页面
    goToForResult() {
        window.location.href = "/html/formResult.html";
    },

    // 跳转至修撤单回执页面
    goToDecModResult() {
        window.location.href = "/html/decModResult.html";
    },

    // 加载导航模板
    loadNavigation() {
        $("body").load("/html/template/NavigationTemplate.html");
    }

}