<template>
    <div v-loading="loading">
        <div class="panel">
            <div>
                用户代码
                <ep-select @open="getUsers()" style="margin-top: 20px;" v-model="send.userCode" filterable>
                    <ep-select-item v-for="(user, key) in users" :key="key" :index="user" :label="user"></ep-select-item>
                </ep-select>
            </div>

            <div style="margin-top: 50px;">
                请求地址
                <ep-select @change="urlChangeAction()" @open="getThirdPartyUrls()" style="margin-top: 20px;" v-model="send.url" filterable>
                    <ep-select-item v-for="(thirdPartyUrl, key) in thirdPartyUrls" :key="key" :index="thirdPartyUrl.url" :label="thirdPartyUrl.note + ' [' +thirdPartyUrl.url + ']'"></ep-select-item>
                </ep-select>
            </div>

            <div style="margin-top: 50px;">
                <div>
                    请求数据
                </div>
                <div style="display: flex;flex-direction: row-reverse;">
                    <ep-button @click="formatAction()" type="text">格式化</ep-button>
                </div>
                <b-code-editor theme="dracula" v-model="send.requestData" />
            </div>

            <div style="margin-top: 50px;">
                <ep-button @click="sendAction()" style="width: 100%;" type="primary">确定</ep-button>
            </div>
        </div>
    </div>
</template>

<script>
    import thirdPartyApi from "../api/thirdPartyApi.js";
    import alterUtil from "../util/alterUtil.js";
    import thirdPartyUrlApi from "../api/thirdPartyUrlApi.js";

    export default {
        name: "thirdParty.vue",

        data() {
            return {
                loading: false,

                send: {
                    userCode: "",
                    url: "",
                    requestData: ""
                },

                users: [],

                thirdPartyUrls: [],

                window
            };
        },

        methods: {
            async sendApi() {
                return await thirdPartyApi.send(this.send).catch((m) => {
                    return Promise.reject(m);
                });
            },

            getUsers() {
                thirdPartyApi.getUsers().then((data) => {
                    this.users = data;
                });
            },

            getThirdPartyUrls() {
                thirdPartyUrlApi.getAll().then((data) => {
                    this.thirdPartyUrls = data;
                });
            },

            sendAction() {
                this.loading = true;
                this.sendApi().then((data) => {
                    alterUtil.popup(data);
                }).catch((m) => {
                    alterUtil.error(m);
                }).finally(() => {
                    this.loading = false;
                });
            },

            urlChangeAction() {
                for (let item of this.thirdPartyUrls) {
                    if (item.url === this.send.url) {
                        this.send.requestData = item.requestData;
                    }
                }
            },

            formatAction() {
                try {
                    this.send.requestData = JSON.stringify(JSON.parse(this.send.requestData),null,2);
                } catch (e) {}
            }
        }
    }
</script>