<template>
    <div class="app-container">
        <el-row :gutter="25">
            <div>
                <div class="ts-tabs-box">
                    <el-tabs>
                        <div>
                            <div >
                                <form name="classifyForm">
                                    <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>委托人：</div>
                                        <div class="wd190 float_left">

                                         <el-input size="mini" v-model="form.trustUser" type="text"  maxlength="10" :disabled="true" >
                                          <el-button slot="append" icon="el-icon-search"  @click="selectTrust"></el-button>
                                         </el-input>

                                        </div>
                                    </div>
                                    <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>代理人：</div>
                                        <div class="wd190 float_left"> <el-input size="mini" v-model="form.agentUser"  :disabled="true" >
                                                <el-button slot="append" icon="el-icon-search"  @click="selectAgent"></el-button>
                                          </el-input>
                                        </div>
                                    </div>

                                    <!--
                                      <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>代办方式：</div>
                                        <div class="wd190 float_left">
                                           <el-select  v-model="form.trustType"  >
                                              <el-option  v-for="(item,index) in trustTypes"  :label="item.label"  :value="item.value" :key="index" />
                                           </el-select>
                                        </div>
                                    </div>
                                 -->
                           <!-- <div class="col_td">
                                        <div class="col4 text_right"><i class="red_input">*</i>有效日期：</div>
                                        <div class="wd190 float_left">
                                       <ts-multi-date-picker
                                 style="width: 300px"
                                @change="changeclick"
                                v-model="pickerValue"
                                :params="params" />
                                        </div>
                                    </div> -->

                                    <div class="col_tr">
                                        <div  style="margin-left:16.5%;">原因：</div>
                                        <div >
                                            <el-input class="layui-textarea" :rows="4" type="textarea" placeholder="请输入内容" v-model="form.remarks" maxlength="10" show-word-limi/>
                                        </div>
                                    </div>
                                    <div class="col_td ng-scope">
                                        <div class="col4 text_right"></div>
                                        <div class="col15 text_right">
                                            <el-button type="primary" size="mini" @click="save" icon="el-icon-check">保存</el-button>
                                            <el-button type="primary" size="mini" @click="backReturn" icon="el-icon-back">返回</el-button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </el-tabs>
                </div>
            </div>
        </el-row>
      <el-dialog title="委托人" :visible.sync="dialogVisible" width="30%">
        <tree height="330px" @node-click="nodeClick" type="user"></tree>
      </el-dialog>

      <el-dialog title="代理人" :visible.sync="visible" width="30%">
        <tree height="330px" @node-click="nodeAgentClick" type="user"></tree>
      </el-dialog>
    </div>
</template>

