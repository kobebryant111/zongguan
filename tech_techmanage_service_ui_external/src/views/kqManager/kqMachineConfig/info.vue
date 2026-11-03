<template>
    <div>
        <!-- 头部 -->
        <div>
            <el-card>
                <el-row type="flex" justify="space-between" align="center">
                    <el-col :span="22">
                        <h2>考勤机配置修改</h2>
                    </el-col>
                    <el-col :span="2">
                        <el-button @click="toClose">关闭</el-button>
                    </el-col>
                </el-row>
            </el-card>
        </div>
        <!-- 个人信息 -->
        <el-card class="marginWrapper">
            <el-form label-width="100px">
                <el-row type="flex" justify="space-between">
                    <el-col :span="4">
                        <el-form-item label="考勤机名称">
                            <el-input></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="IP">
                            <el-input></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="PORT">
                            <el-input></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="考勤机位置">
                            <el-input></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="4">
                        <el-form-item label="是否可用">
                            <el-input></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
    </div>
</template>

<script>
export default {
    data() {
        return {
           
        }
    },
    created() {
        this.getInit()
    },
    methods: {
        // 获取数据
        getInit() {
            console.log(1);
        },
        // 修改按钮
        toModify(row, index) {
            console.log(row, index);
            row.isEdit = true
            console.log(row)
        },
        // 保存按钮
        toSave(row, index) {
            console.log(row, index);
            row.isEdit = false
        },
        toClose() {
            this.$router.push({ name: 'kqMachineConfig' });
        }
    },
}
</script>

<style scoped>
.wrapper {
    padding: 15px;
}
.margin_bottom {
    margin-bottom: 15px;
}
.marginWrapper {
    margin: 15px;
}
</style>
