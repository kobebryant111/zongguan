<!-- 非科技外包人员人脸考勤配置 -->
<template>
    <div class="wrapper">
        <!-- 搜索部分 -->
        <div class="search_box margin_bottom">
            <el-form class="formBox" :model="form" label-width="80px">
              <el-row :gutter="4">
                <el-col :span="4">
                  <el-form-item label="系统编号:">
                    <el-input v-model.trim="form.jobId" placeholder="请输入系统编号"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item  label="姓名:">
                    <el-input v-model="form.userName" placeholder="请输入姓名"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                  <el-form-item label="部门" prop="deptid">
                    <!-- <el-select v-model="form.deptId" placeholder="请选择部门">
                        <el-option v-for="item in depData" :key="item.id" :value="item.id" :label="item.text"></el-option>
                    </el-select> -->
                    <el-input v-model="form.deptName" placeholder="请输入"></el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-form-item label="是否下发">
                        <el-select v-model="form.issueState">
                            <el-option v-for="item in xfOption" :key="item.id" :value="item.value" :label="item.label"  ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-form-item label="在岗状态">
                        <el-select v-model="form.state">
                            <el-option v-for="item in stateOption" :key="item.dictid" :value="item.dictid" :label="item.dictname" :disabled="item.dictid === '2'" ></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="4">
                    <el-row type="flex" justify="end">
                        <clean-btn :size="sizes" :forms="this.form" />
                        <div class="interviewList_head_right_orange">
                            <button-orange :buttonName="buttonSearch" :size="sizes" :fns="search"></button-orange>
                        </div>
                        <!-- <el-button style="margin-left:10px" type="primary" @click="search">查询</el-button> -->
                    </el-row>
                </el-col>
              </el-row>
            </el-form>
        </div>
        <!-- 表格部分 -->
        <div class="table_box">
            <div class="table_box_top">
                <el-button @click="xfmachuine">下发考勤机</el-button>
            </div>
            <el-table  :data="tableData"  class="margin_bottom" height="640" header-align="center" stripe @selection-change="handleSelection">
                <el-table-column type="selection" width="100"></el-table-column>
                <el-table-column prop="deptName" label="部门" align="center"></el-table-column>
                <el-table-column prop="userName" label="姓名" align="center"></el-table-column>
                <el-table-column prop="jobId" label="系统编号" align="center"></el-table-column>
                <el-table-column prop="picture" label="照片" align="center">
                    <template slot-scope="scope">
                        <div class="image">
                            <!-- <el-image
                                :src="scope.row.photo"
                                style="width:100px;height:100px;border-radius: 50%;padding: 10px"
                            ></el-image> -->
                            <img :src="scope.row.pictureUrl" :alt="scope.row.documentno" style="max-height:100px;max-width:100px;" slot="reference">
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="state" label="在岗状态" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.state == '0' ? '在岗' : '离岗' }}
                    </template>
                </el-table-column>
                <el-table-column prop="issueState" label="下发状态" align="center">
                    <template slot-scope="scope">
                        {{ scope.row.issueState == '0' ? '未下发' : '已下发' }}
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-link type="warning" :underline="false" @click="uploadPhoto(scope.row)">上传照片</el-link>
                        <!-- <el-link type="warning" :underline="false" @click="xfmachuine(scope.row)">下发考勤机</el-link> -->
                    </template>
                </el-table-column>
            </el-table>
            <!-- 分页 -->
            <div class="pager_zb">
                <pager :fns="getPager" :total="totalNumber" :pageSize="size" v-model="$attrs" ref="pager" />
            </div>
            <!-- 照片弹框 -->
            <el-dialog title="上传照片" :visible.sync="fileshow" :before-close="closeUpload" :close-on-click-modal="false" :close-on-press-escape="false">
                <el-upload
                    action="Fake Action"
                    :on-exceed="handleExceed"
                    :on-success="handleAvatarSuccess"
                    :http-request="changeUpFile"
                    :on-remove="handleRemove"
                    :file-list="fileList"
                    show-file-list
                    list-type="picture"
                    :limit="1"
                    :before-upload="beforeUpload"
                    :before-remove="beforeRemove"
                >
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">
                      <p style="font-size: 18px;weight: 700;">只能上传jpg文件，且不超过200kb!</p>
                    </div>
                </el-upload>
                <span slot="footer" class="dislog-footer">
                    <el-button type="primary" @click="sureadd">确 定</el-button>
                </span>
            </el-dialog>
            <!-- 下发考勤机 -->
            <el-dialog title="下发考勤机" :visible="xfShow" :before-close="closexf" :close-on-click-modal="false"
                style="font-weight: 700; font-size: 18px;width:1200px;margin-left:200px;margin-top:100px;"
            >
                <el-checkbox-group v-model="checkList" @change="handleChange">
                    <el-checkbox v-for="item in kqjList" :key="item.id" :label="item.id" >{{ item.name }}</el-checkbox>
                </el-checkbox-group>
                <!-- 底部 -->
                <div class="rightdialog-button">
                    <button-common buttonName="取 消" size="mini" :fns="xfcancel" ></button-common>
                    <button-orange class="xkg-marginleft-10px" size="mini" buttonName="确定下发" :fns="xfsubmit"></button-orange>
                </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
