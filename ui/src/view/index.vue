<template>
    <div style="width: 100%;height: 100%;display: flex;flex-direction: column;">
        <!-- 导航栏 -->
        <div class="padding" style="background-color: rgb(57, 61, 73);display: flex;">
            <div class="center" style="padding: 0 20px;font-size: 18px;font-weight: bold;color: white;">
                ep-util
            </div>
            <div>
                <ep-menu style="background-color: rgb(57, 61, 73);box-shadow: none;" is-horizontal class="ep-menu-demo" :default-index="path">
                    <ep-menu-item @click="switchMenu('/home')" index="/home">首页</ep-menu-item>
                    <ep-menu-item @click="switchMenu('/cusResult')" index="/cusResult">报关单回执</ep-menu-item>
                    <ep-menu-item @click="switchMenu('/addUser')" index="/addUser">新建用户</ep-menu-item>
                </ep-menu>
            </div>
        </div>

        <!-- body -->
        <div class="padding" style="height: 100%;overflow: auto;">
            <div style="padding: 50px 0;">
                <router-view></router-view>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "index.vue",

        data() {
            return {
                path: ""
            };
        },

        methods: {
            init() {
                this.path = this.$route.path;
            },

            switchMenu(path) {
                if (path === this.path) {
                    return;
                }
                this.$router.push(path);
                this.init();
            }
        },

        created() {
            this.init();
        }
    }
</script>

<style scoped>
    .padding {
        padding: 0 20%;
    }
</style>