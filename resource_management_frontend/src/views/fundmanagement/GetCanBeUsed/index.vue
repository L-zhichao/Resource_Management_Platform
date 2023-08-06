<template>
  <div :style="{ 'max-height': timeLineHeight + 'px' }" style="overflow-y: scroll">
    <div style="padding: 5px 20px">
      <br />
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">
        <!-- 表单内容 -->
      </el-form>

      <!-- 表格-->
      <el-table :data="list" element-loading-text="Loading" border fit highlight-current-row>
        <el-table-column align="center" label="id" prop="id"></el-table-column>
        <el-table-column align="center" label="资产" prop="asset"></el-table-column>
        <el-table-column align="center" label="资产价值" prop="assetValue"></el-table-column>
        <el-table-column align="center" label="是否可支配(是/否)" prop="judge"></el-table-column>
        <el-table-column align="center" label="创建时间" prop="updateTime">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>&nbsp;
            <span style="margin-left: 5px" v-text="formatDateTime(scope.row.updateTime)"></span>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="listQuery.page"
        :page-sizes="[10]"
        :page-size="listQuery.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        :background="true"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "getCanBeUsed",
  data() {
    return {
      timeLineHeight: "",
      listLoading: true,
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        pageSize: 10
      }
    };
  },
  created() {
    this.fetchData();
  },
  mounted() {
    this.timeLineHeight = document.documentElement.clientHeight - 210;
    window.onresize = () => {
      this.timeLineHeight = document.documentElement.clientHeight - 210;
    };
  },
  methods: {
    formatDateTime(time) {
      const datetime = new Date(time);
      const year = datetime.getFullYear();
      const month = ("0" + (datetime.getMonth() + 1)).slice(-2);
      const day = ("0" + datetime.getDate()).slice(-2);
      const hours = ("0" + datetime.getHours()).slice(-2);
      const minutes = ("0" + datetime.getMinutes()).slice(-2);
      const seconds = ("0" + datetime.getSeconds()).slice(-2);
      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    },

    handleSizeChange(val) {
      this.listQuery.pageSize = val;
      this.fetchData();
    },
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.fetchData();
    },
    fetchData() {
      this.listLoading = true;
      axios
        .get("http://localhost:9090/fundsVo/getCanBeUsed", {
          params: this.listQuery,
          headers: {
            "Content-Type": "application/json"
          }
        })
        .then(response => {
          this.listLoading = false;
          this.list = response.data.data.records;
          this.total = response.data.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style>
</style>
