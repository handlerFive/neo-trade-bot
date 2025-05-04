# 💹 neo-trade-bot

**A powerful trading bot integrating with Kotak Neo APIs for automated trading.**

---

## 📖 Project Description

Neo-trade-bot is a work-in-progress open-source trading automation tool built for seamless integration with Kotak Securities' Neo trading platform. It automates secure login with TOTP, order placement, and market data fetching, aiming to streamline trading activities in a robust and scalable way.

Whether you're an algo trader or a developer exploring fintech, this project offers a solid foundation to build, extend, and experiment with trading strategies.

---

## 🧑‍💻 Tech Stack

- 💻 **Java** — Core language
- 🌱 **Spring Boot** — Backend framework
- 🗄️ **SQL** — Data persistence
- 🐳 **Docker** — Containerization
- 🐧 **Linux** — Target environment
- ☁️ **AWS** — Cloud deployment-ready

---

## ⚙️ Getting Started

### 1. Register for TOTP
Visit [Kotak Neo](https://neo.kotaksecurities.com) and enable **Two-Factor Authentication (TOTP)**.

### 2. Configure Your Environment

Update the `application.properties` or set environment variables:

```properties
CLIENT_KEY=your_client_key
CLIENT_SECRET=your_client_secret
TOTP_SECRET=your_totp_secret
```

### 3. Build and Run

```bash
mvn clean install
mvn spring-boot: run
```

---

## 🚧 Project Status

> **Status:** 🚧 *Active Development*  
We're actively building and testing core components like:
- ✅ Secure login with TOTP  
- ✅ Order placement  
- 🔄 Real-time quotes  
- 🧪 Strategy backtesting (Upcoming)  
- 📈 Advanced analytics (Upcoming)

---

## 🤝 Contributing

We welcome contributors! Here's how you can get started:

1. Fork this repo
2. Clone your fork  
   ```bash
   git clone https://github.com/your-username/neo-trade-bot.git
   ```
3. Create a new branch  
   ```bash
   git checkout -b feature/your-feature-name
   ```
4. Commit and push  
   ```bash
   git commit -m "Add your feature"
   git push origin feature/your-feature-name
   ```
5. Submit a pull request 🎉

### 🛠️ Areas to Contribute
- 🧠 Strategy development
- 🔐 Security enhancements
- 🧪 Unit and integration tests
- 📘 Documentation
- 🧩 API integration improvements

---

## 🗣️ Community & Support

Do you have questions, suggestions, or ideas? Open an [issue](https://github.com/handlerFive/neo-trade-bot/issues) or start a discussion.
