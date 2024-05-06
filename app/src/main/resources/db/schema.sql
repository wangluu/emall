create table if not exists inventory
(
    id           bigint auto_increment primary key comment 'Primary id',
    sku          bigint         not null comment 'Goods sku',
    merchant_id  bigint         not null comment 'Merchant id',
    price        decimal(18, 6) not null comment 'Price',
    inventory    bigint         not null comment 'Inventory of sku',
    version      int            not null default 0 comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);

create table if not exists inventory_daily_statistic
(
    id           bigint auto_increment primary key comment 'Primary id',
    sku          bigint comment 'Goods sku',
    merchant_id  bigint comment 'Merchant id',
    quantity     bigint comment 'Quantity',
    amount       decimal(18, 6) comment 'Price',
    biz_date     date comment 'Business date',
    version      int not null default 0 comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);

create table if not exists inventory_history
(
    id           bigint auto_increment primary key comment 'Primary id',
    sku          bigint comment 'Goods sku',
    merchant_id  bigint comment 'Merchant id',
    price        decimal(18, 6) comment 'Price',
    quantity     bigint comment 'Quantity reduced',
    amount       decimal(18, 6) comment 'Price',
    biz_time     datetime comment 'Business time',
    biz_id       bigint comment 'Business id',
    version      int not null default 0 comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);

create table if not exists customer_account
(
    id           bigint auto_increment primary key comment 'Account id',
    customer_id  bigint         not null comment 'Customer id',
    balance      decimal(18, 6) not null default 0 comment 'Balance of account',
    version      int            not null default 0 comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);

create table if not exists merchant_account
(
    id           bigint auto_increment primary key comment 'Account id',
    merchant_id  bigint         not null comment 'Merchant id',
    balance      decimal(18, 6) not null default 0 comment 'Balance of account',
    version      int            not null default 0 comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);

create table if not exists merchant_balance_history
(
    id           bigint auto_increment primary key comment 'Account id',
    merchant_id  bigint comment 'Merchant id',
    amount       decimal(18, 6) comment 'Balance of account',
    biz_time     datetime comment 'Business time',
    biz_id       bigint comment 'Business id',
    version      int comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);

create table if not exists merchant_daily_income_statistic
(
    id           bigint auto_increment primary key comment 'Account id',
    merchant_id  bigint comment 'Merchant id',
    amount       decimal(18, 6) comment 'daily income',
    biz_date     date comment 'Business date',
    version      int comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);


create table if not exists emall_order
(
    id           bigint auto_increment primary key comment 'Order id',
    customer_id  bigint comment 'Customer id',
    merchant_id  bigint comment 'Merchant id',
    sku          bigint comment 'SKU',
    quantity     bigint comment 'Goods quantity',
    price        decimal(18, 6) comment 'Price',
    paid_amount  decimal comment 'Paid amount',
    version      int not null default 0 comment 'Optimize lock version',
    created_time datetime comment 'Created time',
    updated_time datetime comment 'Updated time'
);
