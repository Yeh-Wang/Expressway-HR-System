<template>
  <el-header>
    <div class="l-content">
      <!-- <el-breadcrumb separator="/"> -->
      <!--        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>-->
      <!-- <el-breadcrumb-item>{{ pathList.title }}</el-breadcrumb-item> -->
      <!--        <el-breadcrumb-item v-for="item in setPathList" :key="item.path" :to="{ path: item.path }">{{item.meta.title}}</el-breadcrumb-item>-->
      <!-- </el-breadcrumb> -->
      <el-tabs style="margin-top: 10px;" type="card" closable :model-value="$route.path" @tab-click="onClickTab" @tab-remove="removeTab">
        <el-tab-pane v-for="(item, index) in setPathList" :key="index" :label="item.label" :name="item.path" />
      </el-tabs>
    </div>
    <div class="r-content">
      <el-dropdown @command="handleClick">
        <span class="el-dropdown-link">
          <el-avatar class="user" :src="useUser().loginUser.avatar" alt="头像" />
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="a">我的</el-dropdown-item>
            <el-dropdown-item command="b">退出</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>
</template>

<script setup lang="ts">
// import {setPathList} from "./menuHeader/options.ts";
import { TabPaneName, TabsPaneContext } from "element-plus";
import { useUser } from "../../store/home.ts";
import { pathList, setPathList } from "../menu/optionType/options.ts";
import {getUserStaffInfo} from "../../api/PersonCenter";

const router = useRouter()
// const test = () => {
// }
const onClickTab = (pane: TabsPaneContext, _event: Event) => {
  router.push(pane.paneName as string)
}
const removeTab = (targetName: TabPaneName) => {
  setPathList.splice(setPathList.findIndex(item => item.path === targetName), 1)
  if (setPathList.length === 0) {
    router.push('/home/index')
  } else {
    router.push(setPathList[setPathList.length - 1].path)
  }
}

//侦听路由变化实现面包屑导航
watch(() => router.currentRoute.value, (newVal, _oldValue) => {
  if(newVal.name === 'user-exam-view'){
    return
  }else{
    pathList.title = newVal.meta.title as string
    pathList.path = newVal.path
    if (!setPathList.find(item => item.path === newVal.path)) {
      setPathList.push({
        path: newVal.path,
        name: newVal.name as string,
        label: newVal.meta.title as string
      })
    }
    //去除重复项
    setPathList.forEach((item, index) => {
      if (setPathList.findIndex(i => i.path === item.path) !== index) {
        setPathList.splice(index, 1)
      }
    })
  }
})

//个人中心
const handleClick = (command: string) => {
  if (command === 'b') {
    // 删除token和store中的用户信息
    useUser().clearUser()
    useUser().clearStorage()
    router.push('/')
  } else {
    getUserStaffInfo(useUser().loginUser.staffId).then(res => {
      useUser().setPersonInfo(res.userInfo, res.staffInfo)
    })
    router.push('/home/personal-center')
  }
}
</script>

<style lang="less" scoped>
header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 100%;
  background: #e1dbdb;
}

.l-content {
  display: flex;
  align-items: center;

  .el-button {
    margin-right: 16px;
  }

  h3 {
    color: #fff;
  }
}

.r-content {
  .user {
    width: 40px;
    height: 40px;
    border-radius: 50%;
  }
}
</style>



<!--<script setup lang="ts">-->

<!--</script>-->
<!--&lt;!&ndash;Header模板&ndash;&gt;-->
<!--<template>-->

<!--&lt;!&ndash;  header&ndash;&gt;-->
<!--  <div class="header-bar">-->
<!--  <el-row>-->
<!--    <el-col :span="12"><div class="grid-content ep-bg-purple-dark" >-->
<!--      <el-breadcrumb separator="/">-->
<!--        <el-breadcrumb-item :to="{ path: '/home' }">homepage</el-breadcrumb-item>-->
<!--        <el-breadcrumb-item>header</el-breadcrumb-item>-->
<!--      </el-breadcrumb>-->
<!--    </div></el-col>-->
<!--    <el-col :span="12"><div class="grid-content ep-bg-purple-dark" >-->

<!--    </div></el-col>-->
<!--  </el-row>-->
<!--  </div>-->
<!--</template>-->

<!--<style scoped>-->
<!--.header-bar{-->
<!--  .el-col{-->
<!--    padding-top: 20px;-->
<!--    padding-left: 20px;-->
<!--  }-->
<!--}-->
<!--</style>-->
