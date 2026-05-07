<script setup lang="ts">
import {listRew, listStaff, RewManageType, UserInfoType} from "./type.ts";
import moment from "moment";
import {getStaffInfo} from "../../api/StaffManage/staff-management.ts";
import {addRewardPunish, getRewardPunish} from "../../api/SundryManage";
import {useUser} from "../../store/home.ts";
import {formDataType} from "../../components/FromCom/type.ts";

const store = useUser()
const searchRew = ref('')
const searchStaff = ref('')
const filterRew = computed(() => {
  return listRew.list.filter(item =>
      !searchRew.value || item.staffName!.includes(searchRew.value) || item.rewTime.includes(searchRew.value)
  )
})
const filterStaff = computed(() => {
  return listStaff.list.filter(item =>
      !searchStaff.value || item.staffName.includes(searchStaff.value) || item.postName!.includes(searchStaff.value)
  )
})
const columns = [
  {
    label: "职员姓名",
    prop: "staffName"
  },
  {
    label: '时间',
    prop: 'rewTime'
  },
  {
    label: "内容",
    prop: "rewContent"
  },
  {
    label: "性质",
    prop: "rewNature"
  }
]
const changeClass = (data: { row: UserInfoType, rowIndex: number }) => {
  data.row.index = data.rowIndex
  return 'warning-row'
}
const nowRowIndex = ref(-1)
const addRewInfo = ref({} as UserInfoType)
const handleClick = (row: UserInfoType, column: any, event: Event) => {
  if (row.index == nowRowIndex.value) {
    nowRowIndex.value = -1
    addRewInfo.value = {} as UserInfoType
    return
  }
  addRewInfo.value = row
  nowRowIndex.value = row.index as number
}
const changeCurrent = (data: { row: UserInfoType, rowIndex: number }) => {
  if (data.rowIndex === nowRowIndex.value)
    return {background: '#e62ead'}
}
//增加奖惩信息
const dialogVisible = ref(false)
const dialogTitle = ref('')
const addData:formDataType<RewManageType> = reactive({
  "data": {} as RewManageType,
  "message": [
    {
      label: "职员姓名",
      type: "name",
      name: "staffName"
    },
    {
      label: "时间",
      type: "date",
      name: "rewTime"
    },
    {
      label: "内容",
      type: "text",
      name: "rewContent"
    },
    {
      label: "性质",
      type: "type",
      name: "rewNature"
    }
  ]
})
const showDialog = () => {
  dialogVisible.value = true
  dialogTitle.value = addRewInfo.value.staffName
  addData.data.rewId = null;addData.data.rewStaff=addRewInfo.value.staffId;
  addData.data.rewTime = moment().format('YYYY-MM-DD HH:mm:ss')
  addData.data.staffName=addRewInfo.value.staffName
}
const submitRew = () => {
  addRewardPunish(addData.data).then(res => {
    ElMessage.success("添加成功")
    res.staffName = addData.data.staffName
    listRew.list.push(res)
    dialogVisible.value = false
  })
}

onBeforeMount(() => {
  getStaffInfo().then(res => {
    listStaff.list = res
    listStaff.list = computed(() => listStaff.list.filter(item =>
        item.staffId !== store.loginUser.staffId
    )).value
  })
  getRewardPunish(store.loginUser.staffId).then(res => {
    listRew.list = res
    console.log(listRew.list)
  })
})
</script>

<template>
<!--  增加表单对话框-->
  <el-dialog :title="dialogTitle" :center="true" v-model="dialogVisible" width="30%">
    <el-form :model="addData.data" label-width="80px">
      <el-form-item v-for="item in addData.message" :label="item.label" :key="item.name">
        <el-input v-if="item.type=='text'" type="textarea" v-model="addData.data[item.name]" placeholder="请输入"/>
        <el-date-picker v-else-if="item.type=='date'" v-model="addData.data[item.name]" type="datetime"
                       placeholder="Pick a Date" format="YYYY-MM-DD HH:mm:ss" date-format="MMM DD, YYYY" time-format="HH:mm"/>
        <el-select v-else-if="item.type=='type'" v-model="addData.data[item.name]" placeholder="请选择">
          <el-option label="奖励" value="红事件"/>
          <el-option label="惩罚" value="黑事件"/>
        </el-select>
        <el-input v-else v-model="addData.data[item.name]" placeholder="请输入" disabled/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitRew">确 定</el-button>
      </div>
    </template>
  </el-dialog>

  <el-row style="margin-bottom: 10px;">
    <el-col :span="2"/>
    <el-col :span="4">
      <el-input v-model="searchRew" placeholder="请输入名字进行搜索" prefix-icon="el-icon-search" clearable></el-input>
    </el-col>
    <el-col :span="4">
      <el-tooltip placement="top" content="全部时间">
        <el-button type="success" plain style="margin-left: 20px" icon="List" @click="searchRew = ''"/>
      </el-tooltip>
      <el-tooltip placement="top" content="本月">
        <el-button type="success" plain style="margin-left: 20px" icon="Opportunity"
                   @click="searchRew = moment().format('YYYY-MM')"/>
      </el-tooltip>
    </el-col>
    <el-col :span="5"/>
    <el-col :span="6">
      <el-input v-model="searchStaff" placeholder="输入幸运儿名字或岗位" prefix-icon="el-icon-search" clearable></el-input>
    </el-col>
    <el-col :span="3">
      <el-divider direction="vertical"/>
      <el-tooltip content="就决定是这位了" placement="top">
        <el-button style="float: none; " type="danger" icon="Plus" @click="showDialog" />
      </el-tooltip>
    </el-col>
  </el-row>
  <el-divider></el-divider>
  <el-row>
    <el-col :span="1"/>
    <el-col :span="11">
      <el-table :data="filterRew" border style="width: 100%" height="550">
        <el-table-column v-for="item in columns" :key="item.prop" :label="item.label" :prop="item.prop">
        </el-table-column>
      </el-table>
    </el-col>
    <el-col :span="1"/>
    <el-col :span="10">
      <el-card class="box-card">
        <div slot="header" class="clearfix">
          <span>奖惩一位幸运儿</span>
        </div>
        <el-divider/>
        <el-table :data="filterStaff" border style="width: 100%" height="400"
                  :row-class-name="changeClass"
                  @row-click="handleClick"
                  :row-style="changeCurrent"
        >
          <el-table-column type="index" width="50"/>
          <el-table-column label="姓名" prop="staffName">
          </el-table-column>
          <el-table-column label="职位" prop="postName">
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="1"/>
  </el-row>
  <!--</el-card>-->
</template>

<style scoped lang="less">
.box-card {
  width: 100%;
  height: 550px;

  .clearfix {
    text-align: center;
  }
}

.el-table .warning-row {
  background: #bc0c0c;
}
</style>
