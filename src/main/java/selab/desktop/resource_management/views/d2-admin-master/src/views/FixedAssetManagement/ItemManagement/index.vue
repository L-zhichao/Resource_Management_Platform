<template>
  <d2-container>
    <template slot="header">
      <el-row :gutter="20" type="flex" justify="space-between">
        <el-col :span="2.5">
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
        <el-col :span="9">
          <el-input
            ref="inputSearch"
            v-model="inputSearch"
            placeholder="请输入搜索内容"
            @change="handleCurrentChange(1)"
            @keyup.enter.native="searchEnter('inputSearch')"
            @focus="inputFocus('inputSearch')">
          </el-input>
        </el-col>
        <el-col :span="2.5">
          <el-tooltip
            content="新物品购置资金请求"
            placement="bottom">
            <el-button
              type="primary"
              size="medium">
              请求历史
            </el-button>
          </el-tooltip>
        </el-col>
      </el-row>
    </template>

    <!-- 表格 -->
    <ItemTable :tableData="tableData" @dialogOldItem="dialogOldItem"/>

    <!-- 底部分页 -->
    <template slot="footer">
      <el-row :gutter="20" type="flex" class="row-bg" justify="space-around">
        <el-col :span="12">
          <div class="block">
          <!-- @prev-click="handleCurrentChange"
              @next-click="handleCurrentChange" -->
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page.sync="pagination.currentPage"
              :page-size="pagination.pageSize"
              :page-sizes="[5, 10, 20, 30]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="pagination.allData">
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
    <DialogOfOldItem :dialogArouse="dialogOldItemArouseChangesNumber" :oldItemId_Name="oldItemId_Name"/>
  </d2-container>
</template>

<script>
import util from '@/libs/util'
import api from '@/api'
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
      tableData: [],
      drawerNewItemArouseChangesNumber: 0,
      drawerOldItemArouseChangesNumber: 0,
      dialogNewItemArouseChangesNumber: 0,
      dialogOldItemArouseChangesNumber: 0,
      oldItemId_Name: {},
      // 搜索栏的值
      inputSearch: '',
      // 分页数据
      pagination: {
        // 当前页
        currentPage: 1,
        // 每页数量
        pageSize: 5,
        // 总页
        allPage: 1,
        // 总数据量
        allData: 1
      },
      // 0 为 true 是管理员
      // 1 为 false 非管理员
      userAdministratorPermissions: util.cookies.get('userStatus') === '0' || false
    }
  },
  methods: {
    /**
     * @description 分页每页数量改变时触发
     * @param {*} val 当前pageSize
     */
    handleSizeChange (val) {
      console.log(`每页 ${val} 条`)
      this.pagination.pageSize = val
      this.pagination.currentPage = 1
      this.itemSearch({
        page: 1,
        pageSize: this.pagination.pageSize,
        search: this.inputSearch
      })
    },
    /**
     * @description 分页页码改变时触发
     * @param {*} val 当前页
     */
    handleCurrentChange (val) {
      console.log(`当前页: ${val}`)
      this.pagination.currentPage = val
      this.itemSearch({
        page: val,
        pageSize: this.pagination.pageSize,
        search: this.inputSearch
      })
    },
    // handlePrevClick (val) {

    // },
    // handleNextClick (val) {

    // },
    /**
     * @description 搜索框聚焦触发
     * @param {String} that 输入框ref
     */
    inputFocus (that) {
      this.$refs[that].select()
    },
    /**
     * @description 搜索框回车触发
     * @param {String} that 输入框ref
     */
    searchEnter (that) {
      this.$refs[that].blur()
    },
    /**
     * @description 物品查询请求api
     * @param {object} page // Number 分页页码
     * @param {object} pageSize // Number 分页每页数量
     * @param {object} search // String 检索内容
     */
    async itemSearchAPI ({ page, pageSize, search }) {
      return await api.ITEM_SEARCH_API({ page, pageSize, search })
    },
    /**
     * @description 物品查询请求
     * @param {object} page // Number 分页页码
     * @param {object} pageSize // Number 分页每页数量
     * @param {object} search // String 检索内容
     */
    itemSearch ({ page = 1, pageSize = 10, search }) {
      this.itemSearchAPI({ page, pageSize, search })
        .then(v => {
          this.tableData = v.rows
          this.pagination.allPage = v.totalPage
          this.pagination.allData = v.total
        })
    },
    /**
     *  @description 唤起 dialog 旧物品损坏上报
     */
    dialogOldItem ({ itemId, itemname, number }, arouse) {
      this.oldItemId_Name = { itemId, itemname, number }
      if (arouse) this.dialogOldItemArouseChangesNumber++
    }
  },
  mounted () {
    this.itemSearch({
      page: 1,
      pageSize: 10,
      search: ''
    })
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
