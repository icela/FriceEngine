# API reference
This is the full API reference of frice engine.<br/>
Some classes and interface are private, so they are not shown here.<br/>

## Abstract: org.frice.game.AbstractGame (also Class: org.frice.game.Game)
**Do not override the constructor, anything about initialization please put them into "onInit()"!**

### Demos
See [README](README.md).

### Life cycle methods
Method|Usage
:---|---:
onInit()|Invoked while initializing
onExit()|Invoked while exiting (NOTE: engine does not perform process termination automatically)
onRefresh()|Invoked after game window is refreshed, if not paused)
onClick(OnClickEvent)|Invoked while clicking in game window
onMouse(OnMouseEvent)|Invoked when a mouse event arrives(moving, pressing, etc)
onLoseFocus(OnWindowEvent)|Invoked when game window blurs
onFocus(OnWindowEvent)|Invoke when game window gets focus
customDraw(Graphics2D)|Override to draw something after everything was drawn.

### APIs
Method|Usage
:---|---:
addObject(FObject)|Adds an object to game window
addObject(Array)|Adds objects to game window
addObject(List)|Adds objects to game window
removeObject(FObject)|Removes an object from screen
removeObject(Array)|Removes objects from screen
removeObject(List)|Removes objects from screen
addTimeListener(FTimeListener)|Adds a timer listener. It will be invoked when timeouts.
addTimeListener(Array)|Add timer listeners. It will be invoked when timeouts.
addTimeListener(List)|Add timer listeners. It will be invoked when timeouts.
removeTimeListener(FTimeListener)|Remove a object from timer listeners.
removeTimeListener(Array)|Remove objects from timer listeners.
removeTimeListener(List)|Remove objects from timer listeners.
setBack(FResource)|Sets window background
setCursor(ImageObject)|Sets the cursor as an ImageObject. You can operate it like any other objects. This will call `addObject()` by itself.
setCursor(ImageResource)|Create an ImageObject and invoke the last method
getScreenCut(): ImageResource|Returns the current screenshot.
setAutoGC(Boolean)|if true, the engine will remove objects far from the game window.
setBounds(Int, Int, Int, Int)|*Inherited from Frame*
setTitle(String)|*Inherited from Frame*
setLoseFocusChangeColor(Boolean)|if true, the game scene color will become darker when lose focus.

## Abstract: org.frice.game.obj.FObject
Represents a game object.

### APIs
Name|Usage
:---|---:
getAnims(): List<(FAnim)>|get the list of anims, you can add and remove animations via this.
getTargets(): List<(Pair<(FObject, Event)>)>|get a list of pairs, it has an object reference and an event interface. when collision occurs, the interface will be called.
addForce(Double, Double)|add a force to this object(actually you added an accelerate anim to it). It's force, and you had better set the mass first.
setMass(Double)|set the mass. It's safe, if you set an unsafe value, it will ignore the operation automatically. :joy:

### Demos
see Demo1.java in the demo repo.

### Members
Name: Type|Usage
:---|---:
id: Int|Unique ID
x: Int|Location x
y: Int|Location y
anims: ArrayList<FAnim>|Animation list of this object. Engine will play them if game is not paused.
targets: ArrayList<(Pair<FObject, OnCollideEvent>)>|Param `FObject` is the target object, `OnCollideEvent` is a lambda which will be called when owner collides the target object.

## Class: org.frice.game.obj.sub.ImageObject

### Demos
see Demo1.java in the demo repo.

### Parent
org.frice.game.obj.FObject

### Constructors
Param|Usage
:---|---:
res: ImageResource|image resource that will display on game scene

## Class: org.frice.game.obj.sub.ShapeObject

### Demos
see Demo7.java in the demo repo.

### Parent
org.frice.game.obj.FObject

### Constructors
Param|Usage
:---|---:
res: ColorResource|Color of this object.
shape: FShape|Shape of this object

## Class: org.frice.game.obj.button.SimpleButton
A Button.

### Constructors
Param|Usage
:---|---:
color: ColorResource|the color of the button
text: String|the text on button
x: Double|position
y: Double|position
width: Double|width
height: Double|height

## Class: org.frice.game.obj.button.ImageButton
A Button containing an image.

### Constructors
Param|Usage
:---|---:
imageNormal: ImageResource|this will be displayed while button is not pressed.
imagePressed: ImageResource|the image will be displayed while button is pressed.
x: Double|position
y: Double|position
width: Double|width
height: Double|height

## Class: org.frice.game.obj.effects.ParticleEffect
An object to display particles.

## Abstract: org.frice.game.resource.ImageResource

### Members
Name: Type|Usage
:---|---:
image: Image|Image object.

### APIs
Name|Usage
:---|---:
fun fromImage(image: BufferedImage)| create an image from bufferedImage
fun fromPath(path: String)| create an image from file path
fun fromWeb(url: String)| create an image from url as path

## Class: org.frice.game.resource.FileImageResource