<script>
import tree from '@/components/Tree'
export default {
  components: {
    tree
  },
  data() {
    return {
      dialogVisible: false,
      visible: false,
      operType: '',
      form: {
        trustUser: '',
        agentUser: '',
        startDate: '',
        endDate: '',
        remarks: '',
        trustType: '1',
        createBy: this.$store.state.user.name,
        tenantId: this.$store.state.user.userInfo.tenantId
      },
      trustTypes: [{ label: '代办', value: '1' }
      ],
      pickerValue: '',
      params: {
        type: 'daterange',
        format: 'yyyy-MM-dd',
        valueFormat: 'yyyy-MM-dd',
        rangeSeparator: '-',
        startPlaceholder: '开始日期',
        endPlaceholder: '结束日期',
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 24 * 60 * 60 * 1000 // 当天之后的时间可选
          }
        }
      },
      taskOptions: {
        leafIcon: require('@/assets/images/404_images/404.png'), // 叶子节点图标
        openIcon: '', // 展开图标
        closeIcon: '', // 未展开图标
        onlyChildSelected: false, // 选填，是否只允许选择叶子节点
        nodeKey: 'id', // 每个树节点用来作为唯一标识的属性，整棵树应该是唯一的
        defaultProp: { child: 'pid' }// list转树时标识 父级的键名、展示的键名 默认 {child: 'pId', label: 'name'}
      }
    }
  },
  methods: {

    changeclick(e) {
      this.form.startDate = e.startDate
      this.form.endDate = e.endDate
    },
    selectTrust() {
      this.dialogVisible = true
    },
    selectAgent() {
      if (!this.form.trustUser) {
        this.$tsVue.info('委托人不能为空！')
        return
      }
      this.visible = true
    },
    nodeClick(data) {
      console.log(data)
      this.form.trustUser = data.id
      this.form.type = data.type
      this.dialogVisible = false
    },
    nodeAgentClick(data) {
      this.form.agentUser = data.id
      this.visible = false
    },
    save() {
      if (!this.form.trustUser) {
        this.$tsVue.info('委托人不能为空！')
        return
      }
      if (!this.form.agentUser) {
        this.$tsVue.info('代理人不能为空！')
        return
      }
      // if (!this.form.startDate) {
      //   this.$tsVue.info('生效日期不能为空！')
      //   return
      // }

      let reqUrl = ''
      if (this.operType && this.operType == 'edit') {
        reqUrl = this.$api.updateWfAgency
      } else {
        reqUrl = this.$api.saveWfAgency
      }
      this.$tsVue.confirm('确信代办!', () => {
        this.$request({
          type: 'post',
          data: this.form,
          url: reqUrl
        }).then(res => {
          this.$tsVue.success(res.description)
          this.$tsVue.tansunBack(this)
        })
      })
    },

    backReturn() {
      this.$tsVue.tansunBack(this)
    }
  },
  mounted() {
    const _this = this
    const initData = this.$router.currentRoute.query
    if (initData) {
      this.operType = initData.operType
      if (this.operType && this.operType === 'edit') {
        this.form = Object.assign({}, initData.data)
        if (this.form.startDate) {
          this.form.startDate = this.$tsVue.tansunFormatDate(this.form.startDate, 'yyyy-MM-dd')
        }
        this.params.startPlaceholder = this.form.startDate
        if (this.form.endDate) {
          this.form.endDate = this.$tsVue.tansunFormatDate(this.form.endDate, 'yyyy-MM-dd')
        }
        this.params.endPlaceholder = this.form.endDate
      } else {
        this.form.trustUser = this.$store.state.user.name
      }
    }
  }
}
</script>

<style lang="scss" scoped>
    .col_td {
        width:50%;
        float:left;
        height:28px;
        margin-top: 15px;
        line-height:28px;
        margin-bottom:8px;
    }
    .col_tr {
        width:100%;
        float:left;
        margin-top: 15px;
        display:table;
        height:28px;
        line-height:28px;
        margin-bottom:8px;
    }
    .red_input {
        font-size:13px;
        color:#FF0000;
        display:inline-block;
        font-weight:bold;
        margin-right:5px;
        font-style:normal;
    }
    .leftmenu_navtitle {
        height:32px;
        line-height:32px;
        font-weight:bold;
        border-bottom:solid 3px #4586E0;
    }
    .col1,.col2,.col3,.col4,.col5,.col6,.col7,.col8,.col9,.col10 {
        line-height:26px;
    }
    .maintree_button {
        width:100%;
        height:38px;
        line-height:38px;
        text-align:center
    }
    .layui-textarea {
        margin-top: -20px;
        margin-left: 20%;
        width: 62%;
        line-height:20px;
        position:relative
    }
    .allcontent {
        width:98%;
        padding:1%;
        display:table;
        margin-bottom:20px;
    }
    .col4 {
        width:40%;
        float:left;
    }
    .text_right {
        text-align:right;
    }
    .textarea {
        margin-right: -40px;
    }
    .wd190 {
        width:190px !important;
        position:relative;
    }
  .wd250 {
        width:250px !important;
        position:relative;
    }
    .float_left {
        float:left;
    }
</style>
