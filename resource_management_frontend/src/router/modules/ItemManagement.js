import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: '/FixedAssetManagement',
  name: 'FixedAssetManagement',
  meta,
  cache: true,
  redirect: { name: 'FixedAssetManagement-ItemManagement' },
  component: layoutHeaderAside,
  children: [{
    path: 'ItemManagement',
    name: 'FixedAssetManagement-ItemManagement',
    meta: {
      title: '物品管理',
      ...meta,
      cache: true
    },
    component: _import('FixedAssetManagement/ItemManagement')
  }]
}
