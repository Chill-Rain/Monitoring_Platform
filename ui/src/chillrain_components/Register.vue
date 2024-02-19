<script lang="ts" setup>
import { reactive, ref } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'

interface RuleForm {
  name: string
  region: string
  count: string
  date1: string
  date2: string
  delivery: boolean
  type: string[]
  resource: string
  desc: string
}

const formSize = ref('default')
const ruleFormRef = ref<FormInstance>()
const ruleForm = reactive<RuleForm>({

})

const rules = reactive<FormRules<RuleForm>>({
  name: [
    { required: true, message: 'Please input Activity name', trigger: 'blur' },
    { min: 3, max: 5, message: 'Length should be 3 to 5', trigger: 'blur' },
  ]
})
const registerConfig = reactive({
  title: "Register",
  show: true,
  button: [
    {
      type: "primary",
      text: "Get Email Code",
      click: () => {
        getCode()
      }
    }
  ]
});
// 获取邮箱验证码
const getCode = () =>{

}
</script>
<template>
  <Dialog title="Register" buttons="">
    <el-form
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width="120px"
        class="demo-ruleForm"
        :size="formSize"
        status-icon>
      <el-form-item label="Email" prop="name">
        <el-input v-model="ruleForm.email" />
      </el-form-item>
      <el-form-item label="Password" prop="name">
        <el-input v-model="ruleForm.password" />
      </el-form-item>
      <el-form-item label="Email Code" prop="name">
        <el-input v-model="ruleForm.code" />
      </el-form-item>
    </el-form>
  </Dialog>
</template>
<style scoped>

</style>