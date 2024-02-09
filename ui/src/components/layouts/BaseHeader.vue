<script lang="ts" setup>
import { toggleDark } from "~/composables";
import {ref} from "vue";
const login = "Login"
const apis = {
  systemSettingAPIS: "/api/system/getApis",
  systemSettingCamera: '/api/system/getApis/camera',
  logins: "/api/user/login",
  logout: "/api/user/logout",
  register: "/api/user/register"
}
// const buttons = [
//   { type: '', text: 'login', },
//   { type: '', text: 'logout' },
//   { type: '', text: 'register' },
// ] as const
const buttons = [
  {
    text: "confirm",
    type: "primary",
  },
];
const showDialog = ref(true)
</script>

<template>
  <div class="el-menu-flex">
    <div style="width: 50%; display: inline;">
      <el-menu class="el-menu-flex">
        <div class="menu-section">
          <el-menu-item index="1">Monitor Platform</el-menu-item>
<!--          <el-menu-item index="2">Camera</el-menu-item>-->
<!--          <el-menu-item index="3">APIS</el-menu-item>-->
          <el-menu-item h="full" @click="toggleDark()">
            <button class="border-none w-full bg-transparent cursor-pointer">
              <i inline-flex i="dark:ep-moon ep-sunny" />
            </button>
          </el-menu-item>
        </div>
      </el-menu>
    </div>
    <div style="width: 50%; display: inline;">
      <el-menu class="el-menu-flex">
        <div class="menu-section right">
          <el-menu-item index="1" @click="showDialog = true">{{ login }}</el-menu-item>
          <el-menu-item index="2" @click="showDialog = true">Register</el-menu-item>
          <el-menu-item index="3" @click=apis.logout>Logout</el-menu-item>
        </div>
      </el-menu>
    </div>
  </div>
  <Dialog :show="showDialog"
          clickToClose="false"
          :buttons="buttons"
          @close="showDialog = false">测试内容</Dialog>
</template>
<style scoped>
.button{

}
.el-menu-flex {
  display: flex;
  justify-content: space-between;
}

.menu-section {
  display: flex;
}

.menu-section.right {
  margin-left: auto;
}
</style>