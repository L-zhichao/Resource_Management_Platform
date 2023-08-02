import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: 'demo',
  name: 'demo',
  meta,
  cache: true,
  redirect: { name: 'demo' },
  component: layoutHeaderAside,
  children: [{
    path: '/page1',
    name: 'page1',
    meta: {
      ...meta,
      title: '资金管理',
      cache: true
    },
    component: _import('demo/page1')
  }, {
    path: '/page2',
    name: 'page2',
    meta: {
      ...meta,
      title: '资产分配',
      cache: true
    },
    component: _import('demo/page2')
  }, {
    path: '/page3',
    name: 'page3',
    meta: {
      ...meta,
      title: '日志记录',
      cache: true
    },
    component: _import('demo/page3')
  }
  // ,
  // {
  //   path: '/page4',
  //   name: 'page4',
  //   meta: {
  //     ...meta,
  //     title: '物品申请',
  //     cache: true
  //   },
  //   component: _import('demo/page4')
  // }
  ]
}
