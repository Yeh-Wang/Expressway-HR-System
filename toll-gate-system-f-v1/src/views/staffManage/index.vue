<script setup lang="ts">
import { ElMessage } from "element-plus";
import { addStaffInfo, changeStaffInfo, deleteStaffInfo, getStaffInfo } from "../../api/StaffManage/staff-management.ts";
import { formDataType } from "../../components/FromCom/type";
import { staffUserInfo, tableColumns } from "./type.ts";
import { useMain } from "../../store/home.ts";
import {getRoleAuth} from "../../api/Login/userLogin.ts";

//定义响应式数组数组接收后端返回的数据
const myStaffInfo = reactive({
  staffInfo: [] as staffUserInfo[]
})
//获取登录用户所属的职员信息
onBeforeMount(() => {
  getStaffInfo().then(res => {
    myStaffInfo.staffInfo = res
  })
})
const dialogVisible = ref(false)
const formData: formDataType<staffUserInfo> = {
  "data": {} as staffUserInfo,
  "message": [{
    label: "姓名",
    type: "name",
    name: "staffName"
  },
  {
    label: "民族",
    type: "nation",
    name: "staffNation"
  },
  {
    label: "性别",
    type: "gender",
    name: "gender"
  },
  {
    label: "年龄",
    type: "age",
    name: "age"
  },
  {
    label: "学历",
    type: "degree",
    name: "staffDegree"
  },
  {
    label: "岗位",
    type: "post",
    name: "staffPost"
  },
  {
    label: "政治面貌",
    type: "politicalStatus",
    name: "staffPolitics"
  },
  {
    label: "联系电话",
    type: "phone",
    name: "staffPhone"
  },
  {
    label: "籍贯",
    type: "address",
    name: "staffAddress"
  },
  {
    label: "身份证号",
    type: "idCard",
    name: "staffCard"
  }
    // {
    //   label: "所属收费站",
    //   type: "tollStation",
    //   name: "staffStation"
    // }
  ]
}
const flag = ref(1)  //1 编辑  2  增加
const dialogTitle = computed(() => flag.value == 1 ? "编辑信息" : "添加信息")
const handleEdit = (_index: number, row: staffUserInfo) => {
  dialogVisible.value = true
  formData.data = row
  flag.value = 1
}
const onSubmit = (staffInfo: staffUserInfo) => {
  if (flag.value == 1) {
    ElMessage.success("修改成功")
    changeStaffInfo(staffInfo)
  }
  else
    addStaffInfo(staffInfo).then(res => {
      ElMessage.success("添加成功")
      myStaffInfo.staffInfo.push(res)
    })
  dialogVisible.value = false
}

const addStaff = () => {
  useMain().setPostList()
  dialogVisible.value = true
  formData.data = {} as staffUserInfo
  formData.data.staffAddress = "北京市/北京市/东城区"
  flag.value = 2
}

const handleDelete = (_index: number, row: staffUserInfo) => {
  deleteStaffInfo(row.staffId).then(_res => {
    ElMessage.success("删除成功")
    myStaffInfo.staffInfo = myStaffInfo.staffInfo.filter(item => item.staffId != row.staffId)
  })
}

const search = ref("")
const filterStaffInfo = computed(() => {
  return myStaffInfo.staffInfo.filter(item => !search.value || item.staffName.includes(search.value) || item.postName.includes(search.value))
})

onBeforeUpdate(() => {
  getRoleAuth()
})
</script>

<template>
  <el-row style="margin-bottom: 10px;">
    <el-col :span="20">
      <el-tooltip placement="top" content="添加">
        <el-button type="success" plain style="margin-left: 20px" icon="Plus" @click="addStaff" />
      </el-tooltip>
    </el-col>
    <el-col :span="4">
      <el-input placeholder="search" v-model="search" />
    </el-col>
    <!-- <el-col :span="1" > -->

    <!-- </el-col> -->
  </el-row>
  <!--  表格展示职员信息-->
  <el-table :data="filterStaffInfo" border style="width: 100%" height="600">
    <el-table-column type="index" width="50" />
    <el-table-column v-for="(item, key) in tableColumns" :prop="item.prop" :label="item.label" :key="key" />
    <el-table-column fixed="right" align="center" label="Operations" width="120">
      <template #default="editItem">
        <el-button link type="primary" size="small" @click="handleEdit(editItem.$index, editItem.row)">Edit</el-button>
        <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(editItem.$index, editItem.row)">
          <template #reference>
            <el-button link type="danger" size="small">Delete</el-button>
          </template>
        </el-popconfirm>
      </template>
    </el-table-column>
  </el-table>
  <!-- 表单-->
  <el-dialog width="500px" v-model="dialogVisible" :title="dialogTitle" destroy-on-close>
    <FormCom :formData="formData" @submit="onSubmit" />
  </el-dialog>
</template>

<style scoped lang="less"></style>
