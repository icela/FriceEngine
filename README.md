# FriceEngine

An easy, light weight, native game engine running on JVM.<br/>
View [Help](help.md) or wiki to learn more about Frice Engine.

## How easy?
[A flappy bird game](demo/Demo7.java) needs 65 lines of code only.<br/>
Written in Kotlin, but also work with Java, JRuby, Groovy, Scala.

## How light?
Kotlin-runtime.jar is 736kb, and the jar of this engine is a little above 800kb.<br/>
And if you use Kotlin, you don't have to copy runtime to your target('cause already built-in)<br/>
mp3 support in the future will take 200kb.

## How native?
No JNI, no native method, everything is written by me in pure Kotlin.<br/>
But mp3 support in the future will probably be external.<br/>
That means this engine is completely platform-independent.<br/>
And this is what a JVM app should exactly be.

# Build
clone and open with IntelliJ IDEA(please install Kotlin, Groovy, Ruby plugin):

clone:
```bash
git clone https://github.com/icela/FriceEngine.git
```

# Use

## Supported

### Languages
- [X] Kotlin(Native)
- [X] Java
- [X] Groovy
- [X] JRuby(seemed not working with my project)
- [ ] Scala(I don't know if supported)

### Functions
- [X] Game Objects(from image or shape, image from file or web)
- [X] Life cycle
- [X] Collision detecting
- [X] Audio playing(wav only, because mp3 needs a library and it's too large)
- [X] Dialogs
- [X] Cursor overriding
- [X] Clock system
- [X] Animations(frame, move, scale, accelerate, etc.)
- [X] A key-value database

## Basic
FriceEngine is based on the life cycle mode.<br/>
When you wanna build games with frice engine, follow these steps:

- Import the jar in the latest release to your project.
- Create a class extends Game in org.frice.game package.
- Implement the abstract methods, just understand them by name.
- call the empty constructor in the public static void main.
- For full API doc please view [help](help.md)

## Demos

- [demo/Demo1.java](demo/Demo1.java)
- [demo/Demo2.java](demo/Demo2.java)
- [demo/Demo3.kt](demo/Demo3.kt)
- [demo/Demo4.groovy](demo/Demo4.groovy)(database demo)
- [demo/Demo5.rb](demo/Demo5.rb) (it can crate game window but cannot add objects)
- [demo/Demo6.java](demo/Demo6.java)(accelerate demo)
- [demo/Demo7.java](demo/Demo7.java)(flappy bird demo)
- [demo/Demo8.java](demo/Demo8.java)(audio and cursor demo)
