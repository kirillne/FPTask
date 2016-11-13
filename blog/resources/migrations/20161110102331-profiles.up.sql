CREATE TABLE profiles
(user_id int PRIMARY KEY,
 name varchar(20) NULL,
 surname varchar(20) NULL,
 birth_date date NULL,
 sex boolean NULL,
 country varchar(20) NULL,
 city varchar(20) NULL,
 address varchar(50) NULL,
 email varchar(20) NULL);

ALTER TABLE profiles 
ADD CONSTRAINT fk_profiles_users FOREIGN KEY (user_id)
REFERENCES users(id);