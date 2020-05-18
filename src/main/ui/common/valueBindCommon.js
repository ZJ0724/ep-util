export default {

    bind(data) {
        this.bindObject(null, data);
    },

    bindObject(parentKey, object) {
        for (let key in object) {
            let value = object[key];

            // 如果是object进行递归
            if (typeof value === "object") {
                parentKey ? key = `${parentKey}.${key}` : key = `${key}`;
                this.bindObject(key, value);
                continue;
            }

            let inputSelector = parentKey ? `input[value-bind='${parentKey}.${key}'` : `input[value-bind=${key}`;
            Object.defineProperty(object, key, {
                set(v) {
                    let elements = document.querySelectorAll(inputSelector);
                    for (let element of elements) {
                        element.value = v;
                    }
                },

                get() {
                    let element = document.querySelector(inputSelector);
                    return element.value;
                }
            });
        }
    }

}