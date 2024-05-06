## 出单步骤

#### 1. 创建商家账户

```bash
curl --location 'http://localhost:8081/merchant-account' \
--header 'Content-Type: application/json' \
--data '{"merchantId":2}'
```

#### 2. 创建商品库存

```bash
curl --location 'http://localhost:8081/inventory' \
--header 'Content-Type: application/json' \
--data '{
    "sku": 2,
    "merchantId": 2,
    "price": 40,
    "inventory": 100
}'
```

#### 3. 创建用户账户

```bash
curl --location 'http://localhost:8081/customer-account' \
--header 'Content-Type: application/json' \
--data '{"customerId":2}'
```

#### 4. 用户账户充值

```bash
curl --location 'http://localhost:8081/customer-account/recharge' \
--header 'Content-Type: application/json' \
--data '{"customerId":2,"amount":"10000"}'
```

#### 5. 用户购买商品

```bash
curl --location 'http://localhost:8081/order' \
--header 'customer-id: 2' \
--header 'Content-Type: application/json' \
--data '{"sku":"2","quantity":10}'
```

## 数据库访问

[ http://localhost:8081/h2 ]( http://localhost:8081/h2 )

<table>
   <tr><td>CUSTOMER_ACCOUNT</td><td>用户账户表</td></tr>
   <tr><td>EMALL_ORDER</td><td>用户订单表 </td></tr>
   <tr><td>INVENTORY</td><td>商家库存表</td></tr>
   <tr><td>INVENTORY_DAILY_STATISTIC</td><td>商家库存销售按天统计表</td></tr>
   <tr><td>INVENTORY_HISTORY</td><td>订单库存变更历史表</td></tr>
   <tr><td>MERCHANT_ACCOUNT</td><td>商家账户表</td></tr>
   <tr><td>MERCHANT_BALANCE_HISTORY</td><td>商家订单收入历史表 </td></tr>
   <tr><td>MERCHANT_DAILY_INCOME_STATISTIC</td><td>商家账户按天收入统计表</td></tr>
</table>


## 其他接口

```bash
#查询用户账户余额:
curl --location 'http://localhost:8081/customer-account?customerId=2'

#查询商家账户余额：
curl --location 'http://localhost:8081/merchant-account?merchantId=2'

#查询商品库存：
curl --location 'http://localhost:8081/merchant-account?merchantId=2'

#更新商品库存 
curl --location 'http://localhost:8081/inventory/update' \
 --header 'Content-Type: application/json' \
 --data '{"sku":2,"inventory":"100"}'
   
#增加商品库存
curl --location 'http://localhost:8081/inventory/increase' \
--header 'Content-Type: application/json' \
--data '{"sku":2,"inventory":"10"}'
   
```