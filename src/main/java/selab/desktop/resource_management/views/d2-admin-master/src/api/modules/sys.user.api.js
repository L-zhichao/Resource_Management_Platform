import { find, assign } from 'lodash'

const users = [
  { username: 'Admin111', password: 'Admin111', uuid: 'Admin111-uuid', name: 'Admi', email: 'Admin111@test.com', userStatus: 0 },
  { username: 'Editor11', password: 'Editor11', uuid: 'Editor11-uuid', name: 'Edit', email: 'Editor11@test.com', userStatus: 1 },
  { username: 'User1111', password: 'User1111', uuid: 'User1111-uuid', name: 'User', email: 'User1111@test.com', userStatus: 1 }
]

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 登录
   * @param {Object} data 登录携带的信息
   */
  SYS_USER_LOGIN (data = {}) {
    // 模拟数据
    mock
      .onAny('/user/login')
      .reply(config => {
        const user = find(users, tools.parse(config.data))
        return user
          ? tools.responseSuccess(assign({}, user, { token: faker.random.uuid() }))
          : tools.responseError({}, '账号或密码不正确')
      })
    // 接口请求
    return request({
      url: 'user/login',
      method: 'post',
      data
    })
  }
})
