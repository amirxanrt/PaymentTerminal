SELECT id, login FROM users
ORDER BY id
LIMIT 3 OFFSET 0
;

SELECT id, login FROM users
ORDER BY id
LIMIT 3 OFFSET 3
;

-- первая страница активных пользователей
SELECT id, login FROM users
WHERE removed = FALSE
ORDER BY id
LIMIT 3 OFFSET 0
;

-- первая страница корзины ноль.
SELECT id, login FROM users
WHERE removed = TRUE
ORDER BY id
LIMIT 3 OFFSET 0
;


SELECT id, login FROM users
WHERE id = 2
;

UPDATE users SET password = 'top-secret'
WHERE id = 1 AND removed = FALSE
RETURNING id, login, role
;

-- удаление
UPDATE users SET removed = TRUE
WHERE id = 1
RETURNING id, login, role
;

-- восстановление
UPDATE users SET removed = FALSE
WHERE id = 1
RETURNING id, login, role
;