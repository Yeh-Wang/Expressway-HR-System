<script setup lang="ts">
import {getPublicNotice} from "../../api/HomeShow";
import {listPublicNotice, PublicNoticeVo} from "../HomeShow/type.ts";
import {PerformanceReview} from "../performanceManage/type.ts";
import {getAllPerformInfoTotal, getPerformInfoByStaffIdAndTime} from "../../api/PerformanceApi";
import {useUser} from "../../store/home.ts";

onBeforeMount(() => {
  getPublicNotice().then(res => {
    listPublicNotice.list = res
    console.log(listPublicNotice.list[0].pubTime)
  })
  getAllPerformInfoTotal().then(res => {
    performanceReviewList.value = res
    performanceReviewList.value = performanceReviewList.value.filter(item => item.perStaff === useUser().loginUser.staffId)
  })
})
//点击行展示公告详细信息
const showDialogVisible = ref(false)
const nowNotice: PublicNoticeVo = reactive({
  data: {} as PublicNoticeVo
})

const handleClick = (row: PublicNoticeVo, column: any, event: Event) => {
  showDialogVisible.value = true
  nowNotice.data = row
}
const performanceColumns = [
  {
    label: "职员姓名",
    prop: "perStaffName"
  },
  {
    label: "职位",
    prop: "perPostName"
  },
  {
    label: '考核内容',
    prop: 'perContent'
  },
  {
    label: "考核时间",
    prop: "perTime"
  },
  {
    label: "结果",
    prop: "perResult"
  }
]
const performanceReviewList = ref([] as PerformanceReview[]);
const performanceDetailList = ref([] as PerformanceReview[])
const performanceDetails = async (row: PerformanceReview) => {
  await getPerformInfoByStaffIdAndTime(row.perStaff as string, row.perTime as string).then(res => {
    performanceDetailList.value = res
    //   删除perRemark==“总分”的项
    performanceDetailList.value = performanceDetailList.value.filter(item => item.perRemark !== '总分')
  })
}
</script>

<template>
  <el-dialog v-model="showDialogVisible" width="30%" style="height: 400px">
    <div slot="header" class="clearfix">
      <el-tag type="danger">公告</el-tag>
    </div>
    <el-divider/>
    <span> {{ nowNotice.data.pubContent }} </span>
    <template #footer>
      <div class="dialog-footer">
        <p><el-tag type="success">{{ nowNotice.data.pubCreateName }}</el-tag></p>
        <span>{{ nowNotice.data.pubTime }} </span>
      </div>
    </template>
  </el-dialog>
<div>
  <el-row>
    <el-col :span="1"></el-col>
    <el-col :span="10">
      <!--      公告展示-->
      <el-empty v-if="listPublicNotice.list.length == 0" description="目前没有公告"/>
      <el-card v-else class="box-card" style="height: 550px;margin-top: 10px">
        <div slot="header" class="clearfix">
          <el-tag type="danger">公告</el-tag>
        </div>
        <el-divider/>
        <el-table :data="listPublicNotice.list" @row-click="handleClick" stripe style="width: 100%">
          <el-table-column prop="pubTitle" label="标题"/>
          <el-table-column prop="pubTime" header-align="center" align="right" label="时间"/>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="1"></el-col>
    <el-col :span="11">
      <el-table :data="performanceReviewList"
                border style="width: 100%;;margin-top: 10px" height="550">
        <el-table-column v-for="item in performanceColumns" :key="item.prop" :label="item.label" :prop="item.prop">
        </el-table-column>
        <!--      操作区域-->
        <el-table-column label="操作">
          <template #default="{row}">
            <el-popover
                placement="left" title="各项得分，没有则是不存在记录" :width="700" trigger="click">
              <template #reference>
                <el-button type="text" @click="performanceDetails(row)">详情</el-button>
              </template>
              <el-table :data="performanceDetailList"
                        border style="width: 100%;" height="400">
                <el-table-column v-for="item in performanceColumns" :key="item.prop" :label="item.label" :prop="item.prop">
                </el-table-column>
              </el-table>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
</div>
</template>

<style scoped lang="less">

</style>
