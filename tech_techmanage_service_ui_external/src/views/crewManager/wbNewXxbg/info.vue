<!-- 提前入场申请新增页面 -->
<template>
  <div class="container container_zb">
    <!-- 头部 -->
    <!-- bin -->
    <div class="nav">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <p class="nav_name">信息变更</p>
            </el-col>
          </div>
          <div class="wrapper_right">
            <el-col :span="1.5">
              <el-button size="mini" @click="toClose">关闭</el-button>
            </el-col>
          </div>
        </div>
      </el-row>
    </div>
    <!-- 主体 -->
    <div class="main-app-container">
      <!-- 面试信息 -->
    </div>

     <div class="contentMain contentMain_Add">
      <div class="contentTwo_title">
        <p class="contentTwo_title_p">申请人信息</p>
      </div>
      <div class="content">
        <el-form :model="formData" ref="formData">
          <div class="row_1">
            <div>
              <el-row type="flex" justify="space-between">
                <el-col :span="7">
                  <div>
                    <p>申请人部门 *</p>
                    <el-form-item prop="deptName">
                      <el-input v-model="formData.deptName" :disabled="disableds"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                  <div>
                    <p>申请人 *</p>
                    <el-form-item prop="msgid" ref="msgForm">
                      <el-input v-model="formData.applyer" :disabled="disableds"></el-input>
                    </el-form-item>
                  </div>
                </el-col>
                <el-col :span="7">
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 表格 -->
          <div class="row_2 row_2_add">
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">提前入场者信息<span class="add_row" @click="eduExperAdd(1)">新增一行</span></p>
                  <el-table :data="formData.updateInfoDtos"  style="width: 100%"  @row-click="rowClick">
                        <el-table-column label="序号" align="center" type="index"></el-table-column>
                        <el-table-column label="姓名 *" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`updateInfoDtos.${scope.$index}.userName`" :rules="[{ required: true, message: '请填写姓名'}]">
                                    <el-select v-model="scope.row.userName" @change="handleChange(scope.row,`${scope.$index}`)">
                                      <el-option
                                        v-for="item in applyData"
                                        :key="item.id"
                                        :value="item.userName"
                                        :label="item.userName"
                                      ></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="虚拟机账号">
                          <template slot-scope="scope">
                            <el-form-item>
                              <el-input v-model="scope.row.vmAccount"></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="门禁卡编号">
                          <template slot-scope="scope">
                            <el-form-item>
                              <el-input v-model="scope.row.entranceCard"></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="总办议案">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.ceneralOfficeMotion`">
                             <el-select
                                v-model="scope.row.ceneralOfficeMotion"
                             >
                               <el-option
                                v-for="(item) in ceneralOfficeMotion_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                             </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="合同">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.newContractId`">
                             <el-select
                                v-model="scope.row.newContractId"
                             >
                               <el-option
                                v-for="(item) in newContractId_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                             </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="征信报告" prop="creditReport">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.creditReport`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action"
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadzxFile"
                                :on-change="changezxFile"
                                v-if="scope.row.creditReport == '' || scope.row.creditReport == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.creditReport)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.creditReport)">下载</div> -->
                                      {{scope.row.creditReport}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadzxFile" :on-change="changezxFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="社保缴纳证明/劳动合同/纳税证明">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.sociaiSecrityPaymentProve`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadsbFile"
                                :on-change="changesbFile"
                                 v-if="scope.row.sociaiSecrityPaymentProve == '' || scope.row.sociaiSecrityPaymentProve == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.sociaiSecrityPaymentProve)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.sociaiSecrityPaymentProve)">下载</div> -->
                                      {{scope.row.sociaiSecrityPaymentProve}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadsbFile" :on-change="changesbFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="保密承诺书">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.confidentialityCommitment`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadbmFile"
                                :on-change="changebmFile"
                                 v-if="scope.row.confidentialityCommitment == '' || scope.row.confidentialityCommitment == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.confidentialityCommitment)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.confidentialityCommitment)">下载</div> -->
                                      {{scope.row.confidentialityCommitment}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadbmFile" :on-change="changebmFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="身份证正反面">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.otherFile`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadsfzFile"
                                :on-change="changesfzFile"
                                 v-if="scope.row.otherFile == '' || scope.row.otherFile == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.otherFile)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.otherFile)">下载</div> -->
                                      {{scope.row.otherFile}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadsfzFile" :on-change="changesfzFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="人脸照片">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.faceimage`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadfacephotoFile"
                                :on-change="changefacephotoFile"
                                 v-if="scope.row.faceimage == '' || scope.row.faceimage == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.faceimage)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.faceimage)">下载</div> -->
                                      {{scope.row.faceimage}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadfacephotoFile" :on-change="changefacephotoFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80" align="center" class="margin0">
                            <template slot-scope="scope">
                                <div class="operation_add">
                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.updateInfoDtos, scope.row)">
                                        <i class="el-icon-delete"></i>
                                    </el-button>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
          <!-- 流程发起后显示 -->
          <div class="row_2 row_2_add" style="display: none">
            <!-- 信息变更前 -->
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">信息变更 - 前<span class="add_row" @click="eduExperAdd(1)">新增一行</span></p>
                  <el-table :data="formData.updateInfoDtos"  style="width: 100%"  @row-click="rowClick">
                        <el-table-column label="序号" align="center" type="index"></el-table-column>
                        <el-table-column label="姓名 *" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`updateInfoDtos.${scope.$index}.userName`" :rules="[{ required: true, message: '请填写姓名'}]">
                                    <el-select v-model="scope.row.userName" @change="handleChange(scope.row,`${scope.$index}`)">
                                      <el-option
                                        v-for="item in applyData"
                                        :key="item.id"
                                        :value="item.userName"
                                        :label="item.userName"
                                        disabled
                                      ></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="前虚拟机账号">
                          <template slot-scope="scope">
                            <el-form-item>
                              <el-input v-model="scope.row.vmAccountBe" disabled></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前门禁卡编号">
                          <template slot-scope="scope">
                            <el-form-item>
                              <el-input v-model="scope.row.entranceCardBe" disabled></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前总办议案">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.ceneralOfficeMotionBe`">
                             <el-select
                                v-model="scope.row.ceneralOfficeMotionBe"
                                disabled
                             >
                               <el-option
                                v-for="(item) in ceneralOfficeMotion_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                             </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前所属合同">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.ceneralOfficeMotionBe`">
                             <el-select
                                v-model="scope.row.ceneralOfficeMotionBe"
                             >
                               <el-option
                                v-for="(item) in newContractId_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                             </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前征信报告" prop="creditReport">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.creditReportBe`">
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action"
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadzxFile"
                                :on-change="changezxFile"
                                v-if="scope.row.creditReportBe == '' || scope.row.creditReportBe == undefined ? true : false"
                                disabled
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.creditReport)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.creditReport)">下载</div> -->
                                      {{scope.row.creditReport}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadzxFile" :on-change="changezxFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前社保缴纳证明/劳动合同/纳税证明">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.ssPaymentProveBe`">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadsbFile"
                                :on-change="changesbFile"
                                 v-if="scope.row.ssPaymentProveBe == '' || scope.row.ssPaymentProveBe == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.sociaiSecrityPaymentProve)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.sociaiSecrityPaymentProve)">下载</div> -->
                                      {{scope.row.sociaiSecrityPaymentProve}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadsbFile" :on-change="changesbFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前保密承诺书">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.confidentCommitmentBe`">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadbmFile"
                                :on-change="changebmFile"
                                 v-if="scope.row.confidentCommitmentBe == '' || scope.row.confidentCommitmentBe == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.confidentialityCommitment)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.confidentialityCommitment)">下载</div> -->
                                      {{scope.row.confidentialityCommitment}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadbmFile" :on-change="changebmFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前身份证正反面">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.otherFileBe`">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadsfzFile"
                                :on-change="changesfzFile"
                                 v-if="scope.row.otherFileBe == '' || scope.row.otherFileBe == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.otherFile)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.otherFile)">下载</div> -->
                                      {{scope.row.otherFile}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadsfzFile" :on-change="changesfzFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="前人脸照片">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.faceimageBe`">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadfacephotoFile"
                                :on-change="changefacephotoFile"
                                 v-if="scope.row.faceimageBe == '' || scope.row.faceimageBe == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.faceimage)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.faceimage)">下载</div> -->
                                      {{scope.row.faceimage}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadfacephotoFile" :on-change="changefacephotoFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80" align="center" class="margin0">
                            <template slot-scope="scope">
                                <div class="operation_add">
                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.updateInfoDtos, scope.row)">
                                        <i class="el-icon-delete"></i>
                                    </el-button>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
              </el-col>
            </el-row>
            <!-- 信息变更后 -->
            <el-row>
              <el-col :span="24">
                <div class="tableCon">
                  <p class="row_2_detail">信息变更 - 后<span class="add_row" @click="eduExperAdd(1)">新增一行</span></p>
                  <el-table :data="formData.updateInfoDtos"  style="width: 100%"  @row-click="rowClick">
                        <el-table-column label="序号" align="center" type="index"></el-table-column>
                        <el-table-column label="姓名 *" width="120" align="center">
                            <template slot-scope="scope">
                                <el-form-item :prop="`updateInfoDtos.${scope.$index}.userName`" :rules="[{ required: true, message: '请填写姓名'}]">
                                    <el-select v-model="scope.row.userName" @change="handleChange(scope.row,`${scope.$index}`)">
                                      <el-option
                                        v-for="item in applyData"
                                        :key="item.id"
                                        :value="item.userName"
                                        :label="item.userName"
                                      ></el-option>
                                    </el-select>
                                </el-form-item>
                            </template>
                        </el-table-column>
                        <el-table-column label="现虚拟机账号">
                          <template slot-scope="scope">
                            <el-form-item>
                              <el-input v-model="scope.row.vmAccountAf" disabled></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现门禁卡编号">
                          <template slot-scope="scope">
                            <el-form-item>
                              <el-input v-model="scope.row.entranceCardAf" disabled></el-input>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现总办议案">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.ceneralOfficeMotionAf`">
                             <el-select
                                v-model="scope.row.ceneralOfficeMotionAf"
                                disabled
                             >
                               <el-option
                                v-for="(item) in ceneralOfficeMotion_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                             </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现所属合同">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.newContractIdAf`">
                             <el-select
                                v-model="scope.row.newContractIdAf"
                                disabled
                             >
                               <el-option
                                v-for="(item) in newContractId_options"
                                :key="item.id"
                                :label="item.label"
                                :value="item.id"
                              ></el-option>
                             </el-select>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现征信报告" prop="creditReportAf">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.creditReportAf`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action"
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadzxFile"
                                :on-change="changezxFile"
                                v-if="scope.row.creditReportAf == '' || scope.row.creditReportAf == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.creditReport)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.creditReport)">下载</div> -->
                                      {{scope.row.creditReport}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadzxFile" :on-change="changezxFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现社保缴纳证明/劳动合同/纳税证明">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.ssPaymentProveAf`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadsbFile"
                                :on-change="changesbFile"
                                 v-if="scope.row.ssPaymentProveAf == '' || scope.row.ssPaymentProveAf == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.sociaiSecrityPaymentProve)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.sociaiSecrityPaymentProve)">下载</div> -->
                                      {{scope.row.sociaiSecrityPaymentProve}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadsbFile" :on-change="changesbFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现保密承诺书">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.confidentCommitmentAf`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadbmFile"
                                :on-change="changebmFile"
                                 v-if="scope.row.confidentCommitmentAf == '' || scope.row.confidentCommitmentAf == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.confidentialityCommitment)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.confidentialityCommitment)">下载</div> -->
                                      {{scope.row.confidentialityCommitment}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadbmFile" :on-change="changebmFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现身份证正反面">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.otherFileAf`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadsfzFile"
                                :on-change="changesfzFile"
                                 v-if="scope.row.otherFileAf == '' || scope.row.otherFileAf == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.otherFile)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.otherFile)">下载</div> -->
                                      {{scope.row.otherFile}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadsfzFile" :on-change="changesfzFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="现人脸照片">
                          <template slot-scope="scope">
                            <el-form-item :prop="`updateInfoDtos.${scope.$index}.faceimageAf`" :rules="[{ required: true, message: '请上传简历', trigger: 'blur' }]">
                              <!-- <el-input v-model="scope.row.entranceCard"></el-input> -->
                              <el-upload 
                                class="upload-demo" 
                                action="Fake Action" 
                                :file-list="fileList"
                                :show-file-list="false"
                                :http-request="uploadfacephotoFile"
                                :on-change="changefacephotoFile"
                                 v-if="scope.row.faceimageAf == '' || scope.row.faceimageAf == undefined ? true : false"
                              >
                                <el-button size="small" type="primary">
                                  <i class="el-icon-upload2"></i>上传文件
                                </el-button>
                              </el-upload>
                              <div class="upload_scss" v-else>
                                      <!-- <div @click="openNtkoWindow(scope.row.faceimage)">预览</div> -->
                                      <!-- <div @click="jlDownliad(scope.row.faceimage)">下载</div> -->
                                      {{scope.row.faceimage}}
                                      <el-upload class="upload-demo againUpLoad" action="Fake Action"
                                        :file-list="fileList" :show-file-list="false" :http-request="uploadfacephotoFile" :on-change="changefacephotoFile">
                                        重传
                                      </el-upload>
                                    </div>
                            </el-form-item>
                          </template>
                        </el-table-column>
                        <el-table-column label="操作" width="80" align="center" class="margin0">
                            <template slot-scope="scope">
                                <div class="operation_add">
                                    <el-button @click.native.prevent="deleteRow(scope.$index, formData.updateInfoDtos, scope.row)">
                                        <i class="el-icon-delete"></i>
                                    </el-button>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-form>
      </div>

      <!-- 操作日志 -->
      <!-- <div class="czLog" v-if="isFlag">
        <flow-logs :processInstId="flowItem.processInstanceId" ref="log"></flow-logs>
      </div> -->
      <!-- 操作日志 - 归档 -->
      <!-- <div class="czLog" v-else>
          <flow-logs-gd :processInstId="flowItem.processInstanceId" ref="log"></flow-logs-gd>
      </div> -->
    </div>

    <div class="blank"></div>

    <!-- 底部操作栏 -->
    <div class="nav bottom">
      <el-row>
        <div class="wrapper">
          <div class="wrapper_left">
            <el-col :span="1.5">
              <!-- <button-delete :buttonName="buttonDel" :size="size" v-show="delBtnIf" :fns="delBox"></button-delete> -->
            </el-col>
          </div>
          <div class="wrapper_right">
            <!-- 草稿按钮 -->
            <div class="wrapper_right_bottom_1_zb">
                <el-col :span="1.5">
                  <button-common :buttonName="buttonSave" :size="size" :fns="saveInterview"></button-common>
              </el-col>
            </div>
            <!-- 提交按钮 -->
            <div>
              <el-col :span="1.5">
                <button-orange :buttonName="buttonName" :size="size" :fns="onSubmit"></button-orange>
              </el-col>
            </div>
          </div>
        </div>
      </el-row>
    </div>
    <!-- <div class="nav bottom" style="display:none">
            <approve-bottom :proving="proving" :flowItem="flowItem"></approve-bottom>
    </div> -->
  </div>
</template>
<script>
import common from "@/api/common";
import buttonOrange from '@/components/eleButton/buttonOrange' //引入按钮组件1
import buttonCommon from '@/components/eleButton/buttonCommon' //引入按钮组件2
import { queryUserDept, createFlow } from "@/api/tqrcApplyfor";
import { validMotion, validContract } from "@/api/integrated/wbryzrlc";
import { saveDraft, liuzhuan, upload } from "@/api/integrated/newWbrz";
import { presenceInfo, initFlow } from '@/api/xxbg/index'
import flowLogs from "@/views/plugins/camunda/FlowLogs";
import FlowLogsGd from "@/views/plugins/camunda/FlowLogsGd";
import approveBottom from "@/views/plugins/camunda/approveBottom";


import { isDate } from 'moment';

  export default {
    name: 'NewXxChange',
    components: { buttonOrange, buttonCommon, approveBottom },
    data() {
        return {
          formData:{
            instanceid: null, // 流程ID
            applerDept: '', // 部门编号
            deptName: "", // 部门名称
            applyer: '', // 申请人名称
            updateInfoDtos: [ // 外包人员集合信息
                {
                  id: '',
                  interviewId: "", // 关联面试ID
                  jobId: "", // 工号
                  userName: '', // 姓名
                  vmAccount: '', // 虚拟机账号
                  entranceCard: '', // 门禁卡编号
                  ceneralOfficeMotion: "", // 总办议案
                  newContractId: "", // 合同
                  creditReport: '', // 征信报告
                  sociaiSecrityPaymentProve: '', // 社保缴纳证明/劳动合同/纳税证明
                  confidentialityCommitment: '', // 保密承诺书
                  otherFile: '', // 身份证反面
                  faceimage: '' // 人脸照片
                }
            ]
          },
          buttonName: "提交",
          buttonSave: '草稿',
          size: "mini",
          errorMessage: '', //失败消息提示,
          objectcode: "6000000300",
          objectgrade: "2",
          depData: [],
          disableds: true,
          isComplementOption: [
            {
              id: "0",
              label: '否',
              value: 'N'
            },
            {
              id: "1",
              label: '是',
              value: 'Y'
            }
          ],
          applyData: [], // 信息变更者信息
          currentRow: {}, //当前行
          instanceid: '', // 流程ID
          ceneralOfficeMotion_options: [], // 总办议案
          newContractId_options: [], // 合同
          fileList: [], //上传文件列表
          zxData: [], // 征信文件
          sbData: [], // 社保文件
          bmData: [], // 保密文件
          sfzData: [], // 身份证文件
          facephotoData: [], // 人脸照片文件
          flowItem: {}
        }
    },
    computed: {
    },
    watch: {
    },
    created() {
      this.getapplyerData();
      this.getvalidMotion();
      this.getvalidContract();
      this.flowItem = this.$route.params || {}
    },
    mounted() {
    },

    methods: {
      // 获取外包人员变更信息
      getpresenceInfo() {
        presenceInfo({presenceState: '1'}).then(res => {
          // console.log('获取外包人员变更信息', res)
          if(res.data.status === 200) {
            // // console.log(res.data.results)
            this.applyData = res.data.results;
            // // console.log(this.applyData, 'this.applyData')
          }
        })
      },
      // 获取申请人部门 
      getapplyerData() {
        queryUserDept().then(res => {
          // console.log(res, '获取申请人部门')
          const { message,results, status } = res.data;
          if(status === 200) {
            this.formData.applyer = results.userName;
            this.formData.deptName = results.deptName;
            this.formData.applerDept = results.deptNo;
            // this.getrcInfo()
            this.getpresenceInfo();
          } else {
            this.errorMessage = message;
            thils.saveError()
          }
        })
      },
      // 改变面试者操作
      handleChange(row, index) {
        // // console.log(row, index, '被上天选择的人')
        let arr = JSON.parse(JSON.stringify(this.applyData))
        arr.forEach(item => {
          if(item.userName === row.userName) {
            this.$set(this.formData.updateInfoDtos, index, item)
          }
        })
        // console.log(this.formData.updateInfoDtos[index])
      },
      // 表格行被点击时触发
      rowClick(row) {
        // // console.log('表格行被点击时触发', row)
        this.currentRow = row;
      },
      // 新增遗憾
      eduExperAdd(n) {
        // // console.log(n)
        var list;
        list = {
            id: '',
            interviewId: "", // 关联面试ID
            jobId: "", // 工号
            userName: '', // 姓名
            ceneralOfficeMotion: "", // 总办议案
            newContractId: "", // 合同
            creditReport: '', // 征信报告
            sociaiSecrityPaymentProve: '', // 社保缴纳证明/劳动合同/纳税证明
            confidentialityCommitment: '', // 保密承诺书
            otherFile: '', // 身份证反面
            faceimage: '' // 人脸照片
        }
        this.formData.updateInfoDtos.push(list)
        // // console.log(this.formData.updateInfoDtos)
      },
      // 删除行
      deleteRow(index, rows, row) {
        if(rows.length !== 1 && row.id == '') {
            rows.splice(index, 1);
        } else if(rows.length != 1 && row.id != '') {
            rows.splice(index, 1);
        } else {
            this.errorMessage = '不能少于一行'
            this.saveError();
        }
      },
      // 删除表中数据
      delTableData(index, rows, row) {
        let params = {};
        params.id = row.id;
        let data = common.paramsFormat(params);
      },
      // 上传征信文件
      uploadzxFile(file) {
        // console.log(file)
      },
      // 上传征信文件状态改变
      changezxFile(file, fileList) {
        // // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData('file', file.file);
        formData.append("file", file.raw);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        upload(data).then(res => {
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          if(res.data.status == '200') {
            this.zxData.push({'docid':res.data.results, 'docname': file.name});
            // this.cleanData('creditReport');
            this.currentRow.creditReport = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传社保文件
      uploadsbFile(file) {
        // // console.log(file, '上传社保文件')
      },
      changesbFile(file, fileList) {
        //  // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData('file', file.file);
        formData.append("file", file.raw);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        upload(data).then(res => {
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          if(res.data.status == '200') {
            this.sbData.push({'docid':res.data.results, 'docname': file.name});
            // this.cleanData('creditReport');
            this.currentRow.sociaiSecrityPaymentProve = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传保密承诺书
      uploadbmFile(file) {
        // // console.log(file, '上传社保文件')
      },
      changebmFile(file, fileList) {
        //  // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData('file', file.file);
        formData.append("file", file.raw);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        upload(data).then(res => {
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          if(res.data.status == '200') {
            this.bmData.push({'docid':res.data.results, 'docname': file.name});
            // this.cleanData('creditReport');
            this.currentRow.confidentialityCommitment = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传身份证正反面
      uploadsfzFile(file) {
        // // console.log(file, '上传社保文件')
      },
      changesfzFile(file, fileList) {
         // console.log(file, fileList, '上传文件状态改变')
        // var formData = new FormData('file', file.file);
        formData.append("file", file.raw);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        upload(data).then(res => {
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          if(res.data.status == '200') {
            this.sfzData.push({'docid':res.data.results, 'docname': file.name});
            // this.cleanData('creditReport');
            this.currentRow.otherFile = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // 上传人脸照片
      uploadfacephotoFile(file) {
        // // console.log(file, '上传社保文件')
      },
      changefacephotoFile(file, fileList) {
        //  // console.log(file, fileList, '上传文件状态改变')
        var formData = new FormData('file', file.file);
        formData.append("file", file.raw);
        formData.append("type", "AI");
        formData.append("catalogNo", "CATALOG2014091500001");
        let data = formData;
        upload(data).then(res => {
          //  // console.log(res, 'zxzxzxzxzxzxzxzxzxzxzxzxzxzx')
          if(res.data.status == '200') {
            this.facephotoData.push({'docid':res.data.results, 'docname': file.name});
            // this.cleanData('creditReport');
            this.currentRow.faceimage = res.data.results;
            this.$message.success('上传成功');
          } else {
            this.$message.error('上传失败');
          }
        })
      },
      // ntko
      openNtkoWindow(objectId) {
        var url = window.location.href;
        let ntkoed = ntkoBrowser.ExtensionInstalled();
        if (ntkoed) {
          ntkoBrowser.openWindow(
            url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
          );
        } else {
          window.open(
            url.substring(0, url.indexOf("#")) + "#/ntko/?id=" + objectId
          );
        }
        // 注册回调方法
        window.ntkoCloseEvent = function () {
          alert("跨浏览器插件应用程序窗口已关闭!");
        };
      },
      // 文档下载(改)
      jlDownliad(docId) {
        const self = this;
        common.downloadJl(docId, self);
      },
      // 保存
      saveInterview() {
       const loading = common.loading(this);
        // console.log('onSubmit',this.formData)
        this.formData.updateInfoDtos = this.formData.updateInfoDtos.map(item => {
          delete item.id
          return item;
        })
        this.$refs['formData'].validate(valid => {
          if(valid) {
            initFlow(this.formData).then(res => {
              // console.log(res, '保存')
              loading.close();
              if (res.data.status == "200") {
                this.$nextTick(() => {
                  this.$message.error("保存成功")
                  this.$router.push({ name: 'tqrcApplyfor' });
                })
              } else {
                this.errorMessage = "操作失败";
                this.messageError();
              }
            })
          } else {
            loading.close();
            this.$message.error('保存失败')
            return
          }
        })
      },
      // 提交
      onSubmit() {
        // const loading = common.loading(this);
        this.formData.updateInfoDtos[0].newContractIdAf = this.formData.updateInfoDtos[0].newContractId,
        this.formData.updateInfoDtos[0].ceneralOfficeMotionAf = this.formData.updateInfoDtos[0].ceneralOfficeMotion,
        this.formData.updateInfoDtos[0].creditReportAf = this.formData.updateInfoDtos[0].creditReport,
        this.formData.updateInfoDtos[0].ssPaymentProveAf = this.formData.updateInfoDtos[0].newContractId,
        this.formData.updateInfoDtos[0].confidentCommitmentAf = this.formData.updateInfoDtos[0].sociaiSecrityPaymentProve,
        this.formData.updateInfoDtos[0].otherFileAf = this.formData.updateInfoDtos[0].confidentialityCommitment,
        this.formData.updateInfoDtos[0].faceimageAf = this.formData.updateInfoDtos[0].faceimage,
          // newContractIdAf: item.newContractId,
          // ceneralOfficeMotionAf: item.ceneralOfficeMotion,
          // creditReportAf: item.creditReport,
          // ssPaymentProveAf: item.sociaiSecrityPaymentProve,
          // confidentCommitmentAf: item.confidentialityCommitment,
          // otherFileAf: item.otherFile,
          // faceimageAf: item.faceimage,
          
        
        // console.log('onSubmit',this.formData)
        this.$refs['formData'].validate(valid => {
          if(valid) {
              initFlow(this.formData).then(res => {
              // console.log(res, '提交')
              // loading.close();
              if (res.data.status == "200") {
                let message = common.getFlowMessgeNew(res.data.message);
                let messageArr = message.split(",");
                this.$nextTick(() => {
                  this.messageNotify(messageArr);
              //     // 此处更换路径
                  this.$router.push({ name: 'wbrzManage' });
                })
              } 
            else {
                this.errorMessage = "操作失败";
                this.messageError();
              }
            })
            } 
            else {
            loading.close();
            this.$message.error('提交失败')
            return
          }
        })
      },
      // 关闭
      toClose() {
        this.$router.push('/wbrzManage')
      },
      // 失败提示消息
      saveError() {
        this.$message({
            showClose: true,
            message: this.errorMessage,
            type: 'error'
        })
      },
      //消息提示
      messageNotify(messageVal) {
        let messageSec = messageVal.slice(2).join("，");
        this.$notify({
          title: '提示',
          dangerouslyUseHTMLString: true,
          message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
          duration: 0
        })
      },
      // 总办议案
      getvalidMotion() {
        validMotion({fileType:0}).then(res => {
          // // console.log(res, '总办议案')
          const {results, status} = res.data;
          if(status === 200) {
            this.ceneralOfficeMotion_options = results.map(item => {
              item.label = item.fileName + "("+item.fileNo+")"
              return item;
            })
          }
        })
      },
      // 合同
      getvalidContract() {
        validContract().then(res => {
          // // console.log(res);
          const {results, status} = res.data;
          if(status === 200) {
            this.newContractId_options = results.map(item => {
              item.label = item.contractName + "(" + item.contractNo +")"
              return item
            })
          }
        })
      }
    },
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
    width: 100px;
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

  .upload_scss div {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
    cursor: pointer;
  }

  /* 重传 */
  .againUpLoad {
    font-size: 14px;
    color: rgba(251, 164, 47, 1);
    display: inline-block;
  }

  .wrapper_right_bottom_1_zb {
    margin-right: 10px;
  }
</style>

<style>
  /* 级联选择器 */
  .contentMain_Add .el-cascader {
    display: block;
  }

  /* 下拉框 */
  .contentMain_Add .el-select {
    display: block;
  }

  /* 表格删除按钮 */
  .contentMain_Add .el-icon-delete {
    width: 11px;
    height: 11px;
    color: #f56c6c;
  }

  .operation_add .el-button {
    border: 1px solid #ffffff;
    padding: 0;
  }

  /* table表格修改 */

  .row_2_add .el-table th {
    background: rgba(250, 250, 250, 1);
  }

  .row_2_add .el-table thead {
    font-size: 14px;
    color: rgba(38, 38, 38, 1);
    font-weight: 400;
  }

  .row_2_add .el-table--medium th {
    padding: 0;
  }

  .row_2_add .el-table--medium td {
    padding: 0;
  }


  .row_2_add .el-table th {
    padding: 10px 0;
  }

  /* form表单样式修改 */
  .contentMain_Add .el-form-item {
    margin: 16px 0;
  }

  /* 计数器 */
  .contentMain_Add .el-input-number--medium {
    width: 100px;
  }

  /* 上传文件按钮 */

  .tableCon .el-button--primary {
    color: #000000;
    background-color: #ffffff;
    border-color: rgba(0, 0, 0, 0.15);
  }

  /* 重传 */
  .againUpLoad .el-upload--picture-card:hover {
    color: rgba(251, 164, 47, 1);
  }

  .againUpLoad .el-upload:focus {
    color: rgba(251, 164, 47, 1);
  }
</style>