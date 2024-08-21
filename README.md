# FDJ Project

## Overview
The FDJ Project is an Android application that provides information about various sports leagues and teams.
The app fetches data from an external API and displays it to the user in a user-friendly manner.

## Features
- Retrieve and display a list of all sports leagues.
- Retrieve and display teams by league.
- Handle network errors.

## Libraries Used
- **Kotlin**: Programming language used for development.
- **Hilt**: Dependency injection framework.
    - `com.google.dagger:hilt-android:2.44`
    - `com.google.dagger:hilt-compiler:2.44`
- **Retrofit**: HTTP client for API calls.
    - `com.squareup.retrofit2:retrofit:2.9.0`
    - `com.squareup.retrofit2:converter-gson:2.9.0`
- **Coroutines**: For asynchronous programming.
    - `org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2`
    - `org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2`
- **Mockk**: Mocking library for unit tests.
    - `io.mockk:mockk:1.12.0`
- **JUnit**: Testing framework.
    - `junit:junit:4.13.2`
- **Kotlinx Serialization**: For JSON serialization.
    - `org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0`
- **Jetpack Compose**: Modern toolkit for building native Android UI.
    - `androidx.compose.ui:ui:1.0.0`
    - `androidx.compose.material:material:1.0.0`
    - `androidx.compose.ui:ui-tooling-preview:1.0.0`
- **Glide Compose**: Image loading library for Jetpack Compose.
    - `com.github.bumptech.glide:compose:1.0.0-alpha.1`

## Setup Instructions
1. **Clone the repository**:
   ```sh
   git clone git@github.com:PritDev02/FDJProject.git
   cd FDJProject