# 🔐 AuthSphere

A full-stack authentication system built using **Spring Boot**, **Spring Security**, **JWT**, **React.js**, and **MySQL**. AuthSphere provides a secure and modern authentication flow with email verification, password reset via OTP, JWT-based authentication using HTTP-only cookies, and a responsive React frontend.

## 🌐 Live Demo

🚀 **Live Application:** https://auth-sphere-chi.vercel.app/

---

## 📸 Features

- ✅ User Registration
- ✅ Secure Login
- ✅ JWT Authentication using HTTP-only Cookies
- ✅ Persistent Login (Session survives page refresh)
- ✅ Logout
- ✅ Email Verification via OTP
- ✅ Forgot Password
- ✅ Password Reset using OTP
- ✅ User Profile
- ✅ Responsive UI
- ✅ Toast Notifications
- ✅ HTML Email Templates using Thymeleaf

---

## 🛠 Tech Stack

### Frontend

- React.js (Vite)
- React Router DOM
- Axios
- Bootstrap 5
- Bootstrap Icons
- React Toastify

### Backend

- Spring Boot
- Spring Security
- Spring Data JPA
- Spring Mail
- Thymeleaf
- JWT (JSON Web Token)
- Maven

### Database

- MySQL

---

## 📂 Project Structure

```
AuthSphere
│
├── authify-client/        # React Frontend
│
├── authify-server/        # Spring Boot Backend
│
└── README.md
```

---

## 🔑 Authentication Flow

### Registration

- User creates an account.
- Password is encrypted using BCrypt.
- Welcome email is sent.
- User can log in immediately.

---

### Login

- User enters email and password.
- Credentials are authenticated.
- JWT token is generated.
- JWT is stored inside an HTTP-only cookie.
- User is redirected to the Home page.

---

### Email Verification

- User requests an OTP.
- Backend generates a 6-digit OTP.
- OTP is sent via HTML email.
- User enters OTP.
- Account gets verified.

---

### Forgot Password

- User enters registered email.
- Password reset OTP is sent.
- User verifies OTP.
- User sets a new password.
- Password is updated securely.

---

## 🔒 Security Features

- BCrypt Password Encryption
- JWT Authentication
- HTTP-only Cookies
- Stateless Authentication
- Spring Security
- Route Protection
- OTP Expiration
- Secure Password Reset Flow

---

## 📧 Email Templates

The project uses **Thymeleaf** to generate beautiful HTML emails for:

- Email Verification
- Password Reset

---

## API Endpoints

| Method | Endpoint | Description |
|----------|--------------------------|----------------------|
| POST | /register | Register User |
| POST | /login | Login |
| POST | /logout | Logout |
| GET | /profile | Get User Profile |
| GET | /is-authenticated | Check Authentication |
| POST | /send-otp | Send Verification OTP |
| POST | /verify-otp | Verify Email OTP |
| POST | /send-reset-otp | Send Password Reset OTP |
| POST | /reset-password | Reset Password |

---

## Future Improvements

- Google OAuth Login
- GitHub OAuth Login
- Remember Me
- User Profile Editing
- Account Deletion
- Refresh Token Support
- Role-Based Authorization (Admin/User)

---

## Learning Outcomes

This project helped me understand:

- Spring Security
- JWT Authentication
- HTTP-only Cookies
- React Context API
- React Router
- Axios API Integration
- Email Verification
- Password Reset Flow
- Thymeleaf Email Templates
- Authentication Persistence
- Full-Stack Application Development

---

## 👨‍💻 Author

**Sanket Kumar**

- GitHub: https://github.com/sanketkumar100
- LinkedIn: https://www.linkedin.com/in/sanket-kumar-java/

---

⭐ If you found this project helpful, consider giving it a **Star** on GitHub!
