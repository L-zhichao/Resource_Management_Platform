// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 所有物品回应信息展示
   */
  ITEM_SHOW_RESPONSE_API () {
    // 模拟数据
    mock
      .onAny('/item/showResponse')
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
      url: 'item/showResponse',
      method: 'post'
    })
  }
})
