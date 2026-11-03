<!-- 资源新增嵌入的配置表单 -->
<template>
    <el-card body-style="background-color:#f0f2f5">
        <el-card>
            <el-form :model="dynamicForm" ref="dynamicForm" inline label-width="120px">
                <el-row>
                    <el-col :span="6">
                        <el-form-item label="IP" prop="ip">
                            <el-select placeholder="请选择" v-model="dynamicForm.ip" clearable @change="getIPChange" size="mini">
                                <el-option v-for="item in ipData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="所属中心：" prop="ownCenter">
                            <el-select placeholder="" v-model="dynamicForm.ownCenter" clearable size="mini">
                                <el-option v-for="item in ownCenterData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="资源类型" prop="resType">
                            <el-select placeholder="" v-model="dynamicForm.resType" clearable size="mini">
                                <el-option v-for="item in resTypeData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                         <el-button icon="el-icon-plus" type="success" size="mini" @click="sourceSelect">添加</el-button>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
        <div style="margin-top:10px"></div>
        <el-card>
            <el-table :data="dynamicForm.resExpandItemPartsList" border stripe style="width:100%" highlight-current-row>
                <el-table-column prop="resConfigId" label="套餐大类" align="center">
                    <template slot-scope="scope">
                        <el-select placeholder="请选择" v-model="scope.row.resConfigId" clearable size="mini" @change="clearSelectHardWare(scope.row)">
                            <el-option v-for="item in resConfigData" :key="item.dictid" :label="item.dictname" :value="item.dictid"></el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column prop="applyScene" label="套餐小项" align="center">
                    <template slot-scope="scope">
                        <el-select placeholder="请选择" v-model="scope.row.applyScene" clearable @change="showRowRelaData(scope.row)" size="mini">
                            <el-option v-for="(item,i) in applySceneData = hardWareRelaData.filter((item)=>{ return item.DICTID == scope.row.resConfigId})" :key="i" :label="item.APPLYSCENE" :value="item.APPLYSCENE"></el-option>
                        </el-select>
                    </template>
                </el-table-column>
                <el-table-column prop="describe" label="资源配置" align="center">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.describe" disabled size="mini"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="describe" label="配置项" align="center">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.describe" disabled size="mini"></el-input>
                    </template>
                </el-table-column>
                
                <el-table-column prop="num" label="数量" align="center">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.num" clearable type="number" size="mini"></el-input>
                    </template>
                </el-table-column>
                <el-table-column prop="price" label="价格(元)" align="center">
                    <template slot-scope="scope">
                        <el-input v-model="scope.row.price" type="number" disabled size="mini"></el-input>
                    </template>
                </el-table-column>
            </el-table>
        </el-card>
        <div align="right">
            <label>总价：{{groupprice}}元</label>
        </div>
    </el-card>
</template>
<script>
import { getDicEntry } from '@/api/eosDictEntry'
import { querySelectionConfig, queryHardWareRela, querySoftWareRela } from '@/api/resource/added/resourceManager'
export default {
    data() {
        return {
            dynamicForm: {
                id:"",
                ip:"",
                ownCenter:"",
                resType:"",
                price:"",
                resExpandItemPartsList:[]            },
            ipData: [], //IP地址
            ownCenterData: [], //中心数据
            resTypeData: [], //资源类型数据
            BooleanData: [], //'是否'字典数据
            groupprice: 0
        }
    },
    created() {
        this.getDictData()
    },
    mounted() {
    },
    methods: {
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_OWNCENTER,RES_YESORNO,RES_RESTYPE'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //所属中心
                this.ownCenterData = data.filter((item) => {
                    return item.dicttypeid == 'RES_OWNCENTER'
                })
                //资源类型
                this.resTypeData = data.filter((item) => {
                    return item.dicttypeid == 'RES_RESTYPE'
                })
                //是否
                this.BooleanData = data.filter((item) => {
                    return item.dicttypeid == 'RES_YESORNO'
                })
                return data
            })
        },
        getIPChange() {//IP修改
        },
        
        clearSelectHardWare(row) {
            this.$set(row, 'applyScene', '')
            this.showRowRelaData(row)
        },
        clearSelectSoftWare(row, type) {
            if (type == '1' || type == '2') {
                if (type == '1') {
                    this.$set(row, 'name', '')
                }
                this.$set(row, 'version', '')
            }
        }
    }
}
</script>