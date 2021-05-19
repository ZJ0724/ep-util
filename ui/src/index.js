import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import index from "./view/index.vue";

// 路由
import VueRouter from "vue-router";
import home from "./view/home.vue";
import cusResult from "./view/cusResult.vue";

new Vue({
    el: "#main",

    components: {
        index
    },

    router: new VueRouter({
        routes: [
            {
                path: "/",
                redirect: "/home"
            },
            {
                path: "/home",
                component: home
            },
            {
                path: "/cusResult",
                component: cusResult
            }
        ]
    })
});