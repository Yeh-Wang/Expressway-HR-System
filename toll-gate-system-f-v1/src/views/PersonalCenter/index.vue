<script setup lang="ts">
import { useUser } from "../../store/home.ts";
import {changeUserInfo, changeUserStaffInfo, getUserStaffInfo} from "../../api/PersonCenter";
import {
  pcaTextArr,
} from "element-china-area-data";
import { useMain } from "../../store/home.ts";
import { TabsPaneContext } from "element-plus";
import {Edit} from '@element-plus/icons-vue'
import router from "../../routes";
const userStore = useUser()
const personInfo = userStore.personInfo

onBeforeMount(() => {
  //获取用户所有信息
  // getUserStaffInfo(userStore.loginUser.staffId).then(res => {
  //   personInfo.userInfo = res.userInfo
  //   personInfo.staffInfo = res.staffInfo
  // })
  console.log(personInfo)
})
const changeAvatar = (url: string) => {
  personInfo.staffInfo.staffAvatar = url
  userStore.loginUser.avatar = url
}

onBeforeRouteLeave(() => {
  userStore.setPersonInfo(personInfo.userInfo, personInfo.staffInfo)
})

//右侧card
const activeName = ref('1')
const formEdit = ref(true)
const addressTmp = ref<string[]>(personInfo.staffInfo.staffAddress.split('/'))
watch(() => addressTmp.value, (newVal) => {
  personInfo.staffInfo.staffAddress = newVal.join('/')
})
const handleClick = (_tab: TabsPaneContext, _event: Event) => {
  // console.log(tab, event)
}
const handleSubmit = () => {
  if (activeName.value === '1') {
    //提交修改后的个人信息
    userStore.setPersonInfo(personInfo.userInfo, personInfo.staffInfo)
    changeUserStaffInfo(personInfo.staffInfo).then(_res => {
      ElMessage({
        message: '修改成功',
        type: 'success'
      })
      formEdit.value = true
    })
  } else {
    //提交修改后的密码
    if (oldPassword.value !== personInfo.userInfo.userPwd) {
      ElMessage({
        message: '旧密码错误',
        type: 'error'
      })
      return
    }
    personInfo.userInfo.userPwd = newPassword.value
    changeUserInfo(personInfo.userInfo).then(_res => {
      ElMessage({
        message: '修改成功',
        type: 'success'
      })
      router.push('/')
    })
  }
}
// 旧密码
const oldPassword = ref('')
// 新密码
const newPassword = ref('')
//密码强度条
const jj = ['warning', 'exception', 'success']
const prop = reactive(
  { value: 0, status: jj[0], msg: ''}
)
watch(() =>newPassword.value, (newVal) => {
  if(newVal.length == 0){
    prop.value = 0
    prop.status = jj[0]
    prop.msg = ''
  }else if (newVal.length < 6) {
    prop.value = 33
    prop.status = jj[1]
    prop.msg = '低'
  } else if (newVal.length < 10) {
    prop.value = 66
    prop.status = jj[0]
    prop.msg = '中'
  } else{
    prop.value = 100
    prop.status = jj[2]
    prop.msg = '高'
}})
</script>

