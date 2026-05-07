<template>
  <el-menu class="el-menu-vertical-demo" background-color="#545c64" text-color="#fff" :collapse-transition="false"
    active-text-color="#ffd04b" :router="true" :default-active="$route.path" mode="vertical" unique-opened>
    <h3>智慧收费站</h3>
    <template v-for="item in useUser().listMenu">
      <el-sub-menu v-if="item.child && item.child.length > 0" :index="item.name">
        <template #title>
          <el-icon>
            <component :is="item.icon"></component>
          </el-icon>
          <span>{{ item.label }}</span>
        </template>
        <el-menu-item v-for="child in item.child" :index="child.path">
          <el-icon>
            <component :is="child.icon"></component>
          </el-icon>
          <span>{{ child.label }}</span>
        </el-menu-item>
      </el-sub-menu>
      <el-menu-item v-else :index="item.path">
        <el-icon>
          <component :is="item.icon"></component>
        </el-icon>
        <span>{{ item.label }}</span>
      </el-menu-item>
    </template>

  </el-menu>
</template>


<script setup lang="ts">
import { useUser } from "../../store/home.ts";
</script>

<style lang="less" scoped>
.icons {
  width: 18px;
  height: 18px;
  margin-right: 4px;
}

.el-menu-vertical-demo {
  width: 100%;
  border-right: none;

  h3 {
    color: #fff;
    text-align: center;
    margin-top: 10px;
  }
}
</style>
