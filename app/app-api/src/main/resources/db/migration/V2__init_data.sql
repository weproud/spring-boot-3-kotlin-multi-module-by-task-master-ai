-- 관리자 사용자 추가 (비밀번호: admin123, BCrypt 해시)
INSERT INTO users (email, name, password, user_status, status, created_at, updated_at)
VALUES ('admin@example.com', '관리자', '$2a$10$qS5VkVUKAIMSHSRQYMVSEOQJuNCuWZaKnXlP9MG9iZ88WTZ9hGEJm', 'ACTIVE', 'ACTIVE', NOW(), NOW());

-- 관리자 역할 추가
INSERT INTO user_roles (user_id, role)
VALUES (1, 'ADMIN');

-- 일반 사용자 추가 (비밀번호: password123, BCrypt 해시)
INSERT INTO users (email, name, password, user_status, status, created_at, updated_at)
VALUES ('user@example.com', '일반 사용자', '$2a$10$qS5VkVUKAIMSHSRQYMVSEOQJuNCuWZaKnXlP9MG9iZ88WTZ9hGEJm', 'ACTIVE', 'ACTIVE', NOW(), NOW());

-- 일반 사용자 역할 추가
INSERT INTO user_roles (user_id, role)
VALUES (2, 'USER');
