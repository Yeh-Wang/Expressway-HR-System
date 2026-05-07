<script setup lang="ts">
import {currentExamInfo, currentPaperInfo, currentQuestion, mockQuestion} from "./commonType.ts";
import {ExamPaperInfo, StudySrc, TrainGradeInfo} from "../TrainManagement/type.ts";
import {cloneDeep} from "lodash";
import {ElMessageBox} from "element-plus";
import {useUser} from "../../store/home.ts";
import {addTrainScore} from "../../api/StaffTrain";
import router from "../../routes";
import {Timer} from "@element-plus/icons-vue";


const questionList = ref([] as StudySrc[])
const currentPaper = ref({} as ExamPaperInfo)
const currentGradeInfo = ref({} as TrainGradeInfo)
onBeforeMount(() => {
  questionList.value = cloneDeep(currentQuestion.value)
  currentPaper.value = cloneDeep(currentPaperInfo.value)
  currentQuestionInfo.value = questionList.value[0]
  questionList.value.forEach((item, index) => {
    item.srcAnalysis = ''
  })
  currentGradeInfo.value = {} as TrainGradeInfo
})

const currentQuestionInfo = ref({} as StudySrc)
const currentQuestionIndex = ref(0)
const examTitle = ref(currentExamInfo.value.exName); // 考试标题
const timeValue = ref(Date.now()+1000*60*Number(currentExamInfo.value.exTotalTime)); // 考试时间
const lastQuestion = () => {
  currentQuestionIndex.value--
  currentQuestionInfo.value = questionList.value[currentQuestionIndex.value]
}
const nextQuestion = () => {
  currentQuestionIndex.value++
  currentQuestionInfo.value = questionList.value[currentQuestionIndex.value]
}
const submitExam = () => {
  // 提交考试逻辑
  //判断是否有未答题目
  let unAnswered = questionList.value.filter(item => item.srcAnalysis === '')
  if (unAnswered.length > 0) {
    ElMessageBox.confirm('您还有未答题目，是否确认提交？', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      // 提交考试逻辑
      // 例如：计算分数，显示结果
      let totalScore = 0;
      questionList.value.forEach(item => {
        if (item.srcAnalysis === item.srcAnswer) {
          totalScore = totalScore + item.srcScore!
        }
      })
      currentGradeInfo.value.graId = 0;
      currentGradeInfo.value.graRemark = currentPaper.value.papRemark;
      currentGradeInfo.value.graScore = totalScore.toString();
      currentGradeInfo.value.graStaffId = useUser().loginUser.staffId;
      currentGradeInfo.value.traId = currentExamInfo.value.traId;
      addTrainScore(currentGradeInfo.value).then(res => {
        ElMessageBox.alert('提交成功', '提示', {
          confirmButtonText: '确定',
          type: 'success'
        }).then(() => {
          ElMessage.success('提交成功')
          router.push('/')
        })
      })
    }).catch(() => {
      console.log('取消提交');
    });
  } else {
    // 提交考试逻辑
    // 例如：计算分数，显示结果
    let totalScore = 0;
    questionList.value.forEach(item => {
      if (item.srcAnalysis === item.srcAnswer) {
        totalScore = totalScore + item.srcScore!
      }
    })
    currentGradeInfo.value.graId = 0;
    currentGradeInfo.value.graRemark = currentPaper.value.papRemark;
    currentGradeInfo.value.graScore = totalScore.toString();
    currentGradeInfo.value.staffId = useUser().loginUser.staffId;
    currentGradeInfo.value.traId = currentExamInfo.value.traId;
    ElMessageBox.confirm('确认提交', '提示', {
      confirmButtonText: '确定',
      type: 'success'}).then(() => {
      addTrainScore(currentGradeInfo.value).then(res => {
        ElMessage.success('提交成功')
        router.push('/')
      })
    })
  }
};

</script>

