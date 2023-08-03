import { uniqueId } from 'lodash'

/**
 * @description 给菜单数据补充上 path 字段
 * @description https://github.com/d2-projects/d2-admin/issues/209
 * @param {Array} menu 原始的菜单数据
 */
function supplementPath (menu) {
  return menu.map(e => ({
    ...e,
    path: e.path || uniqueId('d2-menu-empty-'),
    ...e.children ? {
      children: supplementPath(e.children)
    } : {}
  }))
}

export const menuHeader = supplementPath([
  { path: '/index', title: '首页', icon: 'home' },
  {
    title: '资金管理',
    icon: 'folder-o',
    children: [
      { path: '/page1', title: '资金管理' },
      { path: '/page2', title: '可支配资产' },
      { path: '/page3', title: '日志记录' },
      { path: '/page4', title: '物品申请·' },

    ]
  },

  {
    title: '考试资源',
    icon: '	fa fa-file-text',
    children: [
      { path: '/ResourceTest', title: '考试资源' },
      { path: '/UpLoad', title: '上传资源' },
    ]
  }


])

export const menuAside = supplementPath([
  { path: '/index', title: '首页', icon: 'home' },
  {
    title: '资金管理',
    icon: 'folder-o',
    children: [
      { path: '/page1', title: '资金管理' },
      { path: '/page2', title: '可支配资产' },
      { path: '/page3', title: '日志记录' },
      { path: '/page4', title: '物品申请·' },

    ]
  },

  {
    title: '考试资源',
    icon: '	fa fa-file-text',
    children: [
      { path: '/ResourceTest', title: '考试资源' },
      { path: '/UpLoad', title: '上传资源' },
    ]
   
  }
])
