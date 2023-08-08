<template>
  <d2-container class="page" ref="page">
    <div
      ref="div1"
      class="page-login--layer page-login--layer-title"
      flex="main:center">
      资源管理系统
    </div>
    <div
      ref="div1"
      :style="{top: colTop}"
      class="page-login--layer page-login--layer-button">
      <el-row :gutter="20" v-for="item in list" :key="item">
        <el-col :span="24"><div class="grid-content bg-purple">{{ item }}</div></el-col>
      </el-row>
    </div>
    <el-carousel arrow="never" ref="carouselBox" class="carouselBox" :height="carouselHeight" direction="vertical" :autoplay="false" @change="change">
      <el-carousel-item v-for="item in list" :key="item">
        <h3 :style="{height: carouselHeight}" flex="main:center cross:center" class="medium">{{ item }}</h3>
      </el-carousel-item>
    </el-carousel>
  </d2-container>
</template>

<script>
export default {
  components: {
  },
  data () {
    return {
      list: ['物品管理', '资金管理', '文本编辑'],
      carouselHeight: '200px',
      carouselIndex: 0,
      colHeight: '36px',
      colTop: '',
      randomKey: Math.random()
    }
  },
  methods: {
    change (v) {
      this.carouselIndex = v
      document.getElementsByClassName('bg-purple').forEach((item, index) => {
        if (index === v) document.getElementsByClassName('bg-purple')[index].style.backgroundColor = '#a3bddcaa'
        else document.getElementsByClassName('bg-purple')[index].style.backgroundColor = '#d3dce6aa'
      })
      if (document.getElementsByClassName('el-carousel__indicator--vertical')[v].onclick) return
      document.getElementsByClassName('el-carousel__indicator--vertical')[v].onclick = () => {
        this.linkTo()
      }
    },
    linkTo () {
      switch (this.carouselIndex) {
        case 0: this.$router.push('/fixedAssetManagement/itemManagement')
          break
        case 1: this.$router.push('/fundManagement')
          break
        case 2: this.$router.push('/editor-quill')
          break
      }
    }
  },
  mounted () {
    const page = document.getElementsByClassName('page')[0].querySelector('div').querySelector('div')
    const col = document.getElementsByClassName('grid-content')[0]
    this.carouselHeight = getComputedStyle(page).height
    this.colHeight = getComputedStyle(col).height
    this.colTop = parseInt(this.carouselHeight) / 2 - ((parseInt(this.colHeight) * 3) / 5) + 'px'
    this.randomKey = Math.random()
    window.onresize = () => {
      const page = document.getElementsByClassName('page')[0].querySelector('div').querySelector('div')
      this.carouselHeight = getComputedStyle(page).height
      this.colTop = parseInt(this.carouselHeight) / 2 - ((parseInt(this.colHeight) * 3) / 5) + 'px'
      this.randomKey = Math.random()
    }
    setTimeout(() => {
      document.getElementsByClassName('bg-purple')[0].style.backgroundColor = '#a3bddcaa'
      document.getElementsByClassName('el-carousel__indicator--vertical')[0].onclick = () => {
        this.linkTo()
      }
    }, 100)
  },
  beforeDestroy () {
    window.onresize = null
  }
}
</script>

<style lang="scss" scoped>
.page-login--layer {
  @extend %full;
  overflow: auto;
}
// title
.page-login--layer-title {
  font-size: 9em;
  font-weight: bold;
  color: rgba(0, 0, 0, 0.06);
  overflow: hidden;
}
.page-login--layer-button {
  position: absolute;
  display: table-cell;
  vertical-align: middle;
  font-size: 2em;
  font-weight: bold;
  color: rgba(0, 0, 0, 0.4);
  overflow: hidden;
}
.el-carousel__item h3 {
  color: rgba(0, 0, 0, 0.2);
  font-size: 15em;
  opacity: 0.75;
  line-height: 200px;
  margin: 0;
}
.el-row {
  &:last-child {
    margin-bottom: 0;
  }
}
.bg-purple-dark {
  background: #99a9bf;
}
.bg-purple {
  background: #d3dce6aa;
  transition: all .2s;
}
.bg-purple-light {
  background: #e5e9f2;
}
.grid-content {
  min-height: 23px;
  text-align: center;
  font-size: 16px;
  cursor: pointer;
  letter-spacing: .2em;
}
.row-bg {
  padding: 10px 0;
  background-color: #f9fafc;
}
</style>

<style lang="scss">
.el-carousel__indicators--vertical {
  width: 100%;
  &.el-carousel__indicator {
    width: 100%;
    &.el-carousel__button {
      width: 100%;
    }
  }
}
</style>
