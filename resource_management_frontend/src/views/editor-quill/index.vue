<template>
  <d2-container>
    <d2-ueditor v-model="text"/>
    <d2-quill
      style="min-height: 200px; margin-bottom: 20px;"
      v-model="value"/>
  </d2-container>
</template>

<script>
export default {
  name: 'editor-quill',
  data () {
    return {
      text: '',
      value: ''
    }
  },
  methods: {
  }
}
</script>