<template>
  <n-grid x-gap="12" :cols="24" class="main-grid">
    <n-gi :span="8">
      <n-card header-style="text-align: center;" title="个人信息">
        <el-divider />
        <UploadCom :avatarUrl="personInfo.staffInfo.staffAvatar" @get-avatar-url="changeAvatar" />
        <el-divider />
        <p></p>
        <el-row>
          <el-col :span="6"></el-col>
          <el-col :span="9">账号：</el-col>
          <el-col :span="9">{{ personInfo.userInfo.userNickname }}</el-col>
        </el-row>
        <el-divider />
        <el-row>
          <el-col :span="6"></el-col>
          <el-col :span="9">姓名：</el-col>
          <el-col :span="9">{{ personInfo.staffInfo.staffName }}</el-col>
        </el-row>
        <el-divider />
        <el-row>
          <el-col :span="6"></el-col>
          <el-col :span="9">电话：</el-col>
          <el-col :span="9">{{ personInfo.staffInfo.staffPhone }}</el-col>
        </el-row>
        <el-divider />
        <el-row>
          <el-col :span="6"></el-col>
          <el-col :span="9">所属角色：</el-col>
          <el-col :span="9"><n-tag type="success">
              {{ userStore.loginUser.roles[0] }}
            </n-tag></el-col>
        </el-row>
      </n-card>
    </n-gi>
    <n-gi :span="16">
      <el-card style="margin-bottom: 16px">
        <template #header>
          <p style="text-align:center">亲爱的 <el-tag type="success">{{ personInfo.staffInfo.staffName }}</el-tag> 你是最棒的
          </p>
        </template>
        <el-tabs v-model="activeName" @tab-click="handleClick">
          <el-tab-pane name="1" label="基本信息">
            <!-- 修改表单 -->
            <div>
              <el-divider>
                <el-tooltip content="编辑" placement="top">
                  <el-button :icon="Edit" @click="formEdit = !formEdit" ircle />
                </el-tooltip>
              </el-divider>
            </div>
            <el-form :disabled="formEdit" :label-width="80" :model="personInfo.staffInfo">
              <el-row>
                <el-col :span="12">
                  <el-form-item label="姓名">
                    <el-input v-model:value="personInfo.staffInfo.staffName" placeholder="输入姓名" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="职位">
                    <el-select disabled v-model="personInfo.staffInfo.staffPost" placeholder="select"
                      style="width: 100px;">
                      <el-option v-for="(item1, index) in useMain().postList" :key="index" :label="item1.postName"
                        :value="item1.postId" />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="性别">
                    <el-select v-model="personInfo.staffInfo.gender" placeholder="select" style="width: 100px;">
                      <el-option label="男" value="男" />
                      <el-option label="女" value="女" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="年龄">
                    <el-input-number v-model="personInfo.staffInfo.age" :min="18" :max="80"
                      controls-position="right"></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="学历">
                    <el-select v-model="personInfo.staffInfo.staffDegree" placeholder="select" style="width: 100px;">
                      <el-option label="初中" value="初中" />
                      <el-option label="高中" value="高中" />
                      <el-option label="本科" value="本科" />
                      <el-option label="研究生" value="研究生" />
                      <el-option label="博士" value="博士" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="籍贯">
                    <el-cascader size="large" :options="pcaTextArr as any[]" v-model="addressTmp">
                    </el-cascader>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="民族">
                    <el-input v-model="personInfo.staffInfo.staffNation" placeholder="输入" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="政治面貌">
                    <el-input v-model="personInfo.staffInfo.staffPolitics" placeholder="输入" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <el-form-item label="电话号码">
                    <el-input v-model="personInfo.staffInfo.staffPhone" placeholder="输入" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="身份证号">
                    <el-input v-model="personInfo.staffInfo.staffCard" placeholder="输入" />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </el-tab-pane>
          <el-tab-pane name="2" label="修改密码">
            <el-form :label-width="80" :model="personInfo.userInfo">
              <el-form-item label="旧密码">
                <el-input v-model="oldPassword" type="password" placeholder="输入旧密码" />
              </el-form-item>
              <br><br>
              <el-form-item label="新密码">
                <el-input v-model="newPassword" type="password" placeholder="输入新密码" />
              </el-form-item>
              <el-row >
                <el-col :span="2">
                </el-col>
                <el-col :span="22">
                  <el-progress :stroke-width="12" :percentage="prop.value" :status="prop.status as '' | 'warning' | 'exception' | 'success'">
                <span>{{ prop.msg }}</span>
              </el-progress>
                </el-col>
              </el-row>
            </el-form>
          </el-tab-pane>
        </el-tabs>
        <template #footer>
          <el-button attr-type="button" @click="handleSubmit">
            确认
          </el-button>
        </template>
      </el-card>
    </n-gi>
  </n-grid>
</template>

<style scoped lang="less">
.main-grid {
  .n-card {
    margin-top: 40px;
    height: 600px;

    .upload-com {
      align-items: center;
    }
  }
}

.el-card {
  margin-top: 40px;
  height: 600px;

  .el-tabs {
    height: 380px;
  }

  .el-button {
    margin-left: 45%;
  }
}
</style>
