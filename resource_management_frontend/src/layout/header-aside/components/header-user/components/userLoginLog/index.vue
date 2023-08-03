<template>
  <el-drawer
    title="登录日志"
    :visible.sync="drawer"
    size="80%"
    :append-to-body="true"
    direction="rtl">
    <el-container style="height: 100%">
      <el-main>
        <el-table
          :data="tableData"
          style="width: 100%">
          <el-table-column
            prop="username"
            label="用户">
          </el-table-column>
          <el-table-column
            prop="loginTime"
            label="登录时间">
          </el-table-column>
          <el-table-column
            prop="ip"
            label="登录IP">
          </el-table-column>
        </el-table>
      </el-main>
      <el-footer>
        <div class="block">
          <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="pagination.current"
            :page-size="pagination.size"
            :page-sizes="[5, 10, 20, 30]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="pagination.allData">
          </el-pagination>
        </div>
      </el-footer>
    </el-container>
  </el-drawer>
</template>

<script>
import api from '@api'
export default {
  name: 'userLoginLog',
  props: {
    drawerUserLoginLog: {
      require: true
    }
  },
  data () {
    return {
      tableData: [],
      pagination: {
        current: 1,
        size: 20,
        allData: 300
      },
      drawer: false
    }
  },
  watch: {
    drawerUserLoginLog () {
      this.userLog({ current: this.pagination.current, size: this.pagination.size })
      this.userCount()
      this.drawer = true
    }
  },
  methods: {
    /**
     * @description 每页数量变化时触发
     */
    handleSizeChange (v) {
      this.pagination.size = v
      this.pagination.current = 1
      this.userLog({ current: this.pagination.current, size: this.pagination.size })
      this.userCount()
    },
    /**
     * @description 当前页变化时触发
     */
    handleCurrentChange (v) {
      this.pagination.current = v
      this.userLog({ current: this.pagination.current, size: this.pagination.size })
      this.userCount()
    },
    /**
     * @description 登入记录log api
     * @param {Number} current 当前页
     * @param {Number} size 每页数量
     */
    async userLogAPI ({ current, size }) {
      return await api.SYS_USER_LOG_API({ current, size })
    },
    /**
     * @description 登入记录log
     * @param {Number} current 当前页
     * @param {Number} size 每页数量
     */
    userLog ({ current, size }) {
      this.userLogAPI({ current, size })
        .then(v => {
          if (typeof v[0] === 'undefined') {
            return this.$message.error('没有任何登录日志。???')
          }
          if ('logId' in v[0]) {
            this.tableData = v
          } else {
            this.$message.error('登录日志请求失败')
          }
        })
    },
    /**
     * @description 用户登录log总数 api
     */
    async userCountAPI () {
      return await api.SYS_USER_COUNT_API()
    },
    /**
     * @description 用户登录log总数
     */
    userCount () {
      this.userCountAPI()
        .then(v => {
          if (v === 0) {
            return this.$message.error('没有任何登录日志。???')
          }
          if (typeof v === 'number') {
            this.pagination.allData = v
          } else {
            this.$message.error('登录日志请求失败')
          }
        })
    }
  }
}
</script>

<style>

</style>
