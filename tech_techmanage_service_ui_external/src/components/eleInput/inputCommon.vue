<template>
        <div class="inputContent">
            <el-input v-model="inputData" @input="update"></el-input>
        </div>
    </template>
    <script>
        export default {
            props: {
                forms: {
                    type: Object
                },
                size: {
                    type: String
                },
                butName: {
                    type: String
                },
                call: {
                    type: Function,
                    default: () => { }
                },
            },
            data() {
                return {
                    inputData: '',
                }
            },
            methods: {
                update () {
                    this.$emit("getinputData", this.inputData);
                }
            }
        }
    </script>
    
    <style scoped>
        .buttContent {
            display: inline-block;
        }
    </style>
    
    <!-- element样式修改 -->
    <style>
    .inputContent .el-input__inner:focus {
        border-color: rgba(251, 164, 47, 1);
    }
    </style>