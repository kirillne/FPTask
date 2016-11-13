CREATE TABLE comments
(id int PRIMARY KEY AUTO_INCREMENT NOT NULL,
 user_id int NOT NULL,
 text varchar(max) NOT NULL,
 creation_date date NOT NULL,
 post_id int NOT NULL);

ALTER TABLE comments 
ADD CONSTRAINT fk_comments_users FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE comments 
ADD CONSTRAINT fk_comments_posts FOREIGN KEY (post_id)
REFERENCES posts(id)
ON DELETE CASCADE;