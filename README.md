# FriceEngine

![image](https://avatars1.githubusercontent.com/u/21008243)

CI|status
:---|:---:
Travis CI|[![Build Status](https://travis-ci.org/icela/FriceEngine.svg?branch=master)](https://travis-ci.org/icela/FriceEngine)
AppVeyor|[![Build status](https://ci.appveyor.com/api/projects/status/75d7wx28u3tgtnat?svg=true)](https://ci.appveyor.com/project/ice1000/friceengine)
CircleCI|[![CircleCI](https://circleci.com/gh/icela/FriceEngine.svg?style=svg)](https://circleci.com/gh/icela/FriceEngine)

[![](https://jitpack.io/v/icela/FriceEngine.svg)](https://jitpack.io/#icela/FriceEngine)
[![License: GPL v3](https://img.shields.io/badge/License-GPL%20v3-blue.svg)](http://www.gnu.org/licenses/gpl-3.0)
[![Awesome Kotlin Badge](https://kotlin.link/awesome-kotlin.svg)](https://github.com/KotlinBy/awesome-kotlin)

An easy, light, native game engine running on JVM.<br/>
View [APIs](./res/apis.md) or wiki to learn more about Frice Engine.

## Why easy?
[A flappy bird game](https://github.com/icela/FriceDemo/tree/master/demo/Demo7.java) uses just 65 lines of code only.<br/>
Written in Kotlin, also work on Java, JRuby, Groovy and Scala.

## Why light?
The release build jar is about 200kb only.<br/>

## Why native?
This engine is completely platform-independent: no JNI linkage, no native methods, everything is written in pure Kotlin.<br/>

# Build

Add code below to your gradle scrip:

```groovy
allprojects {
  repositories {
    /// ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
  compile 'com.github.icela:FriceEngine:v1.0.3'
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
- [X] Audio playing (\*.wav only)
- [X] Dialogs
- [X] Cursor overriding
- [X] Clock system
- [X] Animations (frames, moving, scaling, accelerations, etc.)
- [X] A simple key-value database
- [X] Screen cut
- [X] Buttons
- [X] Particle effects
- [X] Easy time controlling
- [X] Language extensions(for Kotlin only)
- [X] Smart auto garbage collection(you can close it)

## DSL
see [DSL for FriceEngine](https://github.com/icela/FriceEngine-DSL)

## Basics
FriceEngine is based on the life cycle mode.<br/>
To build games based on frice engine, follow these steps:

1. Import the jar in the latest release to your project.
2. Create a class extends Game in org.frice.game package.
3. Implement the abstract methods, just understand them by name.
4. call the empty constructor in the public static void main.
5. For full API doc please view [APIs](./res/apis.md)

## Demos
see [FriceDemo](https://github.com/icela/FriceDemo)
