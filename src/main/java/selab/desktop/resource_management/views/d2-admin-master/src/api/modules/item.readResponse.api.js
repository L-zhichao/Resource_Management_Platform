// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 本人所有未读申请回应信息
   */
  ITEM_READ_RESPONSE_API () {
    // 模拟数据
    mock
      .onAny('/item/readResponse')
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
      url: 'item/readResponse',
      method: 'post'
    })
  }
})
