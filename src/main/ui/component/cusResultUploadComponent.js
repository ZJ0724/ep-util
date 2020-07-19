(function (epUtil) {
    // 回执上传组件
    Vue.component("cus-result-upload", {
        template: `<div style="height: 100%;" class="center">
                <div style="width: 40%;">
                    <div class="input-group input-group-sm">
                        <span class="input-group-addon">ediNo&nbsp;&nbsp;&nbsp;</span>
                        <input v-model="ediNo" type="text" class="form-control" aria-describedby="basic-addon1" aria-label="" />
                    </div>

                    <div style="margin-top: 10px;" class="input-group input-group-sm">
                        <span class="input-group-addon">channel</span>
                        <input v-model="cusResultDTO.channel" type="text" class="form-control" aria-label="" />
                        <div class="input-group-btn">
                            <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                选择
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu dropdown-menu-right">
                                <li v-for="channel in channelList" :key="channel.key">
                                    <a @click="selectChannel(channel.key)" href="javascript:">{{channel.value}}</a>
                                </li>
                            </ul>
                        </div>
                    </div>

                    <div style="margin-top: 10px;" class="input-group input-group-sm">
                        <span class="input-group-addon">note&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <input v-model="cusResultDTO.note" type="text" class="form-control" aria-describedby="basic-addon1" aria-label="" />
                    </div>

                    <div style="margin-top: 20px;">
                        <button :disabled="uploadButton.disabled" @click="upload()" style="width: 100%;" class="btn btn-primary" type="submit">{{uploadButton.text}}</button>
                    </div>
                </div>
            </div>`,

        props: ["channel-list", "api"],

        data: function () {
            return {
                ediNo: "",
                cusResultDTO: new epUtil.entity.DTO.CusResultDTO(),

                // 上传按钮
                uploadButton: {
                    disabled: false,
                    text: "上传"
                }
            }
        },

        methods: {
            // 选择channel
            selectChannel(channel) {
                this.cusResultDTO.channel = channel;
            },

            // 上传
            upload() {
                let ediNo = this.ediNo,
                    cusResultDTO = this.cusResultDTO,
                    uploadButton = this.uploadButton,
                    uploadButtonDisabled = uploadButton.disabled,
                    uploadButtonText = uploadButton.text,
                    alertComponent = epUtil.component.alertComponent,
                    api = this.api;

                if (ediNo === "") {
                    alertComponent.message("ediNo不能为空");
                    return;
                }

                if (!cusResultDTO.check(function (message) {
                    alertComponent.message(message);
                })) {
                    return;
                }

                uploadButton.disabled = true;
                uploadButton.text = "正在上传...";

                api(ediNo, cusResultDTO).then(function () {
                    alertComponent.popup("上传成功");
                }).catch(function (message) {
                    alertComponent.popup(message);
                }).finally(function () {
                    uploadButton.disabled = uploadButtonDisabled;
                    uploadButton.text = uploadButtonText;
                });
            }
        }
    });
})(window.epUtil);