import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import index from "./view/index.vue";

// 路由
import VueRouter from "vue-router";
import cusResult from "./view/cusResult.vue";
import addUser from "./view/addUser.vue";

new Vue({
    el: "#main",

    components: {
        index
    },

    router: new VueRouter({
        routes: [
            {
                path: "/",
                redirect: "/cusResult"
            },
            {
                path: "/cusResult",
                component: cusResult
            },
            {
                path: "/addUser",
                component: addUser
            }
        ]
    })
});