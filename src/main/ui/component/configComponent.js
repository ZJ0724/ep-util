import alertComponent from "./alertComponent.js";

export default (function () {

    Vue.component("config", {
        template: `
            <div>
                <div style="margin-top: 20px;" v-for="(value, key, index) in vo" :key="index" class="input-group input-group-sm">
                    <span class="input-group-addon">
                        {{key}}
                    </span>
                    <input v-model="vo[key]" type="text" class="form-control" aria-describedby="basic-addon1" aria-label="" />
                </div>

                <div style="margin-top: 20px;">
                    <button @click="set()" style="width: 100%;" class="btn btn-primary" type="submit">保存</button>
                </div>
            </div>
            `,

        props: ["vo", "dto", "get-api", "set-api"],

        created() {
            let vo = this.vo;

            this.getApi().then(function (data) {
                vo.setData(data);
            });
        },

        methods: {
            // 设置
            set() {
                let vo = this.vo;
                let dto = this.dto;

                dto.setData(vo);

                this.setApi(dto).then(function () {
                    alertComponent.popup("修改成功");
                });
            }
        }
    });

})();