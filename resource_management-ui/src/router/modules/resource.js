import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

const meta = { auth: true }

export default {
  path: 'resource',
  name: 'resource',
  meta,
  cache: true,
  redirect: { name: 'resource' },
  component: layoutHeaderAside,
  children: [{
    path: '/ResourceTest',
    name: 'ResourceTest',
    meta: {
      title: '考试资源',
      ...meta,
      cache: true
    },
    component: _import('resource/ResourceTest')
  }, {
    path: '/UpLoad',
    name: 'UpLoad',
    meta: {
      title: '上传资源',
      ...meta,
      cache: true
    },
    component: _import('resource/UpLoad')
  }]
}
