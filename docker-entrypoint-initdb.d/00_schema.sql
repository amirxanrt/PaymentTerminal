CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    TEXT        NOT NULL UNIQUE,
    password TEXT        NOT NULL,
    role     TEXT        NOT NULL,
    removed  BOOLEAN     NOT NULL DEFAULT FALSE,
    created  timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cards
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT  NOT NULL REFERENCES users,
    city         TEXT    NOT NULL,
    phone_number BIGINT  NOT NULL,
    bonus_card   BIGINT  NOT NULL,
    qr_code      TEXT    NOT NULL,
    removed      BOOLEAN NOT NULL DEFAULT FALSE

);


CREATE TABLE products
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT        NOT NUll,
    price   INT         NOT NULL CHECK ( price >= 0 ),
    qty     INT         NOT NULL,
    photo   TEXT        NOT NULL,
    removed BOOLEAN     NOT NULL DEFAULT FALSE,
    created timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE purchases
(
    id            BIGSERIAL PRIMARY KEY,
    product_id    BIGINT REFERENCES products NOT NULL,
    product_name  TEXT                       NOT NULL,
    qty           INT                        NOT NULL,
    product_price INT                        NOT NULL,
    user_id       BIGINT                     NOT NULL,
    sum           INT                        NOT NULL,
    created       timestamptz                NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE bonuses
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT      NOT NULL REFERENCES users,
    purchase_id BIGINT      NOT NULL REFERENCES purchases,
    bonus       INT         NOT NULL CHECK ( bonus >= 0 ) DEFAULT 0,
    created     timestamptz NOT NULL                      DEFAULT CURRENT_TIMESTAMP

);

CREATE TABLE reviews
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT      NOT NULL REFERENCES users,
    product_id BIGINT      NOT NULL REFERENCES products,
    review     TEXT        NOT NULL,
    removed    BOOLEAN     NOT NULL DEFAULT FALSE,
    created    timestamptz NOT NULL DEFAULT CURRENT_TIMESTAMP
);






















