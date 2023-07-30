<template>
  <div style="padding: 5px 20px">
    <br />
    <el-form :inline="true" :model="listQuery" class="demo-form-inline">
      <el-form-item label="资产查询">
        <el-input v-model="listQuery.asset" placeholder="资产"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitSelect" icon="el-icon-search"
          >查询</el-button
        >
      </el-form-item>
      <el-form-item>
        <el-button type="success" @click="TJCollectionForm" icon="el-icon-edit"
          >添加</el-button
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
    >
      <el-table-column align="center" label="ID" width="50" type="index">
      </el-table-column>
      <el-table-column align="center" label="资产" prop="asset">
      </el-table-column>
      <el-table-column align="center" label="资产数目" prop="assetValue">
      </el-table-column>
      <el-table-column align="center" label="资产价值" prop="assetValue">
      </el-table-column>
      <el-table-column align="center" label="是否可支配(1/0)" prop="judge">
      </el-table-column>
      <el-table-column align="center" label="创建时间">
        <template slot-scope="scope">
          <i class="el-icon-time"></i>&nbsp;
          <span
            style="margin-left: 5px"
            v-text="scope.row.updateTime.substring(-1, 19).replace('T', ' ')"
          ></span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" label="操作" width="120">
        <template slot-scope="scope">
          <el-tooltip
            class="item"
            effect="dark"
            content="修改密码"
            placement="top"
          >
            <el-button
              type="primary"
              icon="el-icon-edit"
              circle
              @click="BJCollectionForm(scope.$index)"
            ></el-button>
          </el-tooltip>
          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button
              type="danger"
              icon="el-icon-delete"
              circle
              @click="deleteVisible(scope)"
            ></el-button>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-sizes="[10]"
      :page-size="listQuery.limit"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total"
      :background="true"
    >
    </el-pagination>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
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
        <el-form-item label="是否可支配" prop="judge">
          <el-select v-model="ruleForm.judge" placeholder="是否可支配">
            <el-option label="是" value="1"></el-option>
            <el-option label="否" value="0"></el-option>
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
</template>

<script>
import axios from "axios";

export default {
  name: "Page1",
  data() {
    return {
      listLoading: true,
      list: [],
      total: 0,
      listQuery: {
        page: 1,
        limit: 10,
        asset: "",
      },
      dialogFormVisible: false,
      dialogStatus: "create",
      textMap: {
        update: "修改资产",
        create: "添加资产",
      },
      ruleForm: {
        id: 0,
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
  methods: {
    handleSizeChange(val) {
      this.listQuery.limit = val;
      this.axiosdata();
    },
    handleCurrentChange(val) {
      this.listQuery.page = val;
      this.axiosdata();
    },
    onSubmitSelect() {
      this.listQuery.page = 1; // 重置页码为第一页
      this.axiosdata();
    },
    axiosdata() {
      this.listLoading = true;

      axios
        .get("/fundsVo/page", {
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
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.resetCollectionForm();
    },
    BJCollectionForm(index) {
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.ruleForm = { ...this.list[index] };
    },

    submitCollectionForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          const url =
            this.dialogStatus === "create" ? "/fundsVo/update" : "/updatehs";
          axios
            .post(url, this.ruleForm)
            .then((response) => {
              if (response.data.code === 1) {
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
    deleteVisible(scope) {
      const asset = scope.row.asset;
      this.$confirm(`确定要删除【${asset}】吗？`, "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          axios
            .delete(`/fundsVo/${scope.row.id}`)
            .then((response) => {
              if (response.data.code === 1) {
                this.axiosdata();
                this.dialogFormVisible = false;
                this.$message.error("删除完毕");
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
  },
};
</script>
<style>
</style>
