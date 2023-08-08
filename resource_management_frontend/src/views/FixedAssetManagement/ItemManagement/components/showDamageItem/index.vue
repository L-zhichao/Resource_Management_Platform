<template>
  <el-drawer
    title="物品损坏上报信息"
    :visible.sync="drawerVisible"
    size="80%"
    direction="btt">
    <el-radio-group size="small" v-model="radio" @input="radioChange" :style="{ marginLeft: '16px' }">
      <el-radio-button label="待处理"></el-radio-button>
      <el-radio-button label="已处理"></el-radio-button>
    </el-radio-group>
    <el-table
      :data="tableData"
      :key="randomKey"
      style="width: 100%"
      empty-text="没有物品损坏或已全部完成处理"
      v-loading="loadAnimation">
      <el-table-column
        prop="damageRecordTime"
        label="损坏日期"
        width="140">
      </el-table-column>
      <el-table-column
        prop="username"
        label="上报人"
        width="140"
        v-if="userAdministratorPermissions">
      </el-table-column>
      <el-table-column
        prop="itemName"
        label="损坏物品"
        width="140">
      </el-table-column>
      <el-table-column
        prop="number"
        label="损坏数量"
        width="120">
      </el-table-column>
      <el-table-column
        prop="damageRecordImgs"
        label="图片"
        width="360">
        <template slot-scope="scope">
          <!-- 这个popover组件用于生成悬浮图片 -->
          <el-row :gutter="20">
            <el-col :span="4" v-for="(img, index) in scope.row.imgs" :key="index">
              <el-popover class="images" placement="top" title="" trigger="hover">
                <img :src="img" alt="" style="height: 400px">
                <!-- image -->
                <el-image slot="reference" style="height: 40px" :src="img" :preview-src-list="[img]">
                  <div slot="error" class="image-slot">
                    <i class="el-icon-picture-outline"></i>
                  </div>
                </el-image>
              </el-popover>
            </el-col>
          </el-row>
        </template>
      </el-table-column>
      <el-table-column
        prop="damageRecordDesc"
        label="描述">
      </el-table-column>
      <el-table-column
      label="操作"
      width="110"
      v-if="userAdministratorPermissions">
      <template slot-scope="scope">
        <el-button @click="finishDamageInformation(scope.row)" type="text" size="small" v-if="radio === '待处理'">完成处理</el-button>
        <el-button @click="deleteDamageInformation(scope.row)" type="text" size="small" v-if="radio === '已处理'">删除</el-button>
      </template>
    </el-table-column>
    </el-table>
  </el-drawer>
</template>

