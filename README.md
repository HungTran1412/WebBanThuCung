# Monito Shop API

## Giới Thiệu
Đây là API backend cho website bán thú cưng, được xây dựng bằng Spring Boot. API cung cấp các chức năng quản lý thú cưng, danh mục, đơn hàng, và khách hàng.

## Công Nghệ Sử Dụng
- **Spring Boot** (Spring MVC, Spring Data JPA)
- **Hibernate** (ORM)
- **SQL Server** (Database)
- **Lombok** (Giảm boilerplate code)

## Cấu Trúc Dự Án
```
WebBanThuCung/
│── src/
│   ├── main/
│   │   ├── java/com/example/petshop/
│   │   │   ├── controller/
│   │   │   ├── entity/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   ├── dto/
│   │   ├── resources/
│   │   │   ├── application.yaml
│── pom.xml
│── README.md
```

## Cài Đặt
### 1. Clone repo
```sh
git clone https://github.com/HungTran1412/WebBanThuCung.git
cd WebBanThuCung
```

### 2. Cấu hình database
Chỉnh sửa `application.yaml` để kết nối với SQL Server:
```properties
url: jdbc:sqlserver://localhost:1433;databaseName=WebBanThuCung;encrypt=false
username: sa
password: yourpassword
driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
```

### 3. Chạy ứng dụng
```sh
mvn spring-boot:run
```
Ứng dụng sẽ chạy trên `http://localhost:8080`

## Các API Chính
| Chức năng          | HTTP Method | Endpoint             | Yêu cầu |
|--------------------|------------|----------------------|---------|
| Lấy danh sách thú cưng | GET        | `/api/pets`          | Không |
| Thêm thú cưng      | POST       | `/api/pets`          | Body JSON |
| Cập nhật thú cưng  | PUT        | `/api/pets/{id}`     | Body JSON |
| Xóa thú cưng       | DELETE     | `/api/pets/{id}`     | Không |
| Đăng nhập         | POST       | `/api/auth/login`    | Body JSON |

## Liên Hệ
Nếu có thắc mắc hoặc cần hỗ trợ, vui lòng liên hệ [hunghung2k4123@gmail.com].

---

*Created by tnmhung*

