<template>
    <div>
        <el-form-item label="招聘岗位" prop="jobName">
            <el-select v-model="addOrUpdateParams.jobName" class="m-2" placeholder="请选择招聘岗位" size="large" v-show="screen">
                <el-option v-for="item in jobNameOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
            <el-input v-model="addOrUpdateParams.jobName" placeholder="请输入招聘岗位" v-show="!screen"></el-input>
            <el-icon @click="screen = !screen" size="24px" style="margin-top: 10px;" title="其他">
                <ArrowRight />
            </el-icon>
        </el-form-item>
        <el-form-item label="工作地址" prop="jobAddress">
            <el-cascader size="large" :options="pcaTextArr" v-model="useOtherStore.selectedOptions"
                :placeholder="addOrUpdateParams.jobAddress" @blur="jobAddressBlur">
            </el-cascader>
        </el-form-item>
        <el-form-item label="所需学历" prop="education">
            <el-select v-model="addOrUpdateParams.education" class="m-2" placeholder="请选择所需学历" size="large">
                <el-option v-for="item in educationOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
        </el-form-item>
        <el-form-item label="工作经验" prop="jobExperience">
            <el-select v-model="addOrUpdateParams.jobExperience" class="m-2" placeholder="请选择工作经验" size="large">
                <el-option v-for="item in jobExperienceOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
        </el-form-item>
        <el-form-item label="最低工资" prop="minSalary">
            <el-select v-model="addOrUpdateParams.minSalary" class="m-2" placeholder="请选择最低工资" size="large">
                <el-option v-for="item in minSalaryOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
        </el-form-item>
        <el-form-item label="最高工资" prop="maxSalary">
            <el-select v-model="addOrUpdateParams.maxSalary" class="m-2" placeholder="请选择最高工资" size="large">
                <el-option v-for="item in maxSalaryOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
        </el-form-item>
        <el-form-item label="所需人数" prop="needPeople">
            <el-select v-model="addOrUpdateParams.needPeople" class="m-2" placeholder="请选择所需人数" size="large">
                <el-option v-for="item in needPeopleOptions" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
        </el-form-item>
    </div>
</template>

<script setup lang="ts">
import { pcaTextArr } from 'element-china-area-data'
import { ref, toRefs  } from 'vue'
import otherStore from '@/store/modules/other'
let useOtherStore = otherStore()
// 接受父组件传递过来的数据
const props = defineProps(['addOrUpdateParams'])
const { addOrUpdateParams } = toRefs(props)
// 控制招聘岗位场景
let screen = ref<boolean>(true)
// 存储地区数据的数组
// let selectedOptions = ref<string[]>([])
// 创建各种选项的数组
let jobNameOptions = ref([
    {
        value: 'java工程师',
        label: 'java工程师'
    },
    {
        value: '前端开发工程师',
        label: '前端开发工程师'
    },
    {
        value: '.NET工程师',
        label: '.NET工程师'
    },
    {
        value: '项目架构师',
        label: '项目架构师'
    },
    {
        value: '保洁',
        label: '保洁'
    }, {
        value: '保安',
        label: '保安'
    }, {
        value: '网络安全工程师',
        label: '网络安全工程师'
    },
])
let educationOptions = ref([
    {
        value: '本科',
        label: '本科'
    },
    {
        value: '大专',
        label: '大专'
    },
    {
        value: '硕士',
        label: '硕士'
    },
    {
        value: '研究生',
        label: '研究生'
    },
    {
        value: '博士',
        label: '博士'
    }, {
        value: '博士后',
        label: '博士后'
    }, {
        value: '不限学历',
        label: '不限学历'
    },
])
let jobExperienceOptions = ref([
    {
        value: '1年',
        label: '1年'
    },
    {
        value: '2年',
        label: '2年'
    },
    {
        value: '3年',
        label: '3年'
    },
    {
        value: '4年',
        label: '4年'
    },
    {
        value: '5年',
        label: '5年'
    }, {
        value: '5年以上',
        label: '5年以上'
    }, {
        value: '不限经验',
        label: '不限经验'
    },
])
let minSalaryOptions = ref([
    {
        value: 1000,
        label: '1K'
    },
    {
        value: 2000,
        label: '2K'
    },
    {
        value: 3000,
        label: '3K'
    },
    {
        value: 4000,
        label: '4K'
    },
    {
        value: 5000,
        label: '5K'
    }, {
        value: 6000,
        label: '6K'
    }, {
        value: 7000,
        label: '7K'
    },
    {
        value: 8000,
        label: '8K'
    },
    {
        value: 9000,
        label: '9K'
    },
    {
        value: 10000,
        label: '10K'
    },
    {
        value: 11000,
        label: '11K'
    },
    {
        value: 12000,
        label: '12K'
    }, {
        value: 13000,
        label: '13K'
    }, {
        value: 14000,
        label: '14K'
    },
    {
        value: 15000,
        label: '15K'
    },
    {
        value: 16000,
        label: '16K'
    },
    {
        value: 17000,
        label: '17K'
    },
    {
        value: 18000,
        label: '18K'
    },
    {
        value: 19000,
        label: '19K'
    }, {
        value: 20000,
        label: '20K'
    }, {
        value: 25000,
        label: '25K'
    },
])
let maxSalaryOptions = ref([
    {
        value: 1000,
        label: '1K'
    },
    {
        value: 2000,
        label: '2K'
    },
    {
        value: 3000,
        label: '3K'
    },
    {
        value: 4000,
        label: '4K'
    },
    {
        value: 5000,
        label: '5K'
    }, {
        value: 6000,
        label: '6K'
    }, {
        value: 7000,
        label: '7K'
    },
    {
        value: 8000,
        label: '8K'
    },
    {
        value: 9000,
        label: '9K'
    },
    {
        value: 10000,
        label: '10K'
    },
    {
        value: 11000,
        label: '11K'
    },
    {
        value: 12000,
        label: '12K'
    }, {
        value: 13000,
        label: '13K'
    }, {
        value: 14000,
        label: '14K'
    },
    {
        value: 15000,
        label: '15K'
    },
    {
        value: 16000,
        label: '16K'
    },
    {
        value: 17000,
        label: '17K'
    },
    {
        value: 18000,
        label: '18K'
    },
    {
        value: 19000,
        label: '19K'
    }, {
        value: 20000,
        label: '20K'
    }, {
        value: 25000,
        label: '25K'
    },
])
let needPeopleOptions = ref([
    {
        value: 1,
        label: '1'
    },
    {
        value: 2,
        label: '2'
    },
    {
        value: 3,
        label: '3'
    },
    {
        value: 4,
        label: '4'
    },
    {
        value: 5,
        label: '5'
    },
    {
        value: 7,
        label: '7'
    },
    {
        value: 10,
        label: '10'
    },
])
// 把选中的地区转成为字符串
const jobAddressBlur = () => {
    // @ts-ignore
    addOrUpdateParams.value.jobAddress = useOtherStore.selectedOptions.join("")
}
</script>

<style scoped>
.el-form-item ::v-deep .el-input__wrapper {
    margin-top: 12px;
}

.el-input {
    height: 52px;
    width: 207px;
}


</style>