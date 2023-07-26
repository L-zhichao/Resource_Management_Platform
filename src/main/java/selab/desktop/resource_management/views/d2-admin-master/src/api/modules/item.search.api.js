// import { find, assign } from 'lodash'

export default ({ service, request, serviceForMock, requestForMock, mock, faker, tools }) => ({
  /**
   * @description 物品查询
   * @param {Object} data 物品查询的信息
   */
  ITEM_SEARCH_API (params = {}) {
    // 模拟数据
    mock
      .onAny('/item')
      .reply(config => {
        // const configData = JSON.parse(config.data)
        return [
          200,
          {
            list: [{
              itemId: 0,
              itemname: 'BUGATTI SUR MESURE',
              itemImg: '',
              number: 3,
              price: 35000000.00,
              itemDescription: '创作个人布加迪杰作的艺术已被提升到一个新的水平。Bugatti Sur Mesure将传统，创新和最精湛的工艺相结合，以完美地实现您的灵感 - 无论它们源于个人想法或与我们品牌过去或现在的联系。'
            }, {
              itemId: 1,
              itemname: '劳斯莱斯幻影 系列 II',
              itemImg: '',
              number: 2,
              price: 8008000.00,
              itemDescription: '20世纪30年代，Phantom II Continental Saloon 是有品位的绅士们的首选座驾。 如今，继承绅士旅行传统的幻影，演绎着旅程和冒险的精髓。 受高性能飞机启发，幻影被赋予了冰封青铜色外观，丝缎银引擎盖与之形成鲜明对比。 由稀有金属钌处理而成的瀑布式画廊，为内饰画龙点睛。'
            }, {
              itemId: 2,
              itemname: '劳斯莱斯幻影长轴距版 系列 II',
              itemImg: '',
              number: 3,
              price: 9860000.00,
              itemDescription: '幻影长轴距版系列II是超越想象的杰作，它为您打造了一个宁静而舒阔的私享奢华殿堂。 其考究的功能和细节的雕琢，是工匠精神的缩影，更成就了劳斯莱斯新的传奇。'
            }],
            totalPage: 30,
            total: 300
          },
          '查询'
        ]
      })
    // 接口请求
    return request({
      url: 'item',
      method: 'get',
      params
    })
  }
})
