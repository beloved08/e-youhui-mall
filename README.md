# 毕设-E优汇商城

#### 介绍
用于毕业设计E优汇商城项目的后端开发

#### 软件架构
spring-boot、spring-cloud、spring-cloud-alibaba、MySQL、Redis


#### 作品简介

该系统主要分为普通用户端的微信小程序商城和管理员端的管理系统。
微信小程序主要功能为商品信息展示、查看商品详情、商品分类、商品搜索、购物车、用户登录注册、用户下单、余额充值、积分充值、用户收藏（商品收藏、商家收藏）、开通会员、用户收货地址的管理、商家入驻等。
管理系统主要功能为管理员登录、用户管理（用户列表、用户收藏、用户收货地址、用户购物车、用户余额积分管理）、商品管理（商品分类管理、商品列表管理-商品信息添加，单个添加与批量导入、商品信息的筛选、商品信息的批量操作）、商家管理（商家店铺的管理，商家入驻的审核、信息查看、可在地图上查看商家的位置信息）、库存管理（商品入库出库记录查看）、营销管理（营销活动。优惠券）、订单管理（用户订单信息查看、发货处理）、物流管理（第三方物流公司列表、用户订单物流的查看）、支付管理（用户的支付方式）、数据可视化大屏展示等功能

#### 作品安装说明

后端程序：下载maven依赖即可，其中的MySQL、Redis、nacos、RabbitMQ等均为服务器中已安装完成，无需本地安装，推荐使用IntelliJ IDEA 。
管理系统：（1）、使用包管理工具yarn下载相关依赖；（2）、使用yarn dev:dev启动开发环境，使用yarn dev:prod启动生产环境；（3）、使用浏览器访问；（4），推荐使用Visual Studio Code。
微信小程序：（1）、使用npm install下载相关依赖；（2）、使用HBuilder X，配置微信开发者工具信息后，运行到微信开发者工具中；（4）、推荐使用HBuilder X和微信开发者工具。

#### 作品效果图

1、后台管理系统效果图：
链接：https://pan.baidu.com/s/1aGQCqUj1fGGySYI6oEoEsg?pwd=rcqi
提取码：rcqi

2、微信小程序商城效果图：
链接：https://pan.baidu.com/s/1KXqjcXFj_glh4cSxwL-GJg?pwd=asbm
提取码：asbm

### 设计思路

1、确定后端架构：本系统后端采用springcloud微服务架构，主要技术包括springboot、nacos、MySQL（主从复制，读写分离）、gateway、RabbitMQ（RabbitMQ集群，HAProxy代理，这里由于服务器内存限制，我关闭了其中两个RabbitMQ实例）、Redis（一主二从三哨兵）、feign等微服务组件、阿里云oss对象存储等技术。

2、管理系统架构：本系统中后台管理系统使用vue框架，主要技术包括vue3、TypeScript、开源组件库、百度地图、axios、Pinia、goeasy通信等技术。

3、微信小程序架构：本系统中微信小程序使用vue2+uniapp框架，主要技术包括vue2、JavaScript、开源组件库、腾讯位置服务、goeasy通信等技术。

### 部署（服务器已到期，无法访问）
作品部署链接	管理系统部署地址：https://www.eyh-shop.com
作品部署链接	微信小程序地址（体验）：#小程序://eyouhui/Pgb9DxgpSHoMJhy
