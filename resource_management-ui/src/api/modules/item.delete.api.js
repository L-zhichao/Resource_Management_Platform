// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 删除物品
   * @param {Object} params 删除物品的信息
   */
  ITEM_DELETE_API (params) {
    // 模拟数据
    mock
      .onAny('/item/delete')
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
      url: 'item/delete',
      method: 'delete',
      params
    })
  }
})
