<template>
  <d2-container>
    <template slot="header">
      <el-row :gutter="20">
        <el-col :span="7">
          <!-- 顶部两个按钮-管理员 -->
          <el-button-group v-if="userAdministratorPermissions">
            <!-- 顶部两个按钮-其一 -->
            <el-tooltip
              content="新物品购置资金请求"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="drawerNewItemArouseChangesNumber++">
                申请信息
              </el-button>
            </el-tooltip>
            <!-- 顶部两个按钮-其二 -->
            <el-tooltip
              content="物品损坏上报"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="drawerOldItemArouseChangesNumber++">
                损坏信息
              </el-button>
            </el-tooltip>
          </el-button-group>
          <!-- 顶部两个按钮-普通用户 -->
          <el-button-group v-if="!userAdministratorPermissions">
            <!-- 顶部两个按钮-其一 -->
            <el-tooltip
              content="新物品购置资金申请"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="dialogNewItemArouseChangesNumber++">
                物品申请
              </el-button>
            </el-tooltip>
            <!-- 顶部两个按钮-其二 -->
            <el-tooltip
              content="物品损坏上报"
              placement="bottom">
              <el-button
                size="medium"
                type="primary"
                @click="dialogOldItemArouseChangesNumber++">
                损坏上报
              </el-button>
            </el-tooltip>
          </el-button-group>
        </el-col>
        <el-col :span="9"><el-input v-model="inputSearch" placeholder="请输入搜索内容"></el-input></el-col>
      </el-row>
    </template>

    <!-- 表格 -->
    <ItemTable/>

    <!-- 底部分页 -->
    <template slot="footer">
      <el-row :gutter="20" type="flex" class="row-bg" justify="space-around">
        <el-col :span="7">
          <div class="block">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="currentPage"
              :page-size="30"
              layout="prev, pager, next, jumper"
              :total="600">
            </el-pagination>
          </div>
        </el-col>
      </el-row>
    </template>
    <!-- 顶部两个按钮其一换出的 drawer -->
    <DrawerOfNewItem :drawerArouse="drawerNewItemArouseChangesNumber"/>
    <!-- 顶部两个按钮其二换出的 drawer -->
    <DrawerOfOldItem :drawerArouse="drawerOldItemArouseChangesNumber"/>
    <!-- 顶部两个按钮其一换出的 dialog -->
    <DialogOfNewItem :dialogArouse="dialogNewItemArouseChangesNumber"/>
    <!-- 顶部两个按钮其二换出的 dialog -->
    <DialogOfOldItem :dialogArouse="dialogOldItemArouseChangesNumber"/>
  </d2-container>
</template>

<script>
import util from '@/libs/util'
import DrawerOfNewItem from './components/DrawerOfNewItem'
import DrawerOfOldItem from './components/DrawerOfOldItem'
import DialogOfNewItem from './components/DialogOfNewItem'
import DialogOfOldItem from './components/DialogOfOldItem'
import ItemTable from './components/ItemTable'
export default {
  name: 'FixedAssetManagement-ItemManagement',
  components: {
    DrawerOfNewItem,
    DrawerOfOldItem,
    DialogOfNewItem,
    DialogOfOldItem,
    ItemTable
  },
  data () {
    return {
      drawerNewItemArouseChangesNumber: 0,
      drawerOldItemArouseChangesNumber: 0,
      dialogNewItemArouseChangesNumber: 0,
      dialogOldItemArouseChangesNumber: 0,
      // 搜索栏的值
      inputSearch: '',
      // 分页跳转
      currentPage: 1,
      // 0 为 true 是管理员
      // 1 为 false 非管理员
      userAdministratorPermissions: util.cookies.get('userStatus') === '0' || false
    }
  },
  methods: {
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
    },
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
    }
  }
}
</script>

<style scoped less="">
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child {
    margin-bottom: 0;
  }
.el-col {
  border-radius: 4px;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc00;
}
.block {
  border-radius: 3px;
}
</style>
