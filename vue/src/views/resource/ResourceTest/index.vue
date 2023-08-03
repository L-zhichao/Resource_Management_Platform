<template>
  <div id="box">
  
    <div id="Vue">
     
      <div class="shine shine2"></div>
     
      <div class="star"></div>
    
      <div class="star pink"></div>
   
      <div class="star blue"></div>
 
    </div>

    <p class="text">已上传的文件：</p>
    <el-table :data="uploadedFiles" border>
      <el-table-column prop="name" label="文件名"></el-table-column>
      <el-table-column prop="size" label="文件大小">
        <template slot-scope="scope">{{
          getFileSize(scope.row.size)
        }}</template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="viewFileContent(scope.row)"
            >查看内容</el-button
          >
          <el-button type="danger" size="mini" @click="deleteFile(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <div v-if="fileContent !== ''">
      <h3>文件内容：</h3>
      <pre>{{ fileContent }}</pre>
    </div>
  </div>
    
</template>

<script>
export default {
  data() {
    return {
      uploadedFiles: [],
      fileContent: "",
    };
  },
  mounted() {
    const files = localStorage.getItem("uploadedFiles");
    if (files) {
      this.uploadedFiles = JSON.parse(files);
    }
  },
  methods: {
    deleteFile(file) {
      // 从已上传的文件列表中删除文件
      const index = this.uploadedFiles.indexOf(file);
      if (index > -1) {
        this.uploadedFiles.splice(index, 1);
        // 将更新后的文件列表保存到localStorage中
        localStorage.setItem(
          "uploadedFiles",
          JSON.stringify(this.uploadedFiles)
        );
      }
    },
    getFileSize(size) {
      const fileSize = size / 1024; // 转换为KB
      return fileSize.toFixed(2) + " KB";
    },
    viewFileContent(file) {
      // 获取文件内容
      fetch("/api/getFileContent", {
        method: "POST",
        body: JSON.stringify({ file: file }),
        headers: {
          "Content-Type": "application/json",
        },
      })
        .then((response) => response.text())
        .then((content) => {
          // 将文件内容保存到组件数据中
          this.fileContent = content;
        })
        .catch((error) => {
          console.error("获取文件内容失败", error);
        });
    },
  },
};
</script>

<style scoped>

::-webkit-scrollbar {
  width: 5px; 
  height: 5px; 
  background-color: #fff;
}

::-webkit-scrollbar-track {
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0);
  background-color: #ccc;
}


::-webkit-scrollbar-thumb {
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0);
  background-color: #1890ff;
  border-radius: 10px;
}
#box{
  width: 100%;
  height: 100%;
}
.text {
  color: white;
  font-size: 18px;
}

.star {
    display: block;
    width: 5px;
    height: 5px;
    border-radius: 50%;
    background: #fff;
    top: 100px;
    left: 500px;
    position: relative;
    transform-origin: 100% 0;
    animation: star-ani 4s linear infinite;
    -webkit-animation: star-ani 5s linear infinite;
    box-shadow: 0 0 5px 5px rgba(255, 255, 255, 0.3);
    opacity: 0;
    z-index: 2;
  }

  .star:after {
    content: "";
    display: block;
    top: 0px;
    left: 4px;
    border: 0px solid #fff;
    border-width: 0px 90px 2px 90px;
    border-color: transparent transparent transparent rgba(255, 255, 255, 0.3);
    transform: rotate(-45deg) translate3d(1px, 3px, 0);
    box-shadow: 0 0 1px 0 rgba(255, 255, 255, 0.1);
    transform-origin: 0% 100%;
  }

  .pink {
    top: 100px;
    left: 800px;
    background: #fff;
    animation-delay: 3s;
    -webkit-animation-delay: 3s;
    -moz-animation-delay: 3s;
  }

  .pink:after {
    border-color: transparent transparent transparent #fff;
    animation-delay: 3s;
    -webkit-animation-delay: 3s;
    -moz-animation-delay: 3s;
  }

  .blue {
    top: 120px;
    left: 1200px;
    background: fff;
    animation-delay: 7s;
    -webkit-animation-delay: 7s;
    -moz-animation-delay: 7s;
  }

  .blue:after {
    border-color: transparent transparent transparent fff;
    -webkit-animation-delay: 7s;
    -moz-animation-delay: 7s;
    animation-delay: 7s;
  }

  @keyframes star-ani {
    0% {
      opacity: 0;
      transform: scale(0) translate3d(0, 0, 0);
    }

    20% {
      opacity: 0.8;
      transform: scale(0.2) translate3d(-100px, 100px, 0);
    }

    40% {
      opacity: 0.8;
      transform: scale(0.4) translate3d(-200px, 200px, 0);
    }

    60% {
      opacity: 0.8;
      transform: scale(0.6) translate3d(-300px, 300px, 0);
    }

    80% {
      opacity: 1;
      transform: scale(1) translate3d(-350px, 350px, 0);
    }

    100% {
      opacity: 1;
      transform: scale(1.2) translate3d(-400px, 380px, 0);
    }
  }

  .shine {
    background-image: url("https://sucai.suoluomei.cn/sucai_zs/images/20201211172037-211357_VOTl_3549294.png");
    background-repeat: no-repeat;
    background-position: center;
    width: 155px;
    height: 155px;
    position: absolute;
    animation: opacity-change 1s ease-in-out infinite;
    -webkit-animation: opacity-change 1s ease-in-out infinite;
    -moz-animation: opacity-change 1s ease-in-out infinite;
    -o-animation: opacity-change 1s ease-in-out infinite;
  }

  .shine2 {
    animation: opacity-change 1.75s ease-in-out infinite;
    -webkit-animation: opacity-change 1.75s ease-in-out infinite;
    -moz-animation: opacity-change 1.75s ease-in-out infinite;
    -o-animation: opacity-change 1.75s ease-in-out infinite;
  }

  @keyframes opacity-change {
    0% {
      opacity: 0;
    }

    50% {
      opacity: 1;
    }

    100% {
      opacity: 0;
    }
  }

  @-webkit-keyframes opacity-change {
    0% {
      opacity: 0;
    }

    50% {
      opacity: 1;
    }

    100% {
      opacity: 0;
    }
  }

  @-moz-keyframes opacity-change {
    0% {
      opacity: 0;
    }

    50% {
      opacity: 1;
    }

    100% {
      opacity: 0;
    }
  }

  @-o-keyframes opacity-change {
    0% {
      opacity: 0;
    }

    50% {
      opacity: 1;
    }

    100% {
      opacity: 0;
    }
  }

  


</style>
