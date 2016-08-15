# FriceEngine

一个易于使用的轻量级原生游戏引擎（于JVM上运行）
An easy, light, native game engine running on JVM

# Build

Clone这个项目，并且使用IntelliJ IDEA打开（确保您已安装Kotlin插件）。

Clone me , (`git clone https://github.com/icela/FriceEngine.git`) and open with IntelliJ IDEA (please install Kotlin plugin first!).

# Usage

## Language supported

- [X] Java
- [X] Kotlin (Native)
- [X] Groovy
- [ ] Scala (not tested)
- [ ] JRuby

## Basic

FriceEngine is based on the life cycle mode.<br/>
To build games with frice engine, follow these steps:

1. Import jars in the latest release to your project.
2. Create a class extends `Game` in package `org.frice.game`.
3. Implement the abstract methods, just understand them by name.
4. call the empty constructor in the public static void main.
5. For full API doc please view [help](help.md)

## Demos

- [root/demo/Demo1.java](demo/Demo1.java)
- [root/test/org/frice/game/Demo.kt](test/org/frice/game/Demo.kt)

