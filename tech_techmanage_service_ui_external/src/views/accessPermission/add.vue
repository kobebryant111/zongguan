<!-- 多选金融科技部行员 -->
<template>
  <div class="single-choice">
    <div class="flDialogBody">
      <div class="flDialogBody_con">
        <el-row>
          <el-col :span="12">
            <div class="flDialogBody_con_left">
              <div class="flDialogBody_con_left_input">
                <el-row :gutter="10">
                  <el-col :span="14">
                    <el-input v-model="searchUser" :placeholder="placeholder"></el-input>
                  </el-col>
                  <el-col :span="10">
                    <div class="butt-search">
                      <button-orange :buttonName="buttonSearch" size="small" :fns="searchUserBtn"></button-orange>
                      <button-orange :buttonName="buttonReturn" size="small" :fns="returnToTree"></button-orange>
                    </div>
                  </el-col>
                </el-row>
              </div>
              <el-scrollbar style="height:310px">
                <div class="flDialogBody_con_tree">
                  <el-tree
                    ref="tree"
                    show-checkbox
                    :data="treeData"
                    :props="treeProps"
                    node-key="id"
                    :default-expand-all="false"
                    @check="getCurrentNode"
                    :filter-node-method="filterNode"
                    highlight-current
                    lazy
                    :load="loadNode"
                    @node-click="getChildrenNode"
                  ></el-tree>
                </div>
              </el-scrollbar>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="flDialogBody_con_right">
              <div class="flDialogBody_con_right_title">已选{{currentDetial.length}}人</div>
              <el-scrollbar style="height:310px">
                <div class="flDialogBody_con_right_main" v-if="ifwbtcRight">
                  <ul v-for="(item, index) in currentDetial" :key="index" class="flDialogBody_ul">
                    <div class="flList">
                      <div>
                        <li class="flDialogBody_ul_up">姓名：{{item.text}}</li>
                        <!-- <li class="flDialogBody_ul_low">部门编号：{{item.pid}}</li> -->
                      </div>
                      <div class="el-icon-circle-close" @click="delSelect(item,index)"></div>
                    </div>
                  </ul>
                </div>
              </el-scrollbar>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="wrapper_bottom">
        <el-row :gutter="10" type="flex" justify="end">
          <el-col :span="1.5">
            <button-common :buttonName="buttonClean" :size="size" :fns="flClean"></button-common>
          </el-col>
          <el-col :span="1.5">
            <button-common :buttonName="buttonCancel" :size="size" :fns="flCancel"></button-common>
          </el-col>
          <el-col :span="1.5">
            <button-orange :buttonName="buttonSure" :size="size" :fns="flSure"></button-orange>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>
