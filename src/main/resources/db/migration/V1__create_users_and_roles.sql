
CREATE TABLE app_users (
                           id BIGSERIAL PRIMARY KEY,
                           username VARCHAR(100) NOT NULL UNIQUE,
                           password VARCHAR(255) NOT NULL,
                           enabled BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE app_roles (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE app_user_roles (
                                user_id BIGINT NOT NULL,
                                role_id BIGINT NOT NULL,
                                PRIMARY KEY (user_id, role_id),

                                CONSTRAINT fk_user
                                    FOREIGN KEY (user_id)
                                        REFERENCES app_users(id),

                                CONSTRAINT fk_role
                                    FOREIGN KEY (role_id)
                                        REFERENCES app_roles(id)
);

INSERT INTO app_roles (name)
VALUES ('ROLE_USER'), ('ROLE_ADMIN');
