<template>
  <div>
    <el-card>
      <h3 slot="header">查询结果：</h3>
      <div class="result-content">
        <el-row>
          <el-col :span="24">
            <el-form label-position="top">
              <el-form-item label="结果信息">
                <el-input v-model="result" readonly></el-input>
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </div>
    </el-card>

    <el-button @click="$router.go(-1)" type="primary">返回</el-button>
  </div>
</template>

<script>
export default {
  name: ResultPage,
  data() {
    return {
      result: "",
    };
  },
  created() {
    // 获取查询参数
    const { searchType, searchValue } = this.$route.params;
    axios.get(`/fundsVo/fundsVo/${searchValue}`).then((response) => {
      this.result = response.data;
    });
  },
};
</script>

<style scoped>
.result-content {
  margin: 20px;
}
</style>
