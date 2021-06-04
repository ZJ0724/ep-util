<template>
    <div v-loading="loading">
        <div class="panel">
            <div>
                EDI_NO
                <ep-input v-model="upload.customsDeclarationNumber" style="margin-top: 20px;"></ep-input>
            </div>

            <div style="margin-top: 20px;">
                code
                <ep-input v-model="upload.cusResult.code" style="margin-top: 10px;"></ep-input>
            </div>

            <div style="margin-top: 20px;">
                note
                <ep-input v-model="upload.cusResult.note" style="margin-top: 10px;"></ep-input>
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
        name: "agentResult.vue",

        data() {
            return {
                loading: false,

                upload: {
                    customsDeclarationNumber: "",
                    cusResult: {
                        code: "",
                        note: ""
                    }
                }
            };
        },

        methods: {
            async uploadAgentResultApi() {
                return await cusResultApi.uploadAgentResult(this.upload).catch((m) => {
                    return Promise.reject(m);
                });
            },

            uploadAction() {
                this.loading = true;
                this.uploadAgentResultApi().then(() => {
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