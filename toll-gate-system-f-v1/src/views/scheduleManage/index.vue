<script setup lang="ts">
import {NeedDuty, SchedulePattern, ScheduleStaff, StaffSchedule} from "./type.ts";
import {ElMessage, ElMessageBox, TableColumnCtx} from "element-plus";
import {
  addOrUpdateSchedulePattern, autoScheduleByDate, deleteSchedulePattern,
  getNeedDuty,
  getSchedule,
  getSchedulePattern,
  getScheduleStaff,
  updateStaffSchedule
} from "../../api/ScheduleApi";
import {ComputedRef} from "vue";
import moment from "moment";

onBeforeMount(async () => {
  //获取排班信息
  await getSchedule().then(res => {
    staffScheduleList.schedule = res
  })
  await getSchedulePattern().then(res => {
    schedulePatternList.schedulePattern = res.map((item: SchedulePattern) => ({
      ...item,
      timeRange: [item.schStart, item.schEnd]
    }))
    // schedulePatternList.schedulePattern = res
  })
  await getNeedDuty().then(res => {
    needDutyList.needDuty = res
  })
})
//flag 计算传入日期中所有排班信息中是否都有人值班
const flag = computed(() => (day: string) => {
  staffScheduleList.schedule.forEach(item => {
    if (item.proTime === day && item.staffName == null) {
      return 0
    }
  })
  return 1
})

const activeName = ref('first')
const handleClick = (tab: any) => {
  activeName.value = tab.props.name
}

//自动排班
const autoScheduleVisible = ref(false)
const autoDate= ref('')
const autoSchedule = () => {
  autoScheduleByDate(autoDate.value).then((res)=>{
    if(res.status == 400){
      ElMessage.error(res?.message)
      return
    }
    ElMessage.success("自动排班成功")
    autoScheduleVisible.value = false
    staffScheduleList.schedule = []
    staffScheduleList.schedule = res.data
  })
}

const nowTime = moment().format('E');//计算今天是这周第几天
//获取这周的起始日期
const startDay = new Date(moment().subtract(Number(nowTime) - 1, 'days').format('YYYY-MM-DD'));
//获取这周的结束日期
const endDay = new Date(moment().add(7 - Number(nowTime), 'days').format('YYYY-MM-DD'));
//结束日期
const endDate = new Date('2024-12-01')

//排班信息表
const staffScheduleList = reactive({
  schedule: [] as StaffSchedule[]
})

//排班方式操作对话框
const scheduleVisible = ref(false)
const scheduleUpdateVisible = ref(false)

interface SpanMethodProps {
  row: SchedulePattern
  column: TableColumnCtx<SchedulePattern>
  rowIndex: number
  columnIndex: number
}

