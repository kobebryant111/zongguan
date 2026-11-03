<template>
    <div>
        <el-dialog :title="titleName" :visible.sync="flDialog" width="70%" center>
            <el-button-group>
                <el-button type="primary" size="mini" @click="confimSelect">确定</el-button>
                <!-- <el-button type="danger" size="mini" @click="closeSelect">关闭</el-button> -->
            </el-button-group>
            <el-table :data="configItemList" ref="configItemGrid" border height="500" stripe style="width:100%" highlight-current-row>
                <el-table-column type="selection" width="35"></el-table-column>
                <el-table-column prop="CATEGORY" label="套餐大类" align="center" width='120px'>
                    <template slot-scope="scope">
                        <span v-for="(item, index) of categoryData" :key="index">
                            <span v-if="item.dictid == scope.row.CATEGORY">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="SUBCATEGORY" label="套餐小项" align="center" width='120px'>
                    <template slot-scope="scope">
                        <span v-for="(item, index) of subCategoryData" :key="index" v-if="scope.row.CATEGORY!='4'">
                            <span v-if="item.dictid == scope.row.SUBCATEGORY">{{ item.dictname}}</span>
                        </span>
                        <span v-for="(item, index) of vmTypeData" :key="index" v-if="scope.row.CATEGORY=='4'">
                            <span v-if="item.dictid == scope.row.SUBCATEGORY">{{ item.dictname}}</span>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="APPLYSCENE" label="适用场景" align="center">
                </el-table-column>
                <el-table-column prop="DESCRIBE" label="资源配置" align="center">
                </el-table-column>
                <el-table-column prop="PRICE" label="单价(元)" align="center" width='120px'>
                </el-table-column>
            </el-table>
        </el-dialog>
    </div>
</template>
<script>
import common from '@/api/common'
import { getDicEntry } from '@/api/eosDictEntry'
import { queryConfgiItemList } from '@/api/resource/expand/resourceExpand'
export default {
    data() {
        return {
            configItemList: [],
            flDialog: false,
            index: '',
            titleName: '',
            categoryData: [], //大类
            subCategoryData: [], //小类,
            vmTypeData: [] // 配件分类
        }
    },
    created() {
        //this.getDictData()
    },
    mounted() {
        this.getDictData()
    },
    methods: {
        getDictData() {
            getDicEntry({
                dicttypeid: 'RES_CATEGORY,RES_SUBCATEGORY,RES_VM_TYPE'
            }).then((res) => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: 'error' })
                    return false
                }
                let data = res.data.results
                //大类
                this.categoryData = data.filter((item) => {
                    return item.dicttypeid == 'RES_CATEGORY'
                })
                console.log(this.categoryData)
                //小项
                this.subCategoryData = data.filter((item) => {
                    return item.dicttypeid == 'RES_SUBCATEGORY'
                })
                //分类
                this.vmTypeData = data.filter((item) => {
                    item.dictid = Number(item.dictid)
                    return item.dicttypeid == 'RES_VM_TYPE'
                })
                return data
            })
        },
        confimSelect() {
            var combodata = this.$refs.configItemGrid.selection

            if (combodata.length == 0) {
                this.$message.error('至少选择一条记录')
                return false
            }
            //向父组件添加数据
            for (var i = 0; i < combodata.length; i++) {
                this.$parent.initAddItemParts(this.index, combodata[i])
            }
            this.flDialog = false
        },
        toSelect(checkValue, index, sysOperLevel, isFundTrans, ids) {
            //根据传值，展示不同数据
            this.titleName = '资源信息添加'
            this.flDialog = true
            this.index = index
            //数据查询
            this.$nextTick(() => {
                let data = common.paramsFormat({ selectType: checkValue, sysOperLevel: sysOperLevel, isFundTrans: isFundTrans, ids: ids })
                queryConfgiItemList(data).then((res) => {
                    if (res.data.status != 200) {
                        this.$alert(res.data.message, { type: 'error' })
                        return false
                    }
                    this.configItemList = res.data.results.filter((item) => {
                        return item.ISCANCEL == 0
                    })
                })
            })
        }
        // closeSelect(){
        //     this.flDialog =false;
        // }
    }
}
</script>
<style lang="scss">
.el-dialog__header {
    padding: 10px 10px 10px;
}
</style>

