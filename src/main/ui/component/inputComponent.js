export default (function () {

    Vue.component("input-component", {

        template: `
            <div>
                <div>
                    {{text}}
                </div>
                <div style="margin-top: 5px;">
                    <input v-model="myValue" class="form-control input-sm" />
                </div>
            </div>
        `,

        props: ["text", "value"],

        data() {
            return {
                myValue: this.value
            }
        },

        watch: {
            myValue(value) {
                this.$emit("update:value", value);
            }
        }
    });

})();