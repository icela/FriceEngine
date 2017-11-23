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

An easy, light, native game engine running on JVM.

+ Why easy?
[A flappy bird game](https://github.com/icela/FriceDemo/tree/master/demo/Demo7.java) uses just 56 lines of Java code only.<br/>
Written in Kotlin, also work on Java, JRuby, Groovy and Scala.

+ Why light?
The release build jar is about 1.6mb (with a 1mb Kotlin runtime) only.

+ Why native?
This engine is completely platform-independent: no JNI linkage, no native methods.

View [Document](https://icela.github.io/#getting-started) to learn more about Frice Engine.

# Build

Add code below to your gradle script:

```gradle
allprojects { repositories { maven { url 'https://jitpack.io' } } }
dependencies {
  compile 'com.github.icela:FriceEngine:v1.6.0'
}
```

Alternatively, you can download a jar from the [release page](https://github.com/icela/FriceEngine/releases).

# Features

## Platform dependent

These features are differently implemented in Swing/JavaFX.  
Since this project is still in progress, the unsupported features will soon be available.

Feature|Swing|JavaFX
:---|:---:|:---:
Life cycle|√|√
Dialogs|√|√
Cursor overriding|√|×
Screenshot|√|×
Buttons|√|×
Full screen|√|√
Always on top|×|√
Text font/size|√|√
Multi-instances|√|×
Resizability|√|√
Rotate animations|√|×

## Platform independent

- Non-rotate animations (move, scale, accelerate, etc.)
- Audio playing (\*.wav, \*.mp3)
- Clock system, timers
- A BoolArray implemented in bitwise operation, each boolean value will only take 1 __bit__ space (instead of 1 __byte__ defaultly on JVM).
- Automatic garbage collection (optional, objects far from the screen will be removed)
- Resource manager (caching IO)
- File/Image/Color/URL utils
- Game objects
- Frame animations
- Key-value database
- Particle effects
- Collision detecting

## DSL
see [DSL for FriceEngine](https://github.com/icela/FriceEngine-DSL)

## Contributions
Feel free to open issue for feature request, bug reports, etc. <br/>
This is generally a personal project, so please do give your genuine suggestions.

## Demos
see [FriceDemo](https://github.com/icela/FriceDemo)
