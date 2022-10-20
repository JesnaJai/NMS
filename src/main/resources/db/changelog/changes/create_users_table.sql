       create table nms_roles(
 roleid int primary key,
 role_name varchar(20) not null
 );


create table nms_users (
    userid uuid primary key not null,
--    default nextval('userid_sequence'::regclass) ,
    employeeCode varchar(50),
    username varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null,
    mobile varchar(25) ,
    dateFormat date

    );

create table nms_user_roles(
    userid uuid ,
    roleid int,
    foreign key (userid) references nms_users(userid),
    foreign key (roleid) references nms_roles(roleid)
    );

