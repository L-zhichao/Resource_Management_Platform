<template>
  <el-dialog
    title="新物品购置资金申请"
    :visible.sync="dialogVisible"
    :before-close="handleClose"
    width="40%">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="名称" prop="itemName">
        <el-input
          type="text"
          v-model="ruleForm.itemName">
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
      <el-form-item label="总金额" prop="price">
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
      <el-form-item label="详情" prop="description">
        <el-input type="textarea" v-model="ruleForm.description" @blur="textProcessing"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submit">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import api from '@/api'
import dayjs from 'dayjs'
import util from '@/libs/util'
export default {
  name: 'DialogOfNewItem',
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
        itemName: '',
        number: 1,
        price: null,
        description: ''
      },
      rules: {
        itemName: [
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
          { required: true, message: '请输入总金额', trigger: 'blur' },
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
        description: [
          { required: true, message: '说明一下', trigger: 'blur' }
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
    handleClose (done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done()
        })
        .catch(_ => {})
    },
    /**
     * @description 申请说明处理
     */
    textProcessing () {
      const reg1 = new RegExp('/', 'g')
      const data = this.ruleForm.description.replace(reg1, '')
      this.ruleForm.description = data
    },
    /**
     * @description 请求新物品
     * @param {Object} param0
     * @param {String} content 申请内容
     * @param {Number} money 金额
     * @param {String} applyTime 时间
     */
    async itemApplyAPI ({ content, money, applyTime, name }) {
      return await api.ITEM_APPLY_API({ content, money, applyTime, name })
    },
    /**
     * @description 请求新物品
     * @param {Object} param0
     * @param {String} content 申请内容
     * @param {Number} money 金额
     */
    itemApply ({ content, money }) {
      this.itemApplyAPI({ content, money, applyTime: dayjs().format('YYYY-MM-DD HH:mm:ss'), name: util.cookies.get('name') })
        .then(v => {
          if (typeof v === 'object') {
            this.ruleForm = {
              itemName: '',
              number: 1,
              price: null,
              description: ''
            }
            this.$message({
              message: '上传成功',
              type: 'success'
            })
            this.dialogVisible = false
          } else {
            this.$message.error('请求发送失败')
          }
        })
    },
    /**
     * @description 发送表单
     */
    submit () {
      this.textProcessing()
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          const text = this.ruleForm.itemName + '//////////' + this.ruleForm.number + '//////////' + this.ruleForm.description
          this.itemApply({
            content: text,
            money: this.ruleForm.price
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

</style>
