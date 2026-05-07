<script setup lang="ts">

//获取所有培训计划信息并过滤出正在进行的培训计划
import {
  addStudyRecord,
  getAllExamInfo, getPaperInfoById,
  getStaffTrainList,
  getStudyRecordByStaffId, getTrainScoreByStaffId,
  getTrainSrcList
} from "../../api/StaffTrain";
import {
  currentExamInfo,
  currentPaperInfo,
  currentQuestion,
  staffExamInfoList,
  staffStudyRecordList,
  staffTrainInfoList
} from "./commonType.ts";
import {Plus, Timer} from "@element-plus/icons-vue";
import {ExamInfo, ExamPaperInfo, StudyRecord, StudySrc, TrainGradeInfo, TrainInfo} from "../TrainManagement/type.ts";
import moment from "moment";
import {ElMessage, ElMessageBox} from "element-plus";
import {useUser} from "../../store/home.ts";
import router from "../../routes";

const store = useUser();
onBeforeMount(()=>{
  getStaffTrainList().then(res=> {
    staffTrainInfoList.value = res;
    staffTrainInfoList.value.forEach((item,index)=>{
      if(item.traStatus === "已结束"){
        staffTrainInfoList.value.splice(index,1);
      }
    })
  })
})
//培训计划可见控制
const showTrainVisible = ref(true);

//tabs切换
const activeName = ref("first");
const handleClick = (tab: any) => {
  if(tab.paneName === "second") {
    getStudyRecordByStaffId(store.loginUser.staffId).then(res => {
      staffStudyRecordList.value = res;
    })
  }
  if (tab.paneName === "three") {
    getAllExamInfo().then(res => {
      staffExamInfoList.value = res;
    })
  }
};
const currentExamGradeInfo = ref({} as TrainGradeInfo)
const showExamDetails = async (item: ExamInfo) => {
  await getPaperInfoById(item.papId as number).then(res => {
    currentPaperInfo.value = res;
    currentQuestion.value = [] as StudySrc[]
    currentPaperInfo.value.papIssueIds!.split(",").forEach((item: string) => {
      const index = resourceList.value.findIndex(value => value.srcId === Number(item))
      currentQuestion.value.push(resourceList.value[index])
    })
    currentExamInfo.value = item
    // console.log(currentPaperInfo.value,currentQuestion.value)
  })
  await getTrainScoreByStaffId(store.loginUser.staffId,item.traId as number).then(res => {
    if(res == null){
      let tips = "考试总分数："+currentPaperInfo.value.papTotalScore+"分，总题目数："+currentPaperInfo.value.papTotalIssues+"题，"+"\n"+"您未参加考试！"
      ElMessageBox.alert(tips,'提示', {
        distinguishCancelAndClose: false,
        confirmButtonText: '开始考试',
        type: 'info'
      }).then(() => {
        router.push('/happy-exam-view')
      }).catch(() => {
      });
      return
    }else{
      currentExamGradeInfo.value = res
      console.log(currentExamGradeInfo.value)
      let tips = "考试总分数："+currentPaperInfo.value.papTotalScore+"分，总题目数："+currentPaperInfo.value.papTotalIssues+"题，您的得分："+currentExamGradeInfo.value.graScore+"分"
      ElMessageBox.confirm(tips,'成绩信息', {
        distinguishCancelAndClose: false,
        confirmButtonText: '确定',
        type: 'success'
      }).then(() => {
        ElMessage.success("考试详情")
      }).catch(() => {
      });
    }
  })
}

