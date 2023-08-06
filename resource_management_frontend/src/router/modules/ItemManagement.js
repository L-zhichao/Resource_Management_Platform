import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/fixedAssetManagement',
  name: 'fixedAssetManagement',
  meta,
  cache: true,
  redirect: { name: 'fixedAssetManagement-itemManagement' },
  component: layoutHeaderAside,
  children: [{
    path: 'itemManagement',
    name: 'fixedAssetManagement-itemManagement',
    meta: {
      title: '物品管理',
      ...meta,
      cache: true
    },
    component: _import('fixedAssetManagement/itemManagement')
  }]
}