import { getDicEntry } from "@/api/eosDictEntry";
import cleanBtn from "@/components/CleanBtn";
import pager from "@/components/Pager";
import common from "@/api/common";
import buttonOrange from "@/components/eleButton/buttonOrange";
import buttonCommon from "@/components/eleButton/buttonCommon";
import { queryPager, uploadFile, uploadFacePicture, queryFaceDevice, handFaceDevice } from "@/api/kqManager/kqFaceConfig"
import { queryResourceDept } from "@/api/integrated/wbryzrlc";

export default {
    components: { cleanBtn, pager, buttonOrange, buttonCommon },
    data() {
        return {
            totalNumber: 0,
            size: 50, // 初始值 默认：50
            index: 1, // 初始值 默认：1
            form: {
                jobId: "", // 工号
                username: "", // 用户名
                deptName: "", // 部门
                issueState: "",
                state: "0"
            },
            tableData: [],
            depData: [], // 部门数据
            sizes: "small",
            buttonSearch: '查 询', //查询按钮
            fileList: [],
            fileshow:false, //上传窗口
            rowdata: null,
            xfShow: false, //下发窗口
            checkList: [],
            kqjList:[],
            fileData: '',
            multipleSelection: [],
            stateOption: [],
            fileSize: false,
            xfOption: [
                {
                    id: 0,
                    label: '否',
                    value: '0'
                },
                {
                    id: 1,
                    label: '是',
                    value: '1'
                }
            ],
            // selectAll: '0', // 是否全选
            messages: ""
        }
    },
    created() {
        this.filDep();
    },
    mounted() {
        this.getInit()
    },
    methods: {

        // 获取数据列表
        getInit() {
            getDicEntry({dicttypeid: "NEWKG_RYZT"}).then(res => {
                if (res.data.status !== 200) {
                    this.$alert(res.data.message, { type: "error" });
                    return false;
                }
                let data = res.data.results;
                // 证件类型
                this.stateOption = data.filter(item => {
                    return item.dicttypeid == "NEWKG_RYZT";
                });
                return data;
            });

            // console.log('gitInit')
            let params = {}
            params.type = "OTHER",
            params.size = this.size;
            params.index = this.index;
            params.state = this.form.state;
            this.getPagerList(params)
        },
        getPagerList(params) {
            let data = common.paramsFormat(params);
            let self = this;
            queryPager(data).then(res => {
                console.log(res, "考勤人脸配置")
                const {results,message,status,total} = res.data
                if(status == 200) {
                    self.totalNumber = total;
                    self.tableData = results;
                    if (self.totalNumber <= 0) {
                        return false
                    }
                }
            })
        },
        // 搜索
        search() {
            console.log(this.form)
            let params = this.form;
             for(var k in params) {
                if(params[k] && this.index > 1) {
                    this.index = 1
                }
            }
            params.type = "OTHER";
            params.size = this.size;
            params.index = this.index;
            this.getPagerList(params)
        },
        // 关闭弹框
        closeUpload() {
          this.fileshow = false;
        },
        // 上传照片
        uploadPhoto(row) {
            // console.log(row, '上传照片')
            this.fileshow = true;
            if(FileList.length == 0) {
                this.fileData = null
            }
            this.rowdata = row;
        },
        // 文件限制
        handleExceed(files, fileList) {
            // console.log(files, fileList, '文件限制')
            this.$message.error("请删除已上传的文件后再次上传");
        },
        // 自定义上传
        changeUpFile(file) {
            if(this.fileSize) {
                return false
            }
            console.log(file.file, '自定义上传');
            var formData = new FormData();
            formData.append('file', file.file);
            // console.log("file", file);
            // let data = formData;
            // data.type = 'AI';
            // data.catalogNo = 'CATALOG2014091500001';
            formData.append("type", "AI");
            formData.append("catalogNo", "CATALOG2014091500001");

            uploadFile(formData).then(res => {
                // console.log(res);
                this.fileSize = false;
                const { message, results, status } = res.data
                if(status == 200) {
                    this.fileList.push({
                        url: URL.createObjectURL(file.file)
                    });
                    this.fileData = results;
                    this.$message.success('上传成功');
                } else {
                    this.$message.success('上传失败');
                    this.fileList = []
                }
            })

        },
        beforeUpload(file) {
            console.log(file, 'beforeUpload')

            const isJPG = file.type === "image/jpeg";
            const isLt500K = file.size / 1024 < 200;
            if(!isJPG) {
                this.fileSize = true
                // this.$message.error('上传图片格式只能是jpg格式')
                this.messages = '上传图片格式只能是jpg格式,'
                return false
            }
            if(!isLt500K) {
                this.fileSize = true
                // this.$message.error('文件大小超出限制，请修改后重新上传')
                this.messages = '文件大小超出限制，请修改后重新上传,'

                return false
            }
            return isJPG && isLt500K
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`${this.messages}确定移除该照片吗？`,'提示',{
                confirmButtonText: '确定',
                concelButtonText: '取消',
                showCancelButton: false
            }).then(() => {
                this.messages = ''
                this.fileSize = false
            })
        },
        // 文件上传成功的钩子
        handleAvatarSuccess(response, file, fileList) {
            console.log(response, file, fileList, '文件上传成功的钩子')
            this.imageUrl = URL.createObjectURL(file.raw)
        },
        // 文件移除时的钩子
        handleRemove() {
            console.log('文件移除时的钩子');
            this.fileList = [];
        },
        // 提交上传照片
        sureadd() {
            console.log('提交上传照片')
            let param = {
                jobId: this.rowdata.jobId != undefined ? this.rowdata.jobId : null,
                picture: this.fileData,
                id: this.rowdata.id != undefined ? this.rowdata.id : null,
            }
            const loading = common.loading(this);
            let data = common.paramsFormat(param);
            uploadFacePicture(data).then(res => {
                console.log(res);
                loading.close();
                const { status } = res.data
                if(status == 200) {
                    this.fileList = [];
                    this.fileshow = false;
                    let params = this.form
                    params.type = "OTHER"
                    this.getPagerList(params)
                }
            }).catch(() => {
                loading.close()
            })
        },
        // 分页
        getPager: function(size, index) {
            let params = this.form;
            params.type = "OTHER",
            params.size = size;
            params.index = index;
            this.size = size;
            this.index = index;
            // console.log('11111111111111111111出发分页',size, index,params)
            this.getPagerList(params);
            // console.log('22222222222222222222调用获取数据的函数',params)
        },
        // 关闭弹框
        closexf() {
          this.xfShow = false;
        },
        //下发考勤机
        xfmachuine(){
            if(this.multipleSelection.length == 0) return this.$message.error('请选择用户')
            this.xfShow = true;
            let data = {}
            data.type = 1
            queryFaceDevice(data).then(res => {
                console.log(res)
                this.kqjList = res.data.results
            })
        },
        // 下发取消
        xfcancel() {
            this.xfShow = false;
        },
        handleChange(val) {
            console.log(val)
            this.checkList = val;
        },
        // 下发确定
        xfsubmit() {
            // console.log('xfsubmit')
            // console.log(this.checkList,"777777777777777777777")

            if(this.multipleSelection.length == 0) {
                return this.xfShow = false;
            }
            if(this.checkList.length == 0) {
                return this.$message.info('请选择考勤机')
            }
            let data = {}
            data.faceDeviceInfoDtoList = this.checkList;
            data.faceConfigDtoList = this.multipleSelection;
            // data.selectAll = this.selectAll;
            handFaceDevice(data).then(res => {
                console.log(res)
                if(res.data.status == 200) {
                    this.$message.success("下发成功")
                    let params = this.form;
                    params.size = size;
                    params.index = index;
                    params.type = "OTHER";
                    this.getPagerList(params)
                } else {
                    this.$message.error(res.data.message)
                }
            })
            this.xfShow = false;
            this.selectAll = '0';
        },
        // 获取部门
        filDep() {
          let params = {};
          params.objectcode = "6000000300";
          params.objectgrade = "2";
          queryResourceDept(params)
            .then(res => {
              var fild = res.data.results[0].children;
              let name = res.data.results[0].text;
              this.depData = fild.filter(tt => {
                tt.text = tt.text.replace(name + "-", "");
                return tt;
              });;
            })
            .catch(error => {
              this.$message.error(error);
            });
        },
        handleSelection(val) {
            console.log(val,'handleSelection');
            this.multipleSelection = val
        },
    },
}
</script>

<style scoped>
.wrapper {
    padding: 15px;
}
.search_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px 18px 0 18px;
}
.table_box {
    width: 100%;
    background-color: #ffffff;
    padding: 18px;
}
.table_box .table_box_top {
    width: 100%;
    padding: 10px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
}
.margin_bottom {
    margin-bottom: 15px
}
.interviewList_head_right_orange {
        margin: 0 6px 0 10px;
    }
/* 分页组件位置 */
.pager_zb {
    width: 100%;
    display: flex;
    flex-direction: row-reverse;
}
.rightdialog-button {
  text-align: right;
}

.card-top {
    margin-bottom: 15px;
    float: right;
    height: 40px;
}
</style>
