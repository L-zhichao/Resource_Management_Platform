<template>
  <el-table
    highlight-current-row
    @current-change="handleCurrentChange"
    height="100%"
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="itemname"
      label="物品"
      width="300">
    </el-table-column>
    <el-table-column
      label="图片"
      width="100">
      <template slot-scope="scope">
        <!-- 这个popover组件用于生成悬浮图片 -->
        <el-popover placement="right" title="" trigger="hover">
          <img :src="scope.row.imgs" alt="" style="height: 200px">
          <!-- image -->
          <el-image slot="reference" style="height: 35px" :src="scope.row.imgs" :preview-src-list="[scope.row.imgs]">
            <div slot="error" class="image-slot">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </el-popover>
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
      prop="itemDescription"
      label="描述">
    </el-table-column>
    <el-table-column
      label="操作"
      width="110">
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row)" type="text" size="small" v-if="userAdministratorPermissions">修改</el-button>
        <el-button type="text" size="small" v-if="userAdministratorPermissions">删除</el-button>
        <el-button @click="dialogOldItemArouse(scope.row)" type="text" size="small" v-if="!userAdministratorPermissions">问题上报</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import util from '@/libs/util'
// import api from '@/api'
export default {
  name: 'ItemTable',
  props: {
    tableData: {
      required: true
    }
  },
  methods: {
    handleClick (row) {
      console.log(row)
      console.log(this.userAdministratorPermissions)
    },
    handleCurrentChange (row) {
      this.currentlySelected = row
      this.$emit('dialogOldItem', { itemId: row.itemId, itemname: row.itemname, number: row.number }, false)
    },
    dialogOldItemArouse (row) {
      this.$emit('dialogOldItem', { itemId: row.itemId, itemname: row.itemname, number: row.number }, true)
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
  mounted () {

  }
}
</script>

<style scoped>

</style>
