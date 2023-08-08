import layoutHeaderAside from '@/layout/header-aside'

// 由于懒加载页面太多的话会造成webpack热更新太慢，所以开发环境不使用懒加载，只有生产环境使用懒加载
const _import = require('@/libs/util.import.' + process.env.NODE_ENV)

export default {
  path: 'editor-quill',
  name: 'editor-quill',
  cache: true,
  meta: { auth: true },
  redirect: { name: 'editor-quill' },
  component: layoutHeaderAside,
  children: [{
    path: '/editor-quill',
    name: 'editor-quill',
    meta: {
      title: '文本编辑器',
      auth: true,
      cache: true
    },
    component: _import('editor-quill')
  }]
}
