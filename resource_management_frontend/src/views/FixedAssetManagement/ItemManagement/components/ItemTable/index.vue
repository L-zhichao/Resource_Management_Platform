<template>
  <el-table
    highlight-current-row
    @current-change="handleCurrentChange"
    height="100%"
    :data="tableData"
    :key="randomKey"
    v-loading="loadAnimation"
    style="width: 100%">
    <el-table-column
      prop="itemname"
      label="物品"
      width="300">
    </el-table-column>
    <el-table-column
      label="视频图片"
      width="100">
      <template slot-scope="scope">
        <!-- 这个popover组件用于生成悬浮图片 -->
        <el-popover placement="right" trigger="hover" v-if="scope.row.imgs !== null">
          <img :src="scope.row.imgs" alt="" style="height: 300px">
          <!-- image -->
          <el-image slot="reference" style="height: 35px" :src="scope.row.imgs" :preview-src-list="[scope.row.imgs]">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-popover>
        <el-button type="text" size="small" v-if="scope.row.videos !== null" @click="dialogVideoPlayer(scope.row.videos)">查看视频</el-button>
      </template>
    </el-table-column>
    <el-table-column
      prop="number"
      label="数量"
      width="120">
    </el-table-column>
    <el-table-column
      prop="price"
      label="单价(元)"
      width="120">
    </el-table-column>
    <el-table-column
      prop="damageRecordDesc"
      label="描述">
    </el-table-column>
    <el-table-column
      label="操作"
      width="110">
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row)" type="text" size="small" v-if="userAdministratorPermissions">修改</el-button>
        <el-popover
          style="margin-left: 5px;"
          placement="top"
          title="确认删除?"
          width="160"
          :ref="`popover-${scope.$index}`"
          v-if="userAdministratorPermissions"
          trigger="click">
          <div style="text-align: right; margin: 0">
            <el-button type="text" size="small" @click="deleteOperation(scope.row, false)">取消</el-button>
            <el-button type="danger" size="small" @click="deleteOperation(scope.row, true)">删除</el-button>
          </div>
          <el-button slot="reference" type="text" size="small">删除</el-button>
        </el-popover>
        <!-- <el-button type="text" size="small" @click="deleteOperation(scope.row)" v-if="userAdministratorPermissions">删除</el-button> -->
        <el-button @click="dialogOldItemArouse(scope.row)" type="text" size="small" v-if="!userAdministratorPermissions">问题上报</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import util from '@/libs/util'
import api from '@/api'
export default {
  name: 'ItemTable',
  props: {
    tableData: {
      required: true
    },
    // 随机key,用于表格强制刷新
    randomKey: {
      required: true
    },
    loadAnimation: {
      required: true
    }
  },
  data () {
    return {
      currentlySelected: {},
      // 0 为 true 是管理员
      // 1 为 false 非管理员
      userAdministratorPermissions: util.cookies.get('userStatus') === '0' || false
    }
  },
  methods: {
    handleClick (row) {
      this.$emit('dialogChangeItem', row)
    },
    handleCurrentChange (row) {
      if (row === null) return
      this.currentlySelected = row
      this.$emit('dialogOldItem', { itemId: row.itemId, itemname: row.itemname, number: row.number }, false)
    },
    dialogOldItemArouse (row) {
      this.$emit('dialogOldItem', { itemId: row.itemId, itemname: row.itemname, number: row.number }, true)
    },
    /**
     * @description 物品删除请求api
     * @param {Number} id 物品id
     */
    async itemDeleteAPI ({ id }) {
      return await api.ITEM_DELETE_API({ id })
    },
    /**
     * @description 物品删除请求
     * @param {Number} id 物品id
     */
    itemDelete ({ id }) {
      this.itemDeleteAPI({ id })
        .then(v => {
          if (v === null) {
            this.$message({
              message: '删除成功',
              type: 'success'
            })
          } else if (v === 'fail') {
            this.$message.error('删除失败')
          }
        })
    },
    /**
     * @description 删除物品触发
     * @param {Object} row
     * @param {Boolean} confirm 是否删除
     */
    deleteOperation (row, confirm) {
      document.body.click()
      if (!confirm) return
      this.itemDelete({ id: row.itemId })
      this.$emit('deleteInformation', true)
    },
    dialogVideoPlayer (url) {
      this.$emit('dialogVideoPlayerArouse', url)
    }
  }
}
</script>

<style scoped>

</style>
