// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 损坏物品上报
   * @param {Object} data 损坏物品上报的信息
   */
  ITEM_REPORT_DAMAGED_API (data) {
    // 模拟数据
    mock
      .onAny('/item/report')
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
      url: 'item/report',
      method: 'post',
      data
    })
  }
})
