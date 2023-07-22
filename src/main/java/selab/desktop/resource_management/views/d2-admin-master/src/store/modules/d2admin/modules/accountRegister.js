// import { Message, MessageBox } from 'element-ui'
// import util from '@/libs/util.js'
import router from '@/router'
import api from '@/api'

export default {
  namespaced: true,
  actions: {
    /**
     * @description  注册
     * @param {Object} context
     * @param {Object} payload username {String} 用户账号
     * @param {Object} payload password {String} 密码
     * @param {Object} payload name {String} 姓名
     * @param {Object} payload userStatue {Number} 权限(0/1)
     * @param {Object} payload email {String} 邮箱
     * @param {Object} payload route {Object} 登录成功后定向的路由对象 任何 vue-router 支持的格式
     */
    async register ({ dispatch }, {
      username = '',
      password = '',
      name = '',
      userStatue = 1,
      email = ''
    } = {}) {
      const res = await api.SYS_USER_REGISTER({
        name: name,
        username: username,
        email: email,
        password: password,
        userStatue: userStatue
      })
      console.log(res)
      router.push({ name: 'login' })
      return res
      // 设置 cookie 一定要存 uuid 和 token 两个 cookie
      // 整个系统依赖这两个数据进行校验和存储
      // uuid 是用户身份唯一标识 用户注册的时候确定 并且不可改变 不可重复
      // token 代表用户当前登录状态 建议在网络请求中携带 token
      // util.cookies.set('uuid', res.uuid)
      // util.cookies.set('token', res.token)
      // // 设置 vuex 用户信息
      // await dispatch('d2admin/user/set', { name: res.name }, { root: true })
      // // 用户登录后从持久化数据加载一系列的设置
      // await dispatch('load')
    }
  }
}
