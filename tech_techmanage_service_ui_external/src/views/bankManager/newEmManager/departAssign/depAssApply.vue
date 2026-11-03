<template>
    <div class="depAss-container">
        <div class="nav">
            <el-row>
                <el-col :span="24">
                    <div class="wrapper">
                        <div class="wrapper_left">
                            <el-col :span="5">
                                <p class="nav_name">新员工部门分配</p>
                            </el-col>
                        </div>
                        <div class="wrapper_right">
                            <el-col :span="1.5">
                                <button-common :buttonName="buttonClose" :size="size" :fns="close"></button-common>
                            </el-col>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </div>
        <div class="depAss-main">
            <el-form inline>
                <div class="depAss-main-head">
                    <div class="head-left">
                        <el-row>
                            <el-col :span="4">
                                <el-form-item>
                                    <el-select placeholder="中心" v-model="paramsForm.center" @change="centerChange">
                                        <el-option v-for="item in centerData" :key="item.centerId" :label="item.centername"
                                            :value="item.centerId"></el-option>
                                        <template slot="prefix">中心：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="5">
                                <el-form-item>
                                    <el-select placeholder="部门" v-model="paramsForm.deptId">
                                        <el-option v-for="item in depData" :key="item.depId" :label="item.depName"
                                            :value="item.depId"></el-option>
                                        <template slot="prefix">部门：</template>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input placeholder="姓名" v-model="paramsForm.name">
                                        <template slot="prefix">姓名：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <el-input placeholder="年份" v-model="paramsForm.year">
                                        <template slot="prefix">年份：</template>
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="4">
                                <el-form-item>
                                    <div class="head-batch">
                                        <el-date-picker v-model="paramsForm.batch" value-format="yyyy-MM" type="month">
                                        </el-date-picker>
                                    </div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="3">
                                <el-form-item>
                                    <div class="head-ID">
                                        <el-input placeholder="ID" v-model="paramsForm.resumeId">
                                            <template slot="prefix">简历ID：</template>
                                        </el-input>
                                    </div>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </div>
                    <div class="head-right">
                        <div class="head-right-clean">
                            <clean-btn :size="size" :forms="this.paramsForm" />
                        </div>
                        <div class="head-right-search">
                            <buttonOrange :buttonName="buttonSearch" :size="size" :fns="submitBtn"></buttonOrange>
                        </div>
                    </div>
                </div>
            </el-form>
            <div class="depAss-main-table">
                <div class="depAss-baob-title">部门分配
                    <span class="mbxz" @click="mbDownload">模板下载</span>
                </div>
                <div class="depAss-main-operation" v-if="operAuth == 1">
                    <el-row type="flex" justify="end" :gutter="70">
                        <el-col :span="1">
                            <button-common :buttonName="buttonAuth" :size="size" :fns="authFn"></button-common>
                        </el-col>
                        <el-col :span="1">
                            <button-common :buttonName="buttonDel" :size="size" :fns="delFn"></button-common>
                        </el-col>
                        <el-col :span="1">
                            <button-common :buttonName="buttonExport" :size="size" :fns="exportFn"></button-common>
                        </el-col>
                        <el-col :span="1">
                            <button-common :buttonName="buttonEnter" :size="size" :fns="enterFn"></button-common>
                        </el-col>
                        <el-col :span="3.5">
                            <button-orange :buttonName="buttonStart" :size="size" :fns="startFn"></button-orange>
                        </el-col>
                    </el-row>
                </div>
                <el-table :data="depAssData.list" height="510" @selection-change="handleSelectionChange">
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column fixed label="序号" width="90">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.sequence" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="姓名" width="100">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.name" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="中心" width="150" prop="">
                        <template slot-scope="scope">
                            <el-select placeholder="" v-model="scope.row.center" @change="tableCenterChange(scope.$index)"
                                :disabled="operAuth != 1">
                                <el-option v-for="item in centerData" :key="item.centerId" :label="item.centername"
                                    :value="item.centerId"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="部门" width="240" prop="">
                        <template slot-scope="scope">
                            <!-- <el-select placeholder="" v-model="scope.row.deptId">
                                <el-option v-for="item in depData" :key="item.depId" :label="item.depName" :value="item.depId"></el-option>
                            </el-select> -->
                            <center-dept :centerId="scope.row.center" :input="scope.row.depName" :tableIndex="scope.$index"
                                @depChange='deptZjChange' :isEdit="isEdit" :depAllDataProp="depAllData"></center-dept>
                        </template>
                    </el-table-column>
                    <el-table-column fixed label="方向" width="130" prop="">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.direction" placeholder="" @change="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1">
                                <el-option v-for="item in dire_options" :key="item.dictid" :label="item.dictname"
                                    :value="item.dictid">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="等级" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.grade" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="简历ID" width="90" prop="resumeId"></el-table-column>
                    <el-table-column label="性别" width="90" prop="">
                        <template slot-scope="scope">
                            <el-select v-model="scope.row.sex" placeholder="" @change="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1">
                                <el-option v-for="item in sex_options" :key="item.dictid" :label="item.dictname" :value="item.dictid">
                                </el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="总分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.totalScore" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="技术面试成绩" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.technicalScore" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="技术面试记分" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.technicalScoring" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="技术面试评语" width="600" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.technicalComments"
                                @blur="tableDataChange(scope.$index)" :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="行政面试成绩" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.administrativeScore" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="行政面试记分" width="105" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.administrativeScoring" @blur="tableDataChange(scope.$index)"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="行政面试评语" width="600" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.administrativeComments"
                                @blur="tableDataChange(scope.$index)" :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="培训成绩" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.trainingScore" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="培训记分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.trainingScoring" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="笔试成绩" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.writtenScore" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="笔试记分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.writtenScoring" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="其他成绩" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.otherScore" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="其他记分" width="90" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.otherScoring" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="备注" width="300" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.note" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="出生日期" width="160" prop="">
                        <template slot-scope="scope">
                            <el-date-picker v-model="scope.row.bornDate" value-format="yyyy-MM-dd" @change="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1">
                            </el-date-picker>
                        </template>
                    </el-table-column>
                    <el-table-column label="籍贯" width="300" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" type="textarea" autosize v-model="scope.row.natives" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制最高学历" width="120" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.highest" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制本科院校" width="120" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.coursesCollege" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制本科专业" width="160" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.coursesProfessional" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制硕士院校" width="120" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.mastersCollege" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="全日制硕士专业" width="160" prop="">
                        <template slot-scope="scope">
                            <el-input placeholder="" v-model="scope.row.degreeProfessional" @blur="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1"></el-input>
                        </template>
                    </el-table-column>
                    <el-table-column label="批次" width="135" prop="">
                        <template slot-scope="scope">
                            <el-date-picker v-model="scope.row.batch" value-format="yyyy-MM" type="month" @change="tableDataChange(scope.$index)"
                                :disabled="operAuth != 1">
                            </el-date-picker>
                        </template>
                    </el-table-column>
                </el-table>

                <div class="pager_zb">
                    <pager :fns="getPager" :total="totalNumber" :pageSize="pageSize" v-model="$attrs" ref="pager" />
                </div>
            </div>
        </div>
        <el-dialog title="选择批次和上传文件" :visible.sync="ifShowExDialog">
            <div>
                <el-date-picker v-model="batchNo" type="month"></el-date-picker>
                <div class="upload-batch">
                    <el-upload action="Fake Action" accept=".xlsx, .xls" :file-list="drList" :show-file-list="true"
                        :http-request="changeBatchFile" :on-change="handleChange" :on-remove="handleRemove">
                        <el-button size="small" type="primary">
                            <i class="el-icon-upload2"></i>
                            <span>{{drList.length == 0 ? '上传文件' : '重新上传'}}</span>
                        </el-button>
                    </el-upload>
                </div>

                <div class="dia-bottom">
                    <el-row :gutter="10" type="flex" justify="end">
                        <el-col :span="1.5">
                            <button-common :buttonName="buttonCancel" :size="size" :fns="cancelSel"></button-common>
                        </el-col>
                        <el-col :span="1.5">
                            <button-orange :buttonName="buttonSure" :size="size" :fns="sureSel"></button-orange>
                        </el-col>
                    </el-row>
                </div>
            </div>
        </el-dialog>
        <el-dialog title="" :visible.sync="ifShowYlDialog" class="ylDialog">
            <div class="dia-table">
                <el-form ref="ruleForm">
                    <div class="dia-table-db">
                        <el-form-item label="待办人：" prop="">
                            <el-cascader ref="cascAppro" :options="kejiBankList" :props="bankParams" v-model="daBank"
                                :show-all-levels="false" filterable @change="dbPeoChange"></el-cascader>
                        </el-form-item>
                    </div>
                    <div class="dia-table-db">
                        <el-form-item label="展示字段：" prop="">
                            <div>
                                <el-checkbox label="序号" v-model="tagShowData.index" disabled></el-checkbox>
                                <el-checkbox label="姓名" v-model="tagShowData.name" disabled></el-checkbox>
                                <el-checkbox label="中心" v-model="tagShowData.center" disabled></el-checkbox>
                                <el-checkbox label="部门" v-model="tagShowData.dept" disabled></el-checkbox>
                                <el-checkbox label="方向" v-model="tagShowData.direct" disabled></el-checkbox>
                                <el-checkbox label="等级" v-model="tagShowData.grade"></el-checkbox>
                                <el-checkbox label="简历ID" v-model="tagShowData.jlId"></el-checkbox>
                                <el-checkbox label="性别" v-model="tagShowData.sex"></el-checkbox>
                                <el-checkbox label="总分" v-model="tagShowData.toScore"></el-checkbox>
                                <el-checkbox label="技术面试成绩" v-model="tagShowData.techInteAch"></el-checkbox>
                                <el-checkbox label="技术面试记分" v-model="tagShowData.techInteScore"></el-checkbox>
                                <el-checkbox label="技术面试评语" v-model="tagShowData.techRemark"></el-checkbox>
                                <el-checkbox label="行政面试成绩" v-model="tagShowData.xzAch"></el-checkbox>
                                <el-checkbox label="行政面试记分" v-model="tagShowData.xzScore"></el-checkbox>
                                <el-checkbox label="行政面试评语" v-model="tagShowData.xzRemark"></el-checkbox>
                                <el-checkbox label="培训成绩" v-model="tagShowData.pxAch"></el-checkbox>
                                <el-checkbox label="培训记分" v-model="tagShowData.pxScore"></el-checkbox>
                                <el-checkbox label="笔试成绩" v-model="tagShowData.bsAch"></el-checkbox>
                                <el-checkbox label="笔试记分" v-model="tagShowData.bsScore"></el-checkbox>
                                <el-checkbox label="其他成绩" v-model="tagShowData.otherAch"></el-checkbox>
                                <el-checkbox label="其他记分" v-model="tagShowData.otherScore"></el-checkbox>
                                <el-checkbox label="备注" v-model="tagShowData.remark"></el-checkbox>
                                <el-checkbox label="出生日期" v-model="tagShowData.birth"></el-checkbox>
                                <el-checkbox label="籍贯" v-model="tagShowData.jiguan"></el-checkbox>
                                <el-checkbox label="全日制最高学历" v-model="tagShowData.higeestEdu"></el-checkbox>
                                <el-checkbox label="全日制本科院校" v-model="tagShowData.bkSchool"></el-checkbox>
                                <el-checkbox label="全日制本科专业" v-model="tagShowData.bkProfess"></el-checkbox>
                                <el-checkbox label="全日制硕士院校" v-model="tagShowData.ssSchool"></el-checkbox>
                                <el-checkbox label="全日制硕士专业" v-model="tagShowData.ssProfess"></el-checkbox>
                                <span class="ylButt" @click="ylTable">预览</span>
                            </div>
                        </el-form-item>
                    </div>
                    <div class="dia-table-ylcon" v-if="ifShowYl">
                        <el-table :data="depAssData.list" height="510" @selection-change="ylTableChange">
                            <el-table-column type="selection" width="55"></el-table-column>
                            <el-table-column fixed label="序号" width="60" prop="sequence" v-if="tagShowData.index"></el-table-column>
                            <el-table-column fixed label="姓名" prop="name" width="90"></el-table-column>
                            <el-table-column fixed label="中心" width="150" prop="">
                                <template slot-scope="scope">
                                    <el-select placeholder="" v-model="scope.row.center" :disabled="true">
                                        <el-option v-for="item in centerData" :key="item.centerId" :label="item.centername"
                                            :value="item.centerId"></el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column fixed label="部门" width="150" prop="depName"></el-table-column>
                            <el-table-column fixed label="方向" width="120" prop="">
                                <template slot-scope="scope">
                                    <el-select v-model="scope.row.direction" placeholder="" :disabled="true">
                                        <el-option v-for="item in dire_options" :key="item.dictid" :label="item.dictname"
                                            :value="item.dictid">
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column label="等级" width="90" prop="grade" v-if="tagShowData.grade"></el-table-column>
                            <el-table-column label="简历ID" width="90" prop="resumeId" v-if="tagShowData.jlId"></el-table-column>
                            <el-table-column label="性别" width="90" prop="" v-if="tagShowData.sex">
                                <template slot-scope="scope">
                                    <el-select v-model="scope.row.sex" placeholder="" :disabled="true">
                                        <el-option v-for="item in sex_options" :key="item.dictid" :label="item.dictname"
                                            :value="item.dictid">
                                        </el-option>
                                    </el-select>
                                </template>
                            </el-table-column>
                            <el-table-column label="总分" width="90" prop="totalScore" v-if="tagShowData.toScore"></el-table-column>
                            <el-table-column label="技术面试成绩" width="105" prop="technicalScore" v-if="tagShowData.techInteAch"></el-table-column>
                            <el-table-column label="技术面试记分" width="105" prop="technicalScoring" v-if="tagShowData.techInteScore">
                            </el-table-column>
                            <el-table-column label="技术面试评语" width="105" prop="technicalComments" v-if="tagShowData.techRemark"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="行政面试成绩" width="105" prop="administrativeScore" v-if="tagShowData.xzAch"></el-table-column>
                            <el-table-column label="行政面试记分" width="105" prop="administrativeScoring" v-if="tagShowData.xzScore"></el-table-column>
                            <el-table-column label="行政面试评语" width="105" prop="administrativeComments" v-if="tagShowData.xzRemark"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="培训成绩" width="90" prop="trainingScore" v-if="tagShowData.pxAch"></el-table-column>
                            <el-table-column label="培训记分" width="90" prop="trainingScoring" v-if="tagShowData.pxScore"></el-table-column>
                            <el-table-column label="笔试成绩" width="90" prop="writtenScore" v-if="tagShowData.bsAch"></el-table-column>
                            <el-table-column label="笔试记分" width="90" prop="writtenScoring" v-if="tagShowData.bsScore"></el-table-column>
                            <el-table-column label="其他成绩" width="90" prop="otherScore" v-if="tagShowData.otherAch"></el-table-column>
                            <el-table-column label="其他记分" width="90" prop="otherScoring" v-if="tagShowData.otherScore"></el-table-column>
                            <el-table-column label="备注" width="90" prop="note" v-if="tagShowData.remark"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="出生日期" width="120" prop="bornDate" v-if="tagShowData.birth"></el-table-column>
                            <el-table-column label="籍贯" width="90" prop="natives" v-if="tagShowData.jiguan"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="全日制最高学历" width="120" prop="highest" v-if="tagShowData.higeestEdu"></el-table-column>
                            <el-table-column label="全日制本科院校" width="120" prop="coursesCollege" v-if="tagShowData.bkSchool"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="全日制本科专业" width="120" prop="coursesProfessional" v-if="tagShowData.bkProfess"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="全日制硕士院校" width="120" prop="mastersCollege" v-if="tagShowData.ssSchool"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="全日制硕士专业" width="120" prop="degreeProfessional" v-if="tagShowData.ssProfess"
                                :show-overflow-tooltip="true"></el-table-column>
                            <el-table-column label="批次" width="120" prop="batch" v-if="tagShowData.batch"></el-table-column>
                        </el-table>
                        <div class="dia-bottom">
                            <el-row :gutter="10" type="flex" justify="end">
                                <el-col :span="1.5">
                                    <button-common :buttonName="ylCloseNa" :size="size" :fns="closeSel"></button-common>
                                </el-col>
                                <el-col :span="1.5">
                                    <button-orange :buttonName="ylStartNa" :size="size" :fns="startSel"></button-orange>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </el-form>
            </div>
        </el-dialog>
        <el-dialog title="权限管理" :visible.sync="ifShowAuthDialog" class="bmfpAuthChioce">
            <div>
                <el-table :data="centerAuthData" height="400">
                    <el-table-column label="中心" width="260" prop="" :disabled="true">
                        <template slot-scope="scope">
                            <el-select placeholder="" v-model="scope.row.centerId" :disabled="true">
                                <el-option v-for="item in centerData" :key="item.centerId" :label="item.centername"
                                    :value="item.centerId"></el-option>
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column label="姓名" prop="userName">
                        <template slot-scope="scope">
                            <morekeji-bank-bmfp :input="scope.row.userName" :tableIndex="scope.$index" @choiceBank="choiceBank"
                                :fxTree="scope.row.userId" :zjData="kejiBankList"></morekeji-bank-bmfp>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="qxgl-bottom">
                    <button-orange :buttonName="qxgxName" :size="size" :fns="qxUpdate"></button-orange>
                </div>
            </div>
        </el-dialog>
    </div>
