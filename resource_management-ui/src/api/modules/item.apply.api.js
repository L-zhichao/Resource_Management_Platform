// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 添加物品
   * @param {Object} data 添加物品的信息
   */
  ITEM_APPLY_API (data) {
    // 模拟数据
    mock
      .onAny('/item/apply')
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
      url: 'item/apply',
      method: 'post',
      data
    })
  }
})
