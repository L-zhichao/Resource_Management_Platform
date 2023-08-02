<template>
  <el-drawer
    title="新物品购置资金请求"
    :visible.sync="drawerVisible"
    size="80%"
    direction="btt"
    :before-close="handleClose">
    <el-radio-group size="small" v-model="radio.admin" @input="radioChange" :style="{ marginLeft: '16px' }" v-if="userAdministratorPermissions">
      <el-radio-button label="未处理"></el-radio-button>
      <el-radio-button label="已处理"></el-radio-button>
    </el-radio-group>
    <el-radio-group size="small" v-model="radio.ordinary" @input="radioChange" :style="{ marginLeft: '16px' }" v-if="!userAdministratorPermissions">
      <el-radio-button label="我的"></el-radio-button>
      <el-radio-button label="全部"></el-radio-button>
    </el-radio-group>
    <el-table
      :data="tableData"
      :key="randomKey"
      style="width: 100%"
      v-loading="loadAnimation">
      <el-table-column
        prop="applyTime"
        label="申请时间"
        width="180">
      </el-table-column>
      <el-table-column
        prop="applyUser"
        label="申请人"
        width="140"
        v-if="radio.ordinary === '全部' || userAdministratorPermissions">
      </el-table-column>
      <el-table-column
        prop="content"
        label="申请内容">
      </el-table-column>
      <el-table-column
        prop="money"
        label="申请总金额"
        width="140">
      </el-table-column>
      <el-table-column
        label="状态"
        width="140">
        <template slot-scope="scope">
          <el-button size="small" disabled v-if="scope.row.state === 0 && !userAdministratorPermissions">未处理</el-button>
          <el-popover
            @show="ruleFormData(scope)"
            @hide="ruleFormClear"
            :key="randomKeyPopover"
            v-if="scope.row.state === 0 && userAdministratorPermissions"
            placement="left"
            width="550"
            trigger="click">
            <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
              <el-form-item>
                <template>
                  <el-radio size="small" v-model="radioDispose" border :label="1">批准</el-radio>
                  <el-radio size="small" v-model="radioDispose" border :label="2">不批准</el-radio>
                </template>
              </el-form-item>
              <el-form-item label="说明" prop="reason">
                <el-input type="textarea" v-model="ruleForm.reason"></el-input>
              </el-form-item>
            </el-form>
            <div style="text-align: right; margin: 0">
              <el-button type="primary" size="small" @click="submit">{{ radioDispose === 1 ? '批准' : '不批准' }}</el-button>
            </div>
            <el-button type="primary" slot="reference" size="small">待处理</el-button>
          </el-popover>
          <el-popover
            placement="left"
            width="800"
            trigger="hover">
            <el-table
              :data="tableDataResponse"
              :key="randomKeyResponse"
              style="width: 100%"
              v-loading="loadAnimationResponse">
              <el-table-column
                prop="responseTime"
                label="回应时间"
                width="180">
              </el-table-column>
              <el-table-column
                prop="responseUser"
                label="回应人"
                width="140">
              </el-table-column>
              <el-table-column
                prop="reason"
                label="回应说明">
              </el-table-column>
            </el-table>
            <template slot="reference">
              <div>
                <el-button size="small" type="success" v-if="scope.row.state === 1" @mouseover.native="tableDataResponseChange(scope.row.applyId)">已通过</el-button>
                <el-button size="small" type="danger" v-if="scope.row.state === 2" @mouseover.native="tableDataResponseChange(scope.row.applyId)">未通过</el-button>
              </div>
            </template>
          </el-popover>
        </template>
      </el-table-column>
    </el-table>
  </el-drawer>
</template>

