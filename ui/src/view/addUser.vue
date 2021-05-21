<template>
    <div v-loading="loading">
        <div class="panel">
            <div>
                用户名
                <ep-input v-model="addUser.username" style="margin-top: 20px;"></ep-input>
            </div>

            <div style="margin-top: 20px;">
                十位编码
                <ep-input v-model="addUser.tradeCode" style="margin-top: 20px;"></ep-input>
            </div>

            <div style="margin-top: 50px;">
                <ep-button @click="addUserAction()" style="width: 100%;" type="primary">确定</ep-button>
            </div>
        </div>
    </div>
</template>

<script>
    import userApi from "../api/userApi.js";
    import alterUtil from "../util/alterUtil.js";

    export default {
        name: "addUser.vue",

        data() {
            return {
                loading: false,

                addUser: {
                    username: "",
                    tradeCode: ""
                }
            };
        },

        methods: {
            async addUserApi() {
                await userApi.addUser(this.addUser).catch((m) => {
                    return Promise.reject(m);
                });
            },

            addUserAction() {
                this.loading = true;
                this.addUserApi().then(() => {
                    alterUtil.success("成功");
                }).catch((m) => {
                    alterUtil.error(m);
                }).finally(() => {
                    this.loading = false;
                });
            }
        }
    }
</script>