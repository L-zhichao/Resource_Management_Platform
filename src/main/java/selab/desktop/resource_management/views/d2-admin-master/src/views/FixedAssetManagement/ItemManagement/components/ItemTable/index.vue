<template>
  <el-table
    highlight-current-row
    height="100%"
    :data="tableData"
    style="width: 100%">
    <el-table-column
      prop="itemName"
      label="物品"
      width="120">
    </el-table-column>
    <el-table-column
      prop="itemNumber"
      label="数量"
      width="120">
    </el-table-column>
    <el-table-column
      prop="itemUnitPrice"
      label="单价"
      width="120">
    </el-table-column>
    <el-table-column
      prop="itemDescription"
      label="描述"
      width="120">
    </el-table-column>
    <el-table-column
      fixed="right"
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row)" type="text" size="small" v-if="userAdministratorPermissions">修改</el-button>
        <el-button type="text" size="small" v-if="userAdministratorPermissions">删除</el-button>
        <el-button @click="handleClick(scope.row)" type="text" size="small" v-if="!userAdministratorPermissions">问题上报</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import util from '@/libs/util'
export default {
  name: 'ItemTable',
  methods: {
    handleClick (row) {
      console.log(row)
      console.log(this.userAdministratorPermissions)
    }
  },
  data () {
    return {
      tableData: [{
        date: '2016-05-02',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1518 弄',
        zip: 200333
      }, {
        date: '2016-05-04',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1517 弄',
        zip: 200333
      }, {
        date: '2016-05-01',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1519 弄',
        zip: 200333
      }, {
        date: '2016-05-03',
        name: '王小虎',
        province: '上海',
        city: '普陀区',
        address: '上海市普陀区金沙江路 1516 弄',
        zip: 200333
      }],
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