//添加排班规则
const addPattern = () => {
  updateRuleForm.data = []
  scheduleUpdateVisible.value = true
}
const schedulePatternList = reactive({
  schedulePattern: [] as SchedulePattern[]
})
const objectSpanMethod = ({row, column, rowIndex, columnIndex}: SpanMethodProps) => {
  if (columnIndex === 0) {
    //根据职位合并单元格
    const currentRemark = row.schRemark;
    let rowspan = 1;
    // Check following rows to see if they have the same name
    for (let i = rowIndex + 1; i < schedulePatternList.schedulePattern.length; i++) {
      if (schedulePatternList.schedulePattern[i].schRemark === currentRemark) {
        rowspan++;
      } else {
        break;
      }
    }
    const prevRow = schedulePatternList.schedulePattern[rowIndex - 1];
    if (rowIndex > 0 && row.schRemark === prevRow.schRemark) {
      return [0, 0]; // 合并单元格
    }
    return [rowspan, 1];
  }
  return [1, 1];
}
const updateRuleForm = reactive({
  data: [] as SchedulePattern[]
})
const onSelectRule = (row: SchedulePattern, column: any, event: Event) => {
  updateRuleForm.data = []
  schedulePatternList.schedulePattern.forEach(item => {
    if (item.schRemark === row.schRemark) {
      updateRuleForm.data.push(item)
    }
  })
  scheduleUpdateVisible.value = true
}
const rulePattern = [
  {label:'收费员',value:2},
  {label:'车辆疏导员',value:3}
]
const addItem = () => {
  let name = ''
  ElMessageBox.prompt('请输入班次名称','',{
    confirmButtonText: 'OK',
    cancelButtonText: 'Cancel'
  }).then(({value})=>{
    name = value
    let remark = ''
    if(updateRuleForm.data.length !=0 ){
      remark = updateRuleForm.data[0].schRemark as string
    }else{
      rulePattern.forEach((item)=>{
        if(item.value === newSelect.value )
          remark = item.label
      })
    }
    updateRuleForm.data.push({
      schId: 0,
      schRemark: remark,
      schName: name,
      schStart: '',
      schEnd: '',
      timeRange: []
    })
  })
}
const deleteItem = (item: any) => {
  if(item.schId != 0){
    deleteSchedulePattern(item.schId).then(()=>{
      ElMessage.success("删除成功")
      let index_1 = schedulePatternList.schedulePattern.indexOf(item)
      if(index_1 != -1){
        schedulePatternList.schedulePattern.splice(index_1,1)
      }
    })
  }
  let index = updateRuleForm.data.indexOf(item)
  if (index != -1) {
    updateRuleForm.data.splice(index, 1)
  }
}
const newSelect =ref(2)
const submitRuleForm = () => {
  // for(let i = 0;i<updateRuleForm.data.length;i++){
  //   updateRuleForm.data[i].schStart = updateRuleForm.data[i].timeRange[0]
  //   updateRuleForm.data[i].schEnd = updateRuleForm.data[i].timeRange[1]
  // }
  addOrUpdateSchedulePattern(updateRuleForm.data).then((res)=>{
    ElMessage.success("保存成功")
    getSchedulePattern().then(res => {
      schedulePatternList.schedulePattern = res.map((item: SchedulePattern) => ({
        ...item,
        timeRange: [item.schStart, item.schEnd]
      }))
    })
    scheduleUpdateVisible.value = false
  })
}
//需要值班的岗位信息
const needDutyList = reactive({
  needDuty: [] as NeedDuty[]
})
const drawer = ref(false)
const nowDay = ref('')
const onSelect = (day: string) => {
  nowDay.value = day
  drawer.value = true
}
const cancelClick = () => {
  drawer.value = false
}
const confirmClick = () => {
  drawer.value = false
}
//展示一天的排班
const selectValue = ref('')
const columns = [
  {
    label: "职员姓名",
    prop: "staffName"
  },
  {
    label: "值班位置",
    prop: "dutyName"
  },
  {
    label: "开始时间",
    prop: "schStart"
  },
  {
    label: "结束时间",
    prop: "schEnd"
  }
]
const scheduleList: ComputedRef<StaffSchedule[]> = computed(() => {
  return staffScheduleList.schedule.filter(item => item.proTime === nowDay.value)
})
const filterTable = computed(() => {
  return scheduleList.value.filter(item =>
      !selectValue.value || item.dutyName!.includes(selectValue.value)
  )
})
//修改班次值班人员
const dialogVisible = ref(false)
const form = reactive({
  data: {} as StaffSchedule
})

const updateSchedule = async (row: StaffSchedule) => {
  let postName = ''
  needDutyList.needDuty.forEach(item => {
    if (item.dutyId === row.proPost) {
      postName = item.dutyRemark as string
    }
  })
  await getScheduleStaff(postName).then(res => {
    allStaffList.allStaff = res
  })
  form.data = row
  dialogVisible.value = true
}
const submitUpdate = () => {
  form.data.staffName = allStaffList.allStaff.find(item => item.staffId === form.data.proStaff)?.staffName
  updateStaffSchedule(form.data).then(() => {
    ElMessage.success("修改成功")
    dialogVisible.value = false
  })
}
const allStaffList = reactive({
  allStaff: [] as ScheduleStaff[]
})
</script>

<template>
  <!--  排班方式操作对话框-->
