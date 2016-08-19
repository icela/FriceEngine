# FriceEngine

![image](https://avatars1.githubusercontent.com/u/21008243)

一个简单、易用的原生轻量级JVM游戏引擎。<br/>
查看 [APIs](apis.md) 或者Wiki以更多地了解Frice Engine。

An easy, light, native game engine running on JVM.<br/>
View [APIs](apis.md) or wiki to learn more about Frice Engine.

## Why easy? 为什么简单易用？
[一个简单的Flappy bird](demo/Demo7.java) 仅用了65行代码。
使用Kotlin写就，并支持Java, JRuby, Groovy, Scala等语言。
[A flappy bird game](demo/Demo7.java) is in 65 lines of code only.<br/>
Written in Kotlin, also work on Java, JRuby, Groovy and Scala.

## Why light? 为什么轻量？
Kotlin运行时库仅用736KB（使用Kotlin开发游戏可以在游戏集成而不必引擎附带），引擎也仅仅略大于800KB。<br/>
未来将会加入的MP3支持可能会占用200KB。

Kotlin runtime is 736KB in size (if not using Kotlin to build games), and the engine JAR file size is just a bit above 800KB.<br/>
MP3 format support (in the future) will take 200KB.

## Why native? 为什么原生？
This engine is completely platform-independent: no JNIs, no native methods, everything is written by myself in pure Kotlin.<br/>
And this is what a JVM app should be exactly.

# Before you build
clone (`clone https://github.com/icela/FriceEngine.git`) and open with IntelliJ IDEA(please install Kotlin, Groovy, or Ruby plugin first).

# Build
Currently under construction. Maybe you know how to build your own apps.

# Usage

## Supported

### Languages
- [X] Kotlin (Native)
- [X] Java
- [X] Groovy
- [X] JRuby
- [ ] Scala (Not tested)

### Functions
- [X] Game Objects (from image or shape, image from file or web)
- [X] Life cycle
- [X] Collision detecting
- [X] Audio playing (*.wav only)
- [X] Dialogs
- [X] Cursor overriding
- [X] Clock system
- [X] Animations (frame, move, scale, accelerate, etc.)
- [X] A simple key-value database
- [X] Screen cut
- [X] Buttons
- [X] Particle effects
- [X] Easy time controlling
- [X] Language extensions(for Kotlin only)

## Basics
FriceEngine is based on the life cycle mode.<br/>
To build games based on frice engine, follow these steps:

1. Import the jar in the latest release to your project.
2. Create a class extends Game in org.frice.game package.
3. Implement the abstract methods, just understand them by name.
4. call the empty constructor in the public static void main.
5. For full API doc please view [help](help.md)

## Demos

- [demo/Demo1.java](demo/Demo1.java)
- [demo/Demo2.java](demo/Demo2.java)
- [demo/Demo3.kt](demo/Demo3.kt)
- [demo/Demo4.groovy](demo/Demo4.groovy) (database demo)
- [demo/Demo5.rb](demo/Demo5.rb) (BUG: it can create a game window but cannot add objects)
- [demo/Demo6.java](demo/Demo6.java) (acceleration demo)
- [demo/Demo7.java](demo/Demo7.java) (flappy bird demo)
- [demo/Demo8.java](demo/Demo8.java) (audio and cursor demo)
- [demo/Demo9.java](demo/Demo9.java)
- [demo/Demo10.groovy](demo/Demo10.groovy)
- [demo/Demo11.java](demo/Demo11.java) (an awesome demo by [@SuperSodaSea](https://github.com/SuperSodaSea))

## How can it be?

![](https://github.com/ice1000/ice1000.github.io/blob/master/assets/images/game/5/1.gif?raw=true)