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
                请求地址<ep-button @click="thirdPartyUrlPopup.open()" style="margin-left: 10px;" size="small">维护</ep-button>
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

        <!-- 维护弹框 -->
        <ep-modal title="维护" v-model="thirdPartyUrlPopup.show" width="1000px">
            <div v-loading="thirdPartyUrlPopup.loading" style="max-height: 700px;overflow: auto;">
                <div>
                    <ep-button @click="saveThirdPartyUrlPopup.open()" size="small" type="primary">新增</ep-button>
                </div>

                <ep-table :data="thirdPartyUrls" style="margin-top: 10px;">
                    <ep-table-item column="url" title="url"></ep-table-item>
                    <ep-table-item column="note" title="备注"></ep-table-item>
                    <ep-table-item column="requestData" title="请求参数"></ep-table-item>
                    <ep-table-item column="action" title="操作">
                        <template slot-scope="props">
                            <ep-button @click="saveThirdPartyUrlPopup.open(props.row)" type="text">编辑</ep-button>
                            <ep-button @click="() => {
                                deleteThirdPartyUrl.id = props.row.id;
                                deleteThirdPartyUrlApi().then(() => {
                                    alterUtil.success('成功');
                                    thirdPartyUrlPopup.init();
                                }).catch((m) => {
                                    alterUtil.error(m);
                                });
                            }" style="color: #FF4D4F;" type="text">删除</ep-button>
                        </template>
                    </ep-table-item>
                </ep-table>
            </div>
        </ep-modal>

        <!-- 编辑url弹框 -->
        <ep-modal :title="saveThirdPartyUrlPopup.title" v-model="saveThirdPartyUrlPopup.show" width="700px">
            <div v-loading="saveThirdPartyUrlPopup.loading">
                <div style="display: flex;">
                    <div style="display: flex;align-items: center;" class="saveThirdPartyUrlPopup-width">
                        url
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="saveThirdPartyUrl.url" size="small" style="width: 100%;"></ep-input>
                    </div>
                </div>

                <div style="display: flex;margin-top: 20px;">
                    <div style="display: flex;align-items: center;" class="saveThirdPartyUrlPopup-width">
                        note
                    </div>
                    <div style="width: 100%;">
                        <ep-input v-model="saveThirdPartyUrl.note" size="small" style="width: 100%;"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    请求参数
                    <ep-input style="margin-top: 10px;" type="textarea" v-model="saveThirdPartyUrl.requestData"></ep-input>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button size="small" @click="() => {
                        saveThirdPartyUrlPopup.loading = true;
                        saveThirdPartyUrlApi().then(() => {
                            alterUtil.success('成功');
                            saveThirdPartyUrlPopup.show = false;
                            thirdPartyUrlPopup.init();
                        }).catch((m) => {
                            alterUtil.error(m);
                        }).finally(() => {
                            saveThirdPartyUrlPopup.loading = false;
                        });
                    }" type="primary" style="width: 100%;">确定</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import thirdPartyApi from "../api/thirdPartyApi.js";
    import alterUtil from "../util/alterUtil.js";
    import thirdPartyUrlApi from "../api/thirdPartyUrlApi.js";
    import {variable} from "../util/zj0724common.js";

    export default {
        name: "thirdParty.vue",

        data() {
            let current = this;

            return {
                loading: false,

                users: [],

                thirdPartyUrls: [],

                send: {
                    userCode: "",
                    url: "",
                    requestData: ""
                },

                saveThirdPartyUrl: {
                    id: null,
                    url: "",
                    note: "",
                    requestData: ""
                },

                deleteThirdPartyUrl: {
                    id: null
                },

                thirdPartyUrlPopup: {
                    show: false,

                    loading: false,

                    open() {
                        this.show = true;
                        this.init();
                    },

                    init() {
                        this.loading = true;
                        current.getThirdPartyUrls().finally(() => {
                            this.loading = false;
                        });
                    }
                },

                saveThirdPartyUrlPopup: {
                    show: false,

                    loading: false,

                    title: "",

                    open(data) {
                        this.show = true;
                        variable.clean(current.saveThirdPartyUrl);
                        if (variable.isEmpty(data)) {
                            this.title = "新增";
                        } else {
                            variable.assignment(current.saveThirdPartyUrl, data);
                            this.title = "编辑";
                        }
                    }
                },

                window,
                alterUtil
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

            async getThirdPartyUrls() {
                return await thirdPartyUrlApi.getAll().then((data) => {
                    this.thirdPartyUrls = data;
                });
            },

            async saveThirdPartyUrlApi() {
                return await thirdPartyUrlApi.save(this.saveThirdPartyUrl).catch((m) => {
                    return Promise.reject(m);
                });
            },

            async deleteThirdPartyUrlApi() {
                return await thirdPartyUrlApi.delete(this.deleteThirdPartyUrl).catch((m) => {
                    return Promise.reject(m);
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
                        let requestData = "";
                        if (item.requestData !== null) {
                            requestData = item.requestData;
                        }
                        this.send.requestData = requestData;
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

<style scoped>
    .saveThirdPartyUrlPopup-width {
        width: 40px;
    }
</style>