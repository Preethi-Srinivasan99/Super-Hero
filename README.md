# Super Hero Application
Get all SuperHeroes and Villains data from all universes.

# Introduction
Over the years we have encountered many superheroes and villans from comic books and films. Most of them have become our favourites but  we
have little information about them and how the came to be what they are. [Heroes] is an Android app consuming [Superheros REST API]
(https://superheroapi.com/) to fetch all SuperHeroes and Villians data from all universes.

## Example Screenshots
<p align="center">
<img src="/SuperHero_SS7.png" alt="Sample Screenshot" width="80%" height="80%">
</p>

| | | |
|:-------------------------:|:-------------------------:|:-------------------------:|
|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="/SuperHero_SS1.jpg">  Splash|  <img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="/SuperHero_SS2.jpg"> DashBoard|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="/SuperHero_SS3.jpg"> Searching Activity|
|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="/SuperHero_SS4.jpg"> Searching Hero Name |  <img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="/SuperHero_SS5.jpg"> Super Hero Details|<img width="1604" alt="screen shot 2017-08-07 at 12 18 15 pm" src="/SuperHero_SS6.jpg"> Filter Activity|

## Getting Started / Running The Project 

This project uses the latest Gradle version. Gradle will update if a new version gets released.  

To run this app, you will need to download [Android Studio](https://developer.android.com/studio). 

You will need to create a new emulator otherwise can use your own android phone [Use Your Android Phone](https://javatutorial.net/connect-android-device-android-studio). 

From there the gradle will build the project and after completed, hit the `run` green button on top of the screen. 

The app will build and run starting from the splash screen. 


Used libraries : 
- [SuperHero API](https://superheroapi.com/)
- [Volley](https://github.com/google/volley) library to make sending HTTP requests really easy 
- [Gson](https://github.com/google/gson) library for handeling Json objects
- [Glide](https://guides.codepath.com/android/Displaying-Images-with-the-Glide-Library) library provides animated GIF support and handles image loading/caching
- [Cardview](https://developer.android.com/guide/topics/ui/layout/cardview) library used to give a rich look to the UI design
- [RecyclerView](https://developer.android.com/guide/topics/ui/layout/recyclerview) library used to display a collection of data

# Development Environment
* The app is written in Java and uses the Gradle build system.
* Min API level == 21  
* Version 1.0
* Android Studio >= 4.3.1
