# API reference
This is the full API reference of frice engine.<br/>
Some classes and interface you don't need to know are not shown here.<br/>
There are information you should know only.

## org.frice.game.Game
Abstract.<br/>
**Do not override the constructor, anything about initializing please put them into "onInit()"!**

### Demos
see every demos are OK.

### Life cycle methods
Method|Usage
:---|---:
onInit()|invoked when initialize
onExit()|invoked when exit button was clicked(will not exit automatically)
onRefresh()|invoked while running and looped
onClick(OnClickEvent)|invoked when window is clicked
onMouse(OnMouseEvent)|invoked while the mouse had done sth(move, press, etc)
onLoseFocus(OnWindowEvent)|invoked when lost focus(user clicked other windows)
onFocus(OnWindowEvent)|invoke when having focus(user clicked the game window)

### APIs
Method|Usage
:---|---:
addObject(FObject)|add an game object to screen
removeObject(FObject)|remove the object from screen(using the **id** field to judge if they are the same)
addTimeListener(listener: FTimeListener)|add an object to timeListeners, and they will be checked when needed.
removeTimeListener(listener: FTimeListener)|remove the object to timeListeners, and it won't be checked when needed.
setBack(FResource)|set the background(color or image)
setRefreshPerSecond(Double)|There will be a sleep between every two refresh, the length of every sleep is 1000/value. Default is 10.
setCursor(o: ImageObject)|set the cursor as an ImageObject, and you can still operate it as an object(will call addObject() automatically)
setCursor(o: ImageResource)|create an ImageObject and invoke the last method
setBounds(int x, int y, int width, int height)|from Frame
setTitle(String)|from Frame

## org.frice.game.obj.FObject
Interface.<br/>
Represent game objects.

### Demos
see Demo1.

### Members
Name and type|Usage
:---|---:
id: Int|to specify objects from others
x: Int|location x
y: Int|location y
anims: ArrayList<FAnim>|Owned animations, will follow these animations while game is running.
targets: ArrayList<Pair<FObject, OnCollideEvent>>|first param is the target object, second one is the lambda will be called while owner collides the target object.

## org.frice.game.obj.ImageObject
Class.

### Demos
see Demo1.

### Parent
org.frice.game.obj.FObject

### Constructors
Param|Usage
:---|---:
res: ImageResource|image resource that will display on game scene

## org.frice.game.obj.ShapedColorObject
Class.

### Demos
see Demo7.

### Parent
org.frice.game.obj.FObject

### Constructors
Param|Usage
:---|---:
res: ColorResource|color that will display on game scene
shape: FShape|shape of this object

## org.frice.game.resource.FResource
Interface.

## org.frice.game.resource.ImageResource
Interface.

### Members
Name and type|Usage
:---|---:
image: Image|image

## org.frice.game.resource.FileImageResource
Class.

### Constructors
Param|Usage
:---|---:
file: File|file that contains the image
path: String|path of file that contains the image

## org.frice.game.resource.WebImageResource
Class.

### Constructors
Param|Usage
:---|---:
url: String|url of website that contains the image

## org.frice.game.resource.FrameImageResource
Class.<br/>
Use this to implement frame animation.

### Constructors
Param|Usage
:---|---:
game: Game|context, just give "this" in your own Game class
list: List<ImageResource>|a list of images
list: Array<ImageResource>|an array of images
div: Int|division time between two images

### APIs

setLoop(Boolean)|

## org.frice.game.resource.ColorResource
Class.

### Constructors
Param|Usage
:---|---:
color: Color|color
color: Int|color code
color: String|color code string

### Built-in colors
```
GREEN, BLUE, GRAY, DARK_GRAY, LIGHT_GRAY, WHITE, RED, BLACK, CYAN, MAGENTA, YELLOW, SHIT_YELLOW, ORANGE, PINK
```

## org.frice.game.utils.message.log.FLog
Static object.

### APIs
Method|Alias|Usage
:---|---|---:
v(e: Any?)|verbose|log a verbose message
d(e: Any?)|debug|log a debug message
i(e: Any?)|info|log a info message
w(e: Any?)|warning|log a warning message
e(e: Any?)|error|log an error message

## org.frice.game.utils.time.FTimer
Class.<br/>
I think I didn't tell the function well(Sorry for my English), so please view usage in demos.

### Constructors
Param|Usage
:---|---:
time: Int|the time(millis seconds) between each invokes

### APIs
Name|Usage
:---|---:
ended()|returns if the time's up.

## org.frice.game.utils.time.FTimeListener
Class.

### Parent
org.frice.game.utils.time.FTimer

### Constructors
Param|Usage
:---|---:
timeUp: () -> Unit|this closure will be invoked when time's up(ended())
timeUp: OnTimeEvent|execute() in this interface will be invoked when time's up(ended())

## org.frice.game.utils.data.Preference
Class.<br/>
Operating an xml file to save data.

### Constructors
All private. Please use the factory methods **getPreference**.

### APIs
Name|Usage
:---|---:
getPreference(file: File)|create a instance from file
getPreference(path: String)|create a instance from file path
insert(key: String, value: Any?)|insert a key-value pair into the xml file
query(key: String, value: Any)|query a value to the key from the xml file

## org.frice.game.utils.message.FDialog
Class.<br/>
To show dialogs on screen. Very simple, I think there's no need to write a doc for it.<br/>
Constructor needs a context.

## org.frice.game.utils.audio.AudioManager
Singleton.<br/>
Use this to play audio. But BGM isn't recommended.

### APIs
Method|Usage
:---|---:
play(file: File)|play the wav file
play(path: String)|create a file from the path and invoke the last method
getPlayer(file: File)|returns a player thread(so that you can stop the thread while playing)
getPlayer(path: String)|create a file from the path and invoke the last method

## org.frice.game.utils.audio.AudioPlayer
Class.<br/>
This is the player thread.

### APIs
Method|Usage
:---|---:
start()|start playing
exit()|stop playing

## org.frice.game.anim.FAnim
Abstract.<br/>
Animations are all subclasses of FAnim.

## org.frice.game.anim.move.SimpleMove
Class.<br/>

### Constructors
Param|Usage
:---|---:
x: Int|number of pixels that the owner should move per second, horizontally.
y: Int|number of pixels that the owner should move per second, vertically.

## org.frice.game.anim.move.AccelerateMove
Class.<br/>

### Demos
See Demo6, Demo7.

### Constructors
Param|Usage
:---|---:
x: Int|number of **pixels per second** that the owner's speed should increase move per second, horizontally.
y: Int|number of **pixels per second** that the owner's speed should increase per second, vertically.

### APIs
Method|Usage
:---|---:
getGravity(g: Double)|returns an AccelerateMove instance with y = g and x = 0
getGravity()|returns an AccelerateMove instance with y = 10 and x = 0

## org.frice.game.anim.move.CustomMove
Abstract.<br/>
For you to create your own way to Move.

### APIs
Method|Usage
:---|---:
getXDelta(timeFromBegin: Double): Double|you receive how many seconds has past from created, and return how many pixels the owner should move, horizontally.
getYDelta(timeFromBegin: Double): Double|same as the one above, but vertically.

## org.frice.game.anim.scale.ScaleAnim
Abstract.

## org.frice.game.anim.scale.SimpleScale
Class.

### Constructors
Param|Usage
:---|---:
x: Double|how many times bigger that the owner should scale per second, horizontally.
y: Double|how many times bigger that the owner should scale per second, vertically.


