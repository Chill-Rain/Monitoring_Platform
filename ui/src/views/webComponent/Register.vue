<script setup lang="ts">

</script>
<template>
  <div>
    <el-dialog
        :visible.sync="show"
        :append-to-body="true"
        :show-close="false"
        width="670px"
    >
      <h1>欢迎登陆</h1>
      <div>
        <el-button @click="reg(false)">个人注册</el-button>
        <el-button @click="reg(true)">机构注册</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style scoped>

</style>