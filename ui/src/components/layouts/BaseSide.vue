<script lang="ts" setup>
import { ref } from "vue";
import {
  Location,
  Document,
  Menu as IconMenu,
  Setting,
} from "@element-plus/icons-vue";

const isCollapse = ref(true);
const handleOpen = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
const handleClose = (key: string, keyPath: string[]) => {
  console.log(key, keyPath);
};
</script>
<template>
  <el-menu
      class="el-menu-vertical-demo"
      :collapse="isCollapse"
      @open="handleOpen"
      @close="handleClose">
    <el-menu-item index="1">
      <el-icon><document /></el-icon>
      <template #title>Log</template>
    </el-menu-item>
    <el-menu-item index="2">
      <el-icon><setting /></el-icon>
      <template #title>SystemSetting</template>
    </el-menu-item>
  </el-menu>
</template>
<style>
</style>
