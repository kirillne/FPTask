CREATE TABLE profiles
(user_id int PRIMARY KEY,
 name varchar(50) NULL,
 surname varchar(50) NULL,
 birth_date date NULL,
 sex boolean NULL,
 country varchar(50) NULL,
 city varchar(50) NULL,
 address varchar(50) NULL,
 email varchar(50) NULL);

ALTER TABLE profiles 
ADD CONSTRAINT fk_profiles_users FOREIGN KEY (user_id)
REFERENCES users(id);