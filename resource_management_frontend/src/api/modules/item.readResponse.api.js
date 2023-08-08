// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 普通用户回复已读
   */
  ITEM_READ_RESPONSE_API (params) {
    // 模拟数据
    mock
      .onAny('/item/readed')
      .reply(config => {
        // const configData = JSON.parse(config.data)
        return [
          200,
          {
            status: 200,
            message: '',
            data: {}
          },
          '损坏物品上报'
        ]
      })
    // 接口请求
    return request({
      url: 'item/readed',
      method: 'get',
      params
    })
  }
})
