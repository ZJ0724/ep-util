import "./js/base.js";
import Vue from "vue/dist/vue.esm.js";
import index from "./view/index.vue";

// 路由
import VueRouter from "vue-router";
import cusResult from "./view/cusResult.vue";
import addUser from "./view/addUser.vue";
import thirdParty from "./view/thirdParty.vue";
import config from "./view/config.vue";
import agentResult from "./view/agentResult.vue";

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
                component: cusResult,
                meta: {
                    title:"报关单回执"
                }
            },
            {
                path: "/agentResult",
                component: agentResult,
                meta: {
                    title:"代理委托回执"
                }
            },
            {
                path: "/addUser",
                component: addUser
            },
            {
                path: "/thirdParty",
                component: thirdParty,
                meta: {
                    title:"第三方接口调试"
                }
            },
            {
                path: "/config",
                component: config,
                meta: {
                    title:"配置"
                }
            }
        ]
    })
});