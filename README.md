# Loonly
[![CircleCI](https://circleci.com/gh/fakhrymubarak/Loonly/tree/master.svg?style=shield)](https://circleci.com/gh/fakhrymubarak/Loonly/tree/master)\
Course Submission for "Menjadi Android Developer Expert Class" on Dicoding Academy

## Show Case
<img src="https://cdn.discordapp.com/attachments/791866991995650081/911522716618727444/Screenshot_2021-11-20-14-43-37-31_9db3180106e11eeee0eddbedba52620b.png" height=450> <img src="https://cdn.discordapp.com/attachments/791866991995650081/911522717050732544/Screenshot_2021-11-20-14-43-53-14_9db3180106e11eeee0eddbedba52620b.png" height=450> <img src="https://cdn.discordapp.com/attachments/791866991995650081/911522717927370802/Screenshot_2021-11-20-14-44-45-14_9db3180106e11eeee0eddbedba52620b.png" height=450> <img src="https://cdn.discordapp.com/attachments/791866991995650081/911522717554081812/Screenshot_2021-11-20-14-44-21-24_9db3180106e11eeee0eddbedba52620b.png" height=450>

## Features
- Show Trending Movies
- Show Top Rated Movies All Times
- Show Popular Movies
- Show Details of The Movies
- Search Movies
- Add Movies to Watchlist
- Dark Mode

## Tech Stack
- Minimum SDK level 23
- Kotlin based, Coroutines + Flow for asynchronous
- Dagger-Hilt for dependency injection
- Android Jetpack
  - Lifecycle - dispose of observing data when lifecycle state changes
  - ViewModel - UI related data holder, lifecycle aware
  - Room Persistence - construct a database using the abstract layer
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Bindables - Android ViewBinding kit for notifying data changes to UI layers
  - Repository pattern
- Retrofit2 & OkHttp3 - construct the REST APIs and paging network data

## MAD Score

## Architechture

## Open API
This app using [TMDB (The Movie Database) API](https://developers.themoviedb.org/3) for constructing RESTful API.
