<!-- 封装表单 -->
<template>
    <el-form :model="formData.data">
        <el-form-item v-for="(item, index) in formData.message" :label="item.label" :key="index">
            <el-select v-if="item.type == 'gender'" v-model="formData.data[item.name]" placeholder="select"
                style="width: 100px;">
                <el-option label="男" value="男" />
                <el-option label="女" value="女" />
            </el-select>
            <el-input-number v-else-if="item.type == 'age'" v-model="formData.data[item.name] as number" :min="18"
                :max="80" controls-position="right"></el-input-number>
            <el-select v-else-if="item.type == 'degree'" v-model="formData.data[item.name]" placeholder="select"
                style="width: 100px;">
                <el-option label="初中" value="初中" />
                <el-option label="高中" value="高中" />
                <el-option label="本科" value="本科" />
                <el-option label="研究生" value="研究生" />
                <el-option label="博士" value="博士" />
            </el-select>
            <el-cascader v-else-if="item.type == 'address'" size="large" :options="pcaTextArr as any[]"
                v-model="address">
            </el-cascader>
            <el-select v-else-if="item.type == 'post'" v-model="formData.data[item.name]" placeholder="select"
                style="width: 100px;">
                <el-option v-for="(item1, index) in useMain().postList" :key="index" :label="item1.postName"
                    :value="item1.postId" />
            </el-select>
            <el-date-picker v-else-if="item.type == 'time'" v-model="formData.data[item.name]" type="datetime"
                placeholder="Pick a Date" format="YYYY-MM-DD HH:mm:ss" date-format="MMM DD, YYYY" time-format="HH:mm" />
            <el-select v-else-if="item.type == 'punch'" v-model="formData.data[item.name]" placeholder="select"
                style="width: 100px;">
                <el-option label="上班" :value="1 as number" />
                <el-option label="下班" :value="2 as number" />
            </el-select>
          <el-select v-else-if="item.type == 'punStation'" v-model="formData.data[item.name]" placeholder="select"
                style="width: 200px;">
                <el-option label="一号人工收费亭" value="一号人工收费亭" />
                <el-option label="二号人工收费亭" value="二号人工收费亭" />
                <el-option label="三号人工收费亭" value="三号人工收费亭" />
                <el-option label="ETC车道1" value="ETC车道1" />
                <el-option label="ETC车道2" value="ETC车道2" />
            </el-select>
          <el-input v-else-if="item.type == 'text'" type="textarea" v-model="formData.data[item.name]"></el-input>
            <el-input v-else v-model="formData.data[item.name]"></el-input>
        </el-form-item>
        <br>
        <el-form-item>
            <el-button style="margin-left: 45%;" type="primary" @click="$emit('submit', formData.data)">submit</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup lang="ts">
import { useMain } from '../../store/home';
import {
    pcaTextArr,
} from "element-china-area-data";

// 组件接收的props
const props = defineProps(['formData'])
// 组件接收函数
const emit = defineEmits(['submit'])

const formData = reactive(props.formData)
const address = ref<string[]>(['', '', '']) // 省市区
watch(() => address.value, (newVal) => {
    formData.data.staffAddress = newVal.join('/')
})

onBeforeMount(() => {
    if (formData.data.staffAddress === undefined) {
    } else {
        address.value = formData.data.staffAddress.split('/')
    }
})
</script>
