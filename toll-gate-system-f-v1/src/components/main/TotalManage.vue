<script setup lang="ts">
import {addPublicNotice, deletePublicNotice, getPublicNotice, updatePublicNotice} from "../../api/HomeShow";
import {listPublicNotice, PublicNoticeVo} from "../../views/HomeShow/type.ts";
import {useUser} from "../../store/home.ts";
import moment from "moment";
import {ElMessageBox} from "element-plus";

onBeforeMount(() => {
  getPublicNotice().then(res => {
    listPublicNotice.list = res
  })
})

const dialogVisible = ref(false)
const updateDialogVisible = ref(false)
const form = ref({} as PublicNoticeVo)
const submitForm = () => {
  form.value.pubId = 0;
  form.value.pubCreate = useUser().loginUser.staffId
  form.value.pubStatus = 1
  form.value.pubTime = moment().format('YYYY-MM-DD HH:mm:ss')
  addPublicNotice(form.value).then(res => {
    form.value = {} as PublicNoticeVo
    ElMessage.success('新增成功')
    getPublicNotice().then(res => {
      listPublicNotice.list = res
    })
  })
  dialogVisible.value = false
}
const handleEdit = (row: PublicNoticeVo) => {
  updateDialogVisible.value = true
  form.value = row
}
const handleDelete = (row: PublicNoticeVo) => {
  ElMessageBox.confirm('此操作将永久删除该公告, 是否继续?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    deletePublicNotice(row.pubId as number).then(res => {
      ElMessage.success('删除成功')
      getPublicNotice().then(res => {
        listPublicNotice.list = res
      })
    })
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}
const submitUpdateForm = () => {
  updatePublicNotice(form.value).then(res => {
    ElMessage.success('修改成功')
    getPublicNotice().then(res => {
      listPublicNotice.list = res
    })
  })
  updateDialogVisible.value = false
}
</script>

<template>
<!--  新增公告对话框-->
  <el-dialog v-model="dialogVisible" title="新增公告" width="30%">
    <el-form v-model="form">
      <el-form-item label="标题" prop="pubTitle">
        <el-input v-model="form.pubTitle"/>
      </el-form-item>
      <el-form-item label="内容" prop="pubContent">
        <el-input type="textarea" :rows="3" v-model="form.pubContent"/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitForm">确 定</el-button>
    </span>
  </el-dialog>
<!--  修改公告对话框-->
  <el-dialog v-model="updateDialogVisible" title="修改公告" width="30%">
    <el-form v-model="form" >
      <el-form-item label="标题" prop="pubTitle">
        <el-input v-model="form.pubTitle"/>
      </el-form-item>
      <el-form-item label="内容" prop="pubContent">
        <el-input type="textarea" :rows="3" v-model="form.pubContent"/>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="updateDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitUpdateForm">确 定</el-button>
    </span>
  </el-dialog>
<!--  操作行-->
  <el-row>
    <el-col :span="2"/>
    <el-col :span="14">
      <el-button type="success" @click="dialogVisible = true">新增公告</el-button>
    </el-col>
    <el-col :span="4">
<!--      <el-input v-model="searchRew" placeholder="请输入搜索内容" style="width: 200px"></el-input>-->
    </el-col>
    <el-col :span="4"/>
  <el-divider></el-divider>
  </el-row>
  <el-row>
    <el-col :span="5"/>
    <el-col :span="14">
      <el-empty v-if="listPublicNotice.list.length == 0" description="目前没有公告"/>
      <el-card v-else class="box-card">
        <div slot="header" class="clearfix">
          <el-tag type="danger">公告</el-tag>
        </div>
        <el-divider/>
        <el-table :data="listPublicNotice.list" stripe height="400" style="width: 100%">
          <el-table-column prop="pubTitle" label="标题" />
          <el-table-column prop="pubContent" label="内容"/>
          <el-table-column prop="pubTime" header-align="center" align="right" label="时间"/>
          <el-table-column label="操作" align="center">
            <template #default="{row}">
              <el-button type="text" @click="handleEdit(row)">编辑</el-button>
              <el-button type="text" @click="handleDelete(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="1"/>
    <el-col :span="3">
    </el-col>
    <el-col :span="1"/>
  </el-row>
</template>

<style scoped lang="less">

</style>
