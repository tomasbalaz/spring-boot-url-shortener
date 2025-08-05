INSERT INTO users (email, password, name, role)
VALUES ('admin@admin.com', 'admin','Administrator', 'ROLE_ADMIN'),
       ('user@user.com', 'user','User', 'ROLE_USER');

INSERT INTO short_urls (short_key, original_url)
VALUES ('asdfv', 'www.google.com');