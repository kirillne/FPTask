CREATE TABLE comments_ratings
(comment_id int NOT NULL,
 user_id int NOT NULL,
 value boolean NOT NULL,
 PRIMARY KEY (comment_id, user_id));

ALTER TABLE comments_ratings 
ADD CONSTRAINT fk_comments_ratings_users FOREIGN KEY (user_id)
REFERENCES users(id);

ALTER TABLE comments_ratings 
ADD CONSTRAINT fk_comments_ratings_comments FOREIGN KEY (comment_id)
REFERENCES comments(id)
ON DELETE CASCADE;