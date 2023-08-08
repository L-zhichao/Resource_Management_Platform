// import { find, assign } from 'lodash'
import { get } from 'lodash'
import util from '@/libs/util'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 上传图片/视频
   * @param {Object} data 图片/视频
   */
  ITEM_IMG_UPLOAD_API (data) {
    // 模拟数据
    mock
      .onAny('/item/img-upload')
      .reply(config => {
        // const configData = JSON.parse(config.data)
        return [
          200,
          null,
          '损坏物品上报'
        ]
      })
    // 接口请求
    const token = util.cookies.get('token')
    return request({
      url: 'item/img-upload',
      method: 'post',
      headers: {
        Authorization: token,
        'Content-Type': get(
          {
            url: 'item/img-upload',
            method: 'post',
            data
          },
          'headers.Content-Type', 'multipart/form-data')
      },
      data
    })
  }
})
