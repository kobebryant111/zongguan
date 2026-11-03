<!-- 添加系统信息 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">{{title}}节假日</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>

    <!-- 节假日信息 -->
    <div class="contentMain">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">节假日信息</p>
      </div>
      <div class="content">
        <el-form :model="formData" ref="formData">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                 <el-col :span="8">
                  <div>
                    <p>日期 </p>
                    <el-form-item prop="calendarDate">
                       <el-date-picker disabled v-model="formData.calendarDate"  style="width:250px;" 
                          type="date"  value-format="yyyy-MM-dd"/> 
                    </el-form-item>
                  </div>
                </el-col>
                 <el-col :span="8">
                  <div>
                    <p>星期 </p>
                    <el-form-item prop="dayOfWeek">
                       <el-select disabled style="width:250px;" v-model="formData.dayOfWeek">
                            <el-option v-for="item in week_options" 
                              :key="item.value" 
                              :label="item.label" 
                             :value="item.value"/>
                            <!-- </el-select>         -->
                        </el-select>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="8">
                  <div>
                    <p>类型 </p>
                    <el-form-item prop="isHoliday">
                       <el-select placeholder="类型" style="width:250px;" v-model="formData.isHoliday">
                            <el-option v-for="item in ryzt_options" 
                              :key="item.value" 
                              :label="item.label" 
                             :value="item.value"/>
                            </el-select>
                    </el-form-item>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </el-form>
      </div>
    </div> 
    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
         <div class="wrapper_left">
            <el-col :span="1.5">
              
            </el-col>
          </div>
          <div class="wrapper_right">
            <div class="wrapper_right_bottom_1_zb">
              <el-col :span="1.5">
                <button-common :buttonName="buttonCancel" :size="size" :fns="close"/>
              </el-col>
            </div>
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonCommit" :size="size" :fns="onSubmit" />
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>

  </div>
</template>
<script>
import common from "@/api/common"; 
import { update, updateRecordHoliday } from '@/api/kqManager/calendar';
import pager from "@/components/Pager"; 
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件Orange
import buttonCommon from '@/components/eleButton/buttonCommon'; //引入按钮组件2
import Moment from 'moment';
export default {
    name: 'calendarInfo',
    components: {buttonOrange, buttonCommon },
    data() {
        return {
            formData: this.$route.query,
            managerData:{},
            deptmentData:{},            
            ryzt_options:[{
                value: 'N',
                label:'工作日'
            },{
                value: 'Y',
                label:'节假日'
            }],
            week_options:[{
                value: '1',
                label:'星期天'
            },{
                value: '2',
                label:'星期一'
            },{
                value: '3',
                label:'星期二'
            },{
                value: '4',
                label:'星期三'
            },{
                value: '5',
                label:'星期四'
            },{
                value: '6',
                label:'星期五'
            },{
                value: '7',
                label:'星期六'
            }],
            size: 'mini',  //按钮大小
            title: '添加',
            buttonClose: '关 闭', //关闭按钮名字
            buttonDel: '删 除',
            buttonCancel: '取 消',
            buttonCommit: '提 交',
            buttonSure: '确 定', 
        };
    }, 
    created() {
        if(this.formData.calendarDate){ 
            this.title = "修改";           
        }
    }, 
    methods: {
      /**
       * 提交
       */
        onSubmit() {
            this.$refs["formData"].validate(valid => {
            if(valid){
                const loading = common.loading(this); 
                update(this.formData).then(res => {
                    loading.close();
                    if (res.data.status == "200") {
                        this.$message.success("操作成功！");
                    } else {
                        this.$message.error(res.data.message); 
                    }
                });
                updateRecordHoliday(this.formData).then(res => {
                  if(res.data.status === 200) {
                    console.log('111')
                    this.$router.push({ name: 'calendar' });
                  }
                })
            }
        });
       },
      // 关闭按钮
      close() {
        this.$router.push({ name: 'calendar' });
      }
    }
};
</script>
<style scoped>
  /* bin修改 */
  .contentMain {
    width: 100%;
    height: 100%;
    font-size: 16px;
    padding: 10px 20px;
  }

  /* 导航菜单栏 */
  .nav {
    width: 100%;
    /* height: 30px; */
    background-color: #ffffff;
  }

  .nav .nav_name {
    width: 180px;
    height: 24px;
    font-size: 16px;
    font-weight: 600;
    color: rgba(38, 38, 38, 1);
    line-height: 24px;
    display: inline-block;
  }

  .nav .wrapper {
    margin-left: 25px;
    margin-right: 25px;
    display: flex;
    justify-content: space-between;
  }

  .wrapper_left,
  .wrapper_right {
    display: flex;
    align-items: center;
  }

  .wrapper_right_bottom_1_zb {
    margin-right: 10px;
  }
  .content {
    width: 100%;
    font-size: 16px; 
  }

  .content p {
    color: #262626;
    font-size: 14px;
    margin: 12px 0;
  }

  .content .row_2_detail {
    font-size: 16px;
    font-weight: 600;
  }

  .contentTwo_title {
    height: 60px;
    line-height: 60px;
    background-color: #ffffff;
    padding: 0 25px;
    border-bottom: 1px solid rgba(0, 0, 0, 0.15);
  }

  .contentTwo_title_p {
    color: #262626;
    font-weight: 600;
  }

  .content .row_1 {
    background-color: #ffffff;
    /* padding: 0 20px 15px 20px; */
    padding: 0 20px;

  }

  .contentMain .row_2 {
    background-color: #ffffff;
    padding: 0 20px 15px 20px;
    margin-top: 15px;
  }

  .add_row {
    color: #faad14;
    font-size: 14px;
    font-weight: 500;
    margin-left: 10px;
    cursor: pointer;
  }

  /* 空格占位 */
  .blank {
    width: 100%;
    height: 160px;
  }

  .bottom {
    position: absolute;
    bottom: 0;
    padding: 15px 0;
  } 
  
</style>