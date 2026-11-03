<template>
    <div class="cleanBtn">
        <el-button @click="cleanBtn" :size="size">{{value}}</el-button>
    </div>
</template>
<script>
    export default {
        name: 'CleanBtn',
        props: {
            forms: {
                type: Object
            },
            size: {
                type: String,
                default : 'medium'
            },
            call: {
                type: Function,
                default: () => { }
            },
            value: {
                type: String,
                default: '重 置'
            }
        },
        data() {
            return {
                paramForm: this.forms,
                backcall: this.call
            }
        },
        methods: {
            cleanBtn: function () { 
                Object.keys(this.paramForm).forEach(key => (this.paramForm[key] = ''));
                this.backcall();
            }
        }
    }
</script>

<style scoped>
    .cleanBtn {
        display: inline-block;
    }
</style>

<style>
    .cleanBtn .el-button:focus {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }

    .cleanBtn .el-button:hover {
        color: rgba(251, 164, 47, 1);
        background-color: #fffff0;
        border-color: #f0e68c;
    }
</style>