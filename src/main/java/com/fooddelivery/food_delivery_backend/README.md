# Food Delivery Backend API

A comprehensive RESTful API for a food delivery platform built with Spring Boot, similar to Swiggy/Zomato.

##  Features

### User Management
- **Multi-role authentication** (Customer, Restaurant Owner, Delivery Partner)
- JWT-based secure authentication
- Role-based access control

### Restaurant Management
- Restaurant CRUD operations
- Menu management with categories
- Public restaurant browsing
- Review and rating system

### Order Management
- Shopping cart functionality
- Order placement and tracking
- Multi-stage order lifecycle
- Coupon system with usage limits
- Multiple payment methods (COD, Online)

### Delivery System
- Delivery partner registration
- Order assignment to delivery partners
- Real-time order status updates

### Additional Features
- Address management
- Review and rating system with auto-calculated restaurant ratings
- Discount coupons (FLAT and PERCENTAGE)
- Usage limit tracking per user

---

## 🛠️ Tech Stack

**Backend Framework:**
- Java 21
- Spring Boot 4.0.3
- Spring Security
- Spring Data JPA

**Database:**
- PostgreSQL

**Libraries:**
- MapStruct (DTO mapping)
- Lombok (boilerplate reduction)
- JWT (authentication)
- Bean Validation

**Build Tool:**
- Maven

---

## 📋 Prerequisites

- Java 21 or higher
- PostgreSQL 14+
- Maven 3.8+

---

##  Installation & Setup

### 1. Clone the repository
```bash
git clone https://github.com/YOUR_USERNAME/food-delivery-backend.git
cd food-delivery-backend
```

### 2. Configure PostgreSQL Database

Create a database:
```sql
CREATE DATABASE food_delivery_db;
```

### 3. Update application.properties

Edit `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/food_delivery_db
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=YOUR_SECRET_KEY_HERE_MINIMUM_256_BITS
jwt.expiration=86400000
```

### 4. Build and Run
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080`

---

## 📚 API Documentation

### Authentication Endpoints

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "9876543210"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}

Response:
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "message": "Login successful"
}
```

#### Get Current User
```http
GET /api/auth/me
Authorization: Bearer {token}
```

---

### Restaurant Endpoints

#### Create Restaurant (Owner only)
```http
POST /api/restaurants
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "Tasty Bites",
  "email": "tasty@restaurant.com",
  "phone": "8888888888",
  "address": "123 Food Street",
  "city": "Hyderabad",
  "state": "Telangana",
  "pincode": "500001",
  "description": "Best food in town"
}
```

#### Get Restaurants by City (Public)
```http
GET /api/restaurants?cityName=Hyderabad
```

#### Get Restaurant Menu (Public)
```http
GET /api/restaurants/{restaurantId}/menu
```

---

### Cart Endpoints

#### Add to Cart
```http
POST /api/cart/items
Authorization: Bearer {token}
Content-Type: application/json

{
  "menuItemId": 1,
  "quantity": 2
}
```

#### Get Active Cart
```http
GET /api/cart
Authorization: Bearer {token}
```

#### Update Cart Item
```http
PUT /api/cart/items
Authorization: Bearer {token}
Content-Type: application/json

{
  "cartItemId": 1,
  "quantity": 3
}
```

#### Remove from Cart
```http
DELETE /api/cart/items/{cartItemId}
Authorization: Bearer {token}
```

#### Clear Cart
```http
DELETE /api/cart
Authorization: Bearer {token}
```

---

### Order Endpoints

#### Place Order
```http
POST /api/orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "deliveryAddressId": 1,
  "paymentMethod": "CASH_ON_DELIVERY",
  "couponCode": "WELCOME50"
}
```

#### Get My Orders
```http
GET /api/orders/my
Authorization: Bearer {token}
```

#### Update Order Status
```http
PATCH /api/orders/{orderId}/status
Authorization: Bearer {token}
Content-Type: application/json

{
  "newStatus": "CONFIRMED"
}
```

