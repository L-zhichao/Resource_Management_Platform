// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 修改物品
   * @param {Object} data 修改物品的信息
   */
  ITEM_CHANGE_API (data) {
    // 模拟数据
    mock
      .onAny('/item/updata')
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
      url: 'item/updata',
      method: 'put',
      data
    })
  }
})
