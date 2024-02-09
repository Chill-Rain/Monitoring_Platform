<script setup lang="ts">
</script>
<template>
      <BaseHeader></BaseHeader>
      <div class="flex main-container">
        <BaseSide></BaseSide>
        <div w="full" py="4">
          <router-view></router-view>
        </div>
      </div>
</template>
<style scoped>

</style>