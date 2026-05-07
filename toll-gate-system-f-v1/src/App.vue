<script setup lang="ts">

import {Delete, Edit, Setting} from "@element-plus/icons-vue";

const contextMenu = ref({
  x: 0,
  y: 0,
  visible: false,
});
const onContextMenu = (e: MouseEvent) => {
  e.preventDefault();
  contextMenu.value.x = e.clientX;
  contextMenu.value.y = e.clientY;
  contextMenu.value.visible = true;
  setTimeout(() => {
    contextMenu.value.visible = true;
  }, 100);
}
onBeforeMount(() => {
  window.addEventListener('click', () => {
    contextMenu.value.visible = false;
  });
});
</script>


<template>
  <div @contextmenu="onContextMenu">
    <RouterView/>
    <!-- 右键菜单 -->
    <div class="context-menu" :class="{ visible: contextMenu.visible }" v-show="contextMenu.visible"
         :style="{ position: 'absolute', top: contextMenu.y + 'px', left: contextMenu.x + 'px' }">
      <el-menu>
        <el-menu-item>
          <el-icon>
            <Edit/>
          </el-icon>
          edit
        </el-menu-item>
        <el-menu-item>
          <el-icon>
            <Delete/>
          </el-icon>
          delete
        </el-menu-item>
        <el-menu-item>
          <el-icon>
            <Setting/>
          </el-icon>
          setting
        </el-menu-item>
      </el-menu>
    </div>
  </div>
</template>


<style scoped>
.context-menu {
  background-color: #f0f0f0;
  border: 1px solid #ccc;
  border-radius: 10px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.15);
  padding: 10px;
  z-index: 1000;
  transition: opacity 0.2s ease-in-out, transform 0.2s ease-in-out;
  transform: scale(0.95);
  opacity: 0.2;
}

.context-menu.visible {
  transform: scale(1);
  opacity: 1;
}

.el-menu {
  background-color: transparent;
  border: none;
}

.el-menu-item {
  color: #333;
  font-size: 15px;
  line-height: 1;
  border-radius: 10px;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
}

.el-menu-item:hover {
  background-color: #007bff;
  color: #fff;
  border-radius: 10px;
}
</style>
