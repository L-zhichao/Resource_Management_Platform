<template>
  <el-drawer
    title="新物品购置资金请求"
    :visible.sync="drawerVisible"
    size="50%"
    direction="rtl">
    <span>我来啦!</span>
  </el-drawer>
</template>

<script>
export default {
  name: 'DrawerOfNewItem',
  props: {
    // 检测 drawer 被唤起
    drawerArouse: {
      type: Number,
      // 限制为必传
      required: true
    }
  },
  data () {
    return {
      drawerVisible: false
    }
  },
  watch: {
    drawerArouse () {
      this.drawerVisible = true
    }
  },
  methods: {

  }
}
</script>

<style scoped>

</style>
