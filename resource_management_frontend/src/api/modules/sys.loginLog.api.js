// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 登入记录log
   * @param {Object} params
   * @param {Number} current 当前页
   * @param {Number} size 每页数量
   */
  SYS_USER_LOG_API (params) {
    // 模拟数据
    mock
      .onAny('/user/log')
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
      url: 'user/log',
      method: 'get',
      params
    })
  }
})