<!--  <el-dialog title="排班规则" v-model="scheduleVisible" :close-on-click-modal="false" width="40%">-->
<!--    <el-table-->
<!--        :data="schedulePatternList.schedulePattern"-->
<!--        :span-method="objectSpanMethod"-->
<!--        border-->
<!--        height="400"-->
<!--        style="width: 100%;"-->
<!--    >-->
<!--      <el-table-column prop="schRemark" label="适用职位"/>-->
<!--      <el-table-column prop="schName" label="班次名称"/>-->
<!--      <el-table-column prop="schStart" label="开始时间"/>-->
<!--      <el-table-column prop="schEnd" label="结束时间"/>-->
<!--    </el-table>-->
<!--  </el-dialog>-->

  <!--  更改排班方式对话框-->
  <el-dialog title="更改排班方式" v-model="scheduleUpdateVisible" :close-on-click-modal="false" width="40%">
    <el-form :model="updateRuleForm.data" label-width="100px">
      <el-form-item v-if="updateRuleForm.data.length!=0" label="适用职位：">
        <el-tag type="success">{{ updateRuleForm.data[0].schRemark }}</el-tag>
      </el-form-item>
      <el-form-item v-else label="适用职位">
        <el-select placeholder="请选择" v-model="newSelect">
          <el-option v-for="item in rulePattern" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item v-for="item in updateRuleForm.data" :label="item.schName">
        <el-row>
          <el-col :span="12">
            <el-time-picker
                v-model="item.schStart"
                start-placeholder="Start time"
                end-placeholder="End time"
                value-format="HH:mm:ss"
                style="width: 150px;"
            />
          </el-col>
          <el-col :span="12">
            <el-time-picker
                v-model="item.schEnd"
                start-placeholder="Start time"
                end-placeholder="End time"
                value-format="HH:mm:ss"
                style="width: 150px;"
            />
          </el-col>
        </el-row>
        <el-tooltip content="删除班次" placement="top">
          <el-button circle icon="plus" @click="deleteItem(item)"/>
        </el-tooltip>
      </el-form-item>
      <!--    操作区域-->
      <el-form-item>
        <el-button type="success" @click="addItem">添加班次</el-button>
        <el-button type="primary" @click="submitRuleForm">保存</el-button>
        <el-button @click="scheduleUpdateVisible=false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

<!--  auto-schedule-->
  <el-dialog title="选择日期" v-model="autoScheduleVisible" width="25%">
    <el-form>
      <el-form-item label="日期">
        <el-date-picker
            v-model="autoDate"
            placeholder="选择日期"
            format="YYYY-MM-DD"
            value-format="YYYY-MM-DD"
            style="width: 100%"
          />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="autoSchedule()">确定</el-button>
        <el-button @click="autoScheduleVisible=false">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>

  <!--  顶部按钮-->
<!--  <el-row>-->
<!--    <el-col :span="11">-->
<!--&lt;!&ndash;      <el-tooltip content="添加排班规则" placement="top">&ndash;&gt;-->
<!--&lt;!&ndash;        <el-button type="primary" @click="addPattern">添加</el-button>&ndash;&gt;-->
<!--&lt;!&ndash;      </el-tooltip>&ndash;&gt;-->
<!--      <el-tooltip content="对指定时间所在周进行自动排班" placement="top">-->
<!--        <el-button type="primary" @click="autoScheduleVisible=true">自动排班</el-button>-->
<!--      </el-tooltip>-->
<!--    </el-col>-->
<!--    <el-col :span="2">-->
<!--      排班日期-->
<!--    </el-col>-->
<!--    <el-col :span="11">-->
<!--    </el-col>-->
<!--  </el-row>-->
  <el-divider/>
  <el-row>
    <el-col :span="1">
    </el-col>
    <el-col :span="9">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="排班规则" name="first">
          <el-tooltip content="对指定时间所在周进行自动排班" placement="top">
            <el-button type="primary" @click="autoScheduleVisible=true">自动排班</el-button>
          </el-tooltip>
          <el-divider />
          <el-table
              :data="schedulePatternList.schedulePattern"
              :span-method="objectSpanMethod"
              @cell-click="onSelectRule"
              border
              height="400"
              style="width: 100%;"
          >
            <el-table-column prop="schRemark" label="适用职位"/>
            <el-table-column prop="schName" label="班次名称"/>
            <el-table-column prop="schStart" label="开始时间"/>
            <el-table-column prop="schEnd" label="结束时间"/>
          </el-table>
        </el-tab-pane>
