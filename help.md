# API reference
This is the full API reference of frice engine.

## org.frice.game.Game
Abstract.<br/>
**Do not override the constructor, anything about initializing please put them into "onInit()"!**

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
setBounds(int x, int y, int width, int height)|from JFrame
setTitle(String)|from JFrame

## org.frice.game.spirit.FObject
Interface.<br/>
Represent game objects.

### Members
Name and type|Usage
:---|---:
id: Int|to specify objects from others
x: Int|location x
y: Int|location y

## org.frice.game.spirit.ImageObject
Class.

### Parent
org.frice.game.spirit.FObject

### Constructors
Param|Usage
:---|---:
res: ImageResource|image resource that will display on game scene

## org.frice.game.spirit.ShapedColorObject
Class.

### Parent
org.frice.game.spirit.FObject

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

## org.frice.utils.message.log.FLog
Static object.

### APIs
Method|Usage
:---|---:
v(e: Any?)|log a verbose message
d(e: Any?)|log a debug message
i(e: Any?)|log a info message
w(e: Any?)|log a warning message
e(e: Any?)|log an error message

## org.frice.utils.time.FTimer
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

## org.frice.utils.time.FTimeListener
Class.

### Parent
org.frice.utils.time.FTimer

### Constructors
Param|Usage
:---|---:
timeUp: () -> Unit|this closure will be invoked when time's up(ended())
timeUp: OnTimeEvent|execute() in this interface will be invoked when time's up(ended())
