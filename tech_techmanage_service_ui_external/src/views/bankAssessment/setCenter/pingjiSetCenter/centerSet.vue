<!-- 中心设置 -->
<template>
    <div class="center-container">
        <div class="center-head">
            <el-form label-position="left" label-width="50px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="中心">
                            <el-select v-model="centerid" filterable clearable>
                                <el-option v-for="item in centerData" :key="item.id" :label="item.centername" :value="item.id"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="5">
                        <el-button type="warning" class="xkg-marginleft-10px" @click="search">搜 索</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </div>
        <div class="center-body">
            <div class="center-body-head">
                <el-button @click="cliTag(1)" :class="{ 'buttFous': showCon == 1}">员工</el-button>
                <el-button @click="cliTag(2)" :class="{ 'buttFous': showCon == 2}">经理层</el-button>
            </div>
            <transition name="el-fade-in">
                <center-set-staff v-if="showCon == 1" :centerid="centerid" ref="staff"></center-set-staff>
                <center-set-manager v-else-if="showCon == 2" :centerid="centerid" ref="manager"></center-set-manager>
            </transition>
        </div>
    </div>
</template>
<script>
    import CenterSetStaff from "@/views/bankAssessment/setCenter/pingjiSetCenter/common/centerSetStaff";
    import CenterSetManager from "@/views/bankAssessment/setCenter/pingjiSetCenter/common/centerSetManager";
    import { queryList } from "@/api/integrated/newEmBmfp";
    export default {
        name: 'centerSet',
        components: { CenterSetStaff, CenterSetManager },
        data() {
            return {
                showCon: 1,
                centerData: [],
                centerid: null
            }
        },
        mounted() {
            this.getCenter();
        },
        methods: {
            // 点击tag
            cliTag(v) {
                if (v != this.showCon) {
                    this.centerid = null
                }
                this.showCon = v;
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    if (res.data.status == 200) {
                        this.centerData = res.data.results.filter(li => {
                            return li.id != 61
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查询
            search() {
                if (this.showCon == 1) {
                    this.$refs.staff.seaTableData();
                } else {
                    this.$refs.manager.seaTableData();
                }
            }
        }
    }
</script>
<style scoped>
    .center-head {
        background: #FFFFFF;
        padding: 22px 10px 0 10px;
        margin-bottom: 15px;
        border-radius: 4px;
    }

    .center-body {
        background: #FFFFFF;
        padding: 10px;
        border-radius: 4px;
    }

    .center-body-head {
        padding-bottom: 15px;
    }

    .buttFous {
        color: rgba(251, 164, 47, 1);
        border-color: rgba(251, 164, 47, 1);
    }
</style>