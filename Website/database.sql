-- Danh mục sản phẩm (hỗ trợ danh mục cha-con)
CREATE TABLE product_categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    slug        VARCHAR(200) UNIQUE NOT NULL,
    parent_id   BIGINT REFERENCES product_categories(id) ON DELETE SET NULL,
    description TEXT,
    image_url   VARCHAR(500),
    sort_order  INT DEFAULT 0,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT NOW(),
    updated_at  TIMESTAMP DEFAULT NOW()
);

-- Sản phẩm
CREATE TABLE products (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(300) NOT NULL,
    slug          VARCHAR(300) UNIQUE NOT NULL,
    category_id   BIGINT REFERENCES product_categories(id) ON DELETE SET NULL,
    description   TEXT,
    content       TEXT,
    price         DECIMAL(15,2),
    sale_price    DECIMAL(15,2),
    sku           VARCHAR(100),
    thumbnail     VARCHAR(500),
    is_featured   BOOLEAN DEFAULT FALSE,
    is_active     BOOLEAN DEFAULT TRUE,
    sort_order    INT DEFAULT 0,
    view_count    INT DEFAULT 0,
    meta_title    VARCHAR(300),
    meta_desc     VARCHAR(500),
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

-- Ảnh phụ của sản phẩm
CREATE TABLE product_images (
    id          BIGSERIAL PRIMARY KEY,
    product_id  BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    image_url   VARCHAR(500) NOT NULL,
    alt_text    VARCHAR(300),
    sort_order  INT DEFAULT 0
);

-- Danh mục tin tức
CREATE TABLE news_categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    slug        VARCHAR(200) UNIQUE NOT NULL,
    sort_order  INT DEFAULT 0,
    is_active   BOOLEAN DEFAULT TRUE
);

-- Tin tức / bài viết
CREATE TABLE news (
    id            BIGSERIAL PRIMARY KEY,
    title         VARCHAR(500) NOT NULL,
    slug          VARCHAR(500) UNIQUE NOT NULL,
    category_id   BIGINT REFERENCES news_categories(id) ON DELETE SET NULL,
    summary       TEXT,
    content       TEXT,
    thumbnail     VARCHAR(500),
    is_featured   BOOLEAN DEFAULT FALSE,
    is_active     BOOLEAN DEFAULT TRUE,
    view_count    INT DEFAULT 0,
    meta_title    VARCHAR(300),
    meta_desc     VARCHAR(500),
    published_at  TIMESTAMP,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

-- Danh mục dự án
CREATE TABLE project_categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    slug        VARCHAR(200) UNIQUE NOT NULL,
    sort_order  INT DEFAULT 0,
    is_active   BOOLEAN DEFAULT TRUE
);

-- Dự án / công trình tiêu biểu
CREATE TABLE projects (
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(300) NOT NULL,
    slug          VARCHAR(300) UNIQUE NOT NULL,
    category_id   BIGINT REFERENCES project_categories(id) ON DELETE SET NULL,
    client        VARCHAR(300),
    location      VARCHAR(300),
    description   TEXT,
    content       TEXT,
    thumbnail     VARCHAR(500),
    year          INT,
    is_featured   BOOLEAN DEFAULT FALSE,
    is_active     BOOLEAN DEFAULT TRUE,
    sort_order    INT DEFAULT 0,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

-- Ảnh phụ của dự án
CREATE TABLE project_images (
    id          BIGSERIAL PRIMARY KEY,
    project_id  BIGINT NOT NULL REFERENCES projects(id) ON DELETE CASCADE,
    image_url   VARCHAR(500) NOT NULL,
    alt_text    VARCHAR(300),
    sort_order  INT DEFAULT 0
);

-- Vị trí tuyển dụng
CREATE TABLE job_postings (
    id            BIGSERIAL PRIMARY KEY,
    title         VARCHAR(300) NOT NULL,
    slug          VARCHAR(300) UNIQUE NOT NULL,
    description   TEXT,
    requirements  TEXT,
    benefits      TEXT,
    location      VARCHAR(200),
    salary_range  VARCHAR(200),
    quantity      INT DEFAULT 1,
    deadline      DATE,
    is_active     BOOLEAN DEFAULT TRUE,
    created_at    TIMESTAMP DEFAULT NOW(),
    updated_at    TIMESTAMP DEFAULT NOW()
);

-- Hồ sơ ứng tuyển
CREATE TABLE job_applications (
    id           BIGSERIAL PRIMARY KEY,
    job_id       BIGINT REFERENCES job_postings(id) ON DELETE SET NULL,
    full_name    VARCHAR(200) NOT NULL,
    email        VARCHAR(200) NOT NULL,
    phone        VARCHAR(20),
    cv_file      VARCHAR(500),
    cover_letter TEXT,
    status       VARCHAR(50) DEFAULT 'NEW',
    applied_at   TIMESTAMP DEFAULT NOW()
);

-- Form liên hệ
CREATE TABLE contacts (
    id           BIGSERIAL PRIMARY KEY,
    full_name    VARCHAR(200) NOT NULL,
    email        VARCHAR(200),
    phone        VARCHAR(20),
    company      VARCHAR(300),
    subject      VARCHAR(500),
    message      TEXT,
    is_read      BOOLEAN DEFAULT FALSE,
    created_at   TIMESTAMP DEFAULT NOW()
);

-- Tài khoản admin
CREATE TABLE users (
    id           BIGSERIAL PRIMARY KEY,
    full_name    VARCHAR(200) NOT NULL,
    email        VARCHAR(200) UNIQUE NOT NULL,
    password     VARCHAR(500) NOT NULL,
    role         VARCHAR(50) DEFAULT 'ADMIN',
    avatar       VARCHAR(500),
    is_active    BOOLEAN DEFAULT TRUE,
    last_login   TIMESTAMP,
    created_at   TIMESTAMP DEFAULT NOW()
);

-- Cài đặt website (logo, hotline, địa chỉ,...)
CREATE TABLE settings (
    id      BIGSERIAL PRIMARY KEY,
    key     VARCHAR(200) UNIQUE NOT NULL,
    value   TEXT,
    label   VARCHAR(200),
    grp     VARCHAR(100)
);

-- Banner / slider trang chủ
CREATE TABLE banners (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(300),
    subtitle    VARCHAR(500),
    image_url   VARCHAR(500) NOT NULL,
    link_url    VARCHAR(500),
    sort_order  INT DEFAULT 0,
    is_active   BOOLEAN DEFAULT TRUE,
    created_at  TIMESTAMP DEFAULT NOW()
);

-- Đối tác / khách hàng
CREATE TABLE partners (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(200) NOT NULL,
    logo_url    VARCHAR(500),
    website     VARCHAR(500),
    type        VARCHAR(50) DEFAULT 'PARTNER',
    sort_order  INT DEFAULT 0,
    is_active   BOOLEAN DEFAULT TRUE
);

-- Tài khoản admin mặc định (password: Admin@123)
INSERT INTO users (full_name, email, password, role)
VALUES ('Administrator', 'admin@yourcompany.com',
'$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', 'ADMIN');

-- Cài đặt cơ bản
INSERT INTO settings (key, value, label, grp) VALUES
('site_name',    'Tên Công Ty',           'Tên website',   'general'),
('site_email',   'songkhaiitm@gmail.com',  'Email',         'general'),
('site_phone',   '0982 773 944 - 0904 565 839',          'Số điện thoại', 'general'),
('site_address', 'Số 17, Ngõ 1081 Đường Hồng Hà, P. Hồng Hà
Quận Hoàn Kiếm, TP Hà Nội', 'Địa chỉ',       'general'),
('site_logo',    '',                       'Logo',          'general'),
('facebook_url', '',                       'Facebook',      'social'),
('youtube_url',  '',                       'Youtube',       'social');

-- 1. THÊM CÁC DANH MỤC SẢN PHẨM (Lĩnh vực kinh doanh của Songkhai)
INSERT INTO product_categories (name, slug, description, is_active, created_at, updated_at) VALUES
('Máy phát điện Bond Generator', 'may-phat-dien-bond', 'Máy phát điện xuất xứ Anh, Mỹ, Nhật, Ấn Độ, China. Chống rung, giải pháp phần mềm kiểm soát.', true, NOW(), NOW()),
('Máy phát điện Cummins / Perkins / Mitsubishi', 'may-phat-dien-cong-nghiep', 'Các dòng máy phát điện công nghiệp nhập khẩu chính hãng.', true, NOW(), NOW()),
('Tủ điện hạ thế BOND SWITCHBOARD', 'tu-dien-ha-the', 'Tủ điện tổng MSB, Tủ phân phối, Tủ điều khiển, Tủ cứu hỏa, Tủ ATS.', true, NOW(), NOW()),
('Hệ thống thang máng cáp', 'thang-mang-cap', 'Hệ thống thang cáp, máng cáp, khay cáp tiêu chuẩn quốc tế.', true, NOW(), NOW()),
('Hệ thống Busway', 'he-thong-busway', 'Thanh dẫn điện Busway, Elbow cho hệ thống Busway.', true, NOW(), NOW());

-- 2. THÊM CÁC DỰ ÁN TIÊU BIỂU (Lấy từ trang 40-42 của PDF)
INSERT INTO projects (name, slug, description, content, is_active, created_at, updated_at) VALUES
('Sân bay Long Thành', 'san-bay-long-thanh', 'Máy phát điện 2 x 2000KVA', 'Cung cấp và lắp đặt hệ thống máy phát điện công suất lớn cho dự án Sân bay Quốc tế Long Thành.', true, NOW(), NOW()),
('Khu Resoft Mikazuki – Đà Nẵng', 'khu-resoft-mikazuki', 'Máy phát điện Mitsubishi 2200kva', 'Dự án cung cấp máy phát điện Mitsubishi 2200kva cho Khu Resoft Mikazuki, Liên Chiểu, Đà Nẵng.', true, NOW(), NOW()),
('Khách sạn Từ Hoa Công Chúa', 'khach-san-tu-hoa', 'Máy phát điện Bond Generator 2 x 1100 kva', 'Cung cấp máy phát điện Bond Generator 2 x 1100 kva đảm bảo nguồn điện liên tục cho khách sạn.', true, NOW(), NOW()),
('Trụ sở Ngân hàng Nhà nước', 'tru-so-ngan-hang-nha-nuoc', 'Máy phát điện Perkins 13750KVA x 2', 'Cung cấp máy phát điện Perkins công suất cực lớn 13750KVA.', true, NOW(), NOW()),
('Nhà máy Fulian Bắc Giang', 'nha-may-fulian', 'Ống khói máy phát điện 20000KVA', 'Thi công hệ thống ống khói cho máy phát điện 20000KVA tại nhà máy Fulian.', true, NOW(), NOW()),
('Tòa nhà HUD Tower – Nha Trang', 'hud-tower-nha-trang', 'Tủ điện tòa nhà', 'Cung cấp và lắp đặt hệ thống tủ điện hạ thế cho tòa nhà HUD Tower.', true, NOW(), NOW());

-- 3. THÊM SẢN PHẨM MẪU (Lấy từ các trang 13-15 PDF)
INSERT INTO products (name, slug, category_id, description, content, is_featured, is_active, created_at, updated_at) 
SELECT 
    'Máy phát điện Cummins', 
    'may-phat-dien-cummins', 
    id, 
    'Xuất xứ Anh - Mỹ - Ấn Độ - China. Giải pháp phần mềm kiểm soát thời gian và chế độ tải, hòa đồng bộ.', 
    '<p>Máy phát điện Cummins do Songkhai ITM cung cấp được nhập khẩu chính hãng, đảm bảo vận hành bền bỉ, tiết kiệm nhiên liệu.</p>', 
    true, true, NOW(), NOW()
FROM product_categories WHERE slug = 'may-phat-dien-cong-nghiep';

INSERT INTO products (name, slug, category_id, description, content, is_featured, is_active, created_at, updated_at) 
SELECT 
    'Tủ điện tổng MSB - LV', 
    'tu-dien-tong-msb', 
    id, 
    'Tủ điện tổng MSB, LV do BOND SWITCHBOARD sản xuất. Tiêu chuẩn IEC 60439-1, Form 1 đến Form 4.', 
    '<p>Hệ thống tủ điện được thiết kế mô phỏng 3D, chế tạo bằng máy CNC AMADA, sơn tĩnh điện tự động.</p>', 
    true, true, NOW(), NOW()
FROM product_categories WHERE slug = 'tu-dien-ha-the';