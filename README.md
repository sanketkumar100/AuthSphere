# 🔐 AuthSphere

AuthSphere is a full-stack authentication system built with **Spring Boot** and **React**, providing secure user authentication with **JWT**, **email OTP verification**, and **role-based access control (RBAC)**.

## 🚀 Features

* ✅ User Registration and Login
* ✅ JWT-based Authentication and Authorization
* ✅ Email OTP Verification
* ✅ Role-Based Access Control (RBAC)
* ✅ Password Reset Functionality
* ✅ Secure REST APIs
* ✅ React Frontend with Responsive UI
* ✅ Spring Security Integration
* ✅ Profile Management
* ✅ API Testing with Postman

---

## 🛠 Tech Stack

### Backend

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* Maven
* MySQL
* JWT
* JavaMail Sender

### Frontend

* React.js
* Vite
* Axios
* CSS

---

## 📂 Project Structure

```text
AuthSphere
│
├── Backend
│   └── authify
│       ├── src
│       ├── pom.xml
│       └── ...
│
├── Frontend
│   └── client
│       ├── src
│       ├── package.json
│       └── ...
│
└── README.md
```

---

## ⚙️ Setup Instructions

### Clone Repository

```bash
git clone https://github.com/sanketkumar100/AuthSphere.git
cd AuthSphere
```

---

### Backend Setup

```bash
cd Backend/authify
```

Install dependencies and run:

```bash
mvn spring-boot:run
```

Backend runs on:

```
http://localhost:8080
```

---

### Frontend Setup

```bash
cd Frontend/client
npm install
npm run dev
```

Frontend runs on:

```
http://localhost:5173
```

---

## 🔑 Environment Variables

Configure the following in `application.properties`:

```properties
spring.mail.username=YOUR_EMAIL
spring.mail.password=YOUR_PASSWORD
jwt.secret=YOUR_SECRET_KEY
```

**Do not commit credentials or API keys to GitHub.**

---

## API Endpoints

| Method | Endpoint           | Description             |
| ------ | ------------------ | ----------------------- |
| POST   | `/register`        | Register User           |
| POST   | `/login`           | Authenticate User       |
| POST   | `/verify-account`  | Verify OTP              |
| POST   | `/forgot-password` | Send Password Reset OTP |
| POST   | `/reset-password`  | Reset Password          |
| GET    | `/profile`         | Fetch User Profile      |

---

## 📸 Screenshots

---

## Future Enhancements

* Google OAuth Login
* Refresh Tokens
* Docker Deployment
* Redis Cache
* Account Lockout Mechanism
* Email Templates
* Two-Factor Authentication (2FA)

---

## 👨‍💻 Author

**Sanket Kumar**

* LinkedIn: https://www.linkedin.com/in/sanket-k-3315aa22a/
* GitHub: https://github.com/sanketkumar100
