

# ¡Aliento App!

|      |      | 
| :--: | :--: |
|  <a href='https://apps.apple.com/mx/app/aliento-de-vida-app/id6451156283?l=en-GB'><img alt='Download on the App Store' src='https://developer.apple.com/assets/elements/badges/download-on-the-app-store.svg' width="280" height="125"/></a>  |  <a href='https://play.google.com/store/apps/details?id=com.alientodevida.app'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="323" height="125"/></a>  |

Aliento App is an Android and IOS native app used to keep the church attendants connected with what's going on with their local church.

It is an open source project built with the aim to be useful for a non profit organization and to help developers practice and learn the latest good practices and technologies.

|    |    |    | 
| -- | -- | -- |
![1](https://i.imgur.com/asYuzUM.png) | ![2](https://i.imgur.com/ZkaeNEO.png)|![3](https://i.imgur.com/RWixtSC.png)
|![4](https://i.imgur.com/hGUdHhV.png)|![5](https://i.imgur.com/e0C9Koz.png) | ![6](https://i.imgur.com/mYilAbT.png) |

## Table of Contents

1. [Architecture](#architecture)
2. [Modularization](#modularization)
3. [Code Layers](#code-layers)
4. [Design System](#design-system)
5. [Run](#run)
6. [Deploy](#deploy)
7. [Contributors](#contributors)

## Architecture

 - This app is written following the [Clean Architecture](https://tech.olx.com/clean-architecture-and-mvvm-on-ios-c9d167d9f5b3) standard and the [Android Architecture guidelines](https://developer.android.com/topic/modularization).  
 - It uses [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for Dependency Injections and MVVM with Compose as Architecture Design Pattern.  
 - The app also contains dark and light theme using the [Material Design 2](https://m3.material.io/) Colors for the Design System.

## Modularization

This app uses clean architecture and the Android architecture guidelines. It is highly modularized in many gradle modules so compile times are short and decoupling is high.

![enter image description here](https://miro.medium.com/v2/resize:fit:1400/1*02Ink_nKAVnzLS8NA3rm_A.png)

The app contains the following gradle modules:

| Module | Description |
|--|--|
| **:app** | This module is the Android App modules which contains all the feature modules and is the entry point for the whole application. |
| **:core** | Core Modules are modules shared by other modules. They contain code reused by other feature modules that needs to be shared among them. |
| **:core:desygnsystem** | This module contains the shared ui components used throughout the app. |
| **:core:analytics** | This module contains the shared analytics classes used by other modules. |
| **:core:data** | This module contains the shared code of the data layer. |
| **:core:domain** | This module contains the shared code of the domain layer. |
| **:core:presentation** | This module contains the shared code of the presentation layer. |
| **:core:common** | This module contains shared common code used by other modules. |
| **:feature** | This modules contain all the feature modules used in the whole app. |
| **:feature:foobar** | Feature modules... |
| **:benchmark** | Module used to generate an android baseline profile for faster launches. |
| **:buildSrc** | Module for Kotlin DSL dependencies. |

## Code Layers

![enter image description here](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

Additionally each module contains 4 packages to separate code in the 3 layers of clean architecture.

DI: This package performs the Dependency Injection of the module.

| Data | Domain | Presentation |
|--|--|--|
| This layer contains all dependencies and code related to obtaining the data from the Backend. It also contains the repositories Implementations. | This layer contains the main models used in the app as well as the repositories protocols. Use cases would also be here containing business logic. | This layer contains all the views and UI things. This layer uses the repositories through the domain layer using Dependency Injection. |

## Design-System
The app uses Material Design 2 colors as it's design system. Using a light and a dark color palette.

![enter image description here](https://lh3.googleusercontent.com/k6WO1fd7T40A9JvSVfHqs0CPLFyTEDCecsVGxEDhOaTP0wUTPYOVVkxt60hKxBprgNoMqs8OyKqtlaQ4tDBtQJs-fTcZrpZEjxhUVQ=w1064-v0)

## Run

In order to run the code you need to create a [Firebase](https://firebase.google.com/) Project and add your own **GoogleServices.json** file to the project.

Then create a [youtube api key](https://developers.google.com/youtube/v3/getting-started#before-you-start) and a [spotify basic access token](https://developer.spotify.com/documentation/web-api/tutorials/getting-started)  so you can use their APIs.

Put the keys in a **local.properties** file on the project's root path:

    youtubeApiKey="key"  
    spotifyBasicToken="Basic key"

The project also uses a **keystore.properties** file to sign the release build. If you need to compile a release build add the file locally on the root of the project.

    storeFile=/path/to/key.jks  
    storePassword=password  
    keyAlias=keyAlias  
    keyPassword=password

Also configure fastlane to use your Firebase and Play Store Console accounts.

## Deploy

The project uses fastlane to deploy to firebase distribution for beta testing and also uses fastlane to send builds to the play store.

 - bundle exec fastlane beta -> send to firebase distribution
   
 - bundle exec fastlane deploy -> send to play store

## Contributors:

<a href="https://github.com/Aliento-de-Vida/aliento-app-android/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=Aliento-de-Vida/aliento-app-android" />
</a>

Made with [contrib.rocks](https://contrib.rocks).