//查看培训计划详情，进入学习
const currentTrainInfo = ref({} as TrainInfo)
const startTime = ref(moment().format('YYYY-MM-DD HH:mm:ss'));
const endTime = ref(moment().format('YYYY-MM-DD HH:mm:ss'));
let timerValue = ref(0);
let timerId = ref(null);
const startTimer = () => {
  timerValue.value = 0;
  // @ts-ignore
  timerId.value = setInterval(() => {
    timerValue.value++;
  }, 1000);
};
const stopTimer = () => {
  if(timerId.value){
    clearInterval(timerId.value);
    timerId.value = null;
    // if (timerValue.value < 60 ) {
    //   timerValue.value = 60;
    // }
  }
};
const showTrainDetails = (item: TrainInfo) => {
  showTrainVisible.value = false;
  currentTrainInfo.value = item
  startTimer();
  startTime.value = moment().format('YYYY-MM-DD HH:mm:ss');
  //获取培训资源列表
  getTrainSrcList(item.traId).then(res => {
    resourceList.value = res
  })
}
// 资源列表
const resourceList = ref([] as StudySrc[]);
const addStudyRecordForm = ref({} as StudyRecord)
const backToTrainInfo = () =>{
  ElMessageBox.confirm('是否退出学习？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    endTime.value = moment().format('YYYY-MM-DD HH:mm:ss');
    let studyTime = moment(endTime.value).diff(moment(startTime.value), 'seconds');
    //  转化成小时并保留两位小时
    studyTime = Number((studyTime / 3600).toFixed(2));
    stopTimer()
    if(timerValue.value<30){
      ElMessage.error("学习时间少于半分钟");
    }else{
      //添加学习记录
      addStudyRecordForm.value.stuId = 0;
      addStudyRecordForm.value.stuStartTime = startTime.value;
      addStudyRecordForm.value.stuEndTime = endTime.value;
      addStudyRecordForm.value.stuTotalTime = studyTime;
      addStudyRecordForm.value.stuTrain = currentTrainInfo.value.traId;
      addStudyRecordForm.value.staffId = store.loginUser.staffId
      addStudyRecord(addStudyRecordForm.value).then(res=> {
        ElMessage.success("学习时长已记录");
      })
    }
    showTrainVisible.value = true;
  }).catch(() => {
  });
}

</script>

<template>
  <!--  展示培训计划列表-->
  <div v-if="showTrainVisible">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="培训计划" name="first">
        <el-row >
          <el-col :span="2"/>
          <el-col :span="21">
            <el-space wrap :size="20">
              <el-card v-for="item in staffTrainInfoList" class="box-card" style="height: 300px;width: 300px">
                <div slot="header" class="clearfix">
                  <el-row>
                    <el-col :span="10">
                      {{ item.traTheme }}
                    </el-col>
                    <el-col :span="8">
                      {{ item.traStatus }}
                    </el-col>
                    <el-col :span="6">
                    </el-col>
                  </el-row>
                </div>
                <el-divider/>
                <p>开始时间：{{ item.traStartTime }}</p>
                <p>结束时间：{{ item.traEndTime }}</p>
                <p>培训方式：{{ item.traType }}</p>
                <div slot="footer" align="center">
                  <el-button type="primary" @click="showTrainDetails(item)">进入学习</el-button>
                </div>
              </el-card>
            </el-space>
          </el-col>
          <el-col :span="1"/>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="学习记录" name="second">
        <el-row>
          <el-col :span="1"/>
          <el-col :span="22">
            <el-table :data="staffStudyRecordList" border height="550">
              <el-table-column type="index" label="编号" width="100"/>
              <el-table-column prop="trainName" label="培训主题"/>
              <el-table-column prop="stuStartTime" label="开始时间"/>
              <el-table-column prop="stuEndTime" label="结束时间"/>
              <el-table-column prop="stuTotalTime" label="学习时长/hours"/>
            </el-table>
          </el-col>
          <el-col :span="1"/>
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="考试信息" name="three">
        <el-row>
          <el-col :span="1"/>
          <el-col :span="22">
            <el-table :data="staffExamInfoList" border height="500">
              <el-table-column type="index" label="编号" width="50"/>
              <el-table-column prop="exName" label="考试名称"/>
              <el-table-column prop="traName" label="培训名称"/>
              <el-table-column prop="exStartTime" label="开始时间"/>
              <el-table-column prop="exEndTime" label="结束时间"/>
              <el-table-column prop="exTotalTime" label="考试时长"/>
              <el-table-column label="操作" width="100">
                <template #default="{row}">
                  <el-button type="text" size="small" @click.stop="showExamDetails(row)">详情</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="1"/>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>

  <!--  展示培训计划详情-->
  <div v-else>
    <el-row>
      <el-col :span="4">
        <el-button type="success" @click="backToTrainInfo">退出</el-button>
      </el-col>
      <el-col :span="7">
      </el-col>
      <el-col :span="13">
        <el-tag type="success" size="large"><el-icon ><Timer /></el-icon> 学习时长：{{ timerValue }}秒</el-tag>
      </el-col>
    </el-row>
    <el-divider/>
    <el-table :data="resourceList" border height="500">
      <el-table-column type="expand">
        <template #default="props">
          <el-card>
            <p>答案：{{ props.row.srcAnswer }}</p>
            <p>问题解析：{{ props.row.srcAnalysis }}</p>
          </el-card>
        </template>
      </el-table-column>
      <el-table-column prop="srcTitle" label="试题题目"/>
      <el-table-column prop="srcType" label="试题类型"/>
      <el-table-column prop="srcScore" label="分值"/>
      <el-table-column prop="srcSelect" label="选项"/>
    </el-table>
  </div>
</template>

<style scoped lang="less">

</style>
