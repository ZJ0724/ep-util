(function () {

    let inputs = document.querySelectorAll("input");

    for (let input of inputs) {
        let inputParent = input.parentElement;
        let i = document.createElement("i");

        i.style.position = "absolute";
        i.style.right = "8px";
        i.style.top = "9px";
        i.style.cursor = "pointer";
        i.setAttribute("class", "layui-icon layui-icon-close");

        input.addEventListener("focus", function () {
            i.setAttribute("class", "layui-icon layui-icon-close layui-anim layui-anim-fadein");
            setTimeout(function () {
                inputParent.appendChild(i);
            }, 150);
        });
        input.addEventListener("blur", function () {
            i.setAttribute("class", "layui-icon layui-icon-close layui-anim layui-anim-fadeout");
            setTimeout(function () {
                i.remove();
            }, 150);
        });
        i.addEventListener("click", function () {
            input.value = "";
        });
    }

})();

