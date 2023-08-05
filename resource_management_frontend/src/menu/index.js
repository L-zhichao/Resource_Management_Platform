import { uniqueId } from 'lodash'

// 菜单导入
import fundmanagement from './modules/fundmanagement'
import itemManagement from './modules/ItemManagement'
import resource from './modules/resource'
import editorQuill from './modules/editorQuill'

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
  // 物品管理页面菜单
  itemManagement,
  // 资金管理
  fundmanagement,
  // 考试资源
  resource,
  editorQuill
])

export const menuAside = supplementPath([
  { path: '/index', title: '首页', icon: 'home' },
  itemManagement,
  fundmanagement,
  resource,
  editorQuill
])