<script>
import util from '@/libs/util'
import api from '@/api'
import dayjs from 'dayjs'
// import dayjs from 'dayjs'
export default {
  name: 'DrawerOfNewItem',
  props: {
    // 检测 drawer 被唤起
    drawerArouse: {
      type: Number,
      // 限制为必传
      required: true
    }
  },
  data () {
    return {
      tableData: [{
        applyId: 0,
        applyTime: '2023-12-14 13:14:15',
        applyUser: 'User',
        content: '原神2个:不玩原神的人，后面忘了',
        money: 0,
        // 0为未读, 1为已通过, 2为未通过
        state: 1
      }],
      // 当前选中项回应
      tableDataResponse: [{
        applyId: 0,
        responseTime: '2023-12-15 13:14:15',
        responseUser: 'XJ',
        reason: '原神,启动!'
      }],
      // 分类
      // |-对应 未处理/已处理
      // |-对应   我的/全部
      allTableData: [[[], []], [[], []]],
      radio: {
        admin: '未处理',
        ordinary: '我的'
      },
      ruleForm: {
        applyId: null,
        name: '',
        result: 2,
        reason: ''
      },
      rules: {
        reason: [
          { required: true, message: '人家超级期待管理员大人的回应的说!你不回复人家会很伤心的嘛!', trigger: 'blur' }
        ]
      },
      radioDispose: 1,
      // 随机Key,用于刷新表格
      randomKey: Math.random(),
      randomKeyResponse: Math.random(),
      randomKeyPopover: Math.random(),
      // 用于启动表格加载动画
      loadAnimation: false,
      loadAnimationResponse: false,
      drawerVisible: false,
      // 0 为 true 是管理员
      // 1 为 false 非管理员
      userAdministratorPermissions: util.cookies.get('userStatus') === '0' || false
    }
  },
  watch: {
    drawerArouse () {
      this.itemShowApply()
      this.drawerVisible = true
    },
    radioDispose (v) {
      if (v === 1) {
        this.ruleForm.result = 2
      } else if (v === 2) {
        this.ruleForm.result = 0
      }
    }
  },
  methods: {
    handleClose (done) {
      done()
      this.tableData = []
      this.tableDataResponse = []
      this.allTableData = [[[], []], [[], []]]
      this.radio = {
        admin: '未处理',
        ordinary: '我的'
      }
    },
    /**
     * @description 修改当前表格显示内容
     */
    radioChange () {
      if (this.userAdministratorPermissions) {
        if (this.radio.admin === '未处理') {
          this.tableData = this.allTableData[0][0]
        } else {
          this.tableData = this.allTableData[0][1]
        }
      } else {
        if (this.radio.ordinary === '我的') {
          this.tableData = this.allTableData[1][0]
        } else {
          this.tableData = this.allTableData[1][1]
        }
      }
    },
    tableDataResponseChange (id) {
      this.tableData.forEach(item => {
        if (item.applyId === id) {
          this.tableDataResponse[0] = item
        }
        this.randomKeyResponse = Math.random()
      })
    },
    /**
     * @description 物品回应上传api
     * @param {Object} param0
     * @param {Number} applyId
     * @param {String} name
     * @param {Number} result 回应结果(同意与否 2为同意，0为否)
     * @param {String} reason 回应结果原因
     * @param {String} responseTime 回应时间
     */
    async itemResponseAPI ({ applyId, name, result, reason, responseTime }) {
      return await api.ITEM_RESPONSE_API({ applyId, name, result, reason, responseTime })
    },
    /**
     * @description 物品回应上传
     * @param {Object} param0
     * @param {Number} applyId
     * @param {String} name
     * @param {Number} result 回应结果(同意与否 2为同意，0为否)
     * @param {String} reason 回应结果原因
     */
    itemResponse ({ applyId, name, result, reason }) {
      const responseTime = dayjs().format('YYYY-MM-DD HH:mm:ss')
      this.itemResponseAPI({ applyId, name, result, reason, responseTime })
        .then(v => {
          if (typeof v === 'object') {
            this.ruleForm = {
              applyId: null,
              name: '',
              result: 2,
              reason: ''
            }
            this.radioDispose = 1
            this.randomKeyPopover = Math.random()
            this.$refs.ruleForm.clearValidate()
            this.$message({
              message: '回应上传成功',
              type: 'success'
            })
            this.loadAnimation = true
            this.itemShowApply()
          } else if (v === 'fail') {
            this.$message.error('上传失败')
          }
        })
    },
    /**
     * @description 所有物品申请信息展示api
     */
    async itemShowApplyAPI () {
      return await api.ITEM_SHOW_APPLY_API()
    },
    /**
     * @description 所有物品申请信息展示
     */
    itemShowApply () {
      this.tableData = []
      this.tableDataResponse = []
      this.allTableData = [[[], []], [[], []]]
      this.radio = {
        admin: '未处理',
        ordinary: '我的'
      }
      this.itemShowApplyAPI()
        .then(v => {
          if (typeof v[0] === 'undefined') {
            return this.$message({
              message: '没有任何物品申请'
            })
          }
          if ('applyId' in v[0]) {
            v.forEach((item, index) => {
              this.allTableData[1][1].push(JSON.parse(JSON.stringify(item)))
              this.allTableData[1][1][this.allTableData[1][1].length - 1].content = item.content.split('//////////')[0] + ' × ' + item.content.split('//////////')[1] + ': ' + item.content.split('//////////')[2]
              if (item.status === -1) {
                this.allTableData[1][1][this.allTableData[1][1].length - 1].state = 0
              }
            })
            this.itemShowResponse()
          } else {
            this.$message.error('物品申请信息请求失败')
          }
        })
    },
    /**
     * @description 所有物品回应信息展示api
     */
    async itemShowResponseAPI () {
      return await api.ITEM_SHOW_RESPONSE_API()
    },
    /**
     * @description 所有物品回应信息展示
     */
    itemShowResponse () {
      this.itemShowResponseAPI()
        .then(v => {
          if (typeof v[0] === 'undefined') {
            this.allTableData[1][0] = this.allTableData[1][1].filter((item, index) => {
              if (item.applyUser === util.cookies.get('name')) {
                return item
              }
            })
            this.allTableData[0][0] = this.allTableData[1][1].filter((item, index) => {
              if (item.status === -1) return item
            })
            this.allTableData[0][1] = this.allTableData[1][1].filter((item, index) => {
              if (item.status !== -1) return item
            })

            if (this.userAdministratorPermissions) {
              if (this.radio.admin === '未处理') {
                this.tableData = this.allTableData[0][0]
              } else {
                this.tableData = this.allTableData[0][1]
              }
            } else {
              if (this.radio.ordinary === '我的') {
                this.tableData = this.allTableData[1][0]
              } else {
                this.tableData = this.allTableData[1][1]
              }
            }
            setTimeout(() => {
              this.loadAnimation = false
            }, 300)
            return
          }
          if ('applyId' in v[0]) {
            const applyData = JSON.parse(JSON.stringify(this.allTableData[1][1]))
            v.forEach((item, index) => {
              applyData.forEach((item2, index2) => {
                if (item.applyId === item2.applyId && item.status === 1) {
                  this.allTableData[1][1][index2].responseTime = item.responseTime
                  this.allTableData[1][1][index2].responseUser = item.responseUser
                  this.allTableData[1][1][index2].reason = item.reason
                  this.allTableData[1][1][index2].state = item.result === 2 ? 1 : 2
                }
              })
            })
            this.allTableData[1][0] = this.allTableData[1][1].filter((item, index) => {
              if (item.applyUser === util.cookies.get('name')) {
                return item
              }
            })
            this.allTableData[0][0] = this.allTableData[1][1].filter((item, index) => {
              if (item.status === -1) return item
            })
            this.allTableData[0][1] = this.allTableData[1][1].filter((item, index) => {
              if (item.status !== -1) return item
            })

            if (this.userAdministratorPermissions) {
              if (this.radio.admin === '未处理') {
                this.tableData = this.allTableData[0][0]
              } else {
                this.tableData = this.allTableData[0][1]
              }
            } else {
              if (this.radio.ordinary === '我的') {
                this.tableData = this.allTableData[1][0]
              } else {
                this.tableData = this.allTableData[1][1]
              }
            }
            setTimeout(() => {
              this.loadAnimation = false
            }, 300)
          } else {
            this.$message.error('物品回应信息请求失败')
          }
        })
    },
    /**
     * @description popover 消失时触发
     */
    ruleFormClear () {
      this.ruleForm = {
        applyId: null,
        name: '',
        result: 2,
        reason: ''
      }
      this.radioDispose = 1
      this.$refs.ruleForm.clearValidate()
    },
    /**
     * @description popover 出现时触发
     * @param {Object} scope
     */
    ruleFormData (scope) {
      setTimeout(() => {
        this.radioDispose = 1
        this.ruleForm = {
          applyId: scope.row.applyId,
          name: util.cookies.get('name'),
          result: 2,
          reason: ''
        }
      }, 30)
    },
    /**
     * @description 提交表单
     */
    submit () {
      this.$refs.ruleForm.validate((valid) => {
        if (valid) {
          this.itemResponse({
            applyId: this.ruleForm.applyId,
            name: this.ruleForm.name,
            result: this.ruleForm.result,
            reason: this.ruleForm.reason
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
