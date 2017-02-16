# ticket

## 项目说明


## 开发环境

* Java 1.8
* Gradle 2.x

## 工程包结构

Package                           | Description
----------------------------------|-----------------
com.peach.ticket.domain     | 领域对象包
com.peach.ticket.repository | 数据存储服务包
com.peach.ticket.service    | 业务逻辑服务包
com.peach.ticket.web        | http接口服务包

## 依赖组件

### 数据存储服务

数据存储使用hibernate，gradle引入spring-boot-starter-data-jpa包。调用代码参考com.meigooo.ticket.repository包中的示例

### 日志

使用logback作为日志输入框架,配置文件参考logback-spring.xml,其中按照spring profile进行区别设置

## 功能拆解(服务端)

### 角色
说明：

角色分为三种，管理员、财务人员、普通用户  

表结构（role）：

字段 | 说明
----|----
id | ID
name | 名称
desc | 说明

接口（管理后台）：

1. /api/role/save 保存更新  
2. /api/role/{id}/remove 删除
3. /api/role/1 查询
4. /api/role/findAll 获取所有


### 用户
说明：

* 普通用户可以登录系统买票
* 结算用户可以登录系统结算
* 管理用户可以登录系统设置角色、用户、线路等功能

表结构（user）：

字段 | 说明
----|----
id | ID
username | 用户名
password | 密码
nickname | 昵称
phone | 手机号
status | 状态
roleId | 角色ID


接口（管理后台）：

1. /api/user/save 保存更新  
2. /api/user/{id}/remove 删除
3. /api/user/1 查询
4. /api/user/findAll 获取所有
5. /api/user/{id}/enabled 启用禁用


接口（前台）：

1. /api/account/login 登录
2. /api/account/logout 退出登录

### 线路
说明：

表结构（line）：

字段 | 说明
----|----
id | ID
code | 编码
name | 名称
price | 票价
miles | 里程

接口（管理后台）：

1. /api/line/save 保存更新  
2. /api/line/{id}/remove 删除
3. /api/line/1 查询
4. /api/line/findAll 获取所有
5. /api/line/findByStations 根据站台查询线路

### 站台

说明：

表结构（station）：

字段 | 说明
----|----
id | ID
code | 编码
name | 名称
status | 状态

接口（管理后台）：

1. /api/station/save 保存更新  
2. /api/station/{id}/remove 删除
3. /api/station/1 查询
4. /api/station/findAll 获取所有
5. /api/station/{id}/enabled 启用禁用

### 线路站台关系表

说明：
表结构（line_station）:

字段 | 说明
----|----
id | ID
lineId | 线路ID
stationId | 站台ID
sort | 排序

接口（管理后台）：

1. /api/line/{id}/addStation 增加站台  
2. /api/line/{id}/removeStation 移除站台
3. /api/line/{id}/changeSort 更改排序
4. /api/line/{id}/getStations 获取某线路的所有站台

### 订单

说明：

1. 用户购买车票，产生订单
2. 订单状态分为未支付、已支付、已取消、已使用、已作废
3. 用户支付变更支付状态为已支付
4. 结算订单更改订单状态从未支付到已支付
5. 用户取消更改订单状态从未支付到已取消
6. 用户使用更改订单状态从已支付到已使用
7. 作废订单更改订单状态从已支付到已作废


表结构（order）：

字段 | 说明
----|----
id | ID
userId | 用户ID
startStationId | 开始站台ID
endStationId | 结束站台ID
totalAmount | 总金额
totalDiscount | 总优惠金额
payAmount | 支付总金额
payStatus | 支付状态，未支付、已支付
status | 状态，进行中、已结束
createAt | 创建时间 
updateAt | 修改时间

接口（管理后台）：

1. /api/order/{id}/confirm 确认订单已支付
2. /api/order/{id}/remove 删除订单
3. /api/order/1 查询
3. /api/order/findAll 查询所有订单
4. /api/order/findMy 查询当前用户的所有订单

接口（前台）：

1. /api/order/{id}/submit 创建订单
2. /api/order/{id}/pay 支付订单
3. /api/order/{id}/cancel 取消订单，用户取消自己未支付的订单
4. /api/order/{id}/invalid 作废订单，已支付的订单可以作废


## 功能拆解（前端）

从用户角度分析  

管理员可以：

1. 操作角色+用户
2. 设置线路
3. 设置站台
4. 查看订单，删除订单

售票人员可以：

1. 查看所有车票
2. 查看车票详情
3. 结算车票（设为已支付）
4. 作废车票


普通用户可以：

1. 查询线路（输入两站台查询对应的线路）
2. 购买车票
3. 查看自己购买的车票
4. 使用车票
