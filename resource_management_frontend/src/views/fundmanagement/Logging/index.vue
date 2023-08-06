<template>
  <div
    :style="{ 'max-height': timeLineHeight + 'px' }"
    style="overflow-y: scroll"
  >
    <div style="padding: 5px 20px">
      <br/>
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">
        <el-form>
          <el-form-item label="选择时间">
            <el-date-picker
              v-model="targetDate"
              align="right"
              type="date"
              placeholder="选择日期"
              :picker-options="pickerOptions"
            >
            </el-date-picker>
          </el-form-item>

          <el-form-item>
            <el-button @click="loadData" type="primary">查询</el-button>
          </el-form-item>

          <el-form-item>
            <el-button @click="fetchData" type="danger">重置</el-button>
          </el-form-item>
        </el-form>
      </el-form>

      <el-table :data="logs" border element-loading-text="Loading">
        <el-table-column
          label="ID"
          type="index"
          align="center"
        ></el-table-column>
        <el-table-column label="更新时间" prop="updateTime" align="center">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>&nbsp;
            <span
              style="margin-left: 5px"
              v-text="formatDateTime(scope.row.updateTime)"
            ></span
            ></template>
        </el-table-column>
        <el-table-column
          label="描述"
          prop="summary"
          align="center"
        ></el-table-column>
        <el-table-column
          label="资产ID"
          prop="assetId"
          align="center"
        ></el-table-column>
        <el-table-column
          label="资产名"
          prop="asset"
          align="center"
        ></el-table-column>
        <el-table-column
          label="资产价值"
          prop="assetValue"
          align="center"
        ></el-table-column>
        <el-table-column
          label="判断是否可支配"
          prop="judge"
          align="center"
        ></el-table-column>
      </el-table>

      <el-pagination
        v-if="total &gt; 0"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="listQuery.page"
        :page-sizes="[10]"
        :page-size="listQuery.pageSize"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>


</template>

<script>
import axios from "axios";

export default {
  name: 'logging',
  data() {
    return {
      timeLineHeight: "",

      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
        shortcuts: [
          {
            text: "今天",
            onClick(picker) {
              picker.$emit("pick", new Date());
            },
          },
          {
            text: "昨天",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24);
              picker.$emit("pick", date);
            },
          },
          {
            text: "一周前",
            onClick(picker) {
              const date = new Date();
              date.setTime(date.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", date);
            },
          },
        ],
      },

      targetDate: "",
      logs: [], // 存储从后端获取的日志数据
      total: 0, // 总记录数
      listQuery: {
        page: 1, // 默认的页面数
        pageSize: 10, // 默认的页面大小
        updateTime: "", // 用于存储 updateTime 参数
      },
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
      return `${year}-${month}-${day}`;

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
        .get("http://localhost:9090/logs/page", {
          params: this.listQuery,
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          this.listLoading = false;
          this.logs = response.data.data.records;
          this.total = response.data.data.total;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    loadData() {
      const params = {
        page: this.listQuery.page,
        pageSize: this.listQuery.pageSize,
        updateTime: this.formatDateTime(this.targetDate),
      };

      if (this.listQuery.updateTime) {
        params.updateTime = this.listQuery.updateTime;
      }

      axios
        .get(`http://localhost:9090/logs`, {
          params: params,
          headers: {
            "Content-Type": "application/x-www-form-urlencoded",
          },
        })
        .then((response) => {
          this.logs = response.data.data.records;
          this.total = response.data.data.total;

        })
        .catch((error) => {
          console.error(error);
        });
    },
  },
};
</script>

<style scoped>
</style>
