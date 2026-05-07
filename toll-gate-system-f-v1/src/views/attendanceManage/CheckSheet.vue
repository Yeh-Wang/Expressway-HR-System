<script setup lang="ts">
import {CheckSheet} from "./type.ts";
import {generateCheckSheet, getAllCheckSheet} from "../../api/attendanceApi";
import moment from "moment/moment";
import {ElMessageBox} from "element-plus";
import {useUser} from "../../store/home.ts";

const allCheckSheetData = reactive({
  checkSheetData: [] as CheckSheet[]
})
const search = ref('')
const filterData = computed(() => {
  return allCheckSheetData.checkSheetData.filter(item =>
      !search.value || item.staffName!.includes(search.value) || item.chMonth!.includes(search.value)
  )
})
const generateMonth = () => {
  //手动生成本月考勤统计
  ElMessageBox.confirm('是否确认手动生成本月考勤统计？这会覆盖本月已有的数据', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    // 生成考勤统计逻辑
    generateCheckSheet(useUser().loginUser.staffId,moment().format("YYYY-MM")).then(res => {
      getAllCheckSheet().then(res => {
        allCheckSheetData.checkSheetData = res
      })
    })
  })
}

const columns = [
  {
    label: '月份',
    prop: 'chMonth'
  },
  {
    label: "职员姓名",
    prop: "staffName"
  },
  {
    label: "应出勤天数",
    prop: "chSetDay"
  },
  {
    label: "实出勤天数",
    prop: "chRealDay"
  },
  {
    label: "请假天数",
    prop: "chVacateDay"
  },
  {
    label: "总上班时长",
    prop: "chOverTime"
  }
]
onBeforeMount(() => {
  // 获取所有考勤表数据
  getAllCheckSheet().then(res => {
    allCheckSheetData.checkSheetData = res
  })
})
</script>

<template>
  <el-row style="margin-bottom: 10px;">
    <el-col :span="20">
      <el-tooltip placement="top" content="全部时间">
        <el-button type="success" plain style="margin-left: 20px" icon="Opportunity" @click="search = ''" />
      </el-tooltip>
      <el-tooltip placement="top" content="本月">
        <el-button type="success" plain style="margin-left: 20px" icon="Filter" @click="search = moment().format('YYYY-MM')" />
      </el-tooltip>
      <el-tooltip placement="top" content="手动生成本月考考勤统计">
        <el-button type="success" plain style="margin-left: 20px" icon="List" @click="generateMonth" />
      </el-tooltip>
    </el-col>
    <el-col :span="4">
      <el-input placeholder="search" v-model="search" />
    </el-col>
    <!-- <el-col :span="1" > -->

    <!-- </el-col> -->
  </el-row>
  <el-table :data="filterData" border style="width: 100%" height="600">
    <el-table-column type="index" width="50" />
    <el-table-column v-for="(item, key) in columns" :prop="item.prop" :label="item.label" :key="key" />
<!--    <el-table-column fixed="right" align="center" label="Operations" width="120">-->
<!--      <template #default="editItem">-->
<!--        <el-button link type="primary" size="small" @click="handleEdit(editItem.$index, editItem.row)">Edit</el-button>-->
<!--      </template>-->
<!--    </el-table-column>-->
  </el-table>
</template>

<style scoped lang="less">

</style>
