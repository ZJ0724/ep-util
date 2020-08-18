export default (function () {
    Vue.component("list", {
        template: `
            <div :style="display ? '' : 'display: none'" style="position: relative;top: -39px;width: 100%;" class="layui-unselect layui-form-select layui-form-selected">
                <dl class="layui-anim layui-anim-upbit">
                    <dd @mousedown="select(d)" v-for="d in data" :key="d" class="select">{{d}}</dd>
                </dl>
            </div>
        `,

        props: ["display", "data"],

        methods: {
            select(data) {
                this.$emit("select", data);
            }
        }
    });
})();