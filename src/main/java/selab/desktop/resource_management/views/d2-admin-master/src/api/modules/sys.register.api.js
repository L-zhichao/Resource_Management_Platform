// import { find, assign } from 'lodash'

const users = [
  { username: 'Admin111', password: 'Admin111', uuid: 'Admin111-uuid', name: 'Admi', email: 'Admin111@test.com', userStatus: 0 },
  { username: 'editor', password: 'editor', uuid: 'editor-uuid', name: 'Edit', email: 'Editor@test.com', userStatus: 1 },
  { username: 'user1', password: 'user1', uuid: 'user1-uuid', name: 'User', email: 'User1@test.com', userStatus: 1 }
]

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 登录
   * @param {Object} data 登录携带的信息
   */
  SYS_USER_REGISTER (data = {}) {
    // 模拟数据
    mock
      .onAny('/user/register')
      .reply(config => {
        const configData = JSON.parse(config.data)
        for (const item of users) {
          if (item.username === configData.username) {
            return [200, {
              code: 200,
              msg: '成功',
              data: {
                status: 40002,
                message: '当前用户名已存在',
                data: null
              }
            }]
          }
        }
        return [
          200,
          {
            code: 200,
            msg: '成功',
            data: {
              status: 200,
              message: null,
              data: null
            }
          }
        ]
      })
    // 接口请求
    return request({
      url: 'user/register',
      method: 'post',
      data
    })
  }
})
