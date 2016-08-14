# API doc

This is the full API doc of frice engine.

## org.frice.game.Game

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

### General APIs

Method|Usage
:---|---:
addObject(FObject)|add an game object to screen
removeObject(FObject)|remove the object from screen(using the **id** field to judge if they are the same)
setBack(FResource)|set the background
setBounds(int x, int y, int width, int height)|from JFrame
setTitle(String)|from JFrame

## org.frice.game.spirit.FObject

Abstract.
Represent game objects.



