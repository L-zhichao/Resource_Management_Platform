<template>
     <div :style="{'max-height': this.timeLineHeight + 'px' }" style="overflow-y:scroll;" >
    
 
    <el-upload
      class="upload-demo"
      drag
      action="https://jsonplaceholder.typicode.com/posts/"
      :on-remove="fileRemoveHandle4"
      :on-change="fileChangeHandle4"
      accept=""
      :auto-upload="false"
      :show-file-list="true"
      multiple
      :file-list="form.instFilePics"
      ref="uploadFile"
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件夹拖到此处，或<em>点击上传</em></div>
    </el-upload>


    <el-button class="button-demo" type="success" round @click="submitFolder">提交文件夹</el-button>
    <el-button  class="button-demo" type="warning" round @click="deleteFolder">删除文件夹</el-button>
    <!-- <el-button type="primary" round @click="submitFolder">
        <router-link to="/ResourceTest" class="router">查看上传资料</router-link>
    </el-button> -->
      <div class="main">
            <div class="water"></div>
        </div>
 
    </div>
 
</template>

<script>



export default {
  name: "uploadFile",
  data() {
    return {
       timeLineHeight: "",
      form: {
        instFilePics: [],
        instFile: []
      }
    }
  },
  mounted() {
    this.$refs.uploadFile.$children[0].$refs.input.webkitdirectory = true;
     this.timeLineHeight = document.documentElement.clientHeight - 210;
    window.onresize = () => {
      this.timeLineHeight = document.documentElement.clientHeight - 210;
    };
   

  },
  methods: {
    fileChangeHandle4(file, fileList, name) {
      // 使用说明文档路径
      this.form.instFilePics = fileList;
    },
    fileRemoveHandle4(file, fileList, name) {
      this.form.instFilePics = fileList;
    },
    deleteFolder() {
      // 删除文件夹
      console.log("删除文件夹");
      // 发起请求，删除文件夹
      // ...
      // 清空文件夹列表
      this.form.instFilePics = [];
    },
    submitFolder() {
        // 将已上传的文件保存到localStorage中
      localStorage.setItem('uploadedFiles', JSON.stringify(this.form.instFilePics));
      // 导航到另一个页面
      this.$router.push('/ResourceTest');
    }
  }
}
</script>

<style scoped>

.upload-demo {
  border: 1px dashed #ccc;
  border-radius: 2px;
  padding: 20px 0;
  text-align: center;
  color: #999;
}

.router{
color:white;
}

.button-demo{
  margin-top: 30px;
}


            
</style>
