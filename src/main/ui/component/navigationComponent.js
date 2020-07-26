import router from "../entity/router.js";

export default (function () {

    Vue.component("navigation", {
        template: `
        <div id="head">
            <div class="padding" style="background-color: #393D49;color: white;display: flex;height: 40px;">
                <div v-for="(item, i) in router" class="dropdown" :key="i">
                    <div v-if="item.children !== undefined" class="item" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
                        {{item.name}}
                    </div>
                    <div v-else-if="item.path === '/'" class="item">
                        <a class="a" href="/">{{item.name}}</a>
                    </div>
                    <div v-else class="item">
                        <a class="a" :href="page + item.path">{{item.name}}</a>
                    </div>
                    <ul v-if="item.children !== undefined" class="dropdown-menu">
                        <li style="padding: 5px; 0" v-for="(child, j) in item.children" :key="j">
                            <a :href="page + item.path + child.path">{{child.name}}</a>
                        </li>
                    </ul>
                </div>
            </div>
            
            <div class="padding" style="margin-top: 20px;">
                <ol class="breadcrumb">
                    <li v-for="(item, i) in pathName" :key="i" class="active">{{item}}</li>
                </ol>
            </div>
        </div>`,

        data() {
            return {
                page: "/page/",
                router: router,
                pathName: []
            }
        },

        created() {
            let pathname = window.location.pathname,
                paths = pathname.split("/"),
                router = this.router,
                routerChildren,
                title;

            // 首页
            if (pathname === "/") {
                for (let routerValue of router) {
                    let path = routerValue.path;
                    if (pathname === path) {
                        let name = routerValue.name;

                        this.pathName.push(name);
                        title = name;
                    }
                }
            } else {
                for (let index in paths) {
                    let value = paths[index];

                    if (index === "2") {
                        for (let routerValue of router) {
                            let path = routerValue.path.replace(/\//g, "");
                            if (value === path) {
                                let name = routerValue.name;

                                routerChildren = routerValue.children;
                                this.pathName.push(name);
                                title = name;
                            }
                        }
                    }

                    if (index === "3") {
                        for (let child of routerChildren) {
                            let path = child.path.replace(/\//g, "");
                            if (value === path) {
                                let name = child.name;

                                this.pathName.push(name);
                                title = `${title} - ${name}`;
                            }
                        }
                    }
                }
            }

            document.title = title;
        }
    });

})();