<script setup lang="ts">
import {ref, inject, onBeforeUnmount} from 'vue';

// 获取echart挂载的DOM节点
const container = ref();

// 定义延时器指针对象，便于组件实例销毁的时候以便清除
const timer = ref();

// 通过 inject 接收Echarts
const Echarts = inject('$echarts');

// 或
// 通过Vue全局注册方式获取
// const {proxy}: any = getCurrentInstance();


const initEchartsOneFn = () => {
  // echarts初始化
  const myChart = (Echarts as any).init(container.value);

  // 或
  // let myChart = proxy.$echarts.init(container.value);

  const option = {
    // 自定义echarts图标相关配置
  };

  myChart.setOption(option);

  // 根据页面大小自动响应图表大小
  window.addEventListener("resize", function () {
    myChart.resize();
  });
}

// 判断定时器是否存在
if (timer.value) {
  clearTimeout(timer.value);
}

// 绑定定时器，销毁的时候再次清除
timer.value = setTimeout(() => initEchartsOneFn(), 1000);

// 组件实例销毁前清除延时器对象
onBeforeUnmount(() => {
  if (timer.value) clearTimeout(timer.value);
});
</script>

// 此处部分将就着看看
<template>
  <div id="echarts1" ref="container"></div>
</template>
