# CountryListing
## An Android app that fetches a list of countries from an open API and displays it in a recycler view

Application uses MVVM architecture and below is the structire

![coroutines_architecture](https://user-images.githubusercontent.com/63911184/112762460-6a7d9b00-901d-11eb-8499-7c5ff27e29a3.png)

We fetch list of counries from an open API and cache it in the File System
When there is no network we return the cached data
If there is no data available in the cache we show user an error.
The App uses RXJava, retrofit along with dagger Android as tech stack.
