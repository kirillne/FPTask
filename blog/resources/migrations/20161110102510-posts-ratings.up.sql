CREATE TABLE posts_ratings
(post_id int NOT NULL,
 user_id int NOT NULL,
 value boolean NOT NULL,
 PRIMARY KEY (post_id, user_id));

ALTER TABLE posts_ratings 
ADD CONSTRAINT fk_posts_ratings_users FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE posts_ratings 
ADD CONSTRAINT fk_posts_ratings_comments FOREIGN KEY (post_id)
REFERENCES posts(id)
ON DELETE CASCADE;