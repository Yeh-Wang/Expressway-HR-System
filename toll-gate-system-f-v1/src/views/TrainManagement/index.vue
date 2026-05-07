<template>
  <!-- 添加或编辑培训计划对话框-->
  <el-dialog v-model="saveOrUpdateFormVisible" width="25%">
    <el-form :model="saveOrUpdateForm" label-width="80px">
      <el-form-item label="培训主题">
        <el-input v-model="saveOrUpdateForm.traTheme"/>
      </el-form-item>
      <el-form-item label="培训状态">
        <el-select v-model="saveOrUpdateForm.traStatus" placeholder="请选择">
          <el-option label="未开始" value="未开始"/>
          <el-option label="进行中" value="进行中"/>
          <el-option label="已结束" value="已结束"/>
        </el-select>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-date-picker
            v-model="saveOrUpdateForm.traStartTime"
            type="datetime"
            placeholder="选择日期时间"
            value-format="YYYY-MM-DD HH:mm:ss"
            format="YYYY-MM-DD HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-date-picker
            v-model="saveOrUpdateForm.traEndTime"
            type="datetime"
            placeholder="选择日期时间"
            value-format="YYYY-MM-DD HH:mm:ss"
            format="YYYY-MM-DD HH:mm:ss"
        >
        </el-date-picker>
      </el-form-item>
      <el-form-item label="培训方式">
        <el-select v-model="saveOrUpdateForm.traType" placeholder="请选择">
          <el-option label="线上培训" value="线上培训"/>
          <el-option label="线下培训" value="线下培训"/>
        </el-select>
      </el-form-item>
    </el-form>
    <div slot="footer" align="center" class="dialog-footer">
      <el-button @click="saveOrUpdateFormVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitSaveOrUpdate">确 定</el-button>
    </div>
  </el-dialog>
  <!--  展示培训计划列表-->
  <div v-if="showTrainVisible">
    <el-row >
      <el-col :span="2"/>
      <el-col :span="21">
        <el-space wrap :size="20">
          <el-card v-for="item in trainInfoList" class="box-card" style="height: 300px;width: 300px">
            <div slot="header" class="clearfix">
              <el-row>
                <el-col :span="10">
                  {{ item.traTheme }}
                </el-col>
                <el-col :span="8">
                  {{ item.traStatus }}
                </el-col>
                <el-col :span="6">
                  <el-row>
                    <el-col :span="12">
                      <el-tooltip effect="dark" content="编辑培训计划" placement="top">
                        <el-button size="small" circle icon="edit" type="success" @click="editTrainInfo(item)"/>
                      </el-tooltip>
                    </el-col>
                    <el-col :span="12">
                      <el-popconfirm
                          title="此操作无法撤销，确定删除该培训计划吗？"
                          @confirm="deleteTrainInfo(item.traId)"
                          confirmButtonText="确定"
                          cancelButtonText="取消">
                        <template #reference>
                          <div>
                            <el-tooltip effect="dark" content="删除培训计划" placement="top">
                              <el-button size="small" circle icon="delete" type="danger"/>
                            </el-tooltip>
                          </div>
                        </template>
                      </el-popconfirm>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </div>
            <el-divider/>
            <p>开始时间：{{ item.traStartTime }}</p>
            <p>结束时间：{{ item.traEndTime }}</p>
            <p>培训方式：{{ item.traType }}</p>
            <div slot="footer" align="center">
              <el-button type="primary" @click="showTrainDetails(item)">培训详情</el-button>
            </div>
          </el-card>
          <el-tooltip
              effect="dark"
              content="点击添加培训计划"
              placement="bottom"
          >
            <el-card @click="addTrainInfo"
                     style="height: 300px;width: 300px" align="center" class="add-train-card-class">
              <el-icon class="add-train-icon-class">
                <Plus/>
              </el-icon>
            </el-card>
          </el-tooltip>
        </el-space>
      </el-col>
      <el-col :span="1"/>
    </el-row>
  </div>
  <!--  操作指定培训计划-->
  <div v-if="!showTrainVisible">
    <!--    上传预览对话框-->
    <el-dialog v-model="uploadPreviewVisible" width="50%" :close-on-click-modal="false">
      <el-table :data="uploadResourceList" border height="500">
        <el-table-column type="index" label="编号" width="40"/>
        <el-table-column prop="srcTitle" label="试题题目"/>
        <el-table-column prop="srcType" label="试题类型" width="40"/>
        <el-table-column prop="srcScore" label="分值" width="40"/>
        <el-table-column prop="srcSelect" label="选项"/>
        <el-table-column prop="srcAnswer" label="答案" width="40"/>
        <el-table-column prop="srcAnalysis" label="问题解析"/>
        <!--        <el-table-column prop="srcTrain" label="所属培训计划"/>-->
        <el-table-column label="操作" width="140">
          <template #default="{row}">
            <el-button type="text" size="small" @click="editResource(row)">编辑</el-button>
            <el-button type="text" size="small" @click="deleteResource(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--      对话框底部操作-->
      <div slot="footer" align="right" class="dialog-footer">
        <el-button @click="uploadPreviewVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitUpload">确 定</el-button>
      </div>
    </el-dialog>
    <!--    修改资源对话框-->
    <el-dialog v-model="editResourceFormVisible" width="25%">
      <el-form :model="editResourceForm" label-width="80px">
        <el-form-item label="试题题目">
          <el-input v-model="editResourceForm.srcTitle"/>
        </el-form-item>
        <el-form-item label="试题类型">
          <el-select v-model="editResourceForm.srcType">
            <el-option label="单选题" value="单选题"/>
            <el-option label="判断题" value="判断题"/>
          </el-select>
        </el-form-item>
        <el-form-item label="分值">
          <el-input v-model="editResourceForm.srcScore"/>
        </el-form-item>
        <el-form-item label="选项">
          <el-input v-model="editResourceForm.srcSelect"/>
        </el-form-item>
        <el-form-item label="答案">
          <el-input v-model="editResourceForm.srcAnswer"/>
        </el-form-item>
        <el-form-item label="问题解析">
          <el-input v-model="editResourceForm.srcAnalysis"/>
        </el-form-item>
      </el-form>
      <div slot="footer" align="center" class="dialog-footer">
        <el-button @click="editResourceFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEditResource">确 定</el-button>
      </div>
    </el-dialog>
    <!--    编辑成绩对话框-->
    <el-dialog v-model="editGradeFormVisible" width="30%"
               :title="editFormData.data.staffName"
               :close-on-click-modal="false">
      <FormCom :form-data="editFormData" @submit="onSubmitEditGrade"/>
    </el-dialog>
    <!--    添加考试对话框-->
    <el-dialog v-model="addExamInfoFormVisible" width="50%" :close-on-click-modal="false">
      <el-steps :active="activeStep" finish-status="success">
        <el-step title="基本信息"></el-step>
        <el-step title="生成考卷"></el-step>
        <el-step title="阅览"></el-step>
      </el-steps>
      <div v-if="activeStep === 0">
        <el-form :model="addExamInfoForm" label-width="80px">
          <el-form-item label="考试名称">
            <el-input v-model="addExamInfoForm.exName"/>
          </el-form-item>
          <el-form-item label="开始时间">
            <el-date-picker
                v-model="addExamInfoForm.exStartTime"
                type="datetime"
                placeholder="选择日期时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间">
            <el-date-picker
                v-model="addExamInfoForm.exEndTime"
                type="datetime"
                placeholder="选择日期时间"
                value-format="YYYY-MM-DD HH:mm:ss"
                format="YYYY-MM-DD HH:mm:ss"
            >
            </el-date-picker>
          </el-form-item>
          <el-row>
            <el-col :span="12">
              <el-form-item label="考试时长">
                <el-input-number v-model="addExamInfoForm.exTotalTime"/>分钟
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="试卷说明">
                <el-input v-model="examPaperDetails.papRemark"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div v-if="activeStep === 1">
        <!-- 在这里添加试题资源选择的代码 -->
        <el-table :data="filterResourceList" border height="400" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50"/>
          <el-table-column prop="srcTitle" label="试题题目"/>
          <el-table-column prop="srcType" label="试题类型"/>
          <el-table-column prop="srcScore" label="分值"/>
          <el-table-column prop="srcSelect" label="选项"/>
          <el-table-column prop="srcAnswer" label="答案"/>
          <el-table-column prop="srcAnalysis" label="问题解析"/>
          <el-table-column label="操作">
            <template #default="{row}">
              <el-button type="text" size="small" @click="editResource(row)">编辑</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div v-if="activeStep === 2">
        <!-- 在这里添加考试阅览的代码 -->
        <el-card>
          <template #header>
            <el-card>
              <p>考试名称：
                <el-tag>{{ addExamInfoForm.exName }}</el-tag>
                开始时间：
                <el-tag>{{ addExamInfoForm.exStartTime }}</el-tag>
                结束时间：
                <el-tag>{{ addExamInfoForm.exEndTime }}</el-tag>
                考试时长：
                <el-tag>{{ addExamInfoForm.exTotalTime }}</el-tag>
              </p>
              <p>试卷说明：
                <el-tag>{{ examPaperDetails.papRemark }}</el-tag>
                试卷总题目数：
                <el-tag>{{ examPaperDetails.papTotalIssues }}</el-tag>
                试卷总分：
                <el-tag>{{ examPaperDetails.papTotalScore }}</el-tag>
              </p>
            </el-card>
          </template>
          <el-scrollbar height="200px">
            <div v-for="(item,index) in selectQuestion">
              <p>{{ index + 1 }}.{{ item.srcTitle }}({{ item.srcScore }}分)</p>
              <p>{{ item.srcSelect }}</p>
            </div>
          </el-scrollbar>
        </el-card>
      </div>
      <div slot="footer" align="center" class="dialog-footer">
        <el-button @click="activeStep--" v-if="activeStep > 0">上一步</el-button>
        <el-button @click="nextStep()" v-if="activeStep < 2">下一步</el-button>
        <el-button type="primary" @click="submitExamInfo()" v-if="activeStep === 2">提交</el-button>
      </div>
    </el-dialog>
    <!--    修改考试对话框-->
    <el-dialog v-model="editExamInfoFormVisible" width="50%" :close-on-click-modal="false">
      <el-form :model="editExamInfoForm" label-width="80px">
        <el-form-item label="考试名称">
          <el-input v-model="editExamInfoForm.exName"/>
        </el-form-item>
        <el-form-item label="开始时间">
          <el-date-picker
              v-model="editExamInfoForm.exStartTime"
              type="datetime"
              placeholder="选择日期时间"
              value-format="YYYY-MM-DD HH:mm:ss"
              format="YYYY-MM-DD HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间">
          <el-date-picker
              v-model="editExamInfoForm.exEndTime"
              type="datetime"
              placeholder="选择日期时间"
              value-format="YYYY-MM-DD HH:mm:ss"
              format="YYYY-MM-DD HH:mm:ss"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="考试时长">
          <el-input-number v-model="editExamInfoForm.exTotalTime"/>
        </el-form-item>
      </el-form>
      <div slot="footer" align="center" class="dialog-footer">
        <el-button @click="editExamInfoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEditExamInfo()">确 定</el-button>
      </div>
    </el-dialog>
    <!--    修改试卷信息对话框-->
    <el-dialog v-model="editPaperInfoFormVisible" width="50%" :close-on-click-modal="false">
      <el-form :model="examPaperDetails" label-width="120px">
        <el-form-item label="试卷说明">
          <el-input v-model="examPaperDetails.papRemark"/>
        </el-form-item>
        <p>&nbsp &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp
          试卷总分：
          <el-tag>{{ examPaperDetails.papTotalScore }}</el-tag>
          试卷总题目数：
          <el-tag>{{ examPaperDetails.papTotalIssues }}</el-tag>
        </p>
      </el-form>
      <el-divider/>
      <el-table :data="selectQuestion" border height="400">
        <el-table-column type="expand">
          <template #default="props">
            <div>
              <p>选项：{{ props.row.srcSelect }}</p>
              <p>答案：{{ props.row.srcAnswer }}</p>
              <p>问题解析：{{ props.row.srcAnalysis }}</p>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="srcTitle" label="试题题目"/>
        <el-table-column prop="srcType" label="试题类型"/>
        <el-table-column prop="srcScore" label="分值"/>
        <el-table-column label="操作" width="140">
          <template #default="{row}">
            <el-button type="text" size="small" @click="editResource(row)">编辑</el-button>
            <el-button type="text" size="small" @click="deleteQuestion(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" align="center" class="dialog-footer">
        <el-button @click="editPaperInfoFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitEditPaperInfo()">确 定</el-button>
      </div>
    </el-dialog>

    <!--    培训计划操作区域-->
    <el-row>
      <el-col :span="4">
        <el-button type="success" @click="showTrainVisible = true">返回</el-button>
      </el-col>
      <el-col :span="20">
      </el-col>
    </el-row>
    <el-divider/>
    <el-row>
      <el-col :span="24">
        <el-tabs tab-position="left" v-model="activeName" @tab-click="handleClick">
          <el-tab-pane name="first">
            <template #label>
              <span class="custom-tabs-label">
                <el-icon><histogram/></el-icon>
                <span>培训详情</span>
              </span>
            </template>
            <el-row>
              <el-col :span="11">
                <el-table :data="staffStuRecordList" border height="500">
                  <el-table-column type="index" label="编号" width="50"/>
                  <el-table-column prop="staffName" label="员工姓名"/>
                  <el-table-column prop="stuStartTime" label="开始时间"/>
                  <el-table-column prop="stuEndTime" label="结束时间"/>
                  <el-table-column prop="stuTotalTime" label="学习时长/hours" width="150" :sortable="true"/>
                </el-table>
              </el-col>
              <el-col :span="1"></el-col>
              <el-col :span="12">
                <el-table :data="staffTraGradeList" border height="500">
                  <el-table-column type="index" label="编号" width="50"/>
                  <el-table-column prop="staffName" label="员工姓名"/>
                  <el-table-column prop="graRemark" label="成绩说明"/>
                  <el-table-column prop="postName" label="岗位"/>
                  <el-table-column prop="graScore" label="培训成绩"/>
                  <el-table-column label="操作" width="140">
                    <template #default="{row}">
                      <el-button type="text" size="small" @click="editGradeInfo(row)">编辑</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane name="second">
            <template #label>
              <span class="custom-tabs-label">
                <el-icon><Document/></el-icon>
                <span>培训资料</span>
              </span>
            </template>
            <el-row>
              <el-col :span="1"/>
              <el-col :span="11">
                <el-upload
                    action="https://jsonplaceholder.typicode.com/posts/"
                    :on-change="handleFileChange"
                    :file-list="fileList"
                    :auto-upload="false"
                    :show-file-list="false"
                >
                  <el-button slot="trigger" size="small" type="primary">导入试题资源</el-button>
                  <div slot="tip" class="el-upload__tip">只能上传xlsx/xls文件</div>
                </el-upload>
              </el-col>
              <el-col :span="8"></el-col>
              <el-col :span="4">
                <el-input v-model="resourceSearch" placeholder="请输入资源类型"/>
              </el-col>
            </el-row>
            <el-divider/>
            <el-row>
              <el-col :span="24">
                <el-table :data="filterResourceList" border height="500">
                  <el-table-column type="index" label="编号" width="100"/>
                  <el-table-column prop="srcTitle" label="试题题目"/>
                  <el-table-column prop="srcType" label="试题类型"/>
                  <el-table-column prop="srcScore" label="分值"/>
                  <el-table-column prop="srcSelect" label="选项"/>
                  <el-table-column prop="srcAnswer" label="答案"/>
                  <el-table-column prop="srcAnalysis" label="问题解析"/>
                  <!--                  <el-table-column prop="srcTrain" label="所属培训计划"/>-->
                  <el-table-column label="操作">
                    <template #default="{row}">
                      <el-button type="text" size="small" @click="editResource(row)">编辑</el-button>
                      <el-button type="text" size="small" @click="deleteResource(row)">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane name="third">
            <template #label>
              <span class="custom-tabs-label">
                <el-icon><Star/></el-icon>
                <span>培训考核</span>
              </span>
            </template>
            <el-row>
              <el-col :span="22"></el-col>
              <el-col :span="2">
                <el-button type="primary" @click="addExamInfo">添加考试</el-button>
              </el-col>
            </el-row>
            <el-divider/>
            <!--            展示考试信息表-->
            <el-row>
              <el-col :span="24">
                <el-tooltip effect="dark" :auto-close="1000" content="点击行查看考试详情" placement="top">
                  <el-table :data="examInfoList" border height="500" @row-click="showExamInfoDetails">
                    <el-table-column type="index" label="编号" width="50"/>
                    <el-table-column prop="exName" label="考试名称"/>
                    <el-table-column prop="exStartTime" label="开始时间"/>
                    <el-table-column prop="exEndTime" label="结束时间"/>
                    <el-table-column prop="exTotalTime" label="考试时长"/>
                    <el-table-column label="操作" width="100">
                      <template #default="{row}">
                        <el-button type="text" size="small" @click.stop="editExamInfo(row)">编辑</el-button>
                        <!--                        <el-button type="text" size="small">删除</el-button>-->
                      </template>
                    </el-table-column>
                  </el-table>
                </el-tooltip>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-col>
    </el-row>
  </div>
