<script setup lang="ts">
import {types} from "sass";
import Boolean = types.Boolean;
import Button  from './ts/types'
import {PropType} from "vue";

const props = defineProps({
  show: {
    type: Boolean,
    default: true
  },
  title: {
    type: String,
    default: "Title"
  },
  showClose: {
    type: Boolean,
    default: true
  },
  width: {
    type: String,
    default: "30%"
  },
  top: {
    type: String,
    default: "50px"
  },
  showCancel: {
    type: Boolean,
    default: true
  },
  buttons: {
    type: Array as PropType<Button[]>,
    required: true
  }
});
const emit = defineEmits();
const close = () => {
  emit("close")
};
</script>

<template>
  <div>
    <el-dialog
        :model-value="show"
        :show-close="showClose"
        :draggable="false"
        :title="title"
        :close-on-click-modal="false"
        :width="width"
        class="cust-dialog"
        :top="top"
        @close="close"
        align-center>
        <div class="dialog-body">
          <slot>

          </slot>
        </div>
      <template v-if="(buttons || showCancel)" #footer>
        <div class="dialog-footer">
          <el-button @click="close" v-if="showCancel">
            cancel
          </el-button>
          <el-button v-for="btn in buttons" :type="btn.type" @click="btn.click">
            {{ btn.text }}
          </el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>
<style lang="scss">
.cust-dialog {
  margin-bottom: 10px;
  .el-dialog__body {
    padding: 0;
  }
  .dialog-body {
    border-top: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
    padding: 15px;
    min-height: 100px;
    max-height: calc(100vh - 190px);
    overflow: auto;
    margin-left: auto;
  }
  .dialog-footer {
    text-align: right;
    //padding: 10px 20px;
  }
}

</style>
<style lang="scss">

</style>