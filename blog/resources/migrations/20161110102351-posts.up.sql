CREATE TABLE posts
(id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
 name varchar(20) NOT NULL,
 creation_date date NOT NULL,
 user_id int NOT NULL,
 text varchar(max) NOT NULL);

ALTER TABLE posts 
ADD CONSTRAINT fk_posts_users FOREIGN KEY (user_id)
REFERENCES users(id);