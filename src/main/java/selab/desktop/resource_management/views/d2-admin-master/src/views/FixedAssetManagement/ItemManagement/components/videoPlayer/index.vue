<template>
<el-dialog
  title="提示"
  :visible.sync="dialogVisible"
  width="30%">
  <span>这是一段信息</span>
  <span slot="footer" class="dialog-footer">
  </span>
</el-dialog>
</template>

<script>
export default {
  name: 'VideoPlayer',
  props: {
    dialogVideoPlayer: {
      required: true
    }
  },
  data () {
    return {
      dialogVisible: false
    }
  },
  watch: {
    dialogVideoPlayer () {
      this.dialogVisible = true
    }
  }
}
</script>

<style scoped>

</style>
