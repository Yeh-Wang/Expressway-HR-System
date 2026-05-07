<script setup lang="ts">
import {
  addStaffDailyPunch,
  addUnapprovedLeave,
  deleteUnapprovedLeave,
  getStaffDailyPunchByStaffId,
  getUnapprovedLeaveByStaffId
} from "../../api/attendanceApi";
import {useUser} from "../../store/home.ts";
import {dailyPunchList, leaveAppList, staffScheduleList} from "./commonType.ts";
import {ApplyFeedback, dailyPunch} from "../attendanceManage/type.ts";
import {ElMessage, ElMessageBox, TabsPaneContext} from "element-plus";
import moment from "moment";
import {getStaffScheduleByStaffId} from "../../api/ScheduleApi";
import {StaffSchedule} from "../scheduleManage/type.ts";
import {cloneDeep} from "lodash";

const store = useUser();

onBeforeMount(() => {
  //获取该员工的每日打卡记录
  getStaffDailyPunchByStaffId(store.loginUser.staffId).then((res) => {
    dailyPunchList.value = res;
  });
  //获取该员工的排班信息
  getStaffScheduleByStaffId(store.loginUser.staffId).then((res) => {
    staffScheduleList.value = res;
    console.log(staffScheduleList.value)
  });
});

//tabs
const activeName = ref("first");
const handleClick = (tab: TabsPaneContext) => {
  if (tab.props.name === "second") {
    //获取该员工的请假记录
    getUnapprovedLeaveByStaffId(store.loginUser.staffId).then((res) => {
      leaveAppList.value = res;
    });
  }
};
//first tab  总览
//选中的日期的打卡信息
const dailyPunchInfo = ref({} as dailyPunch);
//选中的日期的排班信息
const staffScheduleInfo = ref({} as StaffSchedule);
//添加打卡信息
const addDailyPunchVisible = ref(false);
const addDailyPunchInfoForm = ref({} as dailyPunch);
const onSelectDate = (date: string) => {
  //获取选中日期的打卡信息
  dailyPunchInfo.value = {} as dailyPunch
  staffScheduleInfo.value = {} as StaffSchedule
  dailyPunchList.value.forEach(item => {
    if (item.punStartTime?.includes(date)) {
      dailyPunchInfo.value = item;
    }
  });
  staffScheduleList.value.forEach(item => {
    if (item.proTime === date) {
      staffScheduleInfo.value = item;
    }
  });
};
const addDailyPunchInfo = () => {
  addDailyPunchInfoForm.value = cloneDeep(dailyPunchInfo.value);
  addDailyPunchVisible.value = true;
  addDailyPunchInfoForm.value.punRemark = staffScheduleInfo.value.dutyName as string;
  addDailyPunchInfoForm.value.staffId = store.loginUser.staffId;
};
const submitAddDailyPunchInfo = () => {
  if (addDailyPunchInfoForm.value.punType === 1) {
    addDailyPunchInfoForm.value.punStartTime = moment().format('YYYY-MM-DD HH:mm:ss');
    addDailyPunchInfoForm.value.punId = 0
    if (moment(moment().format("YYYY-MM-DD HH:mm:ss")).isAfter(moment().format("YYYY-MM-DD") + " " + staffScheduleInfo.value.schStart, 'minute'))
      addDailyPunchInfoForm.value.punStatus = "迟到";
    else
      addDailyPunchInfoForm.value.punStatus = "正常打卡";
  } else {
    addDailyPunchInfoForm.value.punEndTime = moment().format('YYYY-MM-DD HH:mm:ss');
    if (moment(moment().format("YYYY-MM-DD HH:mm:ss")).isBefore(moment().format("YYYY-MM-DD") + " " + staffScheduleInfo.value.schEnd, 'minute')) {
      addDailyPunchInfoForm.value.punStatus = "早退";
    } else {
      addDailyPunchInfoForm.value.punStatus = "正常打卡";
    }
  }
  addStaffDailyPunch(addDailyPunchInfoForm.value).then(() => {
    getStaffDailyPunchByStaffId(store.loginUser.staffId).then((res) => {
      dailyPunchList.value = res;
      ElMessage.success("打卡成功")
      addDailyPunchVisible.value = false;
    });
  });
  console.log(addDailyPunchInfoForm.value)
}


//second tab  请假界面
const leaveAppForm = ref({} as ApplyFeedback);
const addLeaveAppVisible = ref(false);
const showAddLeaveApp = () => {
  addLeaveAppVisible.value = true;
  leaveAppForm.value = {} as ApplyFeedback;
};
const submitLeaveApp = () => {
  leaveAppForm.value.processTime = moment().format('YYYY-MM-DD HH:mm:ss');
  leaveAppForm.value.processType = 1;
  leaveAppForm.value.cfoView = "未审核";
  leaveAppForm.value.staffId = store.loginUser.staffId;
  //添加数据到后端
  addUnapprovedLeave(leaveAppForm.value).then(() => {
    getUnapprovedLeaveByStaffId(store.loginUser.staffId).then((res) => {
      leaveAppList.value = res;
      ElMessage.success("请假申请成功，等待审核");
    });
  });
  addLeaveAppVisible.value = false;
};
const handleCancel = (index: number, row: ApplyFeedback) => {
  ElMessageBox.confirm('是否取消请假？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deleteUnapprovedLeave(row.processId).then(() => {
      leaveAppList.value.splice(index, 1);
      ElMessage.success("取消请假成功");
    });
  }).catch(() => {
    ElMessage.info('已取消取消操作');
  });
};
</script>