</template>

<script lang="ts" setup>
import {ExamInfo, ExamPaperInfo, StudyRecord, StudySrc, TrainGradeInfo, TrainInfo} from "./type.ts";
import {
  addExamInfoBack, addPaperInfoBack,
  addStaffTrain,
  addTrainSrc,
  deleteStaffTrain, deleteTrainSrc, getExamInfoByTrainId, getPaperInfoById,
  getStaffTrainList, getStudyRecord, getStudyRecordList, getTrainGradeInfoByTrainInfoId,
  getTrainSrcList, updateExamInfo, updatePaperInfo,
  updateStaffTrain, updateTrainGradeInfo, updateTrainSrc
} from "../../api/StaffTrain";
import {Document, Files, Grape, Histogram, Plus, Star} from "@element-plus/icons-vue";
import {cloneDeep} from "lodash";
import {ElMessage, ElMessageBox, UploadFile, UploadUserFile} from "element-plus";
import {readExcel} from "../../utils/excelReader.ts";
import {useUser} from "../../store/home.ts";
import moment from "moment";
import {formDataType} from "../../components/FromCom/type.ts";
import {SchedulePattern} from "../scheduleManage/type.ts";

onBeforeMount(() => {
  //获取培训信息列表
  getStaffTrainList().then(res => {
    trainInfoList.value = res
  })
})
const showTrainVisible = ref(true)

//培训计划管理
const trainInfoList = ref([] as TrainInfo[])
//添加或编辑培训计划表单
const saveOrUpdateForm = ref({} as TrainInfo)
//添加或编辑培训计划表单是否显示
const saveOrUpdateFormVisible = ref(false)
//添加培训计划
const addTrainInfo = () => {
  saveOrUpdateFormVisible.value = true
  saveOrUpdateForm.value = {} as TrainInfo
  saveOrUpdateForm.value.traId = 0
}
//编辑培训计划
const editTrainInfo = (item: TrainInfo) => {
  saveOrUpdateFormVisible.value = true
  saveOrUpdateForm.value = cloneDeep(item)
}
//提交添加或更改表单
const submitSaveOrUpdate = async () => {
  if (saveOrUpdateForm.value.traId === 0) {
    //添加培训计划
    await addStaffTrain(saveOrUpdateForm.value).then(res => {
      trainInfoList.value.push(res)
    })
  } else {
    //编辑培训计划
    await updateStaffTrain(saveOrUpdateForm.value).then(res => {
      const index = trainInfoList.value.findIndex(item => item.traId === saveOrUpdateForm.value.traId)
      trainInfoList.value[index] = saveOrUpdateForm.value
    })
  }
  saveOrUpdateFormVisible.value = false
}
//删除培训计划
const deleteTrainInfo = (traId: number) => {
  //删除培训计划
  deleteStaffTrain(traId).then(() => {
    const index = trainInfoList.value.findIndex(item => item.traId === traId)
    trainInfoList.value.splice(index, 1)
    ElMessage.success("删除成功")
  })
}
//计划详情按钮
const currentTrainInfo = ref({} as TrainInfo)
const showTrainDetails = (item: TrainInfo) => {
  currentTrainInfo.value = item
  //获取培训资源列表
  getTrainSrcList(item.traId).then(res => {
    resourceList.value = res
  })
  //获取学习记录列表（处理后数据）
  getStudyRecordList(useUser().loginUser.staffId, moment(currentTrainInfo.value.traStartTime as string).format("YYYY:MM:DD"), moment(currentTrainInfo.value.traEndTime as string).format("YYYY:MM:DD"), currentTrainInfo.value.traId).then(res => {
    staffStuRecordList.value = res
  })
  //获取培训成绩列表
  getTrainGradeInfoByTrainInfoId(item.traId).then(res => {
    staffTraGradeList.value = res
  })
  //获取考试信息列表
  getExamInfoByTrainId(item.traId).then(res => {
    examInfoList.value = res
  })
  showTrainVisible.value = false
}
//标签属性
const activeName = ref('first')
//标签点击事件
const handleClick = (tab: any) => {
  // console.log(tab)
}

//培训资源管理
// 资源列表
const resourceList = ref([] as StudySrc[]);
//搜索框绑定值
const resourceSearch = ref('')
//根据搜索框使用计算属性过滤资源列表
const filterResourceList = computed(() => {
  return resourceList.value.filter(item => !resourceSearch.value || item.srcType!.includes(resourceSearch.value))
})
//上传解析到的试题资源列表
const uploadResourceList = ref([] as StudySrc[]);
// Excel 列名到属性名的映射
const columnToProperty = {
  "试题编号": "srcId",
  "分值": "srcScore",
  "选项": "srcSelect",
  "题目": "srcTitle",
  "答案解析": "srcAnalysis",
  "答案": "srcAnswer",
  "所属培训计划": "srcTrain",
  "试题类型": "srcType",
};
const fileList = ref<UploadUserFile[]>([]); // 用于存储上传的文件列表
// 处理文件改变事件
const uploadPreviewVisible = ref(false)
const handleFileChange = (file: UploadFile) => {
  fileList.value = [file]; // 只保留最新的文件
  // 检查 file.raw 是否存在
  if (file.raw) {
    // 读取Excel文件并处理数据
    readExcel(file.raw, columnToProperty).then((data) => {
      uploadResourceList.value = data;
      // 所有对象id设为0
      uploadResourceList.value.forEach((item) => {
        item.srcId = 0;
        item.srcTrain = currentTrainInfo.value.traId
      });
      ElMessage.success('文件解析成功');
      uploadPreviewVisible.value = true
      // 在这里处理数据
    }).catch((error) => {
      console.error(error);
      // 在这里处理错误
    });
  } else {
    console.error('File is undefined');
  }
};
//资源编辑表单对话框
const editResourceFormVisible = ref(false)
//资源编辑表单
const editResourceForm = ref({} as StudySrc)
const submitEditResource = () => {
  // 在这里你可以添加将数据发送到服务器的代码
  if (editResourceForm.value.srcId === 0) {
    const index = uploadResourceList.value.findIndex(value => value.srcId === editResourceForm.value.srcId)
    uploadResourceList.value[index] = editResourceForm.value
  } else {
    //   数据库更新
    updateTrainSrc(editResourceForm.value).then(res => {
      const index = resourceList.value.findIndex(value => value.srcId === editResourceForm.value.srcId)
      resourceList.value[index] = editResourceForm.value
      ElMessage.success('更新成功')
    })
  }
  editResourceFormVisible.value = false
}
//资源编辑按钮
const editResource = (item: StudySrc) => {
  editResourceFormVisible.value = true
  editResourceForm.value = cloneDeep(item)
  // console.log(item)
}
//资源删除按钮
const deleteResource = (item: StudySrc) => {
  if (item.srcId === 0) {
    const index = uploadResourceList.value.findIndex(value => value.srcId === item.srcId)
    uploadResourceList.value.splice(index, 1)
  } else {
    //   数据库删除
    deleteTrainSrc(item.srcId as number).then(() => {
      const index = resourceList.value.findIndex(value => value.srcId === item.srcId)
      resourceList.value.splice(index, 1)
      ElMessage.success('删除成功')
    })
  }
}
// 提交上传，将数据存入是数据库并更新表格显示
const submitUpload = () => {
  // 在这里你可以添加将数据发送到服务器的代码，服务器返回添加后的列表
  addTrainSrc(uploadResourceList.value).then(res => {
    resourceList.value.push(...res)
    uploadPreviewVisible.value = false
    ElMessage.success('上传成功')
  })
};
//培训详情管理
const staffStuRecordList = ref([] as StudyRecord[])
const staffTraGradeList = ref([] as TrainGradeInfo[])
//编辑成绩
const editGradeFormVisible = ref(false)
const editFormData: formDataType<TrainGradeInfo> = {
  "data": {} as TrainGradeInfo,
  "message": [{
    "label": "成绩说明",
    "type": "text",
    "name": "graRemark"
  },
    {
      "label": "培训成绩",
      "type": "score",
      "name": "graScore"
    }
  ]
}
const editGradeInfo = (item: TrainGradeInfo) => {
  editGradeFormVisible.value = true
  editFormData.data = cloneDeep(item)
}
const onSubmitEditGrade = (gradeInfo: TrainGradeInfo) => {
//数据库更新
  updateTrainGradeInfo(gradeInfo).then(res => {
    const index = staffTraGradeList.value.findIndex(value => value.graId === gradeInfo.graId)
    staffTraGradeList.value[index] = gradeInfo
    ElMessage.success('更新成功')
    editGradeFormVisible.value = false
  })
}
//培训考核管理
const examInfoList = ref([] as ExamInfo[])
const examPaperDetails = ref({} as ExamPaperInfo)
const editPaperInfoFormVisible = ref(false)
const showExamInfoDetails = (row: any) => {
  getPaperInfoById(row.papId).then(res => {
    examPaperDetails.value = res
    selectQuestion.value = [] as StudySrc[]
    examPaperDetails.value.papQuestionIds!.forEach((item: number) => {
      const index = resourceList.value.findIndex(value => value.srcId === item)
      selectQuestion.value.push(resourceList.value[index])
    })
    editPaperInfoFormVisible.value = true
  })
}
const submitEditPaperInfo = () => {
  //数据库更新
  // examPaperDetails.value.papTotalIssues = selectQuestion.value.length
  // examPaperDetails.value.papTotalScore = selectQuestion.value.reduce((pre, cur) => pre + cur.srcScore!, 0)
  examPaperDetails.value.papQuestionIds = selectQuestion.value.map(item => item.srcId!)
  examPaperDetails.value.papIssueIds = examPaperDetails.value.papQuestionIds.join(',')
  updatePaperInfo(examPaperDetails.value).then(res => {
    ElMessage.success('修改成功')
    editPaperInfoFormVisible.value = false
  })
}
const deleteQuestion = (item: StudySrc) => {
  ElMessageBox.confirm('此操作无法撤销，确定删除该试题吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    const index = selectQuestion.value.findIndex(value => value.srcId === item.srcId)
    selectQuestion.value.splice(index, 1)
    examPaperDetails.value.papTotalIssues = selectQuestion.value.length
    examPaperDetails.value.papTotalScore = selectQuestion.value.reduce((pre, cur) => pre + cur.srcScore!, 0)
    ElMessage.success('删除成功')
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}
//添加考试对话框
const addExamInfoFormVisible = ref(false)
const addExamInfoForm = ref({} as ExamInfo)
const activeStep = ref(0)
const selectQuestion = ref([] as StudySrc[])
const addExamInfo = () => {
  addExamInfoForm.value = {} as ExamInfo
  addExamInfoForm.value.exId = 0
  addExamInfoForm.value.traId = currentTrainInfo.value.traId
  examPaperDetails.value = {} as ExamPaperInfo
  activeStep.value = 0
  addExamInfoFormVisible.value = true
}
const nextStep = () => {
  activeStep.value++
  if (activeStep.value == 1) {
    //生成试卷
  } else if (activeStep.value == 2) {
    //获取试卷详情
    examPaperDetails.value.papTotalIssues = selectQuestion.value.length
    examPaperDetails.value.papTotalScore = selectQuestion.value.reduce((pre, cur) => pre + cur.srcScore!, 0)
    //   选中的试题编号
    examPaperDetails.value.papQuestionIds = selectQuestion.value.map(item => item.srcId!)
    //   转化为字符串
    examPaperDetails.value.papIssueIds = examPaperDetails.value.papQuestionIds.join(',')
  }
}
const handleSelectionChange = (val: StudySrc[]) => {
  selectQuestion.value = val
}
const submitExamInfo = () => {
  //提交考试信息
  addPaperInfoBack(examPaperDetails.value).then(async res => {
    addExamInfoForm.value.papId = res.papId
    await addExamInfoBack(addExamInfoForm.value).then(res => {
      examInfoList.value.push(res)
      addExamInfoFormVisible.value = false
      ElMessage.success('添加成功')
    })
  })
}
//修改考试对话框
const editExamInfoFormVisible = ref(false)
const editExamInfoForm = ref({} as ExamInfo)
const editExamInfo = (item: ExamInfo) => {
  editExamInfoFormVisible.value = true
  editExamInfoForm.value = cloneDeep(item)
}
const submitEditExamInfo = () => {
  //数据库更新
  updateExamInfo(editExamInfoForm.value).then(res => {
    const index = examInfoList.value.findIndex(value => value.exId === editExamInfoForm.value.exId)
    examInfoList.value[index] = editExamInfoForm.value
    ElMessage.success('更新成功')
    editExamInfoFormVisible.value = false
  })
}
</script>

<style lang="less" scoped>


.el-icon.add-train-icon-class {
  font-size: 28px;
  color: #8c939d;
  width: 250px;
  height: 250px;
  text-align: center;
}

.el-card.add-train-card-class {
  background-color: #f5f7fa;
  cursor: pointer;
}

.el-tabs {
  height: 550px;
}

.demo-tabs .custom-tabs-label .el-icon {
  vertical-align: middle;
}

.demo-tabs .custom-tabs-label span {
  vertical-align: middle;
  margin-left: 4px;
}
</style>
