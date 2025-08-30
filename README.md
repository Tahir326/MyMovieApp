# 🎬 MyMovieApp

A modern Android movie browsing app built with **Kotlin**, **Jetpack Compose**, **MVVM + Clean Architecture**, and **Room** for persistence. The app demonstrates robust state handling, local persistence, and clean architecture practices.

---

## 📖 Table of Contents

- [Architecture Overview](#architecture-overview)
- [Features](#features)
- [Screenshots](#screenshots)
- [Live Demo](#live-demo)
- [Instructions to Run](#instructions-to-run)
- [Possible Improvements](#possible-improvements)

---

## 🏗 Architecture Overview

This project follows **Clean Architecture** combined with **MVVM**:

### Layers

1. **Domain Layer**
   - Contains pure business logic.

2. **Data Layer**
   - Handles data persistence and remote data fetching.

3. **Presentation Layer (UI)**
   - Jetpack Compose screens + components.
   - **ViewModels** manage UI state using StateFlow.
   - Screens:
     - **HomeScreen:** movie list, search, sorting, loading/error states
     - **DetailsScreen:** movie details and favorite toggle
     - **FavoritesScreen:** favorite movies persisted locally

4. **Dependency Injection**
   - Hilt modules provide repositories, database, API.

5. **Utilities**

---

## ✨ Features

- Browse movies (title, poster, year, rating)
- Search by movie title
- Loading and error state handling
- Movie details view with favorite toggle
- Favorites screen with persistent storage (Room)
- Mock API with random delay.

---

## 📸 Screenshots

<div align="center">

| | | |
|:---:|:---:|:---:|
| **Details Screen**<br/><img src="https://drive.google.com/uc?id=1zvnP8wNPRDrRQ5VDt9LROXcrBi9adr42" width="250" height="500" alt="Home Screen"/> | **Favorites Screen**<br/><img src="https://drive.google.com/uc?id=10Lt6EdgiEO_kD1UiiT22--Fe2GqmfTok" width="250" height="500" alt="Movie List"/> | **Home Screen**<br/><img src="https://drive.google.com/uc?id=1rlI9C5VDkmIErgtxCHihhradP_QnBa7D" width="250" height="500" alt="Movie Details"/> |
| **Home Screen**<br/><img src="https://drive.google.com/uc?id=1SygmwrtsMcZ8r0V39xsRSPmlYRpkTMeG" width="250" height="500" alt="Favorites Screen"/> | **Search Screen**<br/><img src="https://drive.google.com/uc?id=1dZK-Ua8tBBOV2jPOlflUgCiiQcvCV8L_" width="250" height="500" alt="Search Functionality"/> | **Search Screen**<br/><img src="https://drive.google.com/uc?id=1Ds8ZmlW3igJcZd4-I7yI7Biq2Wdc7MZo" width="250" height="500" alt="App Overview"/> |

</div>


## 🎥 Live Demo

[Live Demo Link](https://your-demo-link.com)

---

## 🚀 Instructions to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/Tahir326/MyMovieApp.git
   cd MyMovieApp

2. **Open in Android Studio**
   - Launch Android Studio
   - Click **"Open"** or **"Open an existing project"**
   - Navigate to and select the cloned `MyMovieApp` folder
   - Click **"OK"**

3. **Sync Project**
   - Android Studio will automatically start syncing the project
   - Wait for Gradle sync to complete (this may take a few minutes on first run)
   - Resolve any SDK or dependency issues if prompted

4. **Prepare Device/Emulator**
   - **Physical Device:** Enable Developer Options and USB Debugging
   - **Emulator:** Create an AVD with API level 21+ through AVD Manager

5. **Build & Run**
   - Click the **Run** button (green play icon) or press **Shift + F10**
   - Select your target device/emulator
   - Wait for the app to build and install (first build may take several minutes)

6. **App Usage**
   - Browse movies on the Home Screen
   - Use search and sort functionality
   - Tap any movie for details and toggle favorite status
   - Navigate to Favorites screen to view saved movies

### Troubleshooting
- If build fails, try **Build → Clean Project** then **Build → Rebuild Project**
- Ensure all required SDK components are installed via SDK Manager
- Check that your device/emulator meets minimum API requirements

---

## 🛠 Possible Improvements

### Unit & UI Testing
- Add unit tests for ViewModels and repository
- Add instrumented UI tests for Compose screens

### Network Layer
- Replace Mock API with a real remote API (Retrofit + REST)
- Add caching strategies with Room + network sync

### Pagination
- Implement paging for larger movie datasets

### Improved UI
- Add animations, swipe-to-refresh, and error retry actions

### Search Enhancements
- Implement debounce search to optimize performance

---
