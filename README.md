# Zemoga Posts

Project proposed by Zemoga as a part of the admission process.

## The aplication

This application fetches a list of posts and their details from [{JsonPlaceholder}](https://jsonplaceholder.typicode.com/) using retrofit and Rx. 

> There are no special configurations to be done to run this project

In terms of the implementation, the option here was to keep it as simple as possible, not adding an overwhelming architecture and lots of libraries. 
There are a project of mine that I have decided to explore a bit more and added a multi-modular approach with clean architecture, [here](https://github.com/lucashenriqueos/tmdb-app).

#### Architecture
 - Model-View-ViewModel (MVVM) Pattern;
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata), for its lifecycle awareness;
 - [RxJava](https://github.com/ReactiveX/RxJava);
 - [Koin](https://insert-koin.io/) for depedency injection;
 - [Room](https://developer.android.com/jetpack/androidx/releases/room) for caching some informations;

#### Tests
  - JUnit for testing application;
  - [Mockk](https://mockk.io/) for mocking purposes;

