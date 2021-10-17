create table litter_bin_post
(
    post_id bigint auto_increment comment '主键自增
'
        primary key,
    creator varchar(200) default '' not null comment '创建者',
    create_time timestamp default CURRENT_TIMESTAMP not null comment '创建时间',
    update_time timestamp default '1971-01-01 00:00:00' null comment '更新时间',
    content text null comment '内容',
    op_status smallint default 1 null comment '软删标记'
)
    comment '帖子表';

create index idx_creator
	on litter_bin_post (creator);

create table litter_bin_tag
(
    tag_id bigint auto_increment comment '主键自增'
        primary key,
    tag_name varchar(200) default '' not null comment '标签名称',
    creator varchar(200) default '' not null comment '创建者',
    create_time timestamp default CURRENT_TIMESTAMP null comment '创建时间',
    update_time timestamp default '1971-01-01 00:00:00' not null comment '更新时间',
    op_status smallint default 1 not null comment '软删标记'
)
    comment '标签表';

create table litter_bin.litter_bin_post_tag_mapping
(
    post_tag_mapping_id bigint auto_increment comment '主键自增'
        primary key,
    post_id bigint default 0 not null comment '帖子id',
    tag_id bigint default 0 not null comment '标签id'
)
    comment '帖子标签映射表';

create index idx_tag_id
    on litter_bin.litter_bin_post_tag_mapping (tag_id);

