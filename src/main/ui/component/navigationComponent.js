import router from "../entity/router.js";

export default (function () {

    Vue.component("navigation", {
        template: `
        <div id="head">
            <div v-for="(item, i) in router" class="dropdown" :key="i">
                <div v-if="item.children !== undefined" class="item" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                    {{item.name}}
                </div>
                <div v-else @click="window.location.href = page + item.path" class="item">
                    {{item.name}}
                </div>
                <ul v-if="item.children !== undefined" class="dropdown-menu">
                    <li style="padding: 5px; 0" v-for="(child, j) in item.children" :key="j">
                        <a :href="page + item.path + child.path">{{child.name}}</a>
                    </li>
                </ul>
            </div>
        </div>`,

        data() {
            return {
                page: "/page/",
                router: router
            }
        }
    });

})();