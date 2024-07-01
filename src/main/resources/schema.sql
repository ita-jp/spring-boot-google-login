CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE user_social_logins
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id  BIGINT       NOT NULL,
    provider VARCHAR(255) NOT NULL,
    subject  VARCHAR(255) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id),
    UNIQUE (provider, subject)
);
