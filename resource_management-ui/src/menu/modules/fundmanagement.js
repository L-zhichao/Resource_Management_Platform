export default {
  path: 'fundmanagement',
  title: '资金管理',
  icon: 'money',
  children: [
    { path: '/fundManagement', name: 'fundManagement', title: '资金管理', icon: 'viacoin' },
    { path: '/getCanBeUsed', name: 'getCanBeUsed', title: '可支配资产展示', icon: 'yen' },
    { path: '/logging', name: 'logging', title: '日志记录', icon: 'dot-circle-o' }
  ]
}
