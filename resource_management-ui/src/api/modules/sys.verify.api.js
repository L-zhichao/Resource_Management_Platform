// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 用户名验证模块
   * @param {Object} params 用户名的信息
   */
  SYS_VERIFY_API (params) {
    // 模拟数据
    mock
      .onAny('/user/user/verify')
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
      url: 'user/user/verify',
      method: 'post',
      params
    })
  }
})
