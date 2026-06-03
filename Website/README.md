# DemoTiến — Website Hoàn Chỉnh

Một trang web thương mại điện tử đầy đủ chức năng — xây dựng bằng Spring Boot, HTML5, CSS3, JavaScript vanilla, PostgreSQL.

## 📋 Các Tính Năng

### Frontend (MVC + Static)
- ✅ **Trang chủ (Home)** — `/` hoặc `/index` — Danh sách sản phẩm nổi bật, CTA, liên hệ.
- ✅ **Danh sách sản phẩm (Products List)** — `/products` — Phân trang, lọc danh mục, tìm kiếm.
- ✅ **Chi tiết sản phẩm (Product Detail)** — `/products/{slug}` — Thông tin đầy đủ, hình ảnh.
- ✅ **Form liên hệ** — Kết nối backend, lưu vào DB.
- ✅ **Thanh tìm kiếm header** — Autocomplete, gợi ý theo thời gian thực (real-time).
- ✅ **Giao diện responsive** — Mobile-first, phù hợp mọi kích thước màn hình.
- ✅ **Navigation mobile** — Hamburger menu, smooth transitions.

### Admin (Web Management)
- ✅ **Dashboard quản trị** — `/admin/` — Tổng quan hệ thống.
- ✅ **Quản lý sản phẩm** — `/admin/products` — Tạo, liệt kê sản phẩm (UI form).
- ✅ **Quản lý liên hệ** — `/admin/contacts` — Xem danh sách liên hệ.

### Backend (API + Database)
- ✅ **Contact API** — `POST /api/contact` — Nhận dữ liệu form, lưu vào bảng `contacts`.
- ✅ **Product API** — GET endpoints (danh mục, danh sách, tìm kiếm, chi tiết).
- ✅ **Product Service & Repository** — JPA-based data access.
- ✅ **Interceptor logs** — Ghi nhật ký request/response.

### SEO & Accessibility
- ✅ **Meta tags** — Title, description, og:image, theme-color.
- ✅ **Semantic HTML** — `<main>`, `<article>`, `<section>`, proper headings.
- ✅ **ARIA attributes** — `aria-label`, `aria-expanded`, `aria-live` cho accessibility.
- ✅ **Keyboard navigation** — Tab order, focus management.
- ✅ **Image lazy loading** — `loading="lazy"` trên tất cả ảnh.

## 🚀 Cách Chạy

### Yêu cầu
- Java 17+
- PostgreSQL 12+ (hoặc tuỳ chỉnh database)
- Gradle 7.0+

### 1. Cấu hình Database
Chỉnh sửa `src/main/resources/application.properties`:
```properties
server.port=8081
spring.datasource.url=jdbc:postgresql://localhost:5432/shopdb
spring.datasource.username=postgres
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 2. Khởi động ứng dụng
```bash
cd I:\Github\DuAnLam\Website
.\gradlew.bat bootRun
```

### 3. Truy cập
- **Trang chủ**: http://localhost:8081/
- **Danh sách sản phẩm**: http://localhost:8081/products
- **Chi tiết sản phẩm**: http://localhost:8081/products/{slug}
- **Admin**: http://localhost:8081/admin/

## 📁 Cấu trúc Files Mới

### Controllers
```
src/main/java/com/yourcompany/website/controller/
├── HomeController.java           (Trang chủ)
├── ProductPageController.java    (Danh sách & chi tiết sản phẩm)
├── AdminController.java          (Trang quản trị)
└── ContactController.java        (API liên hệ)
```

### Services
```
src/main/java/com/yourcompany/website/service/
├── ProductService.java
└── ContactService.java
```

### Repositories
```
src/main/java/com/yourcompany/website/repository/
├── ProductRepository.java
└── ContactRepository.java
```

### Templates (HTML)
```
src/main/resources/templates/
├── index.html             (Trang chủ tĩnh)
├── products-list.html     (Danh sách sản phẩm)
├── product-detail.html    (Chi tiết sản phẩm)
└── admin/
    ├── dashboard.html
    ├── products.html
    └── contacts.html
```

### Static Assets
```
src/main/resources/static/
├── index.html             (Bản sao từ templates/)
├── css/
│   ├── site.css          (Trang chủ + hero)
│   ├── products.css      (Danh sách & chi tiết)
│   └── admin.css         (Quản trị)
├── js/
│   ├── site.js           (Logic trang chủ, autocomplete)
│   ├── products-list.js  (Danh sách sản phẩm)
│   ├── product-detail.js (Chi tiết sản phẩm)
│   ├── admin-products.js (Quản lý sản phẩm)
│   └── admin-contacts.js (Quản lý liên hệ)
└── images/
    └── placeholder-hero.svg
```

## 🔧 Chức Năng Chi Tiết

### Form Liên Hệ
- **HTML**: `src/main/resources/static/index.html` (section `#contact`)
- **JS Handler**: `src/main/resources/static/js/site.js` (function `contact-form`)
- **API Endpoint**: `POST /api/contact` (params: fullName, email, message)
- **Database**: Lưu vào bảng `contacts`
- **Response**: JSON `{ success, message, data }`

### Thanh Tìm Kiếm (Autocomplete)
- **HTML**: Header search-box
- **JS Logic**: `src/main/resources/static/js/site.js` (section `Header search`)
- **Debounce**: 300ms
- **API Call**: `GET /api/products/search?keyword={keyword}&page=0&size=5`
- **Display**: Dropdown gợi ý 5 kết quả đầu tiên

### Danh Sách Sản Phẩm
- **URL**: `/products?page=0&size=12&category={categoryId}&keyword={keyword}`
- **JS**: `src/main/resources/static/js/products-list.js`
- **API**: `/api/products`, `/api/products/category/{id}`, `/api/products/search`
- **UI**: Grid responsive, phân trang, lọc

### Chi Tiết Sản Phẩm
- **URL**: `/products/{slug}`
- **API**: `GET /api/products/{slug}`
- **Display**: Hình ảnh, giá, mô tả, nút thêm vào giỏ

### Admin Dashboard
- **URL**: `/admin/` → `/admin/products` → `/admin/contacts`
- **Sidebar Navigation**: Điều hướng các trang quản lý
- **Product Form**: Tạo sản phẩm mới via `POST /api/admin/products`

## 🎨 Màu Sắc & Thiết Kế

- **Primary Color**: `#1e6fb8` (xanh dương)
- **Muted Text**: `#6b7280` (xám)
- **Background**: `#f8fafc` (trắng nhạt)
- **Card**: `#ffffff` (trắng)
- **Border Radius**: 8–16px (bo góc mựa)
- **Font**: Inter (body), Poppins (heading)
- **Shadow**: Nhẹ, subtle (max `rgba(15,23,36,0.06)`)

## 📱 Responsive Breakpoints

- **Desktop**: > 900px
- **Tablet**: 560–900px
- **Mobile**: < 560px

Tất cả giao diện tự động điều chỉnh kích thước, spacing, và layout.

## 🔒 Bảo Mật (Ghi Chú)

Hiện tại:
- ✅ Spring Security active (app.yml: `spring.security...`)
- ⚠️ Admin pages **không bao vây bởi authentication** — nên thêm `@PreAuthorize` hoặc`.hasRole("ADMIN")` trên các endpoint/controller admin.
- ⚠️ API `/api/contact` public — có thể giới hạn rate limit hoặc CSRF token nếu cần.

Để thêm authentication:
1. Tạo User entity với roles (ADMIN, USER).
2. Thêm login/register pages.
3. Protect admin endpoints bằng `@PreAuthorize("hasRole('ADMIN')")`.

## 📊 Tối Ưu Hóa

- ✅ **CSS Minified** — Inline styles, không external stylesheet (trừ css-external như Google Fonts).
- ✅ **JS Minified** — Production-ready, no source maps.
- ✅ **Image Optimization** — SVG placeholders, lazy loading.
- ✅ **Caching Headers** — Spring Boot Thymeleaf cache (if migrated).

## 🧪 Test

Để test form liên hệ:
```bash
curl -X POST "http://localhost:8081/api/contact" \
  -d "fullName=Nguyễn A&email=a@example.com&message=Xin chào"
```

Để test search:
```bash
curl "http://localhost:8081/api/products/search?keyword=sản phẩm&page=0&size=5"
```

## 🐛 Debugging

- **Console browser**: Mở DevTools → Console để xem JS errors.
- **Network tab**: Inspect HTTP requests, API responses.
- **Server logs**: Chạy `.\gradlew.bat bootRun` và xem logs Spring Boot.
- **Database**: Kết nối PostgreSQL client, chạy `SELECT * FROM contacts;`

## 📝 Ghi Chú Tác Giả

Trang web này được xây dựng hoàn toàn bằng Spring Boot + Vanilla JS + CSS3, không sử dụng frontend framework nặng nề (React, Vue, Angular).

Đó là một proejct định hướng học tập, giáo dục — dễ hiểu, dễ mở rộng, dễ bảo trì.

---

**Cần hỗ trợ?** Kiểm tra console logs hoặc gửi chi tiết lỗi! 🎯

## 🎨 Cách Thêm Nội Dung (Content Management)

**5 cách để thêm sản phẩm & nội dung vào web:**

### 1️⃣ **Thiệt Kế Web (Easy) — Qua Giao Diện Admin**
- Vào http://localhost:8081/admin/products
- Nút **"+ Tạo sản phẩm mới"**
- Điền form: tên, giá, mô tả ngắn, mô tả chi tiết
- Click **Lưu** → Sản phẩm xuất hiện tức thì trên trang chủ
- ✅ Không cần code, không cần biết database

### 2️⃣ **Database Trực Tiếp (Pro) — SQL Query**
- Kết nối PostgreSQL qua DBeaver / pgAdmin / SQL IDE
- Bảng: `product` (fields: id, name, price, short_description, description, slug, featured, active)
- Insert dữ liệu vào bảng
- VD:
  ```sql
  INSERT INTO product (name, price, short_description, description, slug, featured, active)
  VALUES ('Sản phẩm A', 500000, 'Mô tả ngắn', 'Mô tả dài...', 'san-pham-a', true, true);
  ```
- Reload trang web → Thấy sản phẩm mới

### 3️⃣ **API Call (Coder) — POST từ bên ngoài**
- Endpoint: `POST /api/admin/products`
- Content-Type: `application/json`
- Body:
  ```json
  {
    "name": "Sản phẩm B",
    "price": 750000,
    "shortDescription": "Mô tả ngắn",
    "description": "Mô tả dài..."
  }
  ```
- Dùng Postman / curl / code để gọi

### 4️⃣ **Sidebar Chỉnh Sửa (Developer)**
- File: `src/main/resources/templates/products-list.html`
- Phần "**Sidebar**" (cột trái) — dễ tùy biến lọc/tìm kiếm
- File: `src/main/resources/static/css/products.css`
- Thay đổi style sidebar, chọn màu, layout

### 5️⃣ **Thêm Trang Mới (Advanced)**
- Tạo controller mới: `NewPageController.java`
- Tạo template: `src/main/resources/templates/new-page.html`
- Thêm CSS + JS riêng
- Đăng ký route trong nav header

---

## 📝 Sidebar — Để Dễ Chỉnh Sửa

**Tất cả sidebar nằm ở:**
- `src/main/resources/templates/products-list.html` → phần `<aside class="sidebar">`
- CSS: `src/main/resources/static/css/products.css` → `.sidebar-box`, `.sidebar`

**Cách chỉnh sửa:**
1. Đặt tiêu đề mới: sửa `<h3>Tìm kiếm</h3>` → `<h3>Bộ Lọc</h3>`
2. Thêm checkbox lọc: thêm `<label><input type="checkbox"> Danh mục 1</label>` vào `.sidebar-box`
3. Thay đổi màu: sửa `background:#fff` → `background:#e8f0f7`
4. Thêm banner quảng cáo: tạo `.sidebar-box.featured` mới với nội dung tùy ý

---

## 🔧 Tùy Biến Giao Diện

### Đổi màu chính (Primary) từ xanh sang đỏ
- Tìm `--primary:#1e6fb8` trong `src/main/resources/static/css/site.css`
- Thay thành: `--primary:#e74c3c` (đỏ)
- Tất cả nút, link, text sẽ đổi sang đỏ

### Thêm logo
- Đặt file logo vào `src/main/resources/static/images/logo.png`
- Mở `src/main/resources/static/index.html`
- Thay `<svg>...</svg>` → `<img src="/images/logo.png" alt="Logo">`

### Đổi font
- Tìm Google Fonts link ở header HTML
- Chọn font khác từ fonts.google.com
- Copy link mới, dán vào `<link href="..."`

---
