--------------------------------------- users

-- :name get-all-users :query :many
SELECT * FROM users;

-- :name get-user :query :one
SELECT * FROM users
WHERE id = :id

-- :name create-user :execute :affected
INSERT INTO users
VALUES (:id, :login, :password, :seed)

-- :name update-user :execute :affected
UPDATE users
SET login = :login, password = :password, seed = :seed
WHERE id = :id

-- :name delete-user :execute :affected
DELETE FROM users
WHERE id = :id

-- :name get-user-by-login :query :one
SELECT * FROM users
WHERE login = :login

-- :name get-seed-by-id :query :one
SELECT seed FROM users
WHERE id = :id

-- :name get-seed-by-login :query :one
SELECT seed FROM users
WHERE login = :login

-- :name delete-user-by-login :execute :affected
DELETE FROM users
WHERE login = :login

-- :name get-full-users-info :query :many
SELECT u.id, u.login, u.password, u.seed, p.name, p.surname, p.birth_date, p.sex, p.country, p.city, p.address, p.email FROM users AS u
JOIN profiles AS p
ON u.id = p.user_id

-- :name get-full-user-info :query :one
SELECT * FROM
(SELECT u.id, u.login, u.password, u.seed, p.name, p.surname, p.birth_date, p.sex, p.country, p.city, p.address, p.email FROM users AS u
JOIN profiles AS p
ON u.id = p.user_id)
WHERE id = :user-id
--------------------------------------- profiles

-- :name get-all-profiles :query :many
SELECT * FROM profiles

-- :name get-profile :query :one
SELECT * FROM profiles
WHERE user_id = :id

-- :name create-profile :execute :affected
INSERT INTO profiles
VALUES (:id, :name, :surname, :birth-date, :sex, :country, :city, :address, :email)

-- :name update-profile :execute :affected
UPDATE profiles
SET name = :name, surname = :surname, birth_date = :birth-date, sex = :sex, country = :country, city = :city, address = :address, email = :email
WHERE user_id = :id

-- :name delete-profile :execute :affected
DELETE FROM profile
WHERE user_id = :id

-- :name get-profiles-by-surname :query :many
SELECT * FROM profiles
WHERE surname = :surname

-- :name get-profiles-by-email :query :many
SELECT * FROM profiles
WHERE email = :email

-- :name get-profiles-by-country :query :many
SELECT * FROM profiles
WHERE country = :country

-- :name get-profiles-by-city :query :many
SELECT * FROM profiles
WHERE city = :city

--------------------------------------- posts

-- :name get-all-posts :query :many
SELECT * FROM posts

-- :name get-post :query :one
SELECT * FROM posts
WHERE id = :id

-- :name create-post :insert
INSERT INTO posts
VALUES (:id, :name, :creation-date, :user-id, :text)

-- :name update-post :execute :affected
UPDATE posts
SET name = :name, text = :text
WHERE id = :id

-- :name delete-post :execute :affected
DELETE FROM posts
WHERE id = :id

-- :name get-posts-by-user-id :query :many
SELECT * FROM posts
WHERE user_id = :user-id

-- :name get-posts-by-creation-date :query :many
SELECT * FROM posts
WHERE creation_date = :creation-date

-- :name get-posts-with-comments-count :query :many
SELECT * FROM
(SELECT * FROM
	(SELECT p.id, p.name, p.creation_date, p.text, p.user_id, c.count FROM posts AS p
	LEFT JOIN (SELECT post_id, COUNT (*) AS count FROM comments GROUP BY post_id) AS c
	ON p.id = c.post_id)
WHERE user_id = :user-id) AS pc
LEFT JOIN 
(SELECT * FROM posts_ratings
 WHERE user_id = :current-user-id) as pr
ON pc.id = pr.post_id

