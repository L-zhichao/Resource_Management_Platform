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
        <el-form-item label="ID" prop="id" v-if="dialogStatus == 'create'">
          <el-input placeholder="ID" v-model="ruleForm.id"></el-input>
        </el-form-item>
        <el-form-item label="资产" prop="asset" v-if="dialogStatus == 'create'">
          <el-input placeholder="资产" v-model="ruleForm.asset"></el-input>
        </el-form-item>
        <el-form-item label="是否可支配" prop="judge">
          <el-input
            placeholder="是否可支配"
            v-model="ruleForm.judge"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button
          type="primary"
          v-if="dialogStatus == 'create'"
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
  name: "page1",
  code: 0,
  msg: "",
  data() {
    return {
      listLoading: true,
      list: null,
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
        judge: 0,
        updateTime: "",
      },
      rules: {
        id: [{ required: true, message: "请输入ID", trigger: "blur" }],
        asset: [{ required: true, message: "请输入资产", trigger: "blur" }],
        judge: [
          { required: true, message: "请输入是否可支配", trigger: "blur" },
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
      this.axiosdata();
    },
    axiosdata() {
      this.listLoading = true;
      axios
        .post("/api/post/user", this.listQuery)
        .then((response) => {
          this.listLoading = false;
          this.list = response.data.result.list;
          this.total = response.data.result.total;
           console.log(response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    TJCollectionForm() {
      Object.assign(this.$data.ruleForm, this.$options.data().ruleForm);
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
    },
    BJCollectionForm(index) {
      this.ruleForm = JSON.parse(JSON.stringify(this.list[index]));
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
    },
    submitCollectionForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          axios
            .post("/api/post/user", this.ruleForm)
            .then((response) => {
              if (response.data.resStatus.resCode == 0) {
                this.axiosdata();
                this.dialogFormVisible = false;
                this.$message.success("添加数据成功！");
              } else {
                this.$message.error("添加数据失败！");
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
          axios
            .post("https://mock.presstime.cn/mock/64bde910ce1b0ea640803575/fundsVo", this.ruleForm)
            .then((response) => {
              if (response.data.resStatus.resCode == 0) {
                this.axiosdata();
                this.dialogFormVisible = false;
                this.$message.success("修改数据成功！");
              } else {
                this.$message.error("修改数据失败！");
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
      this.$confirm("确定要删除【" + scope.row.asset + "】吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        axios
          .post("/deletehs_collection_unit", scope.row)
          .then((response) => {
            if (response.data.resStatus.resCode == 0) {
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
      });
    },
  },
};
</script>

<style>
</style>
