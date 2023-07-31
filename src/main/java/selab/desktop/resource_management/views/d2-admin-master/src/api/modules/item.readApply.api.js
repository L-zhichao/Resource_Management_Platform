// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 所有未处理申请信息提醒
   */
  ITEM_READ_APPLY_API () {
    // 模拟数据
    mock
      .onAny('/item/readApply')
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
      url: 'item/readApply',
      method: 'post'
    })
  }
})
