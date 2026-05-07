<template>
  <div class="common-layout">
    <!--    <el-button type="text" @click="test">测试按钮</el-button>-->
    <el-container>
      <el-aside class="aside-bar" width="180px">
        <!--        划分菜单-->
        <!--          <div class="logo-box">-->
        <!--            <el-avatar src="src/assets/login/avatar.svg"/><a>后台管理系统</a>-->
        <!--          </div>-->
        <Menu />
      </el-aside>
      <el-container>
        <el-header>
          <Header />
        </el-header>
        <el-main>
          <router-view />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup lang="ts">
// import { pathList, setPathList } from "../../components/menu/optionType/options.ts";
// import { TabPaneName, TabsPaneContext } from "element-plus";
// import { useRouter } from "vue-router";
// const router = useRouter()
// const test = () => {
// }
// const onClickTab = (pane: TabsPaneContext, _event: Event) => {
//   router.push(pane.paneName as string)
// }
// const removeTab = (targetName: TabPaneName) => {
//   setPathList.splice(setPathList.findIndex(item => item.path === targetName), 1)
//   if (setPathList.length === 0) {
//     router.push('/home')
//   } else {
//     router.push(setPathList[setPathList.length - 1].path)
//   }
// }

// onBeforeMount(() => {
//   setPathList.find(item => item.path === "") && setPathList.splice(setPathList.findIndex(item => item.path === ""), 1)
//   setPathList.push({
//     path: router.currentRoute.value.path as string,
//     name: router.currentRoute.value.name as string,
//     label: router.currentRoute.value.meta.title as string
//   })
// })
// //侦听路由变化实现面包屑导航
// watch(() => router.currentRoute.value, (newVal, _oldValue) => {
//   pathList.title = newVal.meta.title as string
//   pathList.path = newVal.path
//   if (!setPathList.find(item => item.path === newVal.path)) {
//     setPathList.push({
//       path: newVal.path,
//       name: newVal.name as string,
//       label: newVal.meta.title as string
//     })
//   }
// })
import {useUser} from "../../store/home.ts";
import router from "../../routes";
import {setPathList} from "../../components/menu/optionType/options.ts";

onBeforeMount(() => {
  setPathList.find(item => item.path === "") && setPathList.splice(setPathList.findIndex(item => item.path === ""), 1)
//   判断当前登录用户角色
  if(useUser().loginUser.roles[0] === "站长"){
    router.push("/home/index")
  }else{
    router.push("/home/user/index")
  }
})
</script>


<style lang="less" scoped>
.common-layout {
  width: 100vw;
  height: 100vh;
  background: #f5f5f5;
  margin: unset;

  &>.el-container {
    height: 100%;

    &>.el-aside {
      height: 100%;
      background: #545c64;
    }

    &>.el-container {
      &>.el-header {
        padding: 0%;
      }
    }
  }
}

.logo-box a {
  margin-bottom: 10px;
}
</style>
