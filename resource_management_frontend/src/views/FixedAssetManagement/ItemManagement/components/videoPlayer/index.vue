<template>
<el-dialog
  :visible.sync="dialogVisible"
  width="80%"
  :before-close="handleClose">
  <video
    :src="playerVideoUrl"
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
      playerVideoUrl: '',
      dialogVisible: false
    }
  },
  watch: {
    dialogVideoPlayer () {
      this.playerVideoUrl = this.videoUrl
      this.dialogVisible = true
    }
  },
  methods: {
    handleClose (done) {
      this.playerVideoUrl = ''
      done()
    }
  }
}
</script>

<style scoped>

</style>