</template>
<script>
    import buttonOrange from '@/components/eleButton/buttonOrange'
    import buttonCommon from '@/components/eleButton/buttonCommon'
    import buttonDelete from '@/components/eleButton/delete'
    import cleanBtn from "@/components/CleanBtn"
    import { getDicEntry } from "@/api/eosDictEntry";
    import { getAuthList, getDownloadUrl } from '@/api/common/flowApi';
    import { importExcel, queryPager, queryDeptList, queryList, update, liuzhuan, exportExcel, queryCenter, getOperatAuth, getViewAuth, authUpdate, delRow, baobiaoImport, baobiaoPager } from "@/api/integrated/newEmBmfp";
    import Moment from 'moment';
    import common from "@/api/common";
    import pager from "@/components/Pager";
    import centerDept from "@/views/bankManager/newEmManager/departAssign/centerDeptDialog";
    import { mapGetters } from "vuex";
    import morekejiBankBmfp from "@/views/bankManager/newEmManager/departAssign/morekejiBankBmfp"
    import { queryEmployeeTree, queryResourceDept } from "@/api/integrated/wbryzrlc";
    export default {
        components: { buttonOrange, buttonCommon, buttonDelete, cleanBtn, pager, centerDept, morekejiBankBmfp },
        name: 'depAssApply',
        data() {
            return {
                size: 'mini',  //按钮大小
                buttonClose: '关 闭', //关闭按钮名字
                buttonSearch: '查 询', //查询按钮
                buttonDel: '删 除',
                buttonExport: '导 出',
                buttonEnter: '导 入',
                buttonCancel: '取 消',
                buttonSure: '确 定',
                buttonStart: '发起部门分配',
                ylCloseNa: '关 闭',
                ylStartNa: '发 起',
                buttonAuth: '权 限',
                buttonAdd: '添 加',
                qxgxName: '更 新',
                paramsForm: {
                    center: null,
                    deptId: null,
                    name: null,
                    year: null,
                    batch: null,
                    resumeId: null,
                },
                depData: [],
                depAssData: {
                    list: []
                },
                sex_options: [], // 性别数据集合
                dire_options: [], //方向集合
                ifShowExDialog: false, //是否显示导出弹窗
                ifShowYlDialog: false, //是否显示预览弹窗
                kejiBankList: [], //科技部行员
                bankParams: {  //配置信息
                    label: 'text',
                    value: 'pcode',
                    children: 'children',
                    emitPath: false
                },
                tagShowData: {
                    index: true,
                    name: true,
                    center: true,
                    dept: true,
                    direct: true,
                    grade: true,
                    jlId: true,
                    sex: true,
                    toScore: true,
                    techInteAch: true,
                    techInteScore: true,
                    techRemark: true,
                    xzAch: true,
                    xzScore: true,
                    xzRemark: true,
                    pxAch: true,
                    pxScore: true,
                    bsAch: true,
                    bsScore: true,
                    otherAch: true,
                    otherScore: true,
                    remark: true,
                    birth: true,
                    jiguan: true,
                    higeestEdu: true,
                    bkSchool: true,
                    bkProfess: true,
                    ssSchool: true,
                    ssProfess: true,
                    batch: true
                },
                batchNo: '', //批次
                selectRow: [], //表格选中行
                batchFile: {}, //导入文件信息
                drList: [], //导入列表
                centerData: [], //中心数据集合
                totalNumber: 0, //分页总条数
                pageSize: 50, // 初始值 默认：50
                index: 1, // 初始值 默认：1
                daBank: '', //待办人
                resumeIds: '', //发起的简历ID
                ifShowYl: false, //是否显示预览表格
                operAuth: 0, //是否有操作权限
                ifShowAuthDialog: false, //是否显示权限弹窗
                authList: [], //权限列表
                authChoice: {
                    centerId: '', //权限中心
                    userName: '', //增加权限人名字
                    userId: '', //增加权限人ID
                },
                centerAuthData: [], //中心权限数据
                isEdit: false,
                depAllData: [], //所有部门
            }
        },
        computed: {
            ...mapGetters(["name", "userId"])
        },
        mounted() {
            this.searchUpLoad();
            this.getKjList();
            this.getDic();
            this.getCenter();
            this.getOpeAuth();
            this.getAllDep();
        },
        methods: {
            init() {
                let date = new Date();
                this.paramsForm.year = date.getFullYear();
            },

            // 查询业务字典
            getDic() {
                getDicEntry({
                    dicttypeid: "BUSI_OUTSOURCE_GENFER,NEWKG_DIRECTION"
                })
                    .then(res => {
                        if (res.data.status !== 200) {
                            this.$alert(res.data.message, { type: "error" });
                            return false;
                        }
                        let data = res.data.results;
                        // 性别
                        this.sex_options = data.filter(item => {
                            return item.dicttypeid == "BUSI_OUTSOURCE_GENFER";
                        });
                        // 方向
                        this.dire_options = data.filter(item => {
                            return item.dicttypeid == "NEWKG_DIRECTION";
                        });
                        return data;
                    });
            },

            // 获取部门
            getDep(id) {
                const loading = common.loading(this);
                queryDeptList({ centerId: id })
                    .then(res => {
                        loading.close();
                        console.log("获取部门", res.data)
                        if (res.data.status == 200) {
                            var fild = res.data.results;
                            this.depData = fild.filter(tt => {
                                tt.depName = tt.depName.replace('总行金融科技部-', "");
                                return tt;
                            });
                        }
                    })
                    .catch(error => {
                        this.$message.error(error)
                    });
            },

            // 获取中心
            getCenter() {
                queryList().then(res => {
                    console.log('中心', res.data)
                    if (res.data.status == 200) {
                        // this.centerData = res.data.results.filter(li => {
                        //     if (li.id != 2) {
                        //         return li
                        //     }
                        // });
                        this.centerAuthData = this.centerData = res.data.results.filter(li => {
                            if (li.id != 2) {
                                li.centerId = li.id;
                                li.id = null;
                                return li
                            }
                        });
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 中心改变
            centerChange(val) {
                console.log('中心改变', val)
                this.paramsForm.deptId = '';
                this.getDep(val);
            },

            // 表格中中心改变
            tableCenterChange(index) {
                if (this.depAssData.list[index].depName != undefined) {
                    this.depAssData.list[index].depName = '';
                }
                if (this.depAssData.list[index].deptId != undefined) {
                    this.depAssData.list[index].deptId = -1;
                }
                let bmfpData = this.depAssData.list[index];
                let data = common.paramsFormat(bmfpData);
                update(data).then(res => {
                    console.log('更新中心', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');
                    } else {
                        this.$message.error('更新失败');
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 表格中数据改变
            tableDataChange(index) {
                let bmfpData = this.depAssData.list[index];
                let data = common.paramsFormat(bmfpData);
                update(data).then(res => {
                    console.log('更新中心', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');
                    } else {
                        this.$message.error('更新失败');
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 查中心
            searchCenter(val, index) {
                queryCenter({ depId: val }).then(res => {
                    console.log('中心', res.data)
                    if (res.data.status == 200) {
                        this.depAssData.list[index].center = res.data.results.id;
                        this.depAssData.list.splice(0, 0);
                        this.$nextTick(() => {
                            this.tableDataChange(index);
                        })
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 部门变化
            deptZjChange(val, index) {
                let dep;
                if (val.depId != undefined) {
                    dep = val.depId.replace('dep', '');
                    this.depAssData.list[index].depName = val.depName;
                } else {
                    dep = val.id.replace('dep', '');
                    this.depAssData.list[index].depName = val.text;
                }
                this.searchCenter(dep, index);
                this.depAssData.list[index].deptId = dep;
                let bmfpData = this.depAssData.list[index];
                let data = common.paramsFormat(bmfpData);
                update(data).then(res => {
                    console.log('更新中心', res.data)
                    if (res.data.status == 200) {
                        this.$message.success('更新成功');
                    } else {
                        this.$message.error('更新失败');
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            //获取科技部行员列表
            getKjList() {
                let params = '';
                params = "7004"; //130是总行科技部部门编号
                queryEmployeeTree(params).then(res => {
                    if (res.data.status == 200) {
                        this.kejiBankList = res.data.results[0].children.filter(li => {
                            li.text = li.text.replace('总行金融科技部-', '');
                            return li;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error);
                });

                this.dialogEmpower = true;
            },

            // 表格选中变化
            handleSelectionChange(val) {
                console.log("分页表格选中", val)
                this.selectRow = val;
            },

            // 关闭按钮
            close() {
                this.$router.push({ name: 'dashboard' });
            },

            // 查询
            submitBtn() {
                let params = {};
                params.center = this.paramsForm.center;
                params.deptId = this.paramsForm.deptId;
                params.name = this.paramsForm.name;
                params.year = this.paramsForm.year;
                params.batch = this.paramsForm.batch;
                params.resumeId = this.paramsForm.resumeId;
                this.searchUpLoad(params);
            },

            // 删除
            delFn() {
                let jlIdAr = [];
                this.selectRow.forEach(li => {
                    jlIdAr.push(li.resumeId)
                })
                let jlIdStr = jlIdAr.join(',');
                delRow({ resumeIds: jlIdStr }).then(res => {
                    console.log("删除", res.data)
                    if (res.data.status == 200) {
                        this.$message.success('删除成功');
                        for (var i = 0; i < this.selectRow.length; i++) {
                            for (var j = 0; j < this.depAssData.list.length; j++) {
                                if (this.depAssData.list[j].id == this.selectRow[i].id) {
                                    this.depAssData.list.splice(j, 1)
                                }
                            }
                        }
                    } else {
                        this.$message.error('删除失败')
                    }
                }).catch(error => {
                    this.$message.error(error);
                });

            },

            // 导出
            exportFn() {
                let searchData = {};
                let jlIdAr = [];
                this.selectRow.forEach(li => {
                    jlIdAr.push(li.resumeId)
                })
                let jlIdStr = jlIdAr.join(',');
                searchData.center = this.paramsForm.center;
                searchData.deptId = this.paramsForm.deptId;
                searchData.name = this.paramsForm.name;
                searchData.year = this.paramsForm.year;
                searchData.batch = this.paramsForm.batch;
                // searchData.resumeId = this.paramsForm.resumeId;
                searchData.resumeIds = jlIdStr;
                let params = common.paramsFormat(searchData);
                const self = this;
                exportExcel(params).then(res => {
                    console.log("导出", res.data)
                    common.download(res.data, self, "新员工部门分配");
                });
            },

            // 导入
            enterFn() {
                this.drList = [];
                this.batchNo = '';
                this.batchFile = {};
                this.ifShowExDialog = true;
            },

            // 取消导入
            cancelSel() {
                this.ifShowExDialog = false;
            },

            // 确认导入
            sureSel() {
                if (this.batchNo == '') {
                    this.$message.error('请选择批次');
                    return
                } else if (this.drList.length == 0) {
                    this.$message.error('请上传文件');
                    return
                } else {
                    const loading = common.loading(this);
                    let batchNo = Moment(this.batchNo).format('YYYY-MM');
                    var formData = new FormData();
                    formData.append("batchNo", batchNo);
                    formData.append("file", this.batchFile);
                    importExcel(formData).then(res => {
                        loading.close();
                        console.log("确认导入", res.data)
                        if (res.data.status == 200) {
                            this.$message.success('导入成功');
                            this.ifShowExDialog = false;
                            this.searchUpLoad();
                        } else {
                            this.$message.error('导入失败')
                        }
                    }).catch(error => {
                        this.$message.error(error)
                    })
                }
            },

            // 查询上传数据
            searchUpLoad(val) {
                let param = common.paramsFormat(val);
                const loading = common.loading(this);
                queryPager(param).then(res => {
                    console.log("查询上传数据", res.data)
                    loading.close();
                    if (res.data.status == 200) {
                        this.totalNumber = res.data.total;
                        res.data.results = res.data.results.filter(li => {
                            if (li.depName != undefined) {
                                li.depName = li.depName.replace('总行金融科技部-', '');
                            }
                            li.bornDate = Moment(li.bornDate).format('YYYY-MM-DD');
                            return li
                        })

                        this.depAssData.list = res.data.results;
                    } else {
                        this.$message.error(res.data.message)
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 分页
            getPager: function (size, index) {
                let params = {};
                params.center = this.paramsForm.center;
                params.deptId = this.paramsForm.deptId;
                params.name = this.paramsForm.name;
                params.year = this.paramsForm.year;
                params.batch = this.paramsForm.batch;
                params.resumeId = this.paramsForm.resumeId;
                params.size = size;
                params.index = index;
                this.pageSize = size;
                this.index = index;
                this.searchUpLoad(params);
            },

            // 上传批次变化
            changeBatchFile(file) {
                console.log('上传批次变化', file, this.drList)
                this.batchFile = file.file;
            },

            // on-change
            handleChange(file, fileList) {
                console.log('on-change', fileList);
                this.drList = [fileList[fileList.length - 1]];
            },

            // on-remove
            handleRemove() {
                this.drList = [];
            },

            // 代办人变化
            dbPeoChange(val) {
                console.log('代办人变化', val)
            },

            // 预览表格
            ylTable() {
                this.ifShowYl = true
            },

            // 发起部门分配
            startFn() {
                this.ifShowYlDialog = true;
            },

            // 关闭预览弹窗
            closeSel() {
                this.ifShowYlDialog = false;
            },

            // 预览表格选中变化
            ylTableChange(val) {
                let resumeIdArr = [];
                for (var i = 0; i < val.length; i++) {
                    resumeIdArr.push(val[i].resumeId)
                }
                this.resumeIds = resumeIdArr.join(',');
            },

            // 预览后发起
            startSel() {
                if (this.daBank == '') {
                    this.$message.error('请选择待办人');
                    return
                }
                if (this.resumeIds == '') {
                    this.$message.error('请选择要分配的人员');
                    return
                }
                let params = {};
                params.initiateId = this.userId;
                params.initiateName = this.name;
                params.upcomBadge = this.daBank;
                params.resumeIds = this.resumeIds;
                const loading = common.loading(this);
                liuzhuan(params).then(res => {
                    loading.close();
                    console.log('流程发起', res.data)
                    if (res.data.status == 200) {
                        let message = common.getFlowMessge(res.data.message);
                        let messageArr = message.split(",");
                        messageArr[2] = messageArr[2].split(")")[0] + ')';
                        this.messageNotifyMore(messageArr);
                        this.$router.push({ name: 'dashboard' });
                    } else {
                        this.$message.error('操作失败');
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 获取操作权限
            getOpeAuth() {
                getOperatAuth().then(res => {
                    if (res.data.status == 200) {
                        this.operAuth = res.data.results;
                        if (res.data.results == 1) {
                            this.isEdit = true;
                            this.authDetail();
                        } else {
                            this.isEdit = false;
                        }
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 获取权限详情
            authDetail() {
                getViewAuth().then(res => {
                    console.log('权限详情', res.data)
                    if (res.data.status == 200) {
                        let seft = this
                        this.authList = res.data.results;
                        this.centerAuthData.forEach((v, k) => {
                            seft.authList.forEach((i, j) => {
                                if (v.centerId == i.centerId) {
                                    Object.assign(v, i);
                                }
                            })
                        })
                    }
                }).catch(error => {
                    this.$message.error(error)
                })
            },

            // 权限更新
            qxUpdate() {
                console.log('更新', this.centerAuthData)
                let lsData = [];
                this.centerAuthData.forEach(li => {
                    lsData.push(li)
                })
                const loading = common.loading(this);
                authUpdate(lsData).then(res => {
                    loading.close();
                    console.log('权限更新', res.data);
                    if (res.data.status == 200) {
                        this.authDetail();
                        this.$message.success('更新成功');
                        this.ifShowAuthDialog = false;
                    } else {
                        this.$message.error('更新失败')
                    }

                })
            },

            // 权限人员改变
            authPeoChange() {
                setTimeout(() => {
                    this.authChoice.userName = this.$refs['cascAuth'].getCheckedNodes()[0].label;
                }, 0)
            },

            // 权限
            authFn() {
                this.ifShowAuthDialog = true;
            },

            // 权限选择人员
            choiceBank(val, index) {
                console.log('权限选择人员', val, index)
                let id = [];
                let name = [];
                for (var i = 0; i < val.length; i++) {
                    id.push(val[i].pcode);
                    name.push(val[i].text)
                }
                this.centerAuthData[index].userId = id.join(',');
                this.centerAuthData[index].userName = name.join(',');
                // if (this.centerAuthData[index].userId == undefined) {
                //     this.centerAuthData[index].userId = id.join(',');
                // } else {
                //     this.centerAuthData[index].userId = id.join(',') + ',' + this.centerAuthData[index].userId;
                // }
                // if (this.centerAuthData[index].userName == undefined) {
                //     this.centerAuthData[index].userName = name.join(',');
                // } else {
                //     this.centerAuthData[index].userName = name.join(',') + ',' + this.centerAuthData[index].userName;
                // }
                this.centerAuthData.splice(0, 0)
            },

            // 消息提示(多节点)
            messageNotifyMore(messageVal) {
                if (messageVal.length > 3) {
                    messageVal[messageVal.length - 1] = messageVal[messageVal.length - 1].split(")")[0] + ')';
                }
                let messageSec = messageVal.slice(2).join("，");
                this.$notify({
                    title: '提示',
                    dangerouslyUseHTMLString: true,
                    message: '<div><div>' + messageVal[1] + '</div><div>' + messageSec + '</div></div>',
                    duration: 0
                })
            },

            // 模板下载
            mbDownload(val) {
                const loading = common.loading(this);
                getDownloadUrl({ key: 'PPM_ORIENTATION' }).then(res => {
                    loading.close();
                    if (res.data.status == 200) {
                        const self = this;
                        common.downloadJl(res.data.results.documentno, self);
                    } else {
                        this.$message.error("获取下载地址失败");
                    }
                })
            },

            // 获取所有部门
            getAllDep() {
                let params = {};
                params.objectcode = "6000000300";
                params.objectgrade = "2";
                queryResourceDept(params).then(res => {
                    if (res.data.status == 200) {
                        this.depAllData = res.data.results[0].children.filter(tt => {
                            tt.text = tt.text.replace('总行金融科技部-', "");
                            return tt;
                        });
                    }
                }).catch(error => {
                    this.$message.error(error);
                });
            },
        }

    }
</script>
<style scoped>
    .depAss-main {
        padding: 17px;
    }

    .depAss-main-head {
        border-radius: 4px;
        background: #FFFFFF;
        padding: 15px 0 15px 15px;
        margin-bottom: 15px;
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        display: flex;
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        -webkit-justify-content: space-between;
        justify-content: space-between;
    }

    .head-left {
        flex: 1;
    }

    .head-right {
        display: -webkit-box;
        display: -ms-flexbox;
        display: -webkit-flex;
        padding-right: 15px;
        width: 145px;
        display: flex;
    }

    .head-right-clean {
        flex: 1;
    }

    .head-right-search {
        padding-left: 10px;
        flex: 1;
    }

    .nav {
        width: 100%;
        background-color: #ffffff;
    }

    .nav .nav_name {
        width: 150px;
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

    .wrapper-right-keep {
        margin-right: 10px;
    }

    .depAss-main-table {
        border-radius: 4px;
        padding: 18px;
        background-color: #ffffff;
    }

    .depAss-main-operation {
        margin-bottom: 15px;
    }

    .dia-bottom {
        margin-top: 30px;
    }

    .ylButt {
        color: red;
        text-decoration: underline;
        margin-left: 10px;
        cursor: pointer;
    }

    .upload-batch {
        margin-top: 20px;
    }

    .pager_zb {
        text-align: center;
    }

    .qxgl-bottom {
        margin-top: 15px;
        text-align: right;
    }

    .depAss-baob {
        padding: 0 17px 17px 17px;
    }

    .depAss-baob-title {
        font-size: 16px;
        font-weight: 600;
        margin-bottom: 5px;
    }

    .depAss-baob-operation {
        text-align: right;
        margin-bottom: 15px;
    }

    .mbxz {
        color: rgba(251, 164, 47, 1);
        font-size: 12px;
        cursor: pointer;
        font-weight: 500;
        font-family: sans-serif;
    }
</style>
<style>
    /* el-input修改 */
    .depAss-main-head .el-input--prefix .el-input__inner {
        padding-left: 50px;
    }

    .depAss-main-head .el-input__prefix {
        left: 8px;
        color: #262626;
        font-size: 14px;
    }

    /* element表单修改 */
    .depAss-main-head .el-form-item {
        margin-bottom: 0px;
    }

    .head-batch .el-input__icon {
        color: #262626;
        font-size: 14px;
        width: 100%;
    }

    .depAss-main-head .head-ID .el-input--prefix .el-input__inner {
        padding-left: 60px;
    }

    .head-batch .el-icon-date:before {
        content: '批次：';
    }

    .ylDialog .el-dialog {
        width: 90%;
    }


    .bmfpAuthChioce .el-dialog {
        width: 90%;
    }

    .upload-batch .el-button--primary {
        color: #000000;
        background-color: #ffffff;
        border-color: rgba(0, 0, 0, 0.15);
    }
</style>