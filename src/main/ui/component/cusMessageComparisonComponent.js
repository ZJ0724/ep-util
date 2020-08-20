import alertComponent from "./alertComponent.js";
import CusMessageComparisonVO from "../entity/VO/CusMessageComparisonVO.js";

export default (function () {

    Vue.component("cus-message-comparison", {
        template: `
            <div style="margin-top: 25%;" class="center">
                <div style="width: 300px;">
                    <button @click="select()" style="width: 100%;" type="button" class="btn btn-primary">选择报文...</button>
                </div>
                
                <!-- 遮罩层 -->
                <div v-if="mask" v-cloak class="layui-anim layui-anim-fadein" style="background-color: #0c0c0c38;position: fixed;top: 0;width: 100%; height: 100%;"></div>
            
                <!-- 弹窗 -->
                <div v-if="log" v-cloak class="panel panel-default layui-anim layui-anim-fadein" style="width: 30%;position: absolute;top: 120px;overflow: auto;max-height: 80%;left: 35%;">
                    <div class="panel-heading" style="font-weight: bold;">比对信息</div>
                    <div id="logBody" class="panel-body" style="padding-top: 0;">
                        <div v-for="(vo, key) in logs" :key="key">
                            <!-- 标题 类型 -->
                            <div v-if="vo.type === '0'" style="font-weight: bold;margin-top: 20px;" class="layui-anim layui-anim-scale">
                                {{vo.message}}
                            </div>
                            
                            <!-- 比对通过 类型 -->
                            <div v-if="vo.type === '1' && vo.message === 'true'" style="margin-top: 10px;display: flex;" class="layui-anim layui-anim-scale">
                                <div style="width: 90%;">
                                    {{vo.node}}
                                </div>
                                <div class="center" style="width: 10%;">
                                    <i style="color: #5FB878;" class="layui-icon layui-icon-ok"></i>
                                </div>
                            </div>
                            
                            <!-- 比对失败 类型 -->
                            <div v-if="vo.type === '1' && vo.message === 'false'" style="margin-top: 10px;display: flex;" class="layui-anim layui-anim-scale">
                                <div style="width: 90%;color: red;">
                                    {{vo.node}}
                                </div>
                                <div class="center" style="width: 10%;">
                                    <i style="color: red;" class="layui-icon layui-icon-close"></i>
                                </div>
                            </div>
                            
                            <!-- 不进行比对 类型 -->
                            <div v-if="vo.type === '1' && vo.message === 'null'" style="margin-top: 10px;display: flex;" class="layui-anim layui-anim-scale">
                                <div style="width: 90%;color: #c2c2c2;">
                                    {{vo.node}}
                                </div>
                                <div class="center" style="width: 10%;">
                                    <i style="color: #c2c2c2;" class="layui-icon layui-icon-subtraction"></i>
                                </div>
                            </div>
                            
                            <!-- 错误 类型 -->
                            <div v-if="vo.type === '2'" style="font-weight: bold;color: red;margin-top: 20px;" class="layui-anim layui-anim-scale">
                                {{vo.message}}
                            </div>
                        </div>
                    </div>
                    <div v-if="closeLog" v-cloak class="panel-footer" style="display: flex;flex-direction: row-reverse;">
                        <button @click="close()" type="button" class="btn btn-primary btn-sm">关闭</button>
                    </div>
                </div>
            </div>
        `,

        props: ["uploadApi", "comparisonApi"],

        data: function () {
            return {
                mask: false,
                log: false,
                closeLog: false,
                logs: []
            }
        },

        methods: {
            select() {
                let fileInput = document.createElement("input");
                let that = this;

                fileInput.setAttribute("type", "file");
                fileInput.addEventListener("change", function () {
                    that.comparison(fileInput.files[0]);
                });

                fileInput.click();
            },

            comparison(file) {
                let that = this;

                that.uploadApi(file).then(function (id) {
                    that.mask = true;
                    that.log = true;
                    that.logs = [];

                    let websocket = new that.comparisonApi(id);

                    // 监听比对消息
                    websocket.onmessage(function (message) {
                        let vo = new CusMessageComparisonVO();

                        console.log(vo);
                        vo.setData(message);
                        that.logs.push(vo);
                    });

                    // 监听连接关闭
                    websocket.onclose(function () {
                        console.log("websocket连接关闭");
                        that.closeLog = true;
                    });
                }).catch(function (message) {
                    alertComponent.message(message);
                });
            },

            // 关闭弹窗
            close() {
                this.log = false;
                this.mask = false;
                this.closeLog = false;
            }
        }
    });

})();