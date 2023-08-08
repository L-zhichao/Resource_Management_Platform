<template>
<el-dialog
  :visible.sync="dialogVisible"
  width="80%"
  :key="randomKey"
  :before-close="handleClose">
  <video
    codecs="avc1.42E01E, mp4v.20.8, mp4v.20.240, avc1.66.30, mp4a.40.2"
    controls
    class="video"
    width="100%">
    <source :src="playerVideoUrl" type="video/mp4" />
  </video>
</el-dialog>
</template>

<script>
export default {
  name: 'videoPlayer',
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
      randomKey: Math.random(),
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
      this.randomKey = Math.random()
      done()
    }
  }
}
</script>

<style scoped>

</style>
