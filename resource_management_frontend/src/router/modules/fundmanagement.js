import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: 'fundmanagement',
  name: 'fundmanagement',
  meta,
  cache: true,
  redirect: { name: 'fundmanagement' },
  component: layoutHeaderAside,
  children: [{
    path: '/fundManagement',
    name: 'fundManagement',
    meta: {
      ...meta,
      title: '资金管理',
      cache: true
    },
    component: _import('fundmanagement/fundManagement')
  }, {
    path: '/getCanBeUsed',
    name: 'getCanBeUsed',
    meta: {
      ...meta,
      title: '可支配资产展示',
      cache: true
    },
    component: _import('fundmanagement/getCanBeUsed')
  }, {
    path: '/logging',
    name: 'logging',
    meta: {
      ...meta,
      title: '日志记录',
      cache: true
    },
    component: _import('fundmanagement/logging')
  }]
}
