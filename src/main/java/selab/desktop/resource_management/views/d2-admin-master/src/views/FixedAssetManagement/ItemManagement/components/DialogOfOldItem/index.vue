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
          <div class="el-upload__tip" slot="tip">只能上传5张jpg/png文件</div>
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
import * as dayjs from 'dayjs'
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
        number: [
          { required: true, message: '请输入损坏数量', trigger: 'blur' },
          { required: true, type: 'number', min: 1, max: this.oldItemId_Name.number, message: '请输入正确损坏数量', trigger: 'blur' }
        ],
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
            number: 1,
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
     * @description 文件大小 格式 校验
     * @param {*} file
     * @return {Boolean} 是否通过校验
     */
    beforeUpload (file) {
      console.log('文件：', file)
      var FileExt = file.name.replace(/.+\./, '')
      const isLt30M = file.size / 1024 / 1024 <= 10
      var extension = ['png', 'jpg', 'jpeg'].indexOf(FileExt.toLowerCase()) === -1
      if (extension) {
        this.$message({
          type: 'warning',
          message: '只能上传 .png, .jpg, .jpeg 文件！'
        })
        this.$refs.img.clearFiles()
        return false
      }
      if (!isLt30M) {
        this.$message({
          type: 'warning',
          message: '附件大小超限，文件不能超过 10M'
        })
        this.$refs.img.clearFiles()
        return false
      }
      return true
    },
    /**
     * @description file Img转base64
     * @param {*} file 读取的文件
     */
    async previewFile (file, arr) {
      const fileBlob = new Blob([file], { type: file.type })
      let reader = null
      if (file) {
        // 创建流对象
        reader = new FileReader()
        reader.readAsDataURL(fileBlob)
      }
      // 捕获 转换完毕
      reader.onload = async () => {
        // 转换后的base64就在reader.result里面,直接放到img标签的src属性即可
        const data = this.imageCompression(reader.result)
        await data
          .then(v => {
            arr.push(v)
            return Promise.resolve(v)
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
        context.drawImage(img, 0, 0, imgWidth, 500)
        const base64Data = canvas.toDataURL()
        resolve(base64Data)
      })
    },
    /**
     * @description base64图片压缩 主函数
     * @param {String} base64
     * @return {Object} Promise对象 data为 base64
     */
    async imageCompression (base64) {
      const img = new Image()
      const canvas = document.createElement('canvas')
      const context = canvas.getContext('2d')
      img.src = base64
      const imgProportion = await this.pImg(img)
      canvas.width = 500 * imgProportion
      canvas.height = 500
      return await this.pCanvas(img, 500 * imgProportion, canvas, context)
    },
    /**
     * @description 损坏物品请求
     * @param {String} username   用户名
     * @param {Number} itemId  损坏物品Id
     * @param {Number} inputText  损坏描述
     * @param {object} imgBase64  List 图片
     */
    async itemReportDamagedAPI ({ username, itemId, inputText, imgBase64, damageRecordTime }) {
      return await api.ITEM_REPORT_DAMAGED_API({ username, itemId, damageRecordDesc: inputText, damageRecordImg: imgBase64, damageRecordTime })
    },
    /**
     * @description 损坏物品请求
     * @param {Number} itemId  损坏物品Id
     * @param {Number} inputText  损坏描述
     * @param {String} imgBase64  // 图片 base64 多个图片由//////////拼接
     *                            // 暂时更新 改为base64 的列表
     */
    itemReportDamaged ({ itemId, inputText, number, imgBase64 }) {
      const username = util.cookies.get('username')
      const damageRecordTime = dayjs().format('YYYY-MM-DD')
      inputText = inputText + '//////////' + number
      this.itemReportDamagedAPI({ username, itemId, inputText, imgBase64, damageRecordTime })
        .then(v => {
          if (v === null) {
            this.$message({
              message: '上传成功',
              type: 'success'
            })
          } else if (v === 'fail') {
            this.$message.error('上传失败')
          }
        })
    },
    /**
     * @description 更新上传的图片
     * @param {*} file
     * @param {*} fileList
     */
    change (file, fileList) {
      if (!this.beforeUpload(file, fileList)) return
      // 将每次图片数组变化的时候，实时的进行监听，更改数组里面的图片数据
      const arr = []
      fileList.forEach((item) => {
        this.previewFile(item.raw, arr)
      })
    },
    /**
     * @description 提交表单
     */
    submit () {
      this.$refs.ruleForm.validate((valid) => {
        console.log(valid)
        if (valid) {
          // this.ruleForm.imgBase64 = this.ruleForm.imgBase64.join('//////////')
          console.log(this.ruleForm.imgBase64)
          this.itemReportDamaged({
            itemId: this.oldItemId_Name,
            inputText: this.ruleForm.inputText,
            imgBase64: this.ruleForm.imgBase64,
            number: this.ruleForm.number
          })
          this.ruleForm = {
            number: 1,
            inputText: '',
            // 上传后的文件列表
            imgBase64: []
          }
          this.$refs.ruleForm.clearValidate()
          this.$refs.imgLoad.clearFiles()
          this.dialogVisible = false
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
