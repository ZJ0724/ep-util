<template>
    <div v-loading="loading">
        <div class="panel">
            <div class="title-2">
                EDI_NO
                <ep-input v-model="customsDeclarationNumber" style="margin-top: 20px;"></ep-input>
            </div>

            <div style="display: flex;margin-top: 50px;">
                <div class="title-2" style="display: flex;align-items: center;">
                    通讯回执
                </div>
                <div style="margin-left: 10px;">
                    <ep-checkbox v-model="tongXunCusResult.display"></ep-checkbox>
                </div>
            </div>

            <div style="margin-top: 20px;">
                code
                <ep-input :disabled="!tongXunCusResult.display" v-model="tongXunCusResult.code" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="margin-top: 20px;">
                note
                <ep-input :disabled="!tongXunCusResult.display" v-model="tongXunCusResult.note" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="display: flex;margin-top: 50px;">
                <div class="title-2" style="display: flex;align-items: center;">
                    业务回执
                </div>
                <div style="margin-left: 10px;">
                    <ep-checkbox v-model="yeWuCusResult.display"></ep-checkbox>
                </div>
            </div>

            <div style="margin-top: 20px;">
                code
                <ep-input :disabled="!yeWuCusResult.display" v-model="yeWuCusResult.code" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="margin-top: 20px;">
                note
                <ep-input :disabled="!yeWuCusResult.display" v-model="yeWuCusResult.note" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="margin-top: 50px;">
                <ep-button @click="uploadAction()" style="width: 100%;" type="primary">上传</ep-button>
            </div>
        </div>
    </div>
</template>

<script>
    import cusResultApi from "../api/cusResultApi.js";
    import alterUtil from "../util/alterUtil.js";

    export default {
        name: "cusResult.vue",

        data() {
            return {
                loading: false,

                customsDeclarationNumber: "",

                tongXunCusResult: {
                    display: true,
                    code: "0",
                    note: "通讯回执"
                },

                yeWuCusResult: {
                    display: true,
                    code: "",
                    note: ""
                }
            };
        },

        methods: {
            async uploadCustomsDeclaration() {
                let req = {
                    customsDeclarationNumber: this.customsDeclarationNumber,
                    tongXunCusResult: this.tongXunCusResult,
                    yeWuCusResult: this.yeWuCusResult
                };
                if (!this.tongXunCusResult.display) {
                    req.tongXunCusResult = null;
                }
                if (!this.yeWuCusResult.display) {
                    req.yeWuCusResult = null;
                }

                return await cusResultApi.uploadCustomsDeclaration(req).catch((m) => {
                    return Promise.reject(m);
                });
            },

            uploadAction() {
                this.loading = true;
                this.uploadCustomsDeclaration().then(() => {
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