<template>
  <div class="exam-container">
    <div class="exam-header">
      <el-row>
        <el-col :span="1">
        </el-col>
        <el-col :span="6">
          <div class="exam-title
          ">{{ examTitle }}
          </div>
        </el-col>
        <el-col :span="5"></el-col>
        <el-col :span="10">
          <el-countdown
              format="HH:mm:ss"
              :value="timeValue"
          >
            <template #title>
              <div style="display: inline-flex; align-items: center">
                <el-icon style="margin-right: 4px" :size="12">
                  <Timer />
                </el-icon>
                剩余时长
              </div>
            </template>
          </el-countdown>
        </el-col>
        <el-col :span="2">
          <el-button type="danger" @click="submitExam">提交</el-button>
        </el-col>
      </el-row>
    </div>
    <div class="exam-content">
      <el-row :gutter="10">
        <!--        展示做题进度-->
        <el-col :span="4">
          <el-card>
            <el-space wrap>
              <div v-for="(item,index) in questionList" style="width: 100%">
                <el-button style="width: 26px;height: 26px" v-if="item.srcAnalysis === ''" plain>{{
                    index + 1
                  }}
                </el-button>
                <el-button style="width: 26px;height: 26px" v-else type="success">{{ index + 1 }}</el-button>
              </div>
            </el-space>
          </el-card>
        </el-col>
        <el-col :span="2">

        </el-col>
        <el-col :span="14">
          <div class="question-item">
            <el-card style="height: 500px;" v-model="currentQuestionInfo">
              <div class="question-header">
                <div class="question-title">{{ currentQuestionIndex + 1 }}.{{ currentQuestionInfo.srcTitle }}</div>
                <div class="question-info">（{{ currentQuestionInfo.srcType }}）</div>
              </div>
              <div class="question-options">
                {{ currentQuestionInfo.srcSelect }}
              </div>
              <div class="question-answer">
                <el-divider>
                  <el-select style="width: 150px" v-model="currentQuestionInfo.srcAnalysis" v-if="currentQuestionInfo.srcType==='单选题'" placeholder="请选择答案">
                    <el-option label="A" value="A"/>
                    <el-option label="B" value="B"/>
                    <el-option label="C" value="C"/>
                    <el-option label="D" value="D"/>
                  </el-select>
                  <el-select style="width: 150px" v-model="currentQuestionInfo.srcAnalysis" v-if="currentQuestionInfo.srcType==='判断题'" placeholder="请选择答案">
                    <el-option label="A" value="A"/>
                    <el-option label="B" value="B"/>
                  </el-select>
                </el-divider>
              </div>
              <div class="question-footer">
                <el-button id="left-but" v-if="currentQuestionIndex!=0" type="primary" @click="lastQuestion">上一题
                </el-button>
                <el-button id="right-but" v-if="currentQuestionIndex!=questionList.length-1" type="primary" @click="nextQuestion">下一题</el-button>
              </div>
            </el-card>
          </div>
        </el-col>
        <el-col :span="4">

        </el-col>
      </el-row>
    </div>
  </div>
</template>

<style scoped lang="less">
.exam-container {
  padding: 20px;
  background-color: #fff;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.exam-header {
  //display: flex;
  align-items: center;
  padding: 10px;
  border-bottom: 1px solid #eee;
}


.exam-title {
  font-size: 24px;
  font-weight: bold;
}

.exam-content {
  padding: 10px;
}

.question-item {
  margin-bottom: 20px;
  border-bottom: 1px solid #eee;
  padding: 20px;
}

.question-header {
  display: flex;
  justify-content: space-between;
  height: 120px;
}

.question-title {
  font-size: 18px;
  font-weight: bold;
}

.question-options {
  height: 180px;
}

.question-answer{
  height: 80px;
}

.question-footer {
  display: flex;
  justify-content: center;
  align-items: center;
}


.submit-button {
  background-color: #007bff;
  color: #fff;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
}

.timer {
  color: #999;
}
</style>
