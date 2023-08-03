// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 用户登录log总数
   */
  SYS_USER_COUNT_API () {
    // 模拟数据
    mock
      .onAny('/user/count')
      .reply(config => {
        // const configData = JSON.parse(config.data)
        return [
          200,
          null,
          '损坏物品上报'
        ]
      })
    // 接口请求
    return request({
      url: 'user/count',
      method: 'get'
    })
  }
})
