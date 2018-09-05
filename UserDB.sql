drop database if exists userinformation;
create database UserInformation;
use Userinformation;
create table Userinfo (
	UserID int(10) primary key not null auto_increment,
	username varchar(50) unique not null,
    Password varchar(50) not null,
    Score int(10) default 0,
    Coins int(10) default 0,
    totalcoins int(10),
    shopstatus varchar(6) default 'nnnnnn',
    equipstatus varchar(6) default 'nnnnnn'
);

insert into userinfo (Username, Password, score, coins) values ('rich', 'password', 201, 900);
insert into userinfo (Username, Password, score, coins) values ('FaZe_ali3n', 'password', 765, 900);
insert into userinfo (Username, Password, score, coins) values ('Prof_Miller', 'password', 9001, 900);
insert into userinfo (Username, Password, score, coins) values ('colespudzo', 'password', 543, 900);
insert into userinfo (Username, Password, score, coins) values ('bad', 'password', 6, 900);
insert into userinfo (Username, Password, score, coins) values ('Mario', 'password', 1981, 900);





    
