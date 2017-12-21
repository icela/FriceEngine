# FriceEngine

![image](https://avatars1.githubusercontent.com/u/21008243)

CI|status
:---|:---:
Travis CI|[![Build Status](https://travis-ci.org/icela/FriceEngine.svg?branch=master)](https://travis-ci.org/icela/FriceEngine)
AppVeyor|[![Build status](https://ci.appveyor.com/api/projects/status/75d7wx28u3tgtnat?svg=true)](https://ci.appveyor.com/project/ice1000/friceengine)
CircleCI|[![CircleCI](https://circleci.com/gh/icela/FriceEngine.svg?style=svg)](https://circleci.com/gh/icela/FriceEngine)
CodeShip|[![CodeShip](https://codeship.com/projects/a1d7bc60-0a30-0135-8b3c-6ed4d7e33e57/status?branch=master)](https://app.codeship.com/projects/214712)

[![](https://jitpack.io/v/icela/FriceEngine.svg)](https://jitpack.io/#icela/FriceEngine)
[![](https://jitpack.io/v/icela/FriceEngine/month.svg)](https://jitpack.io/#icela/FriceEngine) <br/>
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://icela.github.io)

An easy, light, native game engine running on JVM.

+ Why easy? <br/>
[A flappy bird game](https://github.com/icela/FriceDemo/tree/master/1.7.9/Demo7.java) uses just 56 lines of Java code only.<br/>
Written in Kotlin, also work on Java, JRuby, Groovy, Scala and other JVM languages.

+ Why light? <br/>
The release build jar is about 1.6mb (with a 1mb Kotlin runtime) only.

+ Why native? <br/>
This engine is completely platform-independent: no JNI linkage, no native methods.

**View [Document](https://icela.github.io/#getting-started) to learn how to use Frice Engine.**

## Screenshots

- [image link](https://coding.net/u/ice1000/p/Gifs/git/raw/master/frice/frice-01.gif)
- [image link](https://coding.net/u/ice1000/p/Gifs/git/raw/master/frice/frice-02.gif)
- [image link](https://coding.net/u/ice1000/p/Gifs/git/raw/master/frice/frice-03.gif)
- [image link](https://coding.net/u/ice1000/p/Gifs/git/raw/master/frice/frice-04.gif)
- [image link](https://coding.net/u/ice1000/p/Gifs/git/raw/master/frice/frice-05.gif)

## Build

Follow the instruction from [JitPack](https://jitpack.io/#icela/FriceEngine).

Alternatively, you can download a jar from the [release page](https://github.com/icela/FriceEngine/releases).

## Features

This engine is based on Java's built-in GUI framework, both Swing and JavaFX.
"Platform" here refers to Swing/JavaFX.

### Platform dependent

These features are differently implemented in Swing/JavaFX.  
Since this project is still in progress, the unsupported features will soon be available.

Feature|Swing|JavaFX
:---|:---:|:---:
Life cycle|√|√
Dialogs|√|√
Cursor overriding|√|√
Screenshot|√|√
Buttons|√|√
Full screen|√|√
Always on top|√|√
Text font/size|√|√
Multi-instances|√|×
Resizability|√|√
Key listener|√|√
Text measuring|√|√

### Platform independent

- Animations (moving, scaling, rotating, accelerating, chasing, approaching, etc.)
- Audio playing (\*.wav, \*.mp3) by JavaSound/JavaFX Media
- Clock system, timers
- Delayed event manager
- Automatic garbage collection (optional, objects far from the screen will be removed)
- Resource manager (caching IO)
- File/Image/Color/URL utils
- Game objects
- Frame animations
- Key-value database
- Particle effects
- Attaching game objects
- Collision detection with collision box

## DSL
See [DSL for FriceEngine](https://github.com/icela/FriceEngine-DSL)

## Contributions
Feel free to open issue for feature request, bug reports, etc. <br/>
This is generally a personal project, so please do give your genuine suggestions.

This project is using Issue-Driven-Development. <br/>
Issues are well organized and used to managed tasks.

## Demos
See [FriceDemo](https://github.com/icela/FriceDemo) <br/>
And see [TouhouDemo](https://github.com/ice1000/TouhouDemo)
