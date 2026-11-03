<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <div class="layout" style="margin-top: 20px">
            <el-form inline>
                <el-form-item label="流程实例ID：">
                    <el-input placeholder="请输入流程实例ID" size="mini" v-model="query.processInstanceId"></el-input>
                </el-form-item>
                <el-form-item label="待办任务ID：">
                    <el-input placeholder="请输入待办任务ID" size="mini" v-model="query.taskId"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="search">查询</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button :icon="icon" type="primary" size="mini" @click="additionalQuery" ></el-button>
                </el-form-item>
            </el-form>
        </div>
        <div v-show="isQuery" style="margin-left: 10px;margin-top: -10px">
            <el-form inline>
                <el-form-item label="流程定义ID：">
                    <el-input placeholder="请输入流程定义ID" size="mini" v-model="query.processDefineId"></el-input>
                </el-form-item>
                <el-form-item label="扩展字段:">
                    <el-select  style="width: 120px" class="buttonClass" size="mini" v-model="query.fieldColumn" placeholder="扩展字段">
                        <el-option v-for="item in optionsValue" :key="item.value" :label="item.label" :value="item.value"></el-option>
                    </el-select>—
                        <el-input style="width: 120px" placeholder="扩展字段值" size="mini" v-model="query.fieldValue"></el-input>
                </el-form-item>
            </el-form>
        </div>
        <div>
            <el-button  type="primary" size="mini" icon="el-icon-back" @click="back">返回</el-button>
        </div>
        <div>
            <ts-multi-table ref="list" :query="query" :options="options" @reset-query="refreshQuery">
                <el-table-column prop="processInstanceId" label="实例ID" min-width="180" show-overflow-tooltip/>
                <el-table-column prop="processDefineId" show-overflow-tooltip label="定义ID"/>
                <el-table-column prop="taskId" label="任务ID"/>
                <el-table-column prop="taskNodeId"  show-overflow-tooltip label="节点ID"/>
                <el-table-column prop="field01" label="字段01"/>
                <el-table-column prop="field02" label="字段02"/>
                <el-table-column prop="field03" label="字段03"/>
                <el-table-column prop="field04" label="字段04"/>
                <el-table-column prop="field05" label="字段05"/>
                <el-table-column prop="field06" label="字段06"/>
                <el-table-column prop="field07" label="字段07"/>
                <el-table-column prop="field08" label="字段08"/>
                <el-table-column prop="field09" label="字段09"/>
                <el-table-column prop="field10" label="字段10"/>
                <el-table-column prop="field11" label="字段11"/>
                <el-table-column prop="field12" label="字段12"/>
                <el-table-column prop="field13" label="字段13"/>
                <el-table-column prop="field14" label="字段14"/>
                <el-table-column prop="field15" label="字段15"/>
                <el-table-column prop="field16" label="字段16"/>
                <el-table-column prop="field17" label="字段17"/>
                <el-table-column prop="field18" label="字段18"/>
                <el-table-column prop="field19" label="字段19"/>
                <el-table-column prop="field20" label="字段20"/>
            </ts-multi-table>
        </div>
    </div>
</template>

<script>
export default {
  data() {
    return {
      isQuery: false,
      icon: 'el-icon-arrow-down',
      id: '',
      isMultiSelect: false,
      selectParam: { options: [{ text: '隐藏', value: '0' }, { text: '显示', value: '1' }], text: 'text', value: 'value', width: '165px',
        size: 'mini' },
      optionsValue:
            [
              { value: 'field01', label: '拓展字段01' }, { value: 'field02', label: '拓展字段02' }, { value: 'field03', label: '拓展字段03' },
              { value: 'field04', label: '拓展字段04' }, { value: 'field05', label: '拓展字段05' }, { value: 'field06', label: '拓展字段06' },
              { value: 'field07', label: '拓展字段07' }, { value: 'field08', label: '拓展字段08' }, { value: 'field09', label: '拓展字段10' },
              { value: 'field11', label: '拓展字段11' }, { value: 'field12', label: '拓展字段12' }, { value: 'field13', label: '拓展字段13' },
              { value: 'field14', label: '拓展字段14' }, { value: 'field15', label: '拓展字段15' }, { value: 'field16', label: '拓展字段16' },
              { value: 'field17', label: '拓展字段17' }, { value: 'field18', label: '拓展字段18' }, { value: 'field19', label: '拓展字段19' },
              { value: 'field20', label: '拓展字段20' }
            ],
      query: {}, // 最好必填，做搜索条件扩展！！！约定
      options: { // 必填
        type: 'radio', // radio|checkbox|'' 单选/多选/无选
        // 必填，路径请结合实际业务封装的baseURL，url = base url + request url ，这里只要传 request url 如果是对象就不需要写method 如果是字符串就传接口路径 就必须配合method参数一起写
        apiName: this.$api.taskExpandDataList,
        method: 'post', // 默认走‘post’
        paramsOrdata: 'params', // 必填 ‘params’ 走token ‘data’ 走 sesson，默认是data
        apiRequiredParams: {}, // 选填 表示接口的必需恒定不变的参数
        showCancelBtn: true, // [可不填 | Boolean] 默认值true 显示取消按钮
        showConfirmBtn: true, // [可不填 | Boolean] 默认值true 显示确认按钮
        isNeedPagination: true, // 选填 是否需要分页器，默认是true
        pageNum: 1, // 选填 当前页码
        pageSize: 10, // 选填 每页条数
        pagesizes: [5, 10, 15, 20], // 选填 页码间距
        layout: 'total, prev, pager, next, sizes, jumper' // 选填 分页器轮廓
      }
    }
  },
  methods: {
    additionalQuery() {
      if (this.isQuery) {
        this.isQuery = false
        this.icon = 'el-icon-arrow-down'
      } else {
        this.isQuery = true
        this.icon = 'el-icon-arrow-up'
      }
    },
    // 重置
    reset() {
      this.$refs['list'].resetData()
    },
    back() {
      this.$tsVue.tansunBack(this)
    },
    // 更新查询参数
    refreshQuery(query) {
      this.query = query
    },
    // 查询
    search() {
      this.$refs['list'].serchHandel()
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        padding: 10px;
    }

    .mright_10 {
        margin-right: 10px;
    }

    .text_color {
        color: #3B8EDA
    }

    .funBox {
        display: flex;
        justify-content: flex-start;
    }
    .moneyCon {
        margin-top: 20px;
        display: flex;

        .item {
            margin-left: auto;
        }
    }
</style>
