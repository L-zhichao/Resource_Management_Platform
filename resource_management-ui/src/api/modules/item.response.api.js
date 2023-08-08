// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 物品回应上传
   */
  ITEM_RESPONSE_API (data) {
    // 模拟数据
    mock
      .onAny('/item/response')
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
      url: 'item/response',
      method: 'post',
      data
    })
  }
})
