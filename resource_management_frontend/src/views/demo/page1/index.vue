<template>
  <div :style="{ 'max-height': this.timeLineHeight + 'px' }" style="overflow-y: scroll">
   <div v-if="!showResultPage">
    <div style="padding: 5px 20px">
      <br />
      <el-form :inline="true" :model="listQuery" class="demo-form-inline">
        <el-radio-group
          v-model="searchType"
          fill="#c0bce9"
          style="margin-bottom: 10px"
        >
          <el-radio-button label="id">根据ID查询</el-radio-button>
        </el-radio-group>

        <el-input
          v-model="searchValue"
          placeholder="请输入查询内容"
          style="margin-bottom: 10px"
        ></el-input>

        <el-button
          @click="search"
          icon="el-icon-search"
          style="margin-bottom: 10px"
          type="primary"
        >
          查询
        </el-button>

        <el-form-item>
          <el-button
            @click="TJCollectionForm"
            icon="el-icon-edit"
            style="margin-left: 50px"
            type="success"
            >添加</el-button
          >

           <el-button
            @click="getCanBeUsed"
            icon="el-icon-edit"
            style="margin-left: 50px"
            type="warning"
            >查看所有可支配资产</el-button
          >

         
        </el-form-item>
      </el-form>

      <!-- 表格-->
      <el-table
        :data="list"
        element-loading-text="Loading"
        border
        fit
        highlight-current-row
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55px"></el-table-column>

        <el-table-column align="center" label="id" prop="id"> </el-table-column>
        <el-table-column align="center" label="资产" prop="asset">
        </el-table-column>
        <el-table-column align="center" label="资产价值" prop="assetValue">
        </el-table-column>
        <el-table-column align="center" label="是否可支配(是/否)" prop="judge">
        </el-table-column>
        <el-table-column align="center" label="创建时间" prop="updateTime">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>&nbsp;
            <span
              style="margin-left: 5px"
              v-text="formatDateTime(scope.row.updateTime)"
            ></span>
          </template>
        </el-table-column>
        <el-table-column fixed="right" label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip
              class="item"
              effect="dark"
              content="修改"
              placement="top"
            >
              <el-button
                type="primary"
                icon="el-icon-edit"
                circle
                @click="BJCollectionForm(scope.row)"
              ></el-button>
            </el-tooltip>
            <el-tooltip
              class="item"
              effect="dark"
              content="删除"
              placement="top"
            >
              <el-button
                type="danger"
                icon="el-icon-delete"
                circle
                @click="deleteVisible(scope)"
              ></el-button>
            </el-tooltip>
          </template>
        </el-table-column>

        <el-button
          type="danger"
          icon="el-icon-delete"
          style="margin-left: 20px"
          @click="batchDelete"
          v-if="selection.length &gt; 0"
        >
          批量删除
        </el-button>
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
      >
      </el-pagination>

      <el-dialog
        :title="textMap[dialogStatus]"
        :visible.sync="dialogFormVisible"
      >
        <el-form
          ref="collectionform"
          :rules="rules"
          :model="ruleForm"
          label-width="120px"
          label-position="left"
        >
          <el-form-item
            label="资产"
            prop="asset"
            v-if="dialogStatus === 'create'"
          >
            <el-input placeholder="资产" v-model="ruleForm.asset"></el-input>
          </el-form-item>
          <el-form-item
            label="资产价值"
            prop="assetValue"
            v-if="dialogStatus === 'create'"
          >
            <el-input
              placeholder="资产价值"
              v-model="ruleForm.assetValue"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="是否可支配"
            prop="judge"
            v-if="dialogStatus === 'create'"
          >
            <el-select v-model="ruleForm.judge" placeholder="是否可支配">
              <el-option label="是" value="是"></el-option>
              <el-option label="否" value="否"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item
            label="资产"
            prop="asset"
            v-if="dialogStatus === 'update'"
          >
            <el-input placeholder="资产" v-model="ruleForm.asset"></el-input>
          </el-form-item>
          <el-form-item
            label="资产价值"
            prop="assetValue"
            v-if="dialogStatus === 'update'"
          >
            <el-input
              placeholder="资产价值"
              v-model="ruleForm.assetValue"
            ></el-input>
          </el-form-item>
          <el-form-item
            label="是否可支配"
            prop="judge"
            v-if="dialogStatus === 'update'"
          >
            <el-select v-model="ruleForm.judge" placeholder="是否可支配">
              <el-option label="是" value="是"></el-option>
              <el-option label="否" value="否"></el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button
            type="primary"
            v-if="dialogStatus === 'create'"
            @click="submitCollectionForm('collectionform')"
            >添 加</el-button
          >
          <el-button
            type="warning"
            v-else
            @click="updateCollectionForm('collectionform')"
            >修 改</el-button
          >
        </div>
      </el-dialog>
    </div>
   </div>

<div v-else>
  <el-card>
      <h3 slot="header">查询结果：</h3>
      <div class="result-content">
        <el-row>
          <el-col :span="24">
            <el-form label-position="top">
              <el-form-item label="结果信息">
                <el-table
                  :data="result"
                  element-loading-text="Loading"
                  border
                  :key="randomKey"
                  fit
                  highlight-current-row
                >
                  <el-table-column align="center" label="ID" prop="id"></el-table-column>
                  <el-table-column align="center" label="资产" prop="asset"></el-table-column>
                  <el-table-column align="center" label="资产价值" prop="assetValue"></el-table-column>
                  <el-table-column align="center" label="是否可支配(是/否)" prop="judge"></el-table-column>
                  <el-table-column align="center" label="创建时间" prop="updateTime">
                    <template slot-scope="scope">
                      <i class="el-icon-time"></i>&nbsp;
                      <span
                        style="margin-left: 5px"
                        v-text="formatDateTime(scope.row.updateTime)"
                      ></span>
                    </template>
                  </el-table-column>
                </el-table>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
        </el-card>
         <el-button
        @click="goBack"
        icon="el-icon-arrow-left"
        style="margin-top: 10px"
        type="primary"
      >
        返回
      </el-button>