<script>
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import { mapGetters } from "vuex";
import { queryEmployeeTree } from "@/api/integrated/wbryzrlc";
import { userTreeList } from "@/api/systemApi/relateUser";
import {
  queryEmployees,
  queryDetailForDept
} from "@/api/dataCenter/dataCenAccessPermission";
export default {
  name: "add",
  components: { buttonOrange, buttonCommon },
  data() {
    return {
      flDialog: true, //是否选择变更人员弹窗
      size: "mini", //按钮大小
      buttonClean: "清 除",
      buttonCancel: "取 消",
      buttonSure: "确 定",
      groupid_options: [],
      filterText: "", //过滤机构树
      ifwbtcRight: false, //是否显示弹窗右边
      currentDetial: [], //选取的
      searchDetial: [], //搜索选取的
      treeProps: {
        children: "children",
        label: "text",
        isLeaf: "isLeaf"
      }, //配置信息
      treeData: [], //树数据
      deptName: "",
      treeNodeData: {}, //点击的树节点数据
      searchUser: "", // 用户树搜索用户的条件
      buttonSearch: "搜索",
      buttonReturn: "返回",
      isSearch: "false", //是否搜索
      placeholder: ""
    };
  },
  props: {
    userType: {
      //输入框内容
      type: String
    },
    fns: {
      type: Function
    }
  },
  watch: {
    userType: {
      handler(val) {
        console.log(this.userType);
        this.loadDefaultNode();
      }
    }
  },
  computed: {
    ...mapGetters(["name", "userId", "deptId"])
  },
  mounted() {
    this.init();
  },
  methods: {
    init() {
      this.loadDefaultNode();
    },
    // 显示
    showDialog() {
      this.flDialog = true;
    },

    //确认
    flSure() {
      let currentDetialArr = [];
      for (var i = 0; i < this.currentDetial.length; i++) {
        currentDetialArr.push(this.currentDetial[i].text);
      }
      this.fns(this.currentDetial, "确定");
      this.currentDetial = [];
      this.searchDetial = [];
      this.$nextTick(() => {
        this.$refs.tree.setCheckedNodes([]);
      });
    },

    //清除
    flClean() {
      this.currentDetial = [];
      this.searchDetial = [];
      this.$nextTick(() => {
        this.$refs.tree.setCheckedNodes([]);
      });
    },

    //取消
    flCancel() {
      this.currentDetial = [];
      this.searchDetial = [];
      this.fns(this.currentDetial, "取消");
      this.$nextTick(() => {
        this.$refs.tree.setCheckedNodes([]);
      });
    },

    handlerValueAndNode(value, node) {
      let y_index = "";
      let s_index = "";
      let n_index = "";
      let c_index = "";
      y_index = node.checkedNodes.findIndex(item => {
        return value.text == item.text;
      });
      n_index = node.checkedNodes.findIndex(item => {
        return value.text == item.text;
      });
      if (y_index == -1) {
        s_index = this.searchDetial.findIndex(item => {
          return value.text == item.text;
        });
        if (s_index != -1) {
          this.searchDetial.splice(s_index, 1);
        }
      } else {
        s_index = this.searchDetial.findIndex(item => {
          return value.text == item.text;
        });
        if (s_index == -1) {
          this.searchDetial.push(value);
        }
      }
      if (n_index == -1) {
        c_index = this.currentDetial.findIndex(item => {
          return value.text == item.text;
        });
        if (c_index != -1) {
          this.currentDetial.splice(c_index, 1);
        }
      } else {
        c_index = this.currentDetial.findIndex(item => {
          return value.text == item.text;
        });
        if (c_index == -1) {
          this.currentDetial.push(value);
        }
      }
    },

    //选中节点变化时触发的事件
    getCurrentNode: function(value, node) {
      console.log("节点信息", value, node.checkedNodes);
      if (node.children != undefined) {
        return false;
      } else {
        if (value.attr == "emp" || value.attr.split("-")[1] == "emp") {
          this.handlerValueAndNode(value, node);
        } else if (
          value.attr.split("-")[1] == "dep" &&
          value.attr.split("-")[2].indexOf("emp") != -1
        ) {
          let valueChildren = [];
          let params = {};
          params.userType = this.userType;
          params.id = value.id;
          params.current = "dep";
          params.next = "emp";
          queryEmployees(params).then(res => { 
            valueChildren = res.data.results;
            for (var i = 0; i < valueChildren.length; i++) {
              this.handlerValueAndNode(valueChildren[i], node);
            }
          });
        }
        let __temps = this.currentDetial.concat(this.searchDetial);
        let res = new Map();
        this.currentDetial = __temps.filter(
          ii =>
            !res.has(ii.id) &&
            res.set(ii.id, ii) &&
            (ii.attr.split("-")[1] == "emp" || ii.attr == "emp")
        );
        console.log("输出：", this.currentDetial);
        this.ifwbtcRight = true;
      }
    },

    // 删除选中
    delSelect(val, index) {
      console.log("删除的值", val);
      this.currentDetial.splice(index, 1);
      this.$nextTick(() => {
        this.$refs.tree.setCheckedNodes(this.currentDetial);
      });
    },

    // 机构树过滤
    filterNode(value, data) {
      if (!value) return true;
      return (
        data.text.indexOf(value) !== -1 &&
        (data.attr.split("-")[1] == "emp" || data.attr == "emp")
      );
    },

    //分管总经理
    getManager() {
      let params = {};
      params.depid = this.deptId;
      queryDetailForDept(params).then(res => {
        if (res.data.status == 200) {
          this.deptName = res.data.results.location;
        }
      });
    },

    // 加载默认节点
    loadDefaultNode() {
      this.getManager();
      let params = {};
      params.userType = this.userType;
      if (this.userType == "外包") {
        this.placeholder = "员工号/员工名称";
        params.id = this.deptId;
        params.current = "dep";
        params.next = "emp";
      } else {
        this.placeholder = "员工号/员工名称/员工名称拼音";
      }
      queryEmployees(params).then(res => {
        if (this.userType == "外包") {
          let datas = {
            attr: this.deptId + "-dep-emp",
            children: [],
            id: params.id,
            text: this.deptName
          };
          this.treeData = res.data.results.filter(li => {
            li.isLeaf = true;
            return li;
          });
          if (this.treeData.length > 0) {
            datas.children = datas.children.concat(this.treeData);
            this.treeData = [datas];
          }
        } else {
          this.treeData = res.data.results;
        }
        console.log("treeData:", this.treeData);
      });
    }, // end

    // 机构树懒加载
    loadNode(node, resolve) {
      if (node.level === 0) {
        return resolve(node.data);
      }
      if (node.level === 1) {
        if (node.data.children != undefined) {
          return resolve(node.data.children);
        } else {
          return resolve([]);
        }
      }
      if (node.level > 1) {
        this.getCurrentDepPerson(node, resolve);
      }
    },

    //点击节点
    getChildrenNode(val) {
      console.log("子节点信息", val);
      this.treeNodeData = val;
    },

    // 懒加载子级节点
    getCurrentDepPerson(node, resolve) {
      let params = [];
      if (node.data.attr.indexOf("-") == 0) {
        let _params = node.data.attr.split("-");
        params[0] = "-" + _params[1];
        params[1] = _params[2];
        params[2] = _params[3];
      } else {
        params = node.data.attr.split("-");
      }
      console.log("params", params);
      if (params.length < 3) {
        resolve([]);
        return false;
      }
      let data = {};
      data.id = params[0];
      data.current = params[1];
      data.next = params[2];
      data.userType = this.userType;
      queryEmployees(data).then(res => {
        if (res.data.status == 200) {
          
          let data = [];
          if (res.data.results != undefined) {
            data = res.data.results;
            data.forEach(item => {
              item.text = item.text;
              if (item.attr.split("-")[1] == "emp") {
                item.isLeaf = true;
              }
            });
          }
          resolve(data);

          this.$nextTick(() => {
            this.$refs.tree.setCheckedNodes(this.currentDetial);
          });
        } else {
          // this.$message.error('加载人员失败');
          resolve([]);
        }
      });
    },

    // 搜索用户树
    searchUserBtn() {
      if (this.searchUser != "") {
        this.isSearch = true;
        let self = this;
        let params = {};
        params.searchUser = this.searchUser;
        params.userType = this.userType;
        queryEmployees(params).then(res => {
          if (res.data.status !== 200) {
            self.$alert(res.data.message, { type: "error" });
            return false;
          }
          
          if (res.data.results != undefined) { 
            if (this.userType == "外包") {
              self.treeData = res.data.results.filter(li => {
                li.isLeaf = true;
                return li.pid == this.deptId;
              });
            } else {
              self.treeData = res.data.results.filter(li => {
                li.isLeaf = true;
                return li;
              });
            }
          } else {
            self.treeData = [];
          }
          this.$nextTick(() => {
            this.$refs.tree.setCheckedNodes(this.currentDetial);
          });
        });
      }
    }, // end

    returnToTree() {
      this.isSearch = false;
      this.loadDefaultNode();
      this.searchUser = "";
    }
  }
};
</script>

<style scoped>
.butt-search {
  height: 35px;
  line-height: 35px;
}

.flDialogBody_con {
  border: 1px solid #d9d9d9;
  margin: 15px 0;
}

.flDialogBody_con_left {
  border-right: 1px solid #d9d9d9;
  padding: 10px 0 10px 10px;
}

.flDialogBody_con_left_input {
  padding-right: 10px;
}

.flDialogBody_con_right_title {
  font-size: 16px;
  font-weight: 550;
  color: #303133;
}

.flDialogBody_con_right_title span {
  margin: 0 5px;
}

.flDialogBody_con_tree {
  min-height: 310px;
  max-height: 310px;
  padding-top: 10px;
}

.flDialogBody_con_right_main {
  min-height: 310px;
  max-height: 310px;
  padding-top: 5px;
}

.flDialogBody_con_right {
  padding: 18px 0 0 15px;
}

.flDialogBody_ul {
  list-style-type: none;
  margin: 0;
  padding: 0;
}

.flDialogBody_ul div {
  margin-top: 8px;
}

.flDialogBody_ul_up {
  color: #303133;
  font-size: 14px;
  font-weight: 400;
}

.flDialogBody_ul_low {
  color: #c0c4cc;
  font-size: 14px;
  font-weight: 400;
  margin-top: 10px;
}

.flList {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-right: 10px;
}

.el-icon-circle-close {
  cursor: pointer;
}
</style>

<style>
.flDialogCon .el-dialog__body {
  padding: 0 20px 10px 20px;
}

.single-choice .el-input.is-disabled .el-input__inner {
  background-color: transparent;
}
</style>