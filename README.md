# Real-Time Weather Monitoring System 🌦️

A Client-Server application built with Java that fetches real-time weather data and simulates monitoring alerts. This project demonstrates core networking concepts using Sockets and REST API integration.

## 🚀 Features
* **Client-Server Architecture:** Uses Java Sockets for bi-directional communication.
* **Real-Time Data:** Fetches live weather data from the OpenWeatherMap API.
* **JSON Parsing:** Manually parses JSON responses to extract Temperature, Humidity, and Conditions.

## 🛠️ Tech Stack
* **Language:** Java (Core)
* **Networking:** `java.net.Socket`, `java.net.ServerSocket`
* **API:** OpenWeatherMap

## ⚙️ How to Run
1. **Clone the repo:** `git clone https://github.com/Sivasankari865/Weather-Monitoring-System.git`
2. **Setup:** Open `WeatherServer.java` and add your OpenWeatherMap API key.
3. **Compile:** `javac *.java`
4. **Run Server:** `java WeatherServer`
5. **Run Client:** `java WeatherClient`
