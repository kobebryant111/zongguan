<!-- 审核组件（李阳） -->
<template>
    <div class="refuse-container">
        <!-- 按钮 -->
        <el-button type="primary" size="mini" @click="onAccess" v-loading="loading">同意</el-button>
        <el-popover
            placement="top"
            width="400"
            v-model="popoverVisiable">
            <div>拒绝原因：</div>
            <el-form ref="form" :model="form" :rules="rules">
                <el-form-item prop="refuseReason">
                    <el-input type="textarea" rows="6" v-model.trim="form.refuseReason" :placeholder="'拒绝原因，最大支持输入150字符'"  maxlength="150"></el-input>
                </el-form-item>
            </el-form>
            <div class="refuse_btn">
                <el-button type="primary" size="mini" @click="onConfirm">确定</el-button>
                <el-button size="mini" @click="popoverVisiable = false">取消</el-button>
            </div>
            <el-button type="danger" size="mini" slot="reference">拒绝</el-button>
        </el-popover>
        <!-- <el-button type="danger" size="mini" @click="onRefuse">拒绝</el-button> -->
        <el-button size="mini" @click="onCancel">取消</el-button>
    </div>
</template>

<script>
    export default {
        props: {
            isLoading: {
                type: Boolean
            }
        },
        data() {
            let validatorReason = (rule, value, callback) => {
                if(value.length > 150) {
                    callback(new Error('最大支持输入150字'))
                } else {
                    callback()
                }
            }
            return {
                loading: this.isLoading,
                form: {
                    refuseReason: '' // 拒绝原因
                },
                popoverVisiable: false,
                rules: {
                    refuseReason: [
                        {required: true, message: '请输入拒绝原因', trigger: 'blur'},
                        {validator: validatorReason}
                    ]
                }
            }
        },
        methods: {
            // 审核通过
            onAccess() {
                this.$confirm("确认同意审批,是否继续？", "温馨提示", {
                    confirmButtonText: "确定",
                    cancelButtonText: "取消",
                    type: "warning"
                }).then(() => {
                    this.$emit('onCheck', {
                        type: 'access'
                    })
                }).catch(() => {
                    this.$message({
                    type: 'info',
                    message: '已取消'
                    })
                })
            },
            onConfirm() {
                this.$refs.form.validate().then(res => {
                    if(res) {
                        this.$emit('onCheck', {
                            type: 'refuse',
                            reason: this.form.refuseReason
                        })
                        this.popoverVisiable = false
                    }
                })
            },
            // 审核拒绝
            onRefuse() {
                let validatorFunc = (value) => {
                    if(value === null || !value.trim()) {
                        return false
                    } else {
                        if(value.trim().length > 150) {
                            return '最大支持输入150字符'
                        }
                    }
                }
                this.$prompt('请输入拒绝原因', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputValidator: validatorFunc,
                    inputType: 'textarea',
                    inputPlaceholder: '请输入拒绝原因',
                    inputErrorMessage: '最大支持输入150字符',
                    
                }).then(({ value }) => {
                    // if(!value || !value.trim) {
                    //     this.$message({message: '请输入拒绝原因', type: 'error'})
                    //     return 
                    // }
                     this.$emit('onCheck', {
                        type: 'refuse',
                        reason: value
                    })
                })
            },
             // 取消
             onCancel() {
                 this.$emit('onCancel')
             }
        }
    }
</script>


<style lang="scss" scoped>
    .refuse-container{
        text-align: center;
    }
    .refuse_btn {
        padding-top: 10px;
        text-align: center;
    }
</style>
