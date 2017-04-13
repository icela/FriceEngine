# FriceEngine

![image](https://avatars1.githubusercontent.com/u/21008243)

[![Build Status](https://travis-ci.org/icela/FriceEngine.svg?branch=master)](https://travis-ci.org/icela/FriceEngine)

一个简单、易用的原生轻量级JVM游戏引擎。<br/>
查看 [API文档](https://github.com/icela/FriceEngine/blob/master/apis.md) 或者Wiki以更多地了解Frice Engine。

An easy, light, native game engine running on JVM.<br/>
View [APIs](https://github.com/icela/FriceEngine/blob/master/apis.md) or wiki to learn more about Frice Engine.

## Why easy? 为什么简单易用？
[一个简单的Flappy bird](https://github.com/icela/FriceDemo/tree/master/demo/Demo7.java) 仅用了65行代码。
使用Kotlin写就，并支持Java, JRuby, Groovy, Scala等语言。

[A flappy bird game](https://github.com/icela/FriceDemo/tree/master/demo/Demo7.java) is in 65 lines of code only.<br/>
Written in Kotlin, also work on Java, JRuby, Groovy and Scala.

## Why light? 为什么轻量？
Kotlin运行时库仅用736KB（使用Kotlin开发游戏，可以直接使用引擎附带的运行时），引擎也仅仅略大于800KB。<br/>
未来将会加入的MP3支持可能会占用200KB。

Kotlin runtime is only 736KB(if you choose Kotlin to build games, you won't need to add another kotlin-runtime), and the engine JAR file size is just a bit above 800KB.<br/>
MP3 format support (in the future) will take 200KB.

## Why native? 为什么原生？
这个引擎是完全平台无关的：没有JNI，没有native的方法，所有的一切均由Kotlin编写。<br/>
这正是一个JVM App所应该有的样子。

This engine is completely platform-independent: no JNIs, no native methods, everything is written by myself in pure Kotlin.<br/>
And this is what a JVM app should be exactly.

# Build
Download jar in releases.<br/>
Then import the jars into your project.<br/>
Yes, it's only 1MB.

# Usage

## Supported

### Languages
- [X] Kotlin (Native)
- [X] Java
- [X] Groovy
- [X] Scala
- [X] JRuby(with bugs)

### Functions
- [X] Game Objects (from image or shape, image from file or web)
- [X] Life cycle
- [X] Collision detecting
- [X] Audio playing (*.wav only)
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
5. For full API doc please view [APIs](https://github.com/icela/FriceEngine/blob/master/apis.md)

## Demos
see [FriceDemo](https://github.com/icela/FriceDemo)

## How can it be?

![](https://github.com/ice1000/ice1000.github.io/blob/master/assets/images/game/5/0.gif?raw=true)<br/>
![](https://github.com/ice1000/ice1000.github.io/blob/master/assets/images/game/5/2.gif?raw=true)<br/>
![](https://github.com/ice1000/ice1000.github.io/blob/master/assets/images/game/5/3.gif?raw=true)<br/>
![](https://github.com/ice1000/ice1000.github.io/blob/master/assets/images/game/5/4.gif?raw=true)<br/>
![](https://github.com/ice1000/ice1000.github.io/blob/master/assets/images/game/5/5.gif?raw=true)<br/>
![](https://github.com/ice1000/ice1000.github.io/blob/master/assets/images/game/5/6.gif?raw=true)<br/>
