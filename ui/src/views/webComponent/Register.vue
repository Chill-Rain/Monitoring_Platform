<!--<script setup lang="ts">-->

<!--</script>-->
<template>
  <el-dialog
      :visible.sync="show"
      :append-to-body="true"
      :show-close="false"
      width="1000px"
  >
    <div slot="title" class="d-flex justify-content-between height50">
      <div class="dialogTitle">账号注册</div>
      <div class="font-14">
        已有账号？立即
        <span class="hand primaryCplor" @click="login">登录</span>
      </div>
    </div>
    <div class="dialogBody">
      <div class="text-center">
        <span
            class="tab hand"
            :class="{ parimaryBG: !isOrganization }"
            @click="isOrganization = false"
        >个人注册</span
        >
        <span
            class="tab hand"
            :class="{ parimaryBG: isOrganization }"
            @click="isOrganization = true"
        >机构注册</span
        >
      </div>
      <div class="mt-20">
        <div v-show="!isOrganization">
          <el-form :model="personelFrom" :rules="rules" ref="personel">
            <div class="border-bottom">
              <div class="typeTitle">账户信息</div>
            </div>
            <el-form-item label="用户名：" prop="name" class="">
              <el-input
                  v-model="personelFrom.name"
                  size=""
                  :placeholder="placeholder.name"
              ></el-input>
            </el-form-item>
            <el-form-item label="手机号：" prop="name" class="">
              <el-input
                  v-model="personelFrom.name"
                  :placeholder="placeholder.phone"
              ></el-input>
            </el-form-item>
            <el-form-item label="图片验证码：" prop="code" class="">
              <el-input
                  v-model="personelFrom.code"
                  :placeholder="placeholder.code"
              >
                <div slot="append">
                  <img
                      class="code"
                      src=""
                      alt="imgCode"
                  />
                </div>
              </el-input>
            </el-form-item>
            <el-form-item label="短信验证码：" prop="phoneCode" class="">
              <el-input
                  v-model="personelFrom.phoneCode"
                  :placeholder="placeholder.phoneCode"
              >
                <template slot="suffix">
                  <span class="primaryCplor mr-10">获取短信验证码</span>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item label="登录密码：" prop="password" class="">
              <el-input
                  v-model="personelFrom.password"
                  :placeholder="placeholder.password"
              ></el-input>
            </el-form-item>
            <el-form-item label="确认密码：" prop="passwordTest" class="">
              <el-input
                  v-model="personelFrom.passwordTest"
                  :placeholder="placeholder.passwordTest"
              ></el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                  type="primary"
                  @click="onSubmit()"
                  class="W-100 mt-20 parimaryBG border0"
              >注册</el-button
              >
            </el-form-item>
          </el-form>
        </div>
        <div v-show="isOrganization">
          <el-form :model="organizationForm" :rules="rules" ref="organization">
            <div class="typeTitle">机构信息</div>
            <el-form-item label="机构类型：" prop="organizationType" class="">
              <el-radio-group v-model="organizationForm.organizationType" class="W-100">
                <el-radio label="法人"></el-radio>
                <el-radio label="非法人组织"></el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="用户名：" prop="name" class="">
              <el-input
                  v-model="organizationForm.name"
                  :placeholder="placeholder.name"
              ></el-input>
            </el-form-item>
            <el-form-item label="单位名称：" prop="company" class="">
              <el-input v-model="organizationForm.company"  :placeholder="placeholder.company"></el-input>
            </el-form-item>
            <el-form-item label="统一社会信用代码：" prop="socialCreditCode" class="">
              <el-input v-model="organizationForm.socialCreditCode"  :placeholder="placeholder.socialCreditCode"></el-input>
            </el-form-item>
            <el-form-item label="登录密码：" prop="password" class="">
              <el-input
                  v-model="organizationForm.password"
                  :placeholder="placeholder.password"
              ></el-input>
            </el-form-item>
            <el-form-item label="确认密码：" prop="passwordTest" class="">
              <el-input
                  v-model="organizationForm.passwordTest"
                  :placeholder="placeholder.passwordTest"
              ></el-input>
            </el-form-item>
            <div class="typeTitle">经办人信息</div>
            <el-form-item label="经办人姓名：" prop="transactorName" class="">
              <el-input v-model="organizationForm.transactorName"  :placeholder="placeholder.transactorName"></el-input>
            </el-form-item>
            <el-form-item label="身份证号码：" prop="IDCard" class="">
              <el-input v-model="organizationForm.IDCard"  :placeholder="placeholder.IDCard"></el-input>
            </el-form-item>
            <el-form-item label="电子邮箱：" prop="email" class="">
              <el-input v-model="organizationForm.email"  :placeholder="placeholder.email"></el-input>
            </el-form-item>
            <el-form-item label="手机号：" prop="phone" class="">
              <el-input
                  v-model="organizationForm.phone"
                  :placeholder="placeholder.phone"
              ></el-input>
            </el-form-item>
            <el-form-item label="图片验证码：" prop="code" class="">
              <el-input
                  v-model="organizationForm.code"
                  :placeholder="placeholder.code"
              >
                <div slot="append">
                  <img
                      class="code"
                      src=""
                      alt="imgCode"
                  />
                </div>
              </el-input>
            </el-form-item>
            <el-form-item label="短信验证码：" prop="phoneCode" class="">
              <el-input
                  v-model="organizationForm.phoneCode"
                  :placeholder="placeholder.phoneCode"
              >
                <template slot="suffix">
                  <span class="primaryCplor mr-10">获取短信验证码</span>
                </template>
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-button
                  type="primary"
                  @click="onSubmit()"
                  class="W-100 mt-20 parimaryBG border0"
              >注册</el-button
              >
            </el-form-item>
          </el-form>
        </div>
      </div>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      isOrganization: false, //是否是组织
      show: false,
      placeholder: {
        name: "5-25个字符，可使用字母、数字、下划线，需以字母开头",
        phone: "请填写常用号码",
        code: "请填写图片验证码",
        phoneCode: "请填写短信验证码",
        password: "8-20个字符，至少包含一个大写字母，一个小写字母跟一个数字",
        passwordTest: "请再次填写密码",
        company: "请输入单位名称",
        socialCreditCode: "请输入统一社会信用代码",
        transactorName: "请填写真实姓名",
        IDCard: "请填写身份证号码",
        email: "请填写注册人邮箱",
      },
      personelFrom: {
        name: "",
        phone: "",
        code: "",
        phoneCode: "",
        password: "",
        passwordTest: "",
      },
      organizationForm: {
        name: "",
        organizationType:"法人",
        phone: "",
        code: "",
        phoneCode: "",
        password: "",
        passwordTest: "",
        company: "",
        socialCreditCode: "",
        transactorName: "",
        IDCard: "",
        email: "",
      },
      rules: {
        name: [
          { required: true, message: "请输入需求名称", trigger: "blur" },
          { min: 3, max: 5, message: "长度在 3 到 5 个字符", trigger: "blur" },
        ],
        phone: [
          { required: true, message: "请输入需求名称", trigger: "blur" }
        ],
        code: [ { required: true, message: "请输入需求名称", trigger: "blur" },],
        phoneCode: [ { required: true, message: "请输入需求名称", trigger: "blur" },],
        password: [ { required: true, message: "请输入需求名称", trigger: "blur" },],
        passwordTest: [ { required: true, message: "请输入需求名称", trigger: "blur" },],
        company: [ { required: true, message: "请输入需求名称", trigger: "blur" },],
        socialCreditCode: [ { required: true, message: "请输入需求名称", trigger: "blur" },],
        transactorName:[ { required: true, message: "请输入需求名称", trigger: "blur" },],
        IDCard: [ { required: true, message: "请输入需求名称", trigger: "blur" },],
        email:[ { required: true, message: "请输入需求名称", trigger: "blur" },],
      },
    };
  },
  created() {},
  mounted() {},
  methods: {
    onSubmit() {

    },
    login(){
      setTimeout(()=>{
        // this.$Login();
      },500)
      this.show=false;
    }
  },
  computed: {
  },
  watch: {
  },
  components: {},
};
</script>
<style scoped>
.el-dialog__wrapper /deep/ .el-dialog__header {
  padding: 0 20px;
}
.el-form-item {
  margin-bottom: 10px;
}
</style>
<style lang="scss" scoped>
$-primary: #4cad95;
.height50 {
  height: 50px;
  line-height: 50px;
}
.dialogTitle {
  color: $-primary;
  border-bottom: 2px solid $-primary;
  padding: 0 10px;
}
.primaryCplor {
  color: $-primary;
}
.dialogBody {
  margin: 0 240px;
  .tab {
    margin: 0 8px;
    border: 1px solid $-primary;
    padding: 6px 26px;
    font-size: 14px;
    color: $-primary;
    border-radius: 2px;
  }
  .parimaryBG {
    background: $-primary;
    color: #fff;
  }
  .border-bottom {
    border-bottom: 1px solid #ccc;
    margin: 40px 0 20px 0;
  }
  .typeTitle {
    height: 45px;
    line-height: 45px;
    font-size: 14px;
    color: $-primary;
    border-bottom: 1px solid $-primary;
    display: inline-block;
  }
  .code {
    height: 34px;
  }
}
</style>