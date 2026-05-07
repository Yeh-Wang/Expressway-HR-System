<script setup lang="ts">
import {listPublicNotice, PublicNoticeVo} from "./type.ts";
import {getPublicNotice} from "../../api/HomeShow";
import {inject, onBeforeUnmount} from "vue";

onBeforeMount(() => {
  getPublicNotice().then(res => {
    listPublicNotice.list = res
    console.log(listPublicNotice.list[0].pubTime)
  })
})
//点击行展示公告详细信息
const showDialogVisible = ref(false)
const nowNotice: PublicNoticeVo = reactive({
  data: {} as PublicNoticeVo
})

const handleClick = (row: PublicNoticeVo, column: any, event: Event) => {
  showDialogVisible.value = true
  nowNotice.data = row
}


//echarts

// 获取echart挂载的DOM节点
const container = ref();
const container1 = ref();


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
  const myChart1 = (Echarts as any).init(container1.value);

  // 或
  // let myChart = proxy.$echarts.init(container.value);

 const option = {
    title: {
      text: '今日打卡情况',
      subtext: '',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 4, name: '未打卡' },
          { value: 6, name: '已打卡' },
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };
  const option1 = {
    title: {
      text: '今日学习情况',
      subtext: '',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 4, name: '> 1 小时' },
          { value: 6, name: '> 30 分钟' },
          { value: 8, name: '> 15 分钟' },
          { value: 10, name: '< 15 分钟' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  };

  myChart.setOption(option);
  myChart1.setOption(option1);

  // 根据页面大小自动响应图表大小
  window.addEventListener("resize", function () {
    myChart.resize();
    myChart1.resize();
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

<template>
  <el-dialog v-model="showDialogVisible" width="30%" style="height: 400px">
    <div slot="header" class="clearfix">
      <el-tag type="danger">公告</el-tag>
    </div>
    <el-divider/>
    <span> {{ nowNotice.data.pubContent }} </span>
    <template #footer>
      <div class="dialog-footer">
        <p><el-tag type="success">{{ nowNotice.data.pubCreateName }}</el-tag></p>
        <span>{{ nowNotice.data.pubTime }} </span>
      </div>
    </template>
  </el-dialog>

  <el-row>
    <el-col :span="4"/>
    <el-col :span="14">
<!--      <el-carousel indicator-position="outside" :interval="3000">-->
<!--        <el-carousel-item v-for="item in ['我','爱','学','习']" :key="item">-->
<!--          <span class="el-carousel__item">{{ item }}</span>-->
<!--        </el-carousel-item>-->
<!--      </el-carousel>-->
      <!--      公告展示-->
      <el-empty v-if="listPublicNotice.list.length == 0" description="目前没有公告"/>
      <el-card v-else class="box-card">
        <div slot="header" class="clearfix">
          <el-tag type="danger">公告</el-tag>
        </div>
        <el-divider/>
        <el-table :data="listPublicNotice.list" @row-click="handleClick" stripe style="width: 100%">
          <el-table-column prop="pubTitle" label="标题"/>
          <el-table-column prop="pubTime" header-align="center" align="right" label="时间"/>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="2"/>
    <el-col :span="6">

    </el-col>
    <el-col :span="1">
    </el-col>
  </el-row>
  <el-divider/>
  <el-row>
    <el-col :span="2">

    </el-col>
    <el-col :span="9">
      <div id="echarts1" class="echarts-class" ref="container"></div>
    </el-col>
    <el-col :span="4">

  </el-col>
    <el-col :span="7">
      <div id="echarts" class="echarts-class" ref="container1"></div>
    </el-col>
    <el-col :span="2">

    </el-col>
  </el-row>
</template>

<style scoped lang="less">
.echarts-class{
  width: 280px;
  height: 280px;
}
.el-carousel__item h3 {
  display: flex;
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}

.el-carousel__item {
  text-align: center;
  height: 300px;
  line-height: 300px;
  opacity: 0.75;
  color: #475669;
  font-size: 20px;

  .span {
    color: #bc0c0c;
  }
}

.dialog-footer{
  margin-top: 160px;
}

.el-carousel {
  height: 220px;
}

.clearfix {
  .el-tag {
    margin-left: 220px;
  }
}

.box-card {
  height: 330px;
}
</style>
