<template>
  <d2-container>
    <!-- <ueditor v-model="text"/> -->
    <quill
      style="min-height: 200px; margin-bottom: 20px;"
      v-model="value"/>
  </d2-container>
</template>

<script>
import util from '@/libs/util'
export default {
  name: 'editor-quill',
  data () {
    return {
      // text: '',
      value: ''
    }
  },
  methods: {
    /**
     * @description 阻止默认键盘事件
     */
    preventBrowserDefault (e) {
      if ((navigator.platform.match('Mac') ? e.metaKey : e.ctrlKey)) {
        e.preventDefault()
      }
    },
    /**
     * @description ctrl+s 触发事件
     */
    ctrlS (e) {
      if (e.keyCode === 83 && (navigator.platform.match('Mac') ? e.metaKey : e.ctrlKey)) {
        this.saveValue()
      }
    },
    saveValue () {
      const localStorageName = util.cookies.get('username') + 'Value'
      localStorage.setItem(localStorageName, JSON.stringify(this.value))
      this.$message({
        message: '保存成功',
        type: 'success'
      })
    }
  },
  created () {
    window.addEventListener('keydown', this.preventBrowserDefault)
    window.addEventListener('keyup', this.ctrlS)
    const localStorageName = util.cookies.get('username') + 'Value'
    const localStorageNameValue = localStorage.getItem(localStorageName)
    if (localStorageNameValue !== null && localStorageNameValue !== '"<p><br></p>"') {
      this.value = JSON.parse(localStorageNameValue)
      this.$notify({
        title: '成功载入缓存',
        message: 'ctrl+s可以缓存编辑内容哦',
        position: 'bottom-left',
        type: 'success'
      })
    } else {
      this.$notify({
        title: '小贴士',
        message: 'ctrl+s可以缓存编辑内容哦',
        position: 'bottom-left',
        type: 'warning'
      })
    }
  },
  beforeDestroy () {
    window.removeEventListener('keydown', this.preventBrowserDefault)
    window.removeEventListener('keyup', this.ctrlS)
  }
}
</script>
