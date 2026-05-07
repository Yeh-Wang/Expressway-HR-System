<script setup lang="ts">
import {PerformanceInfo, PerformanceReview, performanceReviewList, PerformType, performTypeList} from "./type.ts";
import {
  generatePerform,
  getAllPerformInfoTotal,
  getAllPerformType,
  getPerformInfoByStaffIdAndTime, getUnratedPerformInfo, updatePerformInfo,
  updatePerformType
} from "../../api/PerformanceApi";
import {cloneDeep} from "lodash";
import {ElLoading, ElMessage} from "element-plus";
import moment from "moment";

onBeforeMount(() => {
  //获取所有绩效考核记录
  getAllPerformInfoTotal().then(res => {
    performanceReviewList.value = res
  })
})

const loading = ref(false)
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
const searchPerForm = ref('')
const filterPerformanceData = computed(() => {
  return performanceReviewList.value.filter(item =>
      !searchPerForm.value || item.perStaffName!.includes(searchPerForm.value) || item.perTime!.includes(searchPerForm.value)
  )
})
const performanceForm = ref({} as PerformanceReview)
const performanceInfoForm = ref({} as PerformanceInfo)

/**
 * 进行绩效考核
 */
const selectPerTypeList = ref([] as PerformType[])  //选择的绩效考核方式
const startPerformance = () => {
  activeStep.value = 0
  performanceForm.value = {} as PerformanceReview
  selectPerTypeList.value = [] as PerformType[]
  performanceInfoForm.value = {} as PerformanceInfo
  performanceInfoForm.value.contentWeight = new Map<string, number>()
  //获取所有绩效考核方式
  getAllPerformType().then(res => {
    performTypeList.value = res
    performanceDialogVisible.value = true
  })
}
const performanceDialogVisible = ref(false)  //绩效考核对话框是否显示
const activeStep = ref(0)   //绩效考核步骤
const nextStep = () => {  //下一步
  activeStep.value++
  if (activeStep.value === 1) {
    //   计算选择的绩效考核方式的权重
    let totalWeight = 0
    selectPerTypeList.value.forEach(item => {
      totalWeight += Number(item.weight)
    })
    if (totalWeight !== 1) {
      ElMessage.error('权重总和必须为1')
      activeStep.value--
    }
  } else if (activeStep.value === 2) {
    performanceForm.value.perContent = selectPerTypeList.value.map(item => item.content + ":" + item.weight).join(',')
    performanceForm.value.perId = 0
    performanceForm.value.perTime = moment().format('YYYY-MM-DD HH:mm:ss')
    selectPerTypeList.value.forEach(item => {
      //@ts-ignore
      performanceInfoForm.value.contentWeight?.set(item.content as string, item.weight as number)
    })
    performanceInfoForm.value.perType = performanceForm.value.perType as string
    console.log(performanceInfoForm.value)
  }
}
// 深监听选择的绩效考核方式
watch(() => performanceForm.value.perContentIds, (newVal) => {
  if (newVal) {
    selectPerTypeList.value = performTypeList.value.filter(item => newVal.includes(item.typeId))
  }
})
//编辑绩效考核权重
const editPerTypeDialogVisible = ref(false)
const editPerTypeForm = ref({} as PerformType)
const editPerType = (row: PerformType) => {
  editPerTypeForm.value = row
  editPerTypeDialogVisible.value = true
}
const submitEditPerTypeForm = () => {
  updatePerformType(editPerTypeForm.value).then(res => {
    editPerTypeDialogVisible.value = false
    performTypeList.value = performTypeList.value.map(item => item.typeId === editPerTypeForm.value.typeId ? editPerTypeForm.value : item)
    selectPerTypeList.value = selectPerTypeList.value.map(item => item.typeId === editPerTypeForm.value.typeId ? editPerTypeForm.value : item)
    ElMessage.success('修改成功')
  })
}
const superiorDialogVisible = ref(false)  //上级评价对话框是否显示
const superiorFormList = ref([] as PerformanceReview[])
//提交绩效考核
const submitPerformanceForm = () => {
  performanceForm.value.perContent = selectPerTypeList.value.map(item => item.typeId).join(',')
  loading.value = true
  //传给后端开始绩效考核
  performanceInfoForm.value.contentWeight = Object.fromEntries(performanceInfoForm.value.contentWeight as Map<string, number>)
  console.log(JSON.stringify(performanceInfoForm.value))
  generatePerform(performanceInfoForm.value).then(res => {
    ElMessage.success('生成成功')
    // performanceReviewList.value.push(...res)
    getUnratedPerformInfo().then(res => {
      superiorFormList.value = res
      superiorDialogVisible.value = true
    })
    loading.value = false
    performanceDialogVisible.value = false
  })
}
const editPerResultVisible = ref(false)
const editPerResult = (row: PerformanceReview) => {
  performanceForm.value = row
  editPerResultVisible.value = true
}
const checkDataList = (done: any) => {
  if (superiorFormList.value.some(item => item.perResult === '')) {
    ElMessage.error('请先打分')
    return false
  } else {
    getAllPerformInfoTotal().then(res => {
      performanceReviewList.value = res
    })
    done()
  }
}
const submitEditPerResultForm = () => {
  let weight = selectPerTypeList.value.find(item => item.content === performanceForm.value.perContent)?.weight
  performanceForm.value.perResult = Number(performanceForm.value.perResult) * weight! + ''
  updatePerformInfo(performanceForm.value).then(res => {
    ElMessage.success('打分成功')
    editPerResultVisible.value = false
  })
}

