<script setup lang="ts">
import {
  changeStaffDailyPunch,
  changeUnapprovedLeave,
  getStaffDailyPunch,
  getUnapprovedLeave
} from '../../api/attendanceApi';
import {formDataType} from '../../components/FromCom/type';
import {useUser} from '../../store/home';
import {StaffDailyPunch, dailyPunch, applyFeedbackList} from './type';
import {tableColumns} from './type';
import moment from "moment";


onBeforeMount(() => {
  // 获取所有员工的打卡信息
  getStaffDailyPunch(useUser().loginUser.staffId).then(res => {
    allPunchData.punchData = res
  })
})

const allPunchData = reactive({
  punchData: [] as StaffDailyPunch[]
})

const handleEdit = (_index: number, row: StaffDailyPunch) => {
  dialogVisiable.value = true
  formData.data = row
  flag.value = 1
}

const search = ref('')
const filterData = computed(() => {
  return allPunchData.punchData.filter(item =>
      !search.value || item.staffName.includes(search.value) || item.staffStation?.includes(search.value)
      || item.punStartTime!.includes(search.value) || item.punEndTime!.includes(search.value)
  )
})
const showToday = () => {
  //获取今日打卡情况
  search.value = moment().format("YYYY-MM-DD")
}
const formData: formDataType<StaffDailyPunch> = {
  "data": {} as StaffDailyPunch,
  "message": [{
    label: "上班打卡时间",
    type: "time",
    name: "punStartTime"
  },
    {
      label: "下班打卡时间",
      type: "time",
      name: "punEndTime"
    },
    {
      label: "打卡类型",
      type: "punch",
      name: "punType"
    },
    {
      label: "打卡地点",
      type: "punStation",
      name: "staffStation"
    }]
}
const flag = ref(1)  //1 编辑  2  增加
const dialogVisiable = ref(false)
const onSubmit = (punchInfo: StaffDailyPunch) => {
  if (flag.value == 1) {
    //标准时间转化为“2021-09-09 09:09:09”格式
    let startTime = moment(punchInfo.punStartTime).format("YYYY-MM-DD HH:mm:ss")
    let endTime = moment(punchInfo.punEndTime).format("YYYY-MM-DD HH:mm:ss")
    //判断是否在同一天
    if (startTime.split(" ")[0] != endTime.split(" ")[0]) {
      ElMessage.error("打卡时间不在同一天")
      return
    }
    //计算打卡时间差
    let start = moment(startTime)
    let end = moment(endTime)
    let diff = (end.diff(start, 'minutes') / 60).toFixed(2) as unknown as number
    punchInfo.punWorkTime = diff
    punchInfo.punStartTime = startTime
    punchInfo.punEndTime = endTime
    let data: dailyPunch = {
      punId: punchInfo.punId,
      staffId: "",
      punStartTime: startTime,
      punEndTime: endTime,
      punType: punchInfo.punType,
      punWorkTime: diff,
      punRemark: punchInfo.staffStation
    }
    changeStaffDailyPunch(data)
  } else
  // addStaffInfo(staffInfo).then(res => {
  //   ElMessage.success("添加成功")
  //   myStaffInfo.staffInfo.push(res)
  // })
  dialogVisiable.value = false
}

//请假管理
const leaveAppVisible = ref(false)
const showLeaveApp = () => {
  getUnapprovedLeave(1,'未审核').then(res => {
    applyFeedbackList.applyFeedback = []
    applyFeedbackList.applyFeedback = res
    leaveAppVisible.value = true
  })
}
const handleAgree = (index: number, row: any) => {
  row.cfoView = "批准"
  row.cfoName = useUser().loginUser.username
  changeUnapprovedLeave(row)
  applyFeedbackList.applyFeedback.splice(index, 1)
}
const handleBack = (index: number, row: any) => {
  row.cfoView = "驳回"
  row.cfoName = useUser().loginUser.username
  changeUnapprovedLeave(row)
  applyFeedbackList.applyFeedback.splice(index, 1)
}
</script>

<template>
  <el-dialog v-model="leaveAppVisible" title="请假审批" destroy-on-close >
    <el-table :data="applyFeedbackList.applyFeedback" border style="width: 100%" height="400">
      <el-table-column type="index" width="50"/>
      <el-table-column prop="staffName" label="员工姓名"/>
      <el-table-column prop="processContent" label="请假原因"/>
      <el-table-column prop="processTime" label="申请时间"/>
      <el-table-column prop="isVerify" label="请假时长（天）"/>
      <el-table-column prop="cfoTime" label="开始时间"/>
      <el-table-column fixed="right" align="center" label="Operations" width="120">
        <template #default="editItem">
          <el-button link type="success" size="small" @click="handleAgree(editItem.$index, editItem.row)">批准</el-button>
          <el-button link type="danger" size="small" @click="handleBack(editItem.$index, editItem.row)">驳回</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>

    <el-row style="margin-bottom: 10px;">
      <el-col :span="20">
        <el-tooltip placement="top" content="所有打卡">
          <el-button type="success" plain style="margin-left: 20px" icon="Opportunity" @click="search = ''"/>
        </el-tooltip>
        <el-tooltip placement="top" content="今日打卡情况">
          <el-button type="success" plain style="margin-left: 20px" icon="Filter"
                     @click="search = moment().format('YYYY-MM-DD')"/>
        </el-tooltip>
        <el-tooltip placement="top" content="员工请假审批">
          <el-button type="success" plain style="margin-left: 20px" icon="List" @click="showLeaveApp"/>
        </el-tooltip>
      </el-col>
      <el-col :span="4">
        <el-input placeholder="search" v-model="search"/>
      </el-col>
      <!-- <el-col :span="1" > -->

      <!-- </el-col> -->
    </el-row>
    <!--  表格展示考勤打卡信息-->
    <el-table :data="filterData" border style="width: 100%" height="600">
      <el-table-column type="index" width="50"/>
      <el-table-column v-for="(item, key) in tableColumns" :prop="item.prop" :label="item.label" :key="key"/>
      <el-table-column fixed="right" align="center" label="Operations" width="120">
        <template #default="editItem">
          <el-button link type="primary" size="small" @click="handleEdit(editItem.$index, editItem.row)">Edit
          </el-button>
          <!-- <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(editItem.$index, editItem.row)">
            <template #reference>
              <el-button link type="danger" size="small">Delete</el-button>
            </template>
  </el-popconfirm> -->
        </template>
      </el-table-column>
    </el-table>
    <!-- 表单-->
    <el-dialog v-model="dialogVisiable" title="编辑信息" destroy-on-close width="30%">
      <FormCom :formData="formData" @submit="onSubmit"/>
    </el-dialog>
</template>

<style scoped lang="less"></style>
