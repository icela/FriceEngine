# API reference
This is the full API reference of frice engine.

## org.frice.game.Game
Abstract.<br/>
**DO NOT override the constructor, please put anything about initializing into `onInit()`!**

### Life cycle methods
Method|Usage
:---|---:
`onInit()`|Invoked when initializing
`onExit()`|Invoked when the game window is closing (windowClosing, NOTE that engine will NOT exit the app itself! Exit in your code.)
`onRefresh()`|Invoked before repainting the game panel, if not paused, default is 15 refreshes per second.
`onClick(OnClickEvent)`|Invoked when an object has been clicked
`onMouse(OnMouseEvent)`|Invoked when a mouse event arrives (mouseClicked, mousePressed, etc.).
`onLoseFocus(OnWindowEvent)`|Invoked when the game window blurs
`onFocus(OnWindowEvent)`|Invoked when the game window is focused

### APIs
Method|Usage
:---|---:
`addObject(FObject)|Add `FObject` to the game panel.
`removeObject(FObject)|Remove `FObject` from screen(using the **id** field to judge if they are the same)
`addTimeListener(listener: FTimeListener)`|Add a listener to timeListeners. They will be called when needed.
`removeTimeListener(listener: FTimeListener)`|Remove a listener from timeListeners.
`setBack(FResource)`|Set the background (color or image)
`setRefreshPerSecond(Double)`|Set panel refreshing frequency to a value. Default is 15.
`setBounds(int x, int y, int width, int height)`|Inherited rom JFrame.
`setTitle(String)`|Inherited from JFrame.

## org.frice.game.obj.FObject
An interface representing a game object.<br/>


### Members
Name: Type|Usage
:---|---:
`id: Int`|ID of this object.
`x: Int`|Location x.
`y: Int`|Location y.

## org.frice.game.obj.ImageObject
Class.

### Parent
`org.frice.game.obj.FObject`

### Constructors
Param|Usage
:---|---:
`res: ImageResource`|Wraps a image resource for displaying on game scene.

## org.frice.game.obj.ShapedColorObject
Class.

### Parent
`org.frice.game.obj.FObject`

### Constructor
Param|Usage
:---|---:
`res: ColorResource`|Color of this object.
`shape: FShape`|Shape of this object.

## org.frice.game.resource.FResource
Interface.

## org.frice.game.resource.ImageResource
Interface.

### Members
Name: Type|Usage
:---|---:
`image: Image`|Image object.

## org.frice.game.resource.FileImageResource
Class inherits `org.frice.game.resource.ImageResource`.

### Constructor
Param|Usage
:---|---:
`file: File, String`|Path to the image， or file handler of the image.

## org.frice.game.resource.ColorResource
Class.

### Constructor
Param|Usage
:---|---:
`color: Color, Int, String`|Color, color code, or color in string (pass arg to `java.awt.Color.getColor()` for `Int` and `String` type input to get `Color`.).

### Built-in color codes
```
GREEN, BLUE, GRAY, DARK_GRAY, LIGHT_GRAY, WHITE, RED, BLACK, CYAN, MAGENTA, YELLOW, SHIT_YELLOW, ORANGE, PINK
```

## org.frice.game.utils.message.log.FLog
Static object.

### APIs
Method|Usage
:---|---:
`v(e: Any?)`|Log a verbose message
`d(e: Any?)`|Log a debug message
`i(e: Any?)`|Log a info message
`w(e: Any?)`|Log a warning message
`e(e: Any?)`|Log an error message

## org.frice.game.utils.time.FTimer
Class.<br/>
I'm sorry for not docing the function very well (sorry for my English), so please view its usage in demos.

### Constructor
Param|Usage
:---|---:
`time: Int`|Time (in milliseconds) between each invoke.

### APIs
Name|Usage
:---|---:
`ended()`|Returns whether time's up.

## org.frice.game.utils.time.FTimeListener
Class.

### Parent
`org.frice.game.utils.time.FTimer`

### Constructor
Param|Usage
:---|---:
`timeUp: () -> Unit`|This closure will be invoked when time's up (`ended()`)
`timeUp: OnTimeEvent`|`execute()` in this interface will be invoked when time's up (`ended()`)

## org.frice.game.utils.data.Preference
Class.<br/>
A simple xml file reader/writer for reading/saving preferences.

### Constructor
All private. Please use the factory method `getPreference` instead.

### APIs
Name|Usage
:---|---:
`getPreference(file: File)`|create an instance from file
`getPreference(path: String)`|create an instance from file path
`insert(key: String, value: Any?)`|insert a key-value pair into preference file.
`query(key: String, value: Any)`|query a value related to the key from preference file.

