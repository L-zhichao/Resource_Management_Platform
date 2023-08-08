# 资源管理系统
## 基于springboot + vue 前后端分离项目
###

**项目介绍**
>   项目分为*物品模块*，*资金模块*和*用户登录*三大模块。具备用户登录注册，物品的增加，删除，修改和展示，资金的增加，删除，修改和删除。用户可实现物品申请和和损坏上报业务功能，管理员可实现申请回应和损坏
处理等功能。
> 
**技术支持**
   > 后端： 项目采用 springboot + mybatis plus  框架
> 
   > 前端：基于vue2,使用 vue-cli3 构建,使用npm进行包管理,基于axios进行二次封装

# 安装环境

打开resource_management-ui目录，将终端切换到项目目录

## 安装依赖

```shell
npm i
```

## 项目内启动(开发调试)

```shell
node .\app.js
```

若启动失败，进行以下尝试

修改package.json，在相关构建命令之前加入SET NODE_OPTIONS=--openssl-legacy-provider

```json
"scripts": {
   "serve": "SET NODE_OPTIONS=--openssl-legacy-provider && vue-cli-service serve",
   "build": "SET NODE_OPTIONS=--openssl-legacy-provider && vue-cli-service build"
},
```

## 项目打包

```shell
npm run build
```

<a href="https://github.com/d2-projects/d2-admin" target="_blank"><img src="https://raw.githubusercontent.com/FairyEver/d2-admin/master/docs/image/d2-admin@2x.png" width="200"></a>

