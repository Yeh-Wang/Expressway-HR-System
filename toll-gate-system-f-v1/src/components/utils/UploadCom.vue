<script setup lang="ts">
import { uploadFile } from "../../utils/tencos.ts";
import { useUser } from "../../store/home.ts";
import { Plus } from "@element-plus/icons-vue";
import { ElMessage } from "element-plus";
import 'vue-cropper/dist/index.css'
import { VueCropper } from "vue-cropper";

const props = defineProps({
  avatarUrl: {
    type: String,
    default: ''
  }
})
const emit = defineEmits(['get-avatar-url'])

const imageUrl = ref(props.avatarUrl)


const fileUpload = async (file: { file: File; }) => {
  await uploadFile(file.file, useUser().loginUser.username).then(res => {
    imageUrl.value = "https://" + res
    ElMessage({
      message: 'upload successful.',
      type: 'success',
    })
    emit('get-avatar-url', imageUrl.value)
    // console.log("hello",res)
  })
}
</script>

<template>
  <!-- <vueCropper
  ref="cropper"
  :img="imageUrl"
></vueCropper> -->
  <el-tooltip content="点击上传头像" placement="top">
    <el-upload class="avatar-uploader" :http-request="fileUpload" :show-file-list="false" :auto-upload="true">
      <el-avatar :src="imageUrl" class="avatar" alt="">
      </el-avatar>
    </el-upload>
  </el-tooltip>
</template>

<style scoped lang="less">
.avatar-uploader {
  margin-left: 120px;
  width: 178px;
  height: 178px;
  display: block;
  .avatar {
    width: 178px;
    height: 178px;
  }
}
</style>
<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
