import router from "../entity/router.js";

export default (function () {

    Vue.component("route-navigation", {
        template: `
        <div>
            <ol class="breadcrumb">
                <li v-for="(item, i) in pathName" :key="i" class="active">{{item}}</li>
            </ol>
        </div>`,

        data() {
            return {
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