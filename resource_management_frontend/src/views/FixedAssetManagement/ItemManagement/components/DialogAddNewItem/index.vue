<template>
  <el-dialog
    title="新物品添加"
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    width="40%">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="名称" prop="itemname">
        <el-input
          type="text"
          :controls="false"
          v-model="ruleForm.itemname">
        </el-input>
      </el-form-item>
      <el-form-item label="数量" prop="number">
        <el-input
          type="number"
          :controls="false"
          size="mini"
          min="1"
          v-model.number="ruleForm.number">
        </el-input>
      </el-form-item>
      <el-form-item label="单价金额" prop="price">
        <el-input
          type="number"
          :controls="false"
          size="mini"
          :min="0.01"
          :step="0.01"
          precision="2"
          v-model.number="ruleForm.price">
        </el-input>
      </el-form-item>
      <el-form-item label="详情" prop="damageRecordDesc">
        <el-input type="textarea" v-model="ruleForm.damageRecordDesc"></el-input>
      </el-form-item>
      <el-form-item label="视频图片" prop="img">
        <el-upload
          ref="img"
          drag
          action
          accept=".png, .jpg, .jpeg, .mp4, .flv"
          :limit="1"
          list-type="text"
          :on-change="change"
          :on-exceed="handleExceed"
          :auto-upload="false">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传一个图片或视频文件</div>
        </el-upload>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
// import util from '@/libs/util'
import api from '@/api'
export default {
  name: 'DialogAddNewItem',
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
      ruleForm: {
        itemname: '',
        number: 1,
        price: null,
        damageRecordDesc: '',
        img: '',
        imgs: '',
        videos: ''
      },
      imgType: null,
      rules: {
        itemname: [
          { required: true, message: '请输入物品名', trigger: 'blur' },
          {
            required: true,
            pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9\s*.·-]+$/,
            message: '不支持特殊字符',
            trigger: 'blur'
          }
        ],
        number: [
          { required: true, message: '请输入数量', trigger: 'blur' },
          {
            required: true,
            pattern: /^[0-9|^\\.]/,
            message: '不能为负',
            trigger: 'blur'
          }
        ],
        price: [
          { required: true, message: '请输入单价金额', trigger: 'blur' },
          {
            required: true,
            pattern: /^[0-9|^\\.]/,
            message: '不能为负',
            trigger: 'blur'
          },
          {
            required: true,
            pattern: /(^[1-9](\d+)?(\.\d{1,2})?$)|(^0$)|(^\d\.\d{1,2}$)/,
            message: '请输入正确格式',
            trigger: 'blur'
          }
        ],
        damageRecordDesc: [
          { required: true, message: '说明一下', trigger: 'blur' }
        ],
        img: [
          { required: true, message: '上传视频或图片', trigger: 'blur' }
        ]
      },
      dialogVisible: false
    }
  },
  watch: {
    dialogArouse () {
      this.dialogVisible = true
    }
  },
  methods: {
    /**
     * @description dialog关闭时触发
     * @param {*} done
     */
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.ruleForm = {
            itemname: '',
            number: 1,
            price: null,
            damageRecordDesc: '',
            img: ''
          }
          this.$refs.ruleForm.clearValidate()
          this.$refs.img.clearFiles()
          done()
        })
        .catch(_ => {})
    },
    /**
     * @description 上传文件超过1张时触发
     * @param {*} files
     * @param {*} fileList
     */
    handleExceed (files, fileList) {
      this.$message.error('最多上传1张图片')
    },
    /**
     * @description 文件大小 格式 校验
     * @param {*} file
     * @return {Boolean} 是否通过校验
     */
    beforeUpload (file) {
      const FileExt = file.name.replace(/.+\./, '')
      const isLt30M = file.size / 1024 / 1024 <= 30
      const extension = ['png', 'jpg', 'jpeg', 'mp4', 'flv'].indexOf(FileExt.toLowerCase()) === -1
      if (extension) {
        this.$message({
          type: 'warning',
          message: '只能上传 .png, .jpg, .jpeg, .mp4, .flv 文件！'
        })
        this.$refs.img.clearFiles()
        return false
      }
      if (!isLt30M) {
        this.$message({
          type: 'warning',
          message: '附件大小超限，文件不能超过 30M'
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
    async previewFile (file) {
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
        // 压缩? 压缩个屁!
        // const data = this.imageCompression(reader.result)
        // if (file.type.split('/')[0] === 'video') {
        //   (reader.result)
        //   this.ruleForm.img = reader.result
        //   return
        // }
        // let base64 = ''
        // await data
        //   .then(v => {
        //     base64 = v
        //     return Promise.resolve(v)
        //   })
        this.ruleForm.img = reader.result
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
     * @description 添加物品请求api
     * @param {String} itemname  物品名
     * @param {Number} number  物品数量
     * @param {Number} price  单价
     * @param {String} damageRecordDesc  描述
     * @param {String} imgs  图片Url
     * @param {String} videos  视频Url
     */
    async itemAddAPI ({ itemname, number, price, damageRecordDesc, imgs, videos }) {
      return await api.ITEM_ADD_API({ itemname, number, price, damageRecordDesc, imgs, videos })
    },
    /**
     * @description 添加物品请求
     * @param {String} itemname  物品名
     * @param {Number} number  物品数量
     * @param {Number} price  单价
     * @param {String} damageRecordDesc  描述
     * @param {String} imgs  图片Url
     * @param {String} videos  视频Url
     */
    itemAdd ({ itemname, number, price, damageRecordDesc, imgs, videos }) {
      this.itemAddAPI({ itemname, number, price, damageRecordDesc, imgs, videos })
        .then(v => {
          if ('itemid' in v || 'itemId' in v) {
            this.$message({
              message: '上传成功',
              type: 'success'
            })
            this.ruleForm = {
              itemname: '',
              number: 1,
              price: null,
              damageRecordDesc: '',
              imgs: '',
              videos: ''
            }
            this.$refs.ruleForm.clearValidate()
            this.$refs.img.clearFiles()
            this.dialogVisible = false
          } else if (v === 'fail') {
            this.$message.error('上传失败')
          }
        })
    },
    /**
     * @description 上传图片/视频api
     * @param {form} data form文件
     */
    async imgUploadAPI (data) {
      return await api.IMG_UPLOAD_API(data)
    },
    /**
     * @description 上传图片/视频
     * @param {file} file file文件
     */
    imgUpload (file) {
      const formData = new FormData()
      formData.append('file', file)
      this.imgUploadAPI(formData)
        .then(v => {
          if (v.split('/')[0] === 'http:') {
            if (this.imgType === 'image') {
              this.ruleForm.imgs = v
              this.ruleForm.videos = ''
            } else if (this.imgType === 'video') {
              this.ruleForm.imgs = ''
              this.ruleForm.videos = v
            }
            this.itemAdd({
              itemname: this.ruleForm.itemname,
              number: this.ruleForm.number,
              price: this.ruleForm.price,
              damageRecordDesc: this.ruleForm.damageRecordDesc,
              imgs: this.ruleForm.imgs,
              videos: this.ruleForm.videos
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
      // fileList.forEach((item) => {
      //   this.previewFile(item.raw)
      // })
      this.ruleForm.img = file
      if (file.raw.type.split('/')[0] === 'image') {
        this.imgType = 'image'
      } else if (file.raw.type.split('/')[0] === 'video') {
        this.imgType = 'video'
      }
    },
    /**
     * @description 提交表单
     */
    submit () {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.imgUpload(this.ruleForm.img)
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