<!--        <el-tab-pane label="员工调班" name="second">-->
<!--        </el-tab-pane>-->
      </el-tabs>
    </el-col>
    <el-col :span="1">
    </el-col>
    <el-col :span="12">
      <el-calendar :range="[startDay,endDate]">
        <template #date-cell="{ data }">
          <el-row>
            <el-col :span="18">
              <p @click="onSelect(data.day)"
                 v-if="moment(data.day).isAfter(moment(startDay).subtract(1,'day'))"
                 :class="data.isSelected ? 'is-selected' : ''">
                {{ data.day.split('-').slice(1).join('-') }}
              </p>
            </el-col>
            <el-col :span="6">
              <p v-if="data.day === moment().format('YYYY-MM-DD')">✔️</p>
            </el-col>
          </el-row>
          <el-row v-if="moment(data.day).isBetween(moment(startDay).subtract(1,'day'),endDay)">
            <el-col :span="1">
            </el-col>
            <el-col :span="22">
              <!--          <el-progress v-if="flag(data.day)==1" :percentage="100" :show-text="false"/>-->
            </el-col>
            <el-col :span="1">
            </el-col>
          </el-row>
        </template>
      </el-calendar>
    </el-col>
  </el-row>

  <!--  修改班次弹窗-->
  <el-dialog title="修改班次" v-model="dialogVisible" width="30%">
    <el-form :model="form.data" label-width="80px">
      <el-form-item label="职员">
        <el-select v-model="form.data.proStaff" placeholder="请选择职员">
          <el-option v-for="item in allStaffList.allStaff" :label="item.staffName" :value="item.staffId"/>
        </el-select>
      </el-form-item>
      <el-form-item label="值班位置">
        <el-input v-model="form.data.dutyName" disabled/>
      </el-form-item>
      <el-form-item label="开始时间">
        <el-input v-model="form.data.schStart" disabled/>
      </el-form-item>
      <el-form-item label="结束时间">
        <el-input v-model="form.data.schEnd" disabled/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitUpdate()">确 定</el-button>
    </span>
  </el-dialog>
  <!--  弹出框-->
  <el-drawer v-model="drawer" direction="rtl">
    <template #header>
      <h4>{{ nowDay }}</h4>
    </template>
    <template #default>
      <el-divider/>
      <div>
        <el-row>
          <el-col :span="8">班次</el-col>
          <el-col :span="8">
            <el-select v-model="selectValue" placeholder="请选择值班位置">
              <el-option label="请选择值班位置" value=""></el-option>
              <el-option
                  v-for="item in needDutyList.needDuty"
                  :key="item.dutyId"
                  :label="item.dutyName"
                  :value="item.dutyName"
              />
            </el-select>
          </el-col>
          <el-col :span="8"></el-col>
        </el-row>
        <el-divider/>
        <el-row>
          <el-col :span="3"></el-col>
          <el-col :span="18">
            <el-table :data="filterTable" border style="width: 100%" height="400" @row-click="updateSchedule">
              <el-table-column v-for="item in columns" :key="item.prop" :label="item.label" :prop="item.prop">
              </el-table-column>
            </el-table>
          </el-col>
          <el-col :span="3"></el-col>
        </el-row>
      </div>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button v-if="selectValue != ''" @click="cancelClick">取消</el-button>
        <el-button v-if="selectValue != ''" type="primary" @click="confirmClick">修改排班</el-button>
      </div>
    </template>
  </el-drawer>
</template>

<style scoped lang="less">
.el-calendar-table .el-calendar-day {
  height: 75px;
}
</style>