<template>
  <!--  展示选中日期的打卡信息-->
  <div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="总览" name="first">
        <!--  展示打卡日历-->
        <el-row>
          <el-col :span="1"></el-col>
          <el-col :span="14">
            <el-calendar style="height: 580px;">
              <template #date-cell="{ data }">
                <el-row style="margin-top: 10px">
                  <el-col :span="10"></el-col>
                  <el-col :span="8">
                <span @click="onSelectDate(data.day)"
                      v-if="dailyPunchList.find(item => item.punStartTime?.includes(data.day))"
                      class="circle">{{ data.day.split('-').slice(2)[0] }}</span>
                    <span @click="onSelectDate(data.day)" v-else>{{ data.day.split('-').slice(2)[0] }}</span>
                  </el-col>
                  <el-col :span="6"></el-col>
                </el-row>
              </template>
            </el-calendar>
          </el-col>
          <el-col :span="1"></el-col>
          <el-col :span="7">
            <el-card style="height: 580px" align="center">
              <template #header>
                <span>{{ dailyPunchInfo.punStartTime?.split(' ')[0] }}打卡和排班信息</span>
              </template>
              <p>打卡地点：{{ dailyPunchInfo.punRemark }}</p>
              <p>上班打卡时间：{{ dailyPunchInfo.punStartTime }}</p>
              <p>下班打卡时间：{{ dailyPunchInfo.punEndTime }}</p>
              <p>工作时长：{{ dailyPunchInfo.punWorkTime }}小时</p>
              <el-divider/>
              <p>排班开始时间：{{ staffScheduleInfo.schStart }}</p>
              <p>排班结束时间：{{ staffScheduleInfo.schEnd }}</p>
              <p>值班地点：{{ staffScheduleInfo.dutyName }}</p>
              <template #footer>
                <el-button v-if="moment().format('YYYY-MM-DD') === staffScheduleInfo.proTime" type="primary"
                           @click="addDailyPunchInfo">打卡
                </el-button>
              </template>
            </el-card>
          </el-col>
          <el-col :span="1"></el-col>
          <!--    <el-col :span="10"></el-col>-->
        </el-row>
      </el-tab-pane>
      <el-tab-pane label="请假界面" name="second">
        <el-row>
          <el-col :span="2">

          </el-col>
          <el-col :span="20">
            <el-table :data="leaveAppList" border style="width: 100%" height="550">
              <el-table-column type="index" width="50"/>
              <el-table-column prop="staffName" label="员工姓名"/>
              <el-table-column prop="processContent" label="请假原因"/>
              <el-table-column prop="processTime" label="申请时间"/>
              <el-table-column prop="isVerify" label="请假时长（天）"/>
              <el-table-column prop="cfoTime" label="开始时间"/>
              <el-table-column prop="cfoView" label="审核状态"/>
              <el-table-column fixed="right" align="center" width="300">
                <template #header>
                  <el-tooltip placement="top" content="申请请假">
                    <el-button type="primary" @click="showAddLeaveApp">请假申请</el-button>
                  </el-tooltip>
                </template>
                <template #default="editItem">
                  <el-button v-if="editItem.row.cfoView == '未审核'" link type="danger" size="small"
                             @click="handleCancel(editItem.$index, editItem.row)">取消
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="2">

          </el-col>
        </el-row>
      </el-tab-pane>
    </el-tabs>
  </div>
  <!--  请假申请对话框-->
  <el-dialog v-model="addLeaveAppVisible" width="30%" :close-on-click-modal="false">
    <el-form v-model="leaveAppForm">
      <el-form-item label="请假原因" label-width="80px">
        <el-input v-model="leaveAppForm.processContent" placeholder="请输入请假原因"/>
      </el-form-item>
      <el-form-item label="请假时长" label-width="80px">
        <el-input v-model="leaveAppForm.isVerify" placeholder="请输入请假时长"/>
      </el-form-item>
      <el-form-item label="开始时间" label-width="80px">
        <el-date-picker v-model="leaveAppForm.cfoTime" type="date" format="YYYY-MM-DD" value-format="YYYY-MM-DD"
                        placeholder="选择日期"/>
      </el-form-item>
      <el-form-item>
        <el-button style="margin-left: 40%;" type="primary" @click="submitLeaveApp">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
  <!--  添加打卡信息对话框-->
  <el-dialog v-model="addDailyPunchVisible" width="30%" :close-on-click-modal="false">
    <el-form v-model="addDailyPunchInfoForm">
      <el-form-item label="打卡地点" label-width="80px">
        <el-input v-model="addDailyPunchInfoForm.punRemark" placeholder="请输入打卡地点" disabled/>
      </el-form-item>
      <el-form-item label="打卡时间" label-width="80px">
        <el-select v-model="addDailyPunchInfoForm.punType as number" placeholder="请选择打卡类型">
          <el-option label="上班打卡" :value="1"/>
          <el-option label="下班打卡" :value="2"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button style="margin-left: 40%;" type="primary" @click="submitAddDailyPunchInfo">提交</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>

<style lang="less">
.circle {
  display: inline-block;
  border: 3px solid #3051c1;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  line-height: 30px;
  text-align: center;
}

.el-calendar-table .el-calendar-day {
  height: 70px;
}
</style>
