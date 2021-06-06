create user if not exists 'vec'@'%' identified by 'my-secret-pw';
create database if not exists `vec_main`;
grant all on `vec_main`.* to 'vec'@'%';
