-- Table: roles
CREATE TABLE roles
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    permissions JSON,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table: users
CREATE TABLE users
(
    id                INT AUTO_INCREMENT PRIMARY KEY,
    uuid              BINARY(16) NOT NULL UNIQUE DEFAULT (UUID_TO_BIN(UUID(), true)),
    email             VARCHAR(255) NOT NULL UNIQUE,
    phone             VARCHAR(20),
    password_hash     VARCHAR(255) NOT NULL,
    first_name        VARCHAR(100) NOT NULL,
    last_name         VARCHAR(100) NOT NULL,
    avatar_url        VARCHAR(500),
    date_of_birth     DATE,
    gender            ENUM('male', 'female', 'other'),
    role_id           INT          NOT NULL DEFAULT 2,
    status            ENUM('active', 'inactive', 'suspended') DEFAULT 'active',
    email_verified_at TIMESTAMP NULL,
    last_login_at     TIMESTAMP NULL,
    created_at        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP,
    updated_at        TIMESTAMP             DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE user_addresses
(
    id              INT PRIMARY KEY AUTO_INCREMENT,
    user_id         INT          NOT NULL,
    title           VARCHAR(100) NOT NULL,
    recipient_name  VARCHAR(200) NOT NULL,
    recipient_phone VARCHAR(20)  NOT NULL,
    address_line_1  VARCHAR(255) NOT NULL,
    address_line_2  VARCHAR(255),
    ward            VARCHAR(100) NOT NULL,
    district        VARCHAR(100) NOT NULL,
    city            VARCHAR(100) NOT NULL,
    postal_code     VARCHAR(20),
    country         VARCHAR(100) DEFAULT 'Vietnam',
    is_default      BOOLEAN      DEFAULT FALSE,
    created_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    INDEX idx_user_default (user_id, is_default)
);
