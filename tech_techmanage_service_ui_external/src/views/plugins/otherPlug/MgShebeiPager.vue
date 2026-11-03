<!-- 密管设备分页 -->
<template>
  <div class="app-container">
    <!-- 搜索-->
    <div class="search-container">
      <el-form :model="form" ref="form" label-width="80px" inline>
        <el-form-item label="业务系统">
          <!-- 在需要选择业务项目时使用 -->
            <el-select v-model="form.systemid" placeholder="" v-if="busSystemShow" >
                <el-option v-for="item in sysListData"
                    :key="item.systemid"
                    :label="item.sysName"
                    :value="item.sysName">
                </el-option>
            </el-select>
            <!-- 固定业务项目时使用 -->
           <el-input v-model="systemName" placeholder="" v-else disabled></el-input>
           
        </el-form-item>
        <el-form-item label="用户类型">
            <el-select v-model="form.usertype" placeholder="">
                <el-option v-for="item in typeData"
                    :key="item.dictid"
                    :label="item.dictname"
                    :value="item.dictid">
                </el-option>
            </el-select>
            
        </el-form-item>
        <el-form-item label="设备IP">
          <el-input v-model="form.ip"></el-input>
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="form.userid"></el-input>
        </el-form-item>
        <el-form-item>
          <button-orange :buttonName="searchText" :size="sizes" :fns="searchBtn"></button-orange>
          <clean-btn :size="sizes" :forms="this.form"/>
        </el-form-item>
      </el-form>
    </div>
    <!-- 列表 -->
    <div class="content-container">
      <el-table :data="data" border stripe highlight-current-row ref="sbtable" @selection-change="handleSelectChange">
        <el-table-column type="selection" label="序号"></el-table-column>  
        <el-table-column type="index" label="序号"></el-table-column>
        <el-table-column prop="depid" label="部门"></el-table-column>
        <el-table-column prop="systemid" label="系统"></el-table-column>
        <el-table-column prop="usertype" label="用户类型"></el-table-column>
        <el-table-column prop="ip" label="设备IP"></el-table-column>
        <el-table-column prop="userid" label="用户名"></el-table-column>
        <el-table-column prop="applyer" label="申请人"></el-table-column>
        <el-table-column prop="status" label="状态"></el-table-column>
      </el-table>
      <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager"/>
    </div>
  </div>
</template>
<script>
import { querySheBeiPager,querySysList } from "@/api/branchIntentionApi/yyxmbg";
import cleanBtn from "@/components/CleanBtn";
import common from "@/api/common";
import pager from "@/components/Pager";
import buttonOrange from "@/components/eleButton/buttonOrange"; //引入按钮组件Orange
import { getDicEntry } from "@/api/eosDictEntry";
export default {
  name: "mgShebeiPager",
  components: {
    cleanBtn,
    pager,
    buttonOrange
  },
  props:{
    /**
     * 业务系统是否只读
     */
    busSystemShow: {
      type : Boolean,
      default : false
    },
    /**
     * 业务系统id
     */
    systemName : {
       type : String,
      default : ""
    }
  },
  data() {
    return {
      sizes: "mini", // cleanBtn 大小
      searchText: "搜 索", // 按钮值
      form: {
        systemid: "", // 业务系统
        usertype: "", // 用户类型
        ip: "", // ip
        userid: "" // 用户名
      },
      // 分页
      totalNumber: 0,
      size: 50, // 初始值 默认：50
      index: 1, // 初始值 默认：1
      data: [], // 分页数据
      rowData: {}, // table选中行数据
      params: {},
      typeData : [],        // 用户数据集合
      sysListData : [],  // 系统列表集合
    };
  },
  methods: {
    /**
     * 页面加载
     */
    init(param) {
      let self = this;
      // 用户类型
      let id = {dicttypeid:'PWD_USERTYPE'}
      getDicEntry(id).then(res => {
          self.typeData = res.data.results
      })
      // 业务系统 - 由外部传入
      self.systemid = self.busSystemId
      // 系统列表
      querySysList().then(res => {
          console.log('系统列表', res.data.results)
          self.sysListData = res.data.results
          
      })
      
      self.params = param;
      self.pager();
    },
    /**
     * 搜索事件
     */
    searchBtn() {
        this.pager();
    },
    /**
     * 点击table某一行触发
     */
    hadlenTableRow(row, column, event) {
      
      
    },

    /**
     * 分页方法
     */
    pager() {
      let self = this;
      self.params = Object.assign(self.params, self.form)
      self.params.index = self.index;
      self.params.size = self.size;
      console.log("搜索参数", self.params);
      let data = common.paramsFormat(self.params);
      querySheBeiPager(data).then(res => {
        if (res.data.status !== 200) {
          self.$alert(res.data.message, { type: "error" });
          return false;
        }

        self.data = res.data.results;
        self.totalNumber = res.data.total;
      }); // pagerList
    },
    /**
     * 分页
     */
    getPager(size, index) {
      let self = this;
      
      let param = Object.assign(self.form, self.params);
      param.size = size;
      param.index = index;
      self.size = size;
      self.index = index;
      self.params = param;
      self.pager();
    },
    /**
     * table
     *  checkedbox
     */
    handleSelectChange(rows) {
        let self = this;
      console.log(rows);
      rows.forEach(row => {
        if (row.status === "待重置") {
            let temp = [];
            temp.push("ip:");
            temp.push(row.ip);
            temp.push(",用户:");
            temp.push(row.userid);
            temp.push("状态:");
            temp.push("待重置不可用，请重新选择");
            self.$message.error(temp.join(","));
            self.$refs.sbtable.toggleRowSelection(row,false)
        } 
      })
      self.rowData = rows;
    },
  }
};
</script>

