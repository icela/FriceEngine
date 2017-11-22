# FriceEngine

![image](https://avatars1.githubusercontent.com/u/21008243)

CI|status
:---|:---:
Travis CI|[![Build Status](https://travis-ci.org/icela/FriceEngine.svg?branch=master)](https://travis-ci.org/icela/FriceEngine)
AppVeyor|[![Build status](https://ci.appveyor.com/api/projects/status/75d7wx28u3tgtnat?svg=true)](https://ci.appveyor.com/project/ice1000/friceengine)
CircleCI|[![CircleCI](https://circleci.com/gh/icela/FriceEngine.svg?style=svg)](https://circleci.com/gh/icela/FriceEngine)
CodeShip|[![CodeShip](https://codeship.com/projects/a1d7bc60-0a30-0135-8b3c-6ed4d7e33e57/status?branch=master)](https://app.codeship.com/projects/214712)

[![](https://jitpack.io/v/icela/FriceEngine.svg)](https://jitpack.io/#icela/FriceEngine)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

An easy, light, native game engine running on JVM.<br/>
View [Document](https://icela.github.io/#getting-started) to learn more about Frice Engine.

The doc is poorly maintained and deprecated. I'm deciding to make some tutorials soon.

## Why easy?
[A flappy bird game](https://github.com/icela/FriceDemo/tree/master/demo/Demo7.java) uses just 65 lines of code only.<br/>
Written in Kotlin, also work on Java, JRuby, Groovy and Scala.

## Why light?
The release build jar is about 300kb (with a 800kb Kotlin runtime) only.<br/>

## Why native?
This engine is completely platform-independent: no JNI linkage, no native methods, everything is written in pure Kotlin.<br/>

# Build

Add code below to your gradle script:

```groovy
allprojects {
  repositories {
    /// ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  compile 'com.github.icela:FriceEngine:v1.5.0'
}
```

# Usage

## Supported

### Languages
- [X] Kotlin
- [X] Java
- [X] Groovy
- [X] Scala

### Progress
- [X] Game Objects (from image or shape, image from file or web)
- [X] Life cycle
- [X] Collision detecting
- [X] Audio playing (\*.wav, \*.mp3 only)
- [X] Dialogs
- [X] Cursor overriding
- [X] Clock system
- [X] Animations (frames, moving, scaling, accelerations, etc.)
- [X] A simple key-value database
- [X] Screen cut
- [X] Buttons
- [X] Particle effects
- [X] Easy time controlling
- [X] Language extensions (for Kotlin only)
- [X] Smart auto garbage collection (you can close it (objects far from the screen will be removed))
- [X] Resource manager(when you read files/urls, the result will be cached)
- [X] File/URL utils
- [X] A BoolArray implemented in bitwise operation, each boolean value will only take 1 __bit__ space (instead of 1 __byte__ which is default on JVM).

## DSL
see [DSL for FriceEngine](https://github.com/icela/FriceEngine-DSL)

## Basics
FriceEngine is based on the life cycle mode.<br/>
To build games based on frice engine, follow these steps:

0. Import the jar in the latest release to your project, or add dependency in your `build.gradle` file.
0. Create a class extends Game in org.frice.game package.
0. Override the life cycle methods(`onInit`, `onExit`, `onLastInit`, `onRefresh`, `onFocus`, `onLoseFocus`), just understand them by name.
0. If you want to draw something additionally, override `customDraw`. This method will be invoked every time after all game objects drawn.
0. Write `launch(YourGameClass.class)` in the main function.
0. Feel free to open issue for feature request, bugs, etc.

## Demos
see [FriceDemo](https://github.com/icela/FriceDemo)
