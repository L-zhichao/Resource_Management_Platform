<template>
  <el-dialog
    title="新物品购置资金申请"
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    width="40%">
    <span>这是一段New信息</span>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: 'DialogOfNewItem',
  props: {
    // 检测 dialog 被唤起
    dialogArouse: {
      type: Number,
      // 限制为必传
      required: true
    }
  },
  data () {
    return {
      dialogVisible: false
    }
  },
  watch: {
    dialogArouse () {
      this.dialogVisible = true
    }
  },
  methods: {
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    }
  }
}
</script>

<style scoped>

</style>
