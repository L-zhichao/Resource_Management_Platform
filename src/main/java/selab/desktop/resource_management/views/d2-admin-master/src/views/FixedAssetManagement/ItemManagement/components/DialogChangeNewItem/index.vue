<template>
  <el-dialog
    title="修改物品"
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    width="40%">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="名称" prop="itemname">
        <el-input
          type="text"
          :controls="false"
          v-model.number="ruleForm.itemname">
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
      <el-row type="flex" class="row-bg" justify="space-between" v-if="oldImgView">
        <el-col :span="24">
          <span class="spanText">原图</span>
          <!-- 这个popover组件用于生成悬浮图片 -->
          <el-popover placement="bottom" title="" trigger="hover" width="175">
            <!-- <img :src="ruleForm.imgs" alt="" style="height: 300px"> -->
            <el-button class="buttonDeleteImg" size="medium" type="danger" icon="el-icon-delete" circle @click="deleteOldImg"></el-button>
            <el-button size="medium" type="success" plain @click="whatButton">这是什么按钮</el-button>
            <!-- image -->
            <el-image slot="reference" style="height: 100px" :src="ruleForm.imgs" :preview-src-list="[ruleForm.imgs]">
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </el-popover>
        </el-col>
      </el-row>
      <el-form-item label="上传图片" prop="imgs" v-if="!oldImgView">
        <el-upload
          ref="img"
          drag
          action
          accept=".png, .jpg, jpeg"
          :limit="1"
          list-type="picture"
          :on-change="change"
          :on-exceed="handleExceed"
          :auto-upload="false">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传1张jpg/png文件</div>
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
  name: 'DialogChangeNewItem',
  props: {
    // 检测 dialog 被唤起
    dialogArouse: {
      type: Number,
      // 限制为必传
      required: true
    },
    changeItemInformation: {
      type: Object,
      // 限制为必传
      required: true
    }
  },
  data () {
    return {
      ruleForm: {
        itemId: 0,
        itemname: '',
        number: 1,
        price: null,
        damageRecordDesc: '',
        imgs: ''
      },
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
        imgs: [
          { required: true, message: '上传图片', trigger: 'blur' }
        ]
      },
      oldImgView: true,
      dialogVisible: false
    }
  },
  watch: {
    dialogArouse () {
      this.ruleForm = { ...this.changeItemInformation }
      this.dialogVisible = true
    }
  },
  methods: {
    /**
     * @description 一个不知道什么的按钮触发了这个函数!
     */
    whatButton () {
      for (let i = 0; i < 10; i++) {
        setTimeout(() => {
          this.$notify({
            title: '这是一个绿色按钮',
            message: '就很想点一下,也不知道有什么用',
            type: 'success'
          })
          this.$notify({
            title: '这是一个绿色按钮',
            message: '就很想点一下,也不知道有什么用',
            position: 'bottom-right',
            type: 'success'
          })
          this.$notify({
            title: '这是一个绿色按钮',
            message: '就很想点一下,也不知道有什么用',
            position: 'bottom-left',
            type: 'success'
          })
          this.$notify({
            title: '这是一个绿色按钮',
            message: '就很想点一下,也不知道有什么用',
            position: 'top-left',
            type: 'success'
          })
        }, i * 50)
      }
    },
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
          if (this.oldImgView === false) {
            this.oldImgView = true
            this.$refs.img.clearFiles()
          }
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
     * @description 用于删除旧的图片,显示新图片添加
     */
    deleteOldImg () {
      this.ruleForm.imgs = ''
      this.oldImgView = false
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
        const data = this.imageCompression(reader.result)
        let base64 = ''
        await data
          .then(v => {
            base64 = v
            return Promise.resolve(v)
          })
        this.ruleForm.imgs = base64
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
     * @param {Number} itemId  物品Id
     * @param {String} itemname  物品名
     * @param {Number} number  物品数量
     * @param {Number} price  单价
     * @param {String} damageRecordDesc  描述
     * @param {String} imgs  图片base64
     */
    async itemChangeAPI ({ itemId, itemname, number, price, damageRecordDesc, imgs }) {
      return await api.ITEM_CHANGE_API({ itemId, itemname, number, price, damageRecordDesc, imgs })
    },
    /**
     * @description 添加物品请求api
     * @param {Number} itemId  物品Id
     * @param {String} itemname  物品名
     * @param {Number} number  物品数量
     * @param {Number} price  单价
     * @param {String} damageRecordDesc  描述
     * @param {String} img  图片base64
     */
    itemChange ({ itemId, itemname, number, price, damageRecordDesc, imgs }) {
      this.itemChangeAPI({ itemId, itemname, number, price, damageRecordDesc, imgs })
        .then(v => {
          if (v === null) {
            this.$message({
              message: '上传成功',
              type: 'success'
            })
            this.$emit('changeItemSuccess', this.ruleForm)
            this.ruleForm = {
              itemId: 0,
              itemname: '',
              number: 1,
              price: null,
              damageRecordDesc: '',
              imgs: ''
            }
            this.$refs.ruleForm.clearValidate()
            if (this.oldImgView === false) {
              this.oldImgView = true
              this.$refs.img.clearFiles()
            }
            this.dialogVisible = false
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
      // 将每次图片数组变化的时候，实时的进行监听，更改数组里面的图片数据
      fileList.forEach((item) => {
        this.previewFile(item.raw)
      })
    },
    /**
     * @description 提交表单
     */
    submit () {
      if (JSON.stringify(this.ruleForm) === JSON.stringify(this.changeItemInformation)) {
        // 没有修改走这里
        this.ruleForm = {
          itemId: 0,
          itemname: '',
          number: 1,
          price: null,
          damageRecordDesc: '',
          imgs: ''
        }
        this.$refs.ruleForm.clearValidate()
        if (this.oldImgView === false) {
          this.oldImgView = true
          this.$refs.img.clearFiles()
        }
        this.dialogVisible = false
        return
      }
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.itemChange({
            itemId: this.ruleForm.itemId,
            itemname: this.ruleForm.itemname,
            number: this.ruleForm.number,
            price: this.ruleForm.price,
            damageRecordDesc: this.ruleForm.damageRecordDesc,
            imgs: this.ruleForm.imgs
          })
        } else {
          this.$message.error('表单校验失败，请检查')
        }
      })
    }
  }
}
</script>

<style scoped>
.spanText{
  width: 88px;
  height: 40px;
  padding-right: 12px;
  text-align: right;
  line-height: 40px;
  float: left;
}
/* .buttonDeleteImg {
  float: right;
  border: 120px;
} */
</style>
