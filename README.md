# 💹 neo-trade-bot

A trading bot that integrates with **Kotak Neo APIs** for automated trading operations.

---

## 📖 Project Description

**neo-trade-bot** is designed to simplify and automate trading by securely authenticating, handling TOTP-based login, and managing trades via Kotak Securities' Neo platform. This bot is flexible, easily extendable, and production-friendly.

---

## 🧑‍💻 Tech Stack

- 💻 Java  
- 🗄️ SQL  
- 🐳 Docker  
- 🌱 Spring Boot  
- 🐧 Linux  
- ☁️ AWS  

---

## ⚙️ How to Run

1. **Register for TOTP:**
   - Visit [Kotak Neo](https://neo.kotaksecurities.com) and register for TOTP (Two-Factor Authentication).

2. **Set Up Configuration:**
   - Open `application.properties` or your environment configuration.
   - Add your credentials:
     ```
     CLIENT_KEY=your_client_key
     CLIENT_SECRET=your_client_secret
     ```

3. **Build and Run the Application:**
   ```bash
   mvn spring-boot: run