</div>
  </div>

</template>

<script>
import axios from "axios";

export default {
  name: "Page1",
  data() {
    return {
      randomKey: Math.random(),
      searchType: "id", // 查询类型
      searchValue: "", // 查询内容
      result: [], // 查询结果
      showResultPage: false, // 控制是否显示结果页面
      selection: [],
      timeLineHeight: "",
      listLoading: true,
      idQuery: "",
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        pageSize: 10,
      },

      dialogFormVisible: false,
      dialogStatus: "create",
      textMap: {
        update: "修改资产",
        create: "添加资产",
      },
      ruleForm: {
        id: "",
        asset: "",
        assetValue: "",
        judge: "",
        updateTime: "",
      },
      rules: {
        assetValue: [
          { required: true, message: "请输入资产数目", trigger: "blur" },
        ],
        asset: [{ required: true, message: "请输入资产", trigger: "blur" }],
        judge: [
          { required: true, message: "请选择是否可支配", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.axiosdata();
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
      this.axiosdata();
    },
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.axiosdata();
    },
    onSubmitSelect() {
      this.listQuery.page = 1; // 重置页码为第一页
      this.listQuery.id = this.idQuery; // 设置ID查询参数
      this.axiosdata();
    },
    axiosdata() {
      this.listLoading = true;
      axios
        .get("http://localhost:9090/fundsVo/page", {
          params: this.listQuery,
          headers: {
            "Content-Type": "application/json",
          },
        })
        .then((response) => {
          this.listLoading = false;
          this.list = response.data.data.records;
          this.total = response.data.data.total;
        })

        .catch((error) => {
          console.log(error);
        });
    },

    TJCollectionForm() {
      Object.assign(this.$data.ruleForm, this.$options.data().ruleForm);
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      // this.resetCollectionForm();
    },
    BJCollectionForm(row) {
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.ruleForm = {
        id: row.id,
        asset: row.asset,
        assetValue: row.assetValue,
        judge: row.judge,
        updateTime: row.updateTime,
      };
    },

    submitCollectionForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const url = "http://localhost:9090/fundsVo/update";
          axios
            .post(url, this.ruleForm)
            .then((response) => {
              if (response.data.status === 200) {
                this.axiosdata();
                this.dialogFormVisible = false;
                this.$message.success(
                  this.dialogStatus === "create"
                    ? "添加数据成功！"
                    : "修改数据成功！"
                );
              } else {
                this.$message.error(
                  this.dialogStatus === "create"
                    ? "添加数据失败！"
                    : "修改数据失败！"
                );
              }
            })
            .catch((error) => {
              console.error(error);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    updateCollectionForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const url = "http://localhost:9090/fundsVo/update";
          axios
            .post(url, this.ruleForm)
            .then((response) => {
              if (response.data.status === 200) {
                this.axiosdata();
                this.dialogFormVisible = false;
                this.$message.success(
                  this.dialogStatus === "update"
                    ? "添加数据成功！"
                    : "修改数据成功！"
                );
              } else {
                this.$message.error(
                  this.dialogStatus === "update"
                    ? "添加数据失败！"
                    : "修改数据失败！"
                );
              }
            })
            .catch((error) => {
              console.error(error);
            });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },

    handleSelectionChange(selection) {
      this.selection = selection;
    },

    deleteVisible(scope) {
      this.$confirm("确定要删除" + scope.row.asset + "吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const id = scope.row.id;
          axios
            .delete(`http://localhost:9090/fundsVo/` + id)
            .then((response) => {
              if (response.data.status === 200) {
                this.axiosdata();
                this.dialogFormVisible = false;
                this.$message.success("删除完毕");
              } else {
                this.$message.error("删除数据失败！");
              }
            })
            .catch((error) => {
              console.error(error);
            });
        })
        .catch(() => {
          // 取消删除操作
        });
    },

    batchDelete() {
      // 获取选中行的 ID 数组
      const ids = this.selection.map((item) => item.id);

      // 发送删除请求，调用批量删除的接口
      axios
        .post("http://localhost:9090/fundsVo/batch", ids)
        .then(() => {
          // 删除成功后的操作
          this.$message.success("批量删除成功");
          // 清空选中行
          this.selection = [];
          // 刷新数据
          this.axiosdata();
        })
        .catch((error) => {
          // 处理错误情况
          console.error(error);
          this.$message.error("批量删除失败");
        });
    },

     
    search() {
      const url = `http://localhost:9090/fundsVo/fundsVo/${this.searchValue}`;
      axios
        .get(url)
        .then((response) => {
          this.result[0] = response.data.data
          this.randomKey = Math.random()
          this.showResultPage = true;
        })
        .catch((error) => {
          console.error(error);
        });
    },

    goBack() {
      this.showResultPage = false; // 返回原页面
    },

  getCanBeUsed() {
      this.$router.push('/page2');
    }
  },
};
</script>
<style>
.search-page {
  padding: 20px;
}

.search-title {
  font-size: 18px;
  margin-bottom: 10px;
}

.search-list {
  list-style: none;
  padding-left: 0;
}

.search-item {
  margin-bottom: 5px;
}

.result-content {
  margin: 20px;
}
</style>
