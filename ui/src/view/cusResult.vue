<template>
    <div>
        <div v-loading="cusResultUpload.loading" class="panel">
            <div class="title">
                上传回执
            </div>

            <div style="margin-top: 50px;">
                <div>
                    单号（ediNo、preEntryId）
                </div>
                <div style="margin-top: 10px;">
                    <ep-input v-model="cusResultUpload.data.customsDeclarationNumber"></ep-input>
                </div>
            </div>

            <div style="margin-top: 20px;">
                <div>
                    回执代码
                </div>
                <div style="margin-top: 10px;">
                    <ep-input v-model="cusResultUpload.data.code"></ep-input>
                </div>
            </div>

            <div style="margin-top: 20px;">
                <div>
                    备注
                </div>
                <div style="margin-top: 10px;">
                    <ep-input v-model="cusResultUpload.data.note"></ep-input>
                </div>
            </div>

            <div style="margin-top: 20px;">
                <ep-button @click="() => {
                    cusResultUpload.loading = true;
                    cusResultUploadMethod().then(() => {
                        alterUtil.success('成功');
                    }).catch((m) => {
                        alterUtil.error(m);
                    }).finally(() => {
                        cusResultUpload.loading = false;
                    });
                }" style="width: 100%;" type="primary">确定</ep-button>
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
                // 上传回执
                cusResultUpload: {
                    // 加载
                    loading: false,

                    data: {
                        customsDeclarationNumber: "",
                        code: "",
                        note: ""
                    }
                },

                alterUtil
            };
        },

        methods: {
            async cusResultUploadMethod() {
                return await cusResultApi.uploadCustomsDeclaration(this.cusResultUpload.data).catch((m) => {
                    return Promise.reject(m);
                });
            }
        }
    }
</script>