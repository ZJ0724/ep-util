<template>
    <div>
        <div class="panel">
            <ep-table :data="configs">
                <ep-table-item column="id" title="id"></ep-table-item>
                <ep-table-item column="code" title="code"></ep-table-item>
                <ep-table-item column="note" title="备注"></ep-table-item>
                <ep-table-item column="data" title="值"></ep-table-item>
                <ep-table-item column="action" title="操作">
                    <template slot-scope="props">
                        <ep-button @click="configUpdatePopup.open(props.row)" type="text">编辑</ep-button>
                    </template>
                </ep-table-item>
            </ep-table>
        </div>

        <!-- 编辑弹框 -->
        <ep-modal title="编辑" v-model="configUpdatePopup.show" width="500px">
            <div>
                <div style="display: flex;">
                    <div style="white-space: normal;display: flex;align-items: center;">
                        值
                    </div>
                    <div style="width: 100%;margin-left: 20px;">
                        <ep-input v-model="save.data" size="small" style="width: 100%;"></ep-input>
                    </div>
                </div>

                <div style="margin-top: 20px;">
                    <ep-button @click="saveAction()" size="small" type="primary" style="width: 100%;">确定</ep-button>
                </div>
            </div>
        </ep-modal>
    </div>
</template>

<script>
    import configApi from "../api/configApi.js";
    import {variable} from "../util/zj0724common.js";
    import alterUtil from "../util/alterUtil.js";

    export default {
        name: "config.vue",

        data() {
            let current = this;

            return {
                configs: [],

                save: {
                    id: null,
                    code: null,
                    note: null,
                    data: null
                },

                configUpdatePopup: {
                    show: false,

                    open(data) {
                        this.show = true;
                        variable.assignment(current.save, data);
                    }
                }
            };
        },

        methods: {
            getConfigApi() {
                configApi.getAll().then((data) => {
                    this.configs = data;
                });
            },

            async saveApi() {
                return await configApi.save(this.save).catch((m) => {
                    return Promise.reject(m);
                });
            },

            saveAction() {
                this.saveApi().then(() => {
                    alterUtil.success("成功");
                    this.configUpdatePopup.show = false;
                    this.getConfigApi();
                }).catch((m) => {
                    alterUtil.error(m);
                });
            }
        },

        created() {
            this.getConfigApi();
        }
    }
</script>