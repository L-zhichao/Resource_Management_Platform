<template>
  <d2-container class="page">
    <div
      class="page-login--layer page-login--layer-title"
      flex="main:center cross:center">
      资源管理系统
    </div>
  </d2-container>
</template>

<script>
export default {
  components: {
  },
  data () {
    return {
    }
  }
}
</script>

<style lang="scss" scoped>
.page-login--layer {
  @extend %full;
  overflow: auto;
}
// title
.page-login--layer-title {
  font-size: 9em;
  font-weight: bold;
  color: rgba(0, 0, 0, 0.06);
  overflow: hidden;
}
</style>
