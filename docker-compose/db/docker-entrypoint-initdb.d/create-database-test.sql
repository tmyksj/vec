create user if not exists 'vec'@'%' identified by 'my-secret-pw';
create database if not exists `vec_test`;
grant all on `vec_test`.* to 'vec'@'%';
