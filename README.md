# fetch-rewards

### Problem Scenario
- Display this list of items to the user based on the following requirements:
    - Display all the items grouped by "listId"
    - Sort the results first by "listId" then by "name" when displaying.
    - Filter out any items where "name" is blank or null.
    - The final result should be displayed to the user in an easy-to-read list.

### Runtime Screenshots

| Screen 1      | Screen 2      |
|------------|-------------|
<img src="https://github.com/kapin-k/fetch-rewards-android/blob/master/assets/Screenshot_Splash_FetchRewards-CodingExercise.jpg" width="389" height="800" /> | <img src="https://github.com/kapin-k/fetch-rewards-android/blob/master/assets/Screenshot_Main_FetchRewards-CodingExercise.jpg" width="389" height="800" />

### Development Environment
-------------

- Operating System: Windows 10 10.0
- Android Studio 4.1
- Programming Language: Kotlin
- Build #AI-201.8743.12.41.6858069, built on September 23, 2020
- Runtime version: 1.8.0_242-release-1644-b01 amd64
- VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o

### Libraries Used
-------------

Libraries
- 1. Kotlin Coroutines
    - Link - https://github.com/Kotlin/kotlinx.coroutines
- 2. GSON: Json Parser
    - Link - https://github.com/google/gson
- 3. Recycler View
    - Link - https://developer.android.com/jetpack/androidx/releases/recyclerview
- 5. Retrofit
    - Link - https://square.github.io/retrofit/

### Network and API
-------------

- URL for API: https://fetch-hiring.s3.amazonaws.com/*******
- Check project directory “/network” for more details regarding the API and Repository
- Network requests and call-backs are handled using Retrofit library.