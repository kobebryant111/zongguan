<template>
    <div style="margin-left: 30px;margin-right: 30px">
        <el-col class="cardDiv">
            <el-card shadow="hover">
                <el-divider content-position="left">选择交接人员</el-divider>
                <el-form>
                    <el-form-item label="交接方式" prop="type">
                        <el-radio-group v-model="query.type">
                            <el-radio label="1">简单交接</el-radio>
                            <el-radio label="2">详细交接</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="移交人">
                        <el-select v-model="query.transferor"  clearable  filterable   placeholder="请选择">
                            <el-option v-for="item in userList"  :label="item.userName"  :value="item.userNum" :key="item.userNum" />
                        </el-select>
                    </el-form-item>
                    <el-form-item label="接管人">
                        <el-select v-model="query.receiver"  clearable  filterable   placeholder="请选择">
                            <el-option v-for="item in userList"  :label="item.userName"  :value="item.userNum" :key="item.userNum" />
                        </el-select>
                    </el-form-item>
                    <el-form-item>
                        <el-button icon="el-icon-search" size="mini" type="primary" @click="search" :disabled="searchBtn">查询</el-button>
                        <el-button icon="el-icon-refresh" type="primary" size="mini" @click="reset">重置</el-button>
                        <el-button icon="el-icon-check" size="mini" type="primary" @click="handoverAll" :disabled="isDetailHandover===false">交接</el-button>
                    </el-form-item>
                </el-form>
            </el-card>
        </el-col>
        <el-col class="cardDiv">
            <el-card shadow="hover">
                <el-divider content-position="left">工作交接</el-divider>
                <el-form :inline="true">
                    <el-row v-loading="loading.load">
                        <el-form-item label="待执行的工作项" label-width="180px">
                            <el-input v-model="handoverItem.load"></el-input>
                        </el-form-item>
                        <el-button :disabled="isDetailHandover" v-on:click="handover('load')">交接</el-button>
                        <el-form-item style="margin-left: 10px">
                            <el-select v-model="handoverReceiver.load" clearable  filterable placeholder="请选择" :disabled="isDetailHandover">
                                <el-option v-for="item in userList"  :label="item.userName"  :value="item.userNum" :key="item.userNum" />
                            </el-select>
                        </el-form-item>
                        <el-link :type="handoverMsg.load.type" :title="handoverMsg.load.msg" disabled class="cstLink">{{handoverMsg.load.msg}}</el-link>
                    </el-row>
                    <el-row v-loading="loading.dealOther">
                        <el-form-item label="待办理他人的工作项" label-width="180px">
                            <el-input v-model="handoverItem.dealOther"></el-input>
                        </el-form-item>
                        <el-button :disabled="isDetailHandover" v-on:click="handover('dealOther')">交接</el-button>
                        <el-form-item style="margin-left: 10px">
                            <el-select v-model="handoverReceiver.dealOther" clearable  filterable placeholder="请选择" :disabled="isDetailHandover">
                                <el-option v-for="item in userList"  :label="item.userName"  :value="item.userNum" :key="item.userNum" />
                            </el-select>
                        </el-form-item>
                        <el-link :type="handoverMsg.dealOther.type" :title="handoverMsg.dealOther.msg" disabled class="cstLink">{{handoverMsg.dealOther.msg}}</el-link>
                    </el-row>
                    <el-row v-loading="loading.entrust">
                        <el-form-item label="待委托他人的工作项" label-width="180px">
                            <el-input v-model="handoverItem.entrust"></el-input>
                        </el-form-item>
                        <el-button :disabled="isDetailHandover" v-on:click="handover('entrust')">交接</el-button>
                        <el-form-item style="margin-left: 10px">
                            <el-select v-model="handoverReceiver.entrust" clearable  filterable placeholder="请选择" :disabled="isDetailHandover">
                                <el-option v-for="item in userList"  :label="item.userName"  :value="item.userNum" :key="item.userNum" />
                            </el-select>
                        </el-form-item>
                        <el-link :type="handoverMsg.entrust.type" :title="handoverMsg.entrust.msg" disabled class="cstLink">{{handoverMsg.entrust.msg}}</el-link>
                    </el-row>
                    <el-row v-loading="loading.loadReceive">
                        <el-form-item label="待领取的工作项" label-width="180px">
                            <el-input v-model="handoverItem.loadReceive"></el-input>
                        </el-form-item>
                        <el-button :disabled="isDetailHandover" v-on:click="handover('loadReceive')">交接</el-button>
                        <el-form-item style="margin-left: 10px">
                            <el-select v-model="handoverReceiver.loadReceive" clearable  filterable placeholder="请选择" :disabled="isDetailHandover">
                                <el-option v-for="item in userList"  :label="item.userName"  :value="item.userNum" :key="item.userNum" />
                            </el-select>
                        </el-form-item>
                        <el-link :type="handoverMsg.loadReceive.type" :title="handoverMsg.loadReceive.msg" disabled class="cstLink">{{handoverMsg.loadReceive.msg}}</el-link>
                    </el-row>
                    <el-row v-loading="loading.workflowDef">
                        <el-form-item label="需要交接的流程定义" label-width="180px">
                            <el-input v-model="handoverItem.workflowDef"></el-input>
                        </el-form-item>
                        <el-button :disabled="isDetailHandover" v-on:click="handover('workflowDef')">交接</el-button>
                        <el-form-item style="margin-left: 10px">
                            <el-select v-model="handoverReceiver.workflowDef" clearable  filterable placeholder="请选择" :disabled="isDetailHandover">
                                <el-option v-for="item in userList"  :label="item.userName"  :value="item.userNum" :key="item.userNum" />
                            </el-select>
                        </el-form-item>
                        <el-link :type="handoverMsg.workflowDef.type" :title="handoverMsg.workflowDef.msg" disabled class="cstLink">{{handoverMsg.workflowDef.msg}}</el-link>
                    </el-row>
                    <el-row v-loading="loading.agencyRelationship">
                        <el-form-item label="需要删除的代理关系" label-width="180px">
                            <el-input v-model="handoverItem.agencyRelationship"></el-input>
                        </el-form-item>
                        <el-button :disabled="isDetailHandover" v-on:click="handover('agencyRelationship')">删除</el-button>
                        <el-link style="margin-left: 10px;" :type="handoverMsg.agencyRelationship.type" :title="handoverMsg.agencyRelationship.msg" disabled class="cstLink">{{handoverMsg.agencyRelationship.msg}}</el-link>
                    </el-row>
                </el-form>
            </el-card>
        </el-col>
    </div>
</template>

<script>
export default {
  data() {
    return {
      itemName: [
        'load',
        'dealOther',
        'entrust',
        'loadReceive',
        'workflowDef',
        'agencyRelationship'
      ],
      searchUrls: {
        'load': this.$api.queryLoadItem,
        'dealOther': this.$api.queryDealOtherItem,
        'entrust': this.$api.queryEntrustItem,
        'loadReceive': this.$api.queryLoadReceiveItem,
        'workflowDef': this.$api.queryWorkflowDefItem,
        'agencyRelationship': this.$api.queryAgencyRelationshipItem
      },
      handoverUrls: {
        'load': this.$api.handoverLoadItem,
        'dealOther': this.$api.handoverDealOtherItem,
        'entrust': this.$api.handoverEntrustItem,
        'loadReceive': this.$api.handoverLoadReceiveItem,
        'workflowDef': this.$api.handoverWorkflowDefItem,
        'agencyRelationship': this.$api.handoverAgencyRelationshipItem
      },
      query: {
        type: '1',
        transferor: undefined,
        receiver: undefined
      },
      loading: {
        load: false,
        dealOther: false,
        entrust: false,
        loadReceive: false,
        workflowDef: false,
        agencyRelationship: false
      },
      handoverItem: {
        load: undefined,
        dealOther: undefined,
        entrust: undefined,
        loadReceive: undefined,
        workflowDef: undefined,
        agencyRelationship: undefined
      },
      handoverReceiver: {
        load: undefined,
        dealOther: undefined,
        entrust: undefined,
        loadReceive: undefined,
        workflowDef: undefined,
        agencyRelationship: undefined
      },
      handoverMsg: {
        load: { type: 'success', msg: '' },
        dealOther: { type: 'success', msg: '' },
        entrust: { type: 'success', msg: '' },
        loadReceive: { type: 'success', msg: '' },
        workflowDef: { type: 'success', msg: '' },
        agencyRelationship: { type: 'success', msg: '' }
      },
      searchBtn: false,
      userList: []
    }
  },
  computed: {
    isDetailHandover: function() {
      return this.query.type !== '2'
    }
  },
  mounted() {
    const _this = this
    this.$request({
      url: this.$api.pendOrgUserList,
      data: {},
      type: 'post'
    }).then(res => {
      for (const v of res.data) {
        const user = {}
        _this.$set(user, 'userNum', v.userLoginName)
        const userName = v.userName + '(' + v.userLoginName + ')'
        _this.$set(user, 'userName', userName)
        _this.userList.push(user)
      }
    })
  },
  methods: {
    // 重置
    reset() {
      this.query = this.$options.data().query
    },
    // 查询
    search() {
      if (!this.query.transferor) {
        this.$message.error('请选择移交人')
        return
      }
      for (let i = 0; i < this.itemName.length; i++) {
        this.loading[this.itemName[i]] = true
        this.searchBtn = true
        this.$request({
          url: this.searchUrls[this.itemName[i]],
          data: { loginUserName: this.query.transferor },
          type: 'post'
        }).then(res => {
          if (!res || res.status !== 200) {
            this.$message.error(res.description || '查询移交项目失败了')
          } else {
            this.handoverItem[this.itemName[i]] = res.data
          }
        }).finally(() => {
          this.loading[this.itemName[i]] = false
          this.searchBtn = false
        })
      }
    },
    // 交接
    handoverAll() {
      for (let i = 0; i < this.itemName.length; i++) {
        this.handover(this.itemName[i])
      }
    },
    // 交接
    handover(itemName) {
      const receiver = this.handoverReceiver[itemName] || this.query.receiver
      const transferor = this.query.transferor
      if (!receiver) {
        this.handoverMsg[itemName].msg = '请选择接管人'
        this.handoverMsg[itemName].type = 'danger'
        return
      }
      if (!transferor) {
        this.handoverMsg[itemName].msg = '请选择移交人'
        this.handoverMsg[itemName].type = 'danger'
        return
      }
      if (receiver === transferor) {
        this.handoverMsg[itemName].msg = '移交给自己没有意义'
        this.handoverMsg[itemName].type = 'danger'
        return
      }
      this.loading[itemName] = true
      this.searchBtn = true
      this.$request({
        url: this.handoverUrls[itemName],
        data: { receiver, transferor },
        type: 'post'
      }).then(res => {
        if (!res || res.status !== 200) {
          this.handoverMsg[itemName].msg = res.description || '移交项目失败了'
          this.handoverMsg[itemName].type = 'danger'
        } else {
          this.handoverMsg[itemName].msg = res.description || '移交项目成功'
          this.handoverMsg[itemName].type = 'success'
        }
      }).finally(() => {
        this.loading[itemName] = false
        this.searchBtn = false
      })
    }
  },
  watch: {
    // isDetailHandover: {
    //   handler(val) {
    //     if (val) {
    //       this.handoverReceiver = this.$options.data().handoverReceiver
    //     } else {
    //       this.query.receiver = undefined
    //     }
    //   }
    // },
    'query.receiver': {
      handler(val) {
        if (!val) {
          return
        }
        for (let i = 0; i < this.itemName.length; i++) {
          this.handoverReceiver[this.itemName[i]] = val
        }
      }
    }
  }
}
</script>

<style lang="scss" scoped>
    .layout {
        margin-left: 10px;
        margin-top: 10px;
    }
    .buttonDiv{
        margin-bottom: 10px;
    }
    .cardDiv{
        margin-top: 20px;
    }
    .cstLink{
        max-width: 500px;
        overflow:hidden;
        text-overflow:ellipsis;
        white-space:nowrap;
    }
</style>