//绩效详情
const performanceDetailList = ref([] as PerformanceReview[])
const performanceDetails = async (row: PerformanceReview) => {
  console.log(row)
  await getPerformInfoByStaffIdAndTime(row.perStaff as string, row.perTime as string).then(res => {
    performanceDetailList.value = res
  //   删除perRemark==“总分”的项
    performanceDetailList.value = performanceDetailList.value.filter(item => item.perRemark !== '总分')
  })
}

</script>

<template>
  <div v-loading="loading">
    <!--  头部操作-->
    <el-row>
      <el-col :span="1"></el-col>
      <el-col :span="8">
        <el-button type="primary" @click="startPerformance()">绩效考核</el-button>
      </el-col>
      <el-col :span="10">
      </el-col>
      <el-col :span="4">
        <el-input v-model="searchPerForm" placeholder="请输入搜索内容" style="width: 200px"></el-input>
      </el-col>
    </el-row>
    <el-divider/>
    <el-table :data="filterPerformanceData"
    :row-class-name="({row}) => Number(row.perResult) >= 85 ? 'success-row' : Number(row.perResult) <=65 ? 'warning-row' :
    'danger-row'"
    border style="width: 100%" height="550">
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
  </div>
  <!--  绩效考核步骤对话框-->
  <el-dialog title="绩效考核" v-model="performanceDialogVisible" width="30%" :close-on-click-modal="false">
    <el-steps :active="activeStep" finish-status="success" align-center>
      <el-step title="填写考核内容" description="填写考核内容"/>
      <el-step title="选择考核人" description="选择考核人"/>
      <el-step title="提交" description="提交"/>
    </el-steps>
    <div v-if="activeStep === 0">
      <el-select v-model="performanceForm.perContentIds" placeholder="请选择考核内容" multiple style="width: 100%">
        <el-option v-for="item in performTypeList" :key="item.typeId" :label="item.content" :value="item.typeId"/>
      </el-select>
      <div>
        <el-empty v-if="performanceForm.perContentIds?.length === 0" description="暂无数据，请选择考核方式"/>
        <el-table v-else :data="selectPerTypeList" border style="width: 100%" height="300">
          <el-table-column label="考核内容" prop="content"/>
          <el-table-column label="权重" prop="weight"/>
          <el-table-column label="操作">
            <template #default="{row}">
              <el-button type="text" @click="editPerType(row)">编辑权重</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <div v-if="activeStep === 1">
      <el-form :model="performanceForm" label-width="150px">
        <el-form-item label="考核对象" prop="perContent">
          <el-select v-model="performanceForm.perType" placeholder="请选择考核对象" style="width: 66%">
            <el-option label="收费员" value="收费员"/>
            <el-option label="车辆疏导员" value="车辆疏导员"/>
          </el-select>
        </el-form-item>
        <el-form-item label="考核月份" prop="perTime">
          <el-date-picker v-model="performanceInfoForm.startTime" type="month" placeholder="选择日期时间"
                          format="YYYY-MM" value-format="YYYY-MM"/>
        </el-form-item>
        <!--        <el-form-item label="考核区间结束时间" prop="perTime">-->
        <!--          <el-date-picker v-model="performanceInfoForm.endTime" type="date" placeholder="选择日期时间" format="YYYY-MM-DD" value-format="YYYY-MM-DD"/>-->
        <!--        </el-form-item>-->
      </el-form>
    </div>
    <div v-if="activeStep === 2">
      <p>考核内容：{{ performanceForm.perContent }}</p>
      <p>考核时间：{{ performanceForm.perTime }}</p>
      <p>考核对象：{{ performanceForm.perType }}</p>
    </div>
    <div slot="footer" align="center" class="dialog-footer">
      <el-button @click="activeStep--" v-if="activeStep > 0">上一步</el-button>
      <el-button @click="nextStep()" v-if="activeStep < 2">下一步</el-button>
      <el-button type="primary" @click="submitPerformanceForm()" v-if="activeStep === 2">提交</el-button>
    </div>
  </el-dialog>
  <!--  修改绩效考核权重-->
  <el-dialog title="修改绩效考核权重" v-model="editPerTypeDialogVisible" width="30%">
    <el-form :model="editPerTypeForm" label-width="100px">
      <!--      <el-form-item label="考核内容" prop="content">-->
      <!--        <el-input v-model="editPerTypeForm.content" placeholder="请输入考核内容" />-->
      <!--      </el-form-item>-->
      <el-form-item label="权重" prop="weight">
        <!--        <el-input v-model="editPerTypeForm.weight" type="number"  placeholder="请输入权重"/>-->
        <el-input-number v-model="editPerTypeForm.weight" :min="0" :max="2" :step="0.1"/>
      </el-form-item>
    </el-form>
    <div slot="footer" align="center" class="dialog-footer">
      <el-button @click="editPerTypeDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitEditPerTypeForm">确 定</el-button>
    </div>
  </el-dialog>
<!--  上级评价对话框-->
  <el-dialog title="上级评价" v-model="superiorDialogVisible" width="40%" :close-on-click-modal="false" :before-close="checkDataList">
    <el-table :data="superiorFormList" border style="width: 100%" height="300">
      <el-table-column v-for="item in performanceColumns" :key="item.prop" :label="item.label" :prop="item.prop">
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{row}">
          <el-button type="text" @click="editPerResult(row)">打分</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
<!--  修改结果对话框-->
  <el-dialog title="修改结果" v-model="editPerResultVisible" width="30%">
    <el-form :model="performanceForm" label-width="150px">
      <el-form-item label="备注" prop="perRemark">
        <el-input v-model="performanceForm.perResult" placeholder="请输入分数"/>
      </el-form-item>
    </el-form>
    <div slot="footer" align="center" class="dialog-footer">
      <el-button @click="editPerResultVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitEditPerResultForm()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<style lang="less">
.el-table .warning-row {
  //--el-table-tr-bg-color: var(--el-color-warning-light-9);
  background-color: #f89898;
}
.el-table .success-row {
  //--el-table-tr-bg-color: var(--el-color-success-light-9);
  background-color: #95d475;
}
</style>
