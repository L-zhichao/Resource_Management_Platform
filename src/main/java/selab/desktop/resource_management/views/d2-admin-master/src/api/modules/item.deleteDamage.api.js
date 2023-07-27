// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 删除损坏物品
   * @param {Object} Id 唯一参数 物品Id
   */
  ITEM_DELETE_DAMAGED_API (params) {
    // 模拟数据
    mock
      .onAny('/damage/record')
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
      url: 'damage/record',
      method: 'delete',
      params
    })
  }
})