### Constructors
Param|Usage
:---|---:
file: File|Image file handler to the image file.
file: String|Image path to the image file.

## Class: org.frice.game.resource.WebImageResource

### Constructors
Param|Usage
:---|---:
url: String|URL of the image

## Class: org.frice.game.resource.FrameImageResource
Implements frame animation.

### Constructors
Param|Usage
:---|---:
game: Game|Game context, just pass `this` in your own Game class
list: List<ImageResource>, Array<ImageResource>|List of images
div: Int|Division time between two images

### APIs
Method|Usage
:---|---:
setLoop(Boolean)|Set whether the animation should loop.

## Class: org.frice.game.resource.image.PartImageResource
Create an image resource from a part of another image.

## Class: org.frice.game.resource.ColorResource

### Constructors
Param|Usage
:---|---:
color: Color, Int, String|color

### Built-in colors
```
GREEN, BLUE, GRAY, DARK_GRAY, LIGHT_GRAY, WHITE, RED, BLACK, CYAN, MAGENTA, YELLOW, SHIT_YELLOW, ORANGE, PINK
and so on!
```

## Static object: org.frice.game.utils.message.log.FLog

### APIs
Method|Alias|Usage
:---|---|---:
v(e: Any?)|verbose|Log a verbose message
d(e: Any?)|debug|Log a debug message
i(e: Any?)|info|Log a info message
w(e: Any?)|warning|Log a warning message
e(e: Any?)|error|Log an error message

## Class: org.frice.game.utils.time.FTimer
I'm afraid that I can't doc this function very well for my poor English, so please view its usage in demos.

### Constructors
Param|Usage
:---|---:
time: Int|Interval between two invokes

### APIs
Name|Usage
:---|---:
ended()|Returns whether time's up.

## Class: org.frice.game.utils.time.FTimeListener

### Parent
org.frice.game.utils.time.FTimer

### Constructors
Param|Usage
:---|---:
timeUp: () -> Unit, OnTimeEvent|function that will be invoked when time's up(`ended()`)

## Class: XMLPreference
Operates an xml file for config.

### Constructors
All private. Please use the factory methods `getPreference()`.

### APIs
Name|Usage
:---|---:
getPreference(file: File)<br />getPreference(file: String)|Creates a instance from a file handler, or path to the file.
insert(key: String, value: Any?)|Insert a key-value pair into the file
query(key: String, value: Any)|Query a value by `key` from the file

## Class:Preference

### APIs
Name|Usage
:---|---:
list()|get a list of pair
insert(key: String, value: Any?)|Insert a key-value pair into the file
query(key: String, value: Any)|Query a value by `key` from the file


## Class: org.frice.game.utils.message.FDialog
Shows dialogs on screen. Quite simple to use, I think there's no need to write a doc for it.<br/>
Constructor needs a context.
See [FDialog.kt](src/org/frice/game/utils/message/FDialog.kt).

## org.frice.game.utils.audio.AudioManager
Singleton.<br/>
Plays audio. However background music isn't recommended.

### APIs
Method|Usage
:---|---:
play(file: File)<br />play(file: String)|Play the wav file specified in arg `file`.
getPlayer(file: File)<br />getPlayer(file: String)|Returns the player thread(for pausing, stopping, etc.)

## Class: org.frice.game.utils.audio.AudioPlayer
The audio player thread.

### APIs
Method|Usage
:---|---:
start()|Starts playing
exit()|Stops playing

## Class: org.frice.game.anim.move.SimpleMove

### Constructors
Param|Usage
:---|---:
x: Int|Pixels that the owner should move by **per second**, horizontally.
y: Int|Pixels that the owner should move by **per second**, vertically.

## Class: org.frice.game.anim.move.AccurateMove
a more specific "simple move".

### Constructors
Param|Usage
:---|---:
x: Double|horizontally.
y: Double|vertically.

## Class: org.frice.game.anim.move.AccelerateMove

### Demos
See [demos/demo/Demo6.java](demos/demo/Demo6.java), [demos/demo/Demo7.java](demos/demo/Demo7.java)

### Constructors
Param|Usage
:---|---:
x: Int|Pixels that the owner's speed should be increased by **per second**, horizontally.
y: Int|Pixels that the owner's speed should be increased by **per second**, vertically.

### APIs
Method|Usage
:---|---:
getGravity(g: Double)<br/>getGravity()|Returns an `AccelerateMove` instance which y = g  (default 10) and x = 0

## Abstract:org.frice.game.anim.move.CustomMove
Defines a user-customized move.

### APIs
Method|Usage
:---|---:
getXDelta(timeFromBegin: Double): Double|Receives time in seconds elapsed since creation of the owner. Returns pixels that the owner should move by, horizontally.
getYDelta(timeFromBegin: Double): Double|same as the one above, but vertically.

## Class: org.frice.game.anim.scale.SimpleScale

### Constructors
Param|Usage
:---|---:
x: Double|How many times bigger that the owner should scale per second, horizontally.
y: Double|How many times bigger that the owner should scale per second, vertically.
