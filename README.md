# FriceEngine

An easy, light weight, native game engine running on JVM.

View [Help](help.md) to learn more about Frice Engine.

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
- [ ] JRuby(seemed not working with my project)
- [ ] Scala(I don't know if supported)

### Functions
- [X] Game Objects(from image or shape, image from file or web)
- [X] Collision detecting
- [X] Audio playing(wav only, because mp3 needs a library and it's too large)
- [X] Dialogs
- [X] Cursor overriding
- [X] 

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
- [demo/Demo4.groovy](demo/Demo4.groovy)
- [demo/Demo5.rb](demo/Demo5.rb) (not working)
- [demo/Demo6.java](demo/Demo6.java)
- [demo/Demo7.java](demo/Demo7.java)(flappy bird demo)

