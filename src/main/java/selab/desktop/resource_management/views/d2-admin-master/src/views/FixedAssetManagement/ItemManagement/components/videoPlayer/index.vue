<template>
<el-dialog
  :visible.sync="dialogVisible"
  width="80%">
  <video
    :src="videoUrl"
    controls
    class="video"
    width="100%">
  </video>
</el-dialog>
</template>

<script>
export default {
  name: 'VideoPlayer',
  props: {
    dialogVideoPlayer: {
      required: true
    },
    videoUrl: {
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
      console.log(this.videoUrl)
    }
  }
}
</script>

<style scoped>

</style>