-- :name get-posts-with-comments-count-and-ratings :query :many
SELECT r.id, r.name, r.creation_date, r.text, r.user_id, r.count, r.value, rat.rating FROM
  (SELECT pc.id, pc.name, pc.creation_date, pc.text, pc.user_id, pc.count, pr.value FROM
    (
      SELECT * FROM
  	   (
  	     SELECT p.id, p.name, p.creation_date, p.text, p.user_id, c.count
  	     FROM posts AS p
  	     LEFT JOIN 
  	           (
  	             SELECT post_id, COUNT (*) AS count 
  	             FROM comments GROUP BY post_id
  	           ) AS c
  	     ON p.id = c.post_id
  	   )
      WHERE user_id = :user-id
    ) AS pc
    LEFT JOIN 
    (
      SELECT * FROM posts_ratings
      WHERE user_id = :user-id
    
     ) as pr
  ON pc.id = pr.post_id) as r
LEFT JOIN (SELECT post_id, SUM(CASE value WHEN true THEN 1 ELSE -1 END) as rating FROM posts_ratings GROUP BY post_id) as rat
ON r.id = rat.post_id


--------------------------------------- comments

-- :name get-all-comments :query :many
SELECT * FROM comments

-- :name get-comment :query :one
SELECT * FROM comments
WHERE id = :id

-- :name create-comment :execute :affected
INSERT INTO comments
VALUES (:id, :user-id, :text, :creation-date, :post-id)

-- :name update-comment :execute :affected
UPDATE comments
SET text = :text
WHERE id = :id

-- :name delete-comment :execute :affected
DELETE FROM comments
WHERE id = :id

-- :name get-comments-by-user-id :query :many
SELECT * FROM comments
WHERE user_id = :user-id

-- :name get-comments-by-post-id :query :many
SELECT * FROM comments
WHERE post_id = :post-id
ORDER BY creation_date

-- :name get-comments-by-post-id-with-ratings :query :many
SELECT com.id, com.user_id, com.text, com.creation_date, com.post_id, com.rating, cr.value FROM (SELECT id, user_id, text, creation_date, post_id, rating FROM comments
LEFT JOIN (SELECT comment_id, SUM(CASE value WHEN true THEN 1 WHEN false THEN -1 ELSE 0 END) as rating FROM comments_ratings GROUP BY comment_id) as rat
ON comments.id = rat.comment_id
WHERE post_id = 65
ORDER BY creation_date) as com
LEFT JOIN (SELECT * FROM comments_ratings WHERE user_id = 1) AS cr
ON cr.comment_id = com.id 

-- :name delete-comment-by-post-id :execute :affected
DELETE FROM comments
WHERE post_id = :post_id

--------------------------------------- comments_ratings

-- :name create-comments-ratings :execute :affected
INSERT INTO comments_ratings
VALUES (:comment-id, :user-id, :value)

-- :name update-comments-ratings :execute :affected
UPDATE comments_ratings
SET value = :value
WHERE comment_id = :comment-id AND user_id = :user-id

-- :name get-comment-sum-rating :query : one
SELECT SUM(CASE value WHEN true THEN 1 ELSE -1 END) as rating
FROM comments_ratings WHERE comment_id = :comment-id

-- :name get-comment-existing :query :one
SELECT *
FROM comments_ratings
WHERE comment_id = :comment-id AND user_id = :user-id

-------------------------------------- posts_ratings

-- :name create-posts-ratings :execute :affected
INSERT INTO posts_ratings
VALUES (:post-id, :user-id, :value)

-- :name update-post-rating :execute :affected
UPDATE posts_ratings
SET value = :value
WHERE post_id = :post-id AND user_id = :user-id

-- :name get-existing :query :one
SELECT *
FROM posts_ratings
WHERE post_id = :post-id AND user_id = :user-id

-- :name get-post-sum-rating :query :one
SELECT SUM(CASE value WHEN true THEN 1 ELSE -1 END) as rating
FROM Posts_ratings WHERE post_id = :post-id

-------------------------------------- user_rating
-- :name get-user-rating :query :one
SELECT ISNULL((
SELECT  SUM(CASE  PR.value WHEN true THEN 1 ELSE -1 END)
FROM Posts_ratings AS PR
JOIN Posts AS P
ON PR.post_id = p.id
WHERE P.user_id = :user-id),0) + 0.2* 

ISNULL((
SELECT SUM(CASE  CR.value WHEN true THEN 1 ELSE -1 END)
FROM comments_ratings AS CR
JOIN Comments AS C
ON CR.comment_id = C.id
WHERE C.user_id = :user-id),0) AS rate