---

### Coupon Endpoints

#### Create Coupon (Admin/Owner)
```http
POST /api/coupons
Authorization: Bearer {token}
Content-Type: application/json

{
  "code": "WELCOME50",
  "description": "Welcome offer",
  "discountType": "FLAT",
  "discountValue": 50.00,
  "minOrderValue": 500.00,
  "validFrom": "2026-01-01",
  "validUntil": "2026-12-31",
  "isActive": true,
  "usageLimitPerUser": 1,
  "totalUsageLimit": 100
}
```

#### Get Active Coupons (Public)
```http
GET /api/coupons
```

#### Validate Coupon
```http
GET /api/coupons/validate?code=WELCOME50&orderAmount=750
```

---

### Review Endpoints

#### Create Review (After delivery)
```http
POST /api/reviews
Authorization: Bearer {token}
Content-Type: application/json

{
  "orderId": 1,
  "ratings": 5,
  "comment": "Amazing food!"
}
```

#### Get Restaurant Reviews (Public)
```http
GET /api/restaurants/{restaurantId}/reviews
```

---

##  User Roles & Permissions

### CUSTOMER
- Browse restaurants and menus
- Manage cart
- Place orders
- Write reviews
- Manage addresses

### RESTAURANT_OWNER
- Create and manage restaurant
- Manage menu items and categories
- View restaurant orders
- Update order status (CONFIRMED, PREPARING, READY)
- Assign delivery partners

### DELIVERY_PARTNER
- View assigned orders
- Update order status (PICKED_UP, OUT_FOR_DELIVERY, DELIVERED)

---

##  Database Schema

### Core Tables
- `users` - User accounts
- `addresses` - User delivery addresses
- `restaurants` - Restaurant details
- `categories` - Menu categories
- `menu_items` - Restaurant menu items
- `carts` - Active shopping carts
- `cart_items` - Items in cart
- `orders` - Customer orders
- `order_items` - Items in order
- `coupons` - Discount coupons
- `coupon_usage` - Coupon usage tracking
- `reviews` - Restaurant reviews
- `delivery_partners` - Delivery partner details

---

##  Order Status Flow
```
PLACED → CONFIRMED → PREPARING → READY → ASSIGNED → 
PICKED_UP → OUT_FOR_DELIVERY → DELIVERED

(CANCELLED - possible from PLACED or CONFIRMED)
```

---

##  Payment Flow

### Cash on Delivery (COD)
- Status: PENDING → PAID (when delivered)

### Online Payment
- Status: PAID (immediately after payment)

---

##  Testing

### Sample Test Flow

1. **Register as Customer**
2. **Browse Restaurants** (no auth needed)
3. **Add Items to Cart**
4. **Create Address**
5. **Place Order with Coupon**
6. **Register as Restaurant Owner**
7. **Update Order Status** (CONFIRMED → PREPARING → READY)
8. **Register as Delivery Partner**
9. **Assign Delivery Partner to Order**
10. **Update Order Status** (PICKED_UP → OUT_FOR_DELIVERY → DELIVERED)
11. **Create Review**

---

##  Future Enhancements

- [ ] Image upload for restaurants and menu items
- [ ] Real-time order tracking
- [ ] Email/SMS notifications
- [ ] Payment gateway integration (Razorpay/Stripe)
- [ ] Admin dashboard
- [ ] Advanced search and filters
- [ ] Real-time chat support
- [ ] Order history analytics

---

##  Author

**Sai Bhargav**
- GitHub: [@saibhargav18](https://github.com/saibhargav18)
- LinkedIn: [Sai Bhargav](https://linkedin.com/in/your-profile)
- Email: saibhargav092@gmail.com

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

##  Acknowledgments

- Spring Boot Documentation
- PostgreSQL Documentation
- MapStruct Documentation

---

##  Support

For support, email your.email@example.com or create an issue in the repository.