import Vue from "vue/dist/vue.esm.js";
import "../css/base.css";
import VueRouter from "vue-router";
import VueWechatTitle from "vue-wechat-title";

Vue.use(VueRouter);
Vue.use(VueWechatTitle);

// ep-ui
import "ionicons-npm/css/ionicons.css";
import "ep-ui/theme/lib/epui.min.css";
import epUi from "ep-ui";
Vue.use(epUi);