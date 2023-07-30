
<template>
  <el-carousel :interval="4000" type="card" width="640px" height="400px">
    <el-carousel-item v-for="item in 3" :key="item">
      <h3 class="medium">haoya</h3>
    </el-carousel-item>
  </el-carousel>
</template>

<style>
  .el-carousel__item h3 {
 
    color: #475669;
    font-size: 14px;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
  }

    .el-carousel__item:first-child {
         background-image: url(https://i.postimg.cc/J00GFR1L/1.png); 
          background-repeat: no-repeat;
    }
  
  .el-carousel__item:nth-child(2) {
    background-image: url(https://i.postimg.cc/28q1Fjcd/2.png);
     background-repeat: no-repeat;

  }
  
  .el-carousel__item:nth-child(3) {
    background-image: url(https://i.postimg.cc/442K9H5N/3.png);
     background-repeat: no-repeat;
  }
</style>

<script>

  /* background-image: url(https://i.postimg.cc/pLZ96Mqd/bigbigai-com-1690107724622.jpg); */
     /* background-image: url(  https://i.postimg.cc/VvKLmnH0/bigbigai-com-1690107727574.jpg); */
  /* background-image: url(https://i.postimg.cc/tCD2GJR0/bigbigai-com-1690107770048.jpg */
 

</script>