<script>
import api from '@/api'
import util from '@/libs/util'
export default {
  name: 'showDamageItem',
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
        damageRecordId: 1,
        damageRecordDesc: '桌腿坏了',
        damageRecordTime: '2023-5-5',
        itemName: '桌子',
        username: '王五',
        damageRecordImg: '',
        imgs: [],
        damageRecordIsHandle: false
      }],
      allTableData: {
        tableDataWait: [],
        tableDataFinish: []
      },
      // 用于判断 tableData 内容
      // 待处理 放 tableDataWait 待处理
      // 已处理 放 tableDataFinish 已处理
      radio: '待处理',
      // 随机Key,用于刷新表格
      randomKey: Math.random(),
      // 用于启动表格加载动画
      loadAnimation: false,
      drawerVisible: false,
      // 0 为 true 是管理员
      // 1 为 false 非管理员
      userAdministratorPermissions: util.cookies.get('userStatus') === '0' || false
    }
  },
  watch: {
    drawerArouse () {
      this.loadAnimation = true
      if (this.userAdministratorPermissions) this.itemSearchDamage({ username: null })
      if (!this.userAdministratorPermissions) this.itemSearchDamage({ username: util.cookies.get('username') })
      this.drawerVisible = true
    },
    /**
     * @description 检测 抽屉 开合时,数据恢复展示 待处理 数据
     */
    drawerVisible () {
      this.radio = '待处理'
    }
  },
  methods: {
    handleClick (row) {
      this.$emit('dialogChangeItem', row)
    },
    radioChange (row) {
      clearTimeout(this.radioChange_timeout)
      this.radio = row
      this.loadAnimation = true
      this.radioChange_timeout = setTimeout(() => {
        if (this.radio === '待处理') {
          this.tableData = this.allTableData.tableDataWait
        } else {
          this.tableData = this.allTableData.tableDataFinish
        }
        this.loadAnimation = false
      }, 300)
    },
    async showImgAPI (url) {
      return await api.SHOW_IMG_API(url)
    },
    /**
     * @description 损坏物品查询请求api
     */
    async itemSearchDamageAPI ({ username }) {
      if (username === null) return await api.ITEM_SEARCH_DAMAGED_API()
      if (typeof username === 'string') return await api.ITEM_SEARCH_DAMAGED_API({ username: username })
    },
    /**
     * @description 损坏物品查询请求
     */
    itemSearchDamage ({ username = null }) {
      this.itemSearchDamageAPI({ username })
        .then(async v => {
          if (v === 'fail') {
            this.loadAnimation = false
            return this.$message.error('信息获取失败')
          } else if (v.status >= 40000) {
            this.loadAnimation = false
            this.$log.push({
              message: '错误代码:' + v.status + ',' + v.message,
              type: 'warning'
            })
            return this.$notify({
              title: v.message,
              message: '错误代码:' + v.status,
              position: 'bottom-left',
              type: 'warning'
            })
          }
          for (let i = 0; i < v.length; i++) {
            v[i].imgs = v[i].damageRecordImg.split('//////////')
            for (let j = 0; j < v[i].imgs.length; j++) {
              await this.showImgAPI(v[i].imgs[j])
                .then(value => {
                  v[i].imgs[j] = value
                })
            }
          }
          this.allTableData.tableDataWait = await v.filter((item, index) => {
            if (item.damageRecordIsHandle === false) {
              item.number = item.damageRecordDesc.split('//////////')[1]
              item.damageRecordDesc = item.damageRecordDesc.split('//////////')[0]
              return item
            }
          })
          this.allTableData.tableDataFinish = await v.filter((item, index) => {
            if (item.damageRecordIsHandle === true) {
              item.number = item.damageRecordDesc.split('//////////')[1]
              item.damageRecordDesc = item.damageRecordDesc.split('//////////')[0]
              return item
            }
          })
          if (this.radio === '待处理') {
            this.tableData = this.allTableData.tableDataWait
          } else {
            this.tableData = this.allTableData.tableDataFinish
          }
          this.loadAnimation = false
        })
    },
    /**
     * @description 删除物品损坏api
     * @param {Object} param0
     * @param {Number} param0.id 物品Id
     */
    async itemDeleteDamageAPI ({ id }) {
      return await api.ITEM_DELETE_DAMAGED_API({ id })
    },
    /**
     * @description 删除物品损坏
     * @param {Object} param0
     * @param {Number} param0.id 物品Id
     */
    itemDeleteDamage ({ id }) {
      this.itemDeleteDamageAPI({ id })
        .then(v => {
          if (v === null) {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
            this.itemSearchDamage({ username: null })
          } else if (v === 'fail') {
            this.$message.error('删除失败')
          } else if (v.status >= 40000) {
            this.$log.push({
              message: '错误代码:' + v.status + ',' + v.message,
              type: 'warning'
            })
            return this.$notify({
              title: v.message,
              message: '错误代码:' + v.status,
              position: 'bottom-left',
              type: 'warning'
            })
          }
        })
    },
    /**
     * @description 删除物品损坏api
     * @param {Object} param0
     * @param {Number} param0.id 物品Id
     */
    async itemFinishDamageAPI ({ id }) {
      return await api.ITEM_FINISH_DAMAGED_API({ id })
    },
    /**
     * @description 完成物品损坏
     * @param {Object} param0
     * @param {Number} param0.id 物品Id
     */
    itemFinishDamage ({ id }) {
      this.itemFinishDamageAPI({ id })
        .then(v => {
          if (v === null) {
            this.$message({
              message: '处理成功',
              type: 'success'
            })
            this.itemSearchDamage({ username: null })
          } else if (v === 'fail') {
            this.$message.error('处理失败')
          } else if (v.status >= 40000) {
            this.$log.push({
              message: '错误代码:' + v.status + ',' + v.message,
              type: 'warning'
            })
            return this.$notify({
              title: v.message,
              message: '错误代码:' + v.status,
              position: 'bottom-left',
              type: 'warning'
            })
          }
        })
    },
    /**
     * @description 修改损坏为完成
     * @param {*} row
     */
    finishDamageInformation (row) {
      this.itemFinishDamage({ id: row.damageRecordId })
    },
    /**
     * @description 修改损坏为删除
     * @param {*} row
     */
    deleteDamageInformation (row) {
      this.itemDeleteDamage({ id: row.damageRecordId })
    }
  }
}
</script>

<style scoped>

</style>
