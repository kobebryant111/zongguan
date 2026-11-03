<template>
  <div class="app-index-container">
    <div class="flow-container">
      <!-- <el-card :body-style="{ padding: '0px' }">
     
        <flow-group-list></flow-group-list>
      </el-card> -->

      <div class="workPlatform">
        <div class="workPlatform_con">
          <div class="workPlatform_left">
            <div class="workPlatform_left_top">
              <div class="userPhoto">
                <img src="@/assets/avatar.png">
              </div>
              <div class="userMessage">
                <p class="userName">{{greetTime}}，{{name}}，祝你开122心每一天！</p>
                <div class="userRole">
                  <p v-for="item in roles" :key="item.roleId"><span class="userRole-bar">|</span>{{item.roleName}}</p>
                </div>
              </div>
            </div>
            <div class="workPlatform_left_middle">
              <!-- <el-card :body-style="{ padding: '0px' }"> -->
              <!-- card body -->
              <flow-group-list></flow-group-list>
              <!-- </el-card> -->
            </div>
            <div class="workPlatform_left_bottom">
              <el-calendar v-model="calendarDate"></el-calendar>
            </div>
          </div>
          <div class="workPlatform_right">
            <div class="workPlatform_right_top">
              <div>投产提醒</div>
              <div class="tctx">
                <i class="el-icon-alarm-clock"></i>
              </div>
            </div>
            <div class="workPlatform_right_middle">
              <div>快捷方式</div>
              <div class="kjfs">
                <i class="el-icon-message-solid"></i>
              </div>
            </div>
            <div class="workPlatform_right_bottom">
              <div>动态</div>
              <div class="dt">
                <i class="el-icon-lightning"></i>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>
  <!-- </div> -->
</template>

<script>
  import flowGroupList from "@/views/plugins/flowPlug/groupList/index";
  import store from '@/store'
  export default {
    components: {
      flowGroupList
    },
    name: "Dashboard",
    data() {
      return {
        name: '',
        roles: [],
        greetTime: '',
        calendarDate: new Date()
      }
    },
    created() {
      this.name = store.getters.name;
      this.roles = store.getters.roles;
      this.getTime();
    },
    methods: {
      getTime() {
        let now = new Date();
        let hour = now.getHours();
        if (hour < 6) {
          this.greetTime = '清晨好'
        } else if (hour < 9) {
          this.greetTime = '早上好'
        } else if (hour < 12) {
          this.greetTime = '上午好'
        } else if (hour < 14) {
          this.greetTime = '中午好'
        } else if (hour < 17) {
          this.greetTime = '下午好'
        } else if (hour < 19) {
          this.greetTime = '傍晚好'
        } else if (hour < 22) {
          this.greetTime = '晚上好'
        } else {
          this.greetTime = '夜里好'
        }
      }
    }
  };


</script>

<style scoped>
  .flow-container {
    /* width: 45%; */
    width: 100%;
  }

  /* .loading{
  width: 100%;
}
.tip{
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  color: #606266;
} */
  .app-container {
    min-height: calc(100vh - 84px);
    position: relative;
    padding: 0;
  }

  .bg {
    position: absolute;
    bottom: 0;
    width: 100%;
  }

  .workPlatform {
    width: 100%;
    padding: 15px;
  }

  .workPlatform_con {
    display: flex;
    justify-content: space-between;
  }

  .workPlatform_left {
    width: 65%;
  }

  .workPlatform_right {
    width: 33%;
  }

  .workPlatform_left_top {
    height: 116px;
    background: #ffffff;
    padding: 15px;
    display: flex;
    align-items: center;
  }

  .workPlatform_left_middle {
    background: #ffffff;
    padding: 15px;
    margin: 15px 0;
  }

  .workPlatform_left_bottom {
    height: 680px;
    background: #ffffff;
    padding: 15px;
  }

  .workPlatform_right_top {
    height: 116px;
    background: #ffffff;
    padding: 15px;
  }

  .workPlatform_right_middle {
    height: 592px;
    background: #ffffff;
    padding: 15px;
    margin: 15px 0;
  }

  .workPlatform_right_bottom {
    height: 680px;
    background: #ffffff;
    padding: 15px;
  }

  .userPhoto {
    margin-right: 15px;
  }

  .userName {
    color: #303133;
    font-size: 18px;
    font-weight: 300;
    margin: 0 0 15px 0;
  }

  .userRole {
    color: #909399;
    font-size: 14px;
  }

  .userRole>p {
    padding: 0;
    margin: 0;
    display: inline-block;
  }

  .userRole>p:nth-child(1) span {
    display: none;
  }

  .userRole>p>span {
    padding: 0 6px;
  }

  .tctx {
    margin-top: 15px;
  }

  .kjfs {
    margin-top: 15px;
  }

  .dt {
    margin-top: 15px;
  }
</style>

<style>
  .workPlatform .el-card {
    border: 0;
  }

  .kjfs .el-icon-message-solid {
    font-size: 50px;
  }

  .tctx .el-icon-alarm-clock {
    font-size: 50px;
  }

  .dt .el-icon-lightning {
    font-size: 50px;
  }

  .workPlatform_left_bottom .el-button:hover {
    color: rgba(251, 164, 47, 1);
    border-color: #FAF0E6;
    background-color: #FFFAFA;
  }

  .workPlatform_left_bottom .el-button:focus {
    color: rgba(251, 164, 47, 1);
    border-color: #FAF0E6;
    background-color: #FFFAFA;
  }
</style>