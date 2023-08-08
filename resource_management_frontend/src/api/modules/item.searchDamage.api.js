// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 查询损坏物品
   * @param {Object} 唯一参数username 不传为查全部
   */
  ITEM_SEARCH_DAMAGED_API (params) {
    // 模拟数据
    mock
      .onAny('/damage/record/query')
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
      url: 'damage/record/query',
      method: 'get',
      params
    })
  }
})
