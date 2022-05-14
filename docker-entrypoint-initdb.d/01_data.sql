INSERT INTO users (login, password, role)
VALUES ('vasya', '$2a$10$G0OcYjFd2uRc8JY99Gxk4.hrjIOXFIx/XYTEakafCbVLP2xn2ZS1m', 'USER');

INSERT INTO users (login, password, role)
VALUES ('petya', '$2a$10$bYQFbKJQvAU6b2QfWsMuL.bt/8iRaNFsWj4DRibOSUtlfsz9Rn4TW', 'USER');

INSERT INTO users (login, password, role)
VALUES ('vanya', '$2a$10$xjlpOAzwDRM0lrZGYM6q7Oka2McLtTHWaIlxmJUxcKh1LxzEhwNyS', 'USER');

INSERT INTO users (login, password, role)
VALUES ('danya', '$2a$10$8BPHZ1fpnKIedG24eRFbW.kWZx14kh7yd2vPRG6DXsmV/F8Z5H8mm', 'USER');

INSERT INTO users (login, password, role)
VALUES ('admin', '$2a$10$m/stkfp21FNOfs7mXLbt7eB8mQuQOXYlU1ccLX09OsLVZezvqj5g6', 'ADMIN');




INSERT INTO products ( name, price, qty, photo)
VALUES ( 'Кирпич керамический  (250х120х65мм)', 31, 1000, 'brick.png'),
       ( 'Радиатор алюминиевый STI 500х80', 9536, 100, 'radiator.png'),
       ( 'Краска влагос. акриловая Parade W3 белая матовая (10л)', 3455, 500, 'paint.png'),
       ( 'Линолеум JT Venus Moscow-137M', 232, 50, 'linoleum.png');

/*INSERT INTO cards (user_id, city, phone_number bonus_card)
VALUES  (1, 'Казань', 79196259697, 70001160960096)*/


