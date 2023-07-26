<template>
  <el-dialog
    title="物品损坏上报"
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    width="40%">
    <span><b>{{ oldItemId_Name.itemname }}</b></span>
    <br/><br/>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="损坏数量" prop="number">
        <el-input
          type="number"
          :controls="false"
          size="mini"
          min="1"
          :max="oldItemId_Name.number"
          v-model.number="ruleForm.number">
        </el-input>
      </el-form-item>
      <el-form-item label="损坏详情" prop="inputText">
        <el-input type="textarea" v-model="ruleForm.inputText"></el-input>
      </el-form-item>
      <el-form-item label="上传图片" prop="imgBase64">
        <el-upload
          ref="imgLoad"
          drag
          action
          multiple
          accept=".png, .jpg, jpeg"
          :limit="5"
          list-type="picture"
          :on-change="change"
          :on-exceed="handleExceed"
          :auto-upload="false">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传5张jpg/png文件，且不超过500kb</div>
        </el-upload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import util from '@/libs/util'
import api from '@/api'
export default {
  name: 'DialogOfOldItem',
  props: {
    // 检测 dialog 被唤起
    dialogArouse: {
      type: Number,
      // 限制为必传
      required: true
    },
    oldItemId_Name: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      ruleForm: {
        // 损坏物品数量
        number: 1,
        // 描述
        inputText: '',
        // 上传后的文件列表
        imgBase64: []
      },
      // 表单验证规则
      rules: {
        inputText: [
          { required: true, message: '请输入损坏详情', trigger: 'blur' }
        ],
        imgBase64: [
          { required: true, message: '上传损坏图片', trigger: 'blur' }
        ]
      },
      dialogVisible: false
    }
  },
  watch: {
    dialogArouse () {
      if ('itemId' in this.oldItemId_Name) {
        this.dialogVisible = true
      } else {
        this.$message.error('当前未选中任何物品')
      }
    }
  },
  methods: {
    dialogArouseOperate () {
      this.dialogVisible = true
    },
    /**
     * @description dialog关闭时触发
     * @param {*} done
     */
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.ruleForm = {
            inputText: '',
            // 上传后的文件列表
            imgBase64: []
          }
          this.$refs.ruleForm.clearValidate()
          this.$refs.imgLoad.clearFiles()
          done()
        })
        .catch(_ => {})
    },
    /**
     * @description 上传文件超过5张时触发
     * @param {*} files
     * @param {*} fileList
     */
    handleExceed (files, fileList) {
      this.$message.error('最多上传5张图片')
    },
    /**
     * @description file Img转base64
     * @param {*} file 读取的文件
     */
    previewFile (file, arr) {
      const fileBlob = new Blob([file], { type: file.type })
      let reader = null
      if (file) {
        // 创建流对象
        reader = new FileReader()
        reader.readAsDataURL(fileBlob)
      }
      // 捕获 转换完毕
      reader.onload = () => {
        // 转换后的base64就在reader.result里面,直接放到img标签的src属性即可
        this.imageCompression(reader.result)
          .then(v => {
            arr.push(v)
          })
        this.ruleForm.imgBase64 = arr
      }
    },
    /**
     * @description base64图片压缩 辅助函数1
     * @param {Object} img
     */
    async pImg (img) {
      return new Promise(function (resolve, reject) {
        img.onload = () => {
          const imgProportion = parseInt(img.width) / parseInt(img.height)
          resolve(imgProportion)
        }
      })
    },
    /**
     * @description base64图片压缩 辅助函数2
     * @param {Object} img
     * @param {Number} imgWidth
     * @param {Object} canvas
     * @param {Object} context
     */
    async pCanvas (img, imgWidth, canvas, context) {
      return new Promise(function (resolve, reject) {
        context.drawImage(img, 0, 0, imgWidth, 200)
        const base64Data = canvas.toDataURL()
        resolve(base64Data)
      })
    },
    /**
     * @description base64图片压缩 主函数
     * @param {String} base64
     * @return {String} base64
     */
    async imageCompression (base64) {
      const img = new Image()
      const canvas = document.createElement('canvas')
      const context = canvas.getContext('2d')
      img.src = base64
      const imgProportion = await this.pImg(img)
      canvas.width = 200 * imgProportion
      canvas.height = 200
      return await this.pCanvas(img, 200 * imgProportion, canvas, context)
    },
    /**
     * @description 损坏物品请求
     * @param {String} username   用户名
     * @param {Number} itemId  损坏物品Id
     * @param {Number} inputText  损坏描述
     * @param {object} imgBase64  List 图片
     */
    async itemReportDamagedAPI ({ username, itemId, inputText, imgBase64 }) {
      return await api.ITEM_REPORT_DAMAGED_API({ username, itemId, inputText, imgBase64 })
    },
    /**
     * @description 损坏物品请求
     * @param {Number} itemId  损坏物品Id
     * @param {Number} inputText  损坏描述
     * @param {object} imgBase64  List 图片
     */
    itemReportDamaged ({ itemId, inputText, imgBase64 }) {
      const username = util.cookies.get('username')
      this.itemReportDamagedAPI({ username, itemId, inputText, imgBase64 })
        .then(v => {
          // console.log(v)
        })
    },
    /**
     * @description 更新上传的图片
     * @param {*} file
     * @param {*} fileList
     */
    change (file, fileList) {
      // 将每次图片数组变化的时候，实时的进行监听，更改数组里面的图片数据
      const arr = []
      fileList.forEach((item) => {
        this.previewFile(item.raw, arr)
      })
    },
    submit () {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          console.log(this.ruleForm)
          this.itemReportDamaged({
            itemId: this.oldItemId_Name,
            inputText: this.ruleForm.inputText,
            imgBase64: this.ruleForm.imgBase64
          })
          this.ruleForm = {
            inputText: '',
            // 上传后的文件列表
            imgBase64: []
          }
          this.$refs.ruleForm.clearValidate()
          this.$refs.imgLoad.clearFiles()
        } else {
          this.$message.error('表单校验失败，请检查')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
