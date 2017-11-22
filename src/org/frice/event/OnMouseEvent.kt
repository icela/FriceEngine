@file:JvmName("Events")

package org.frice.event

import java.awt.event.MouseEvent
import javafx.scene.input.MouseEvent as JfxMouseEvent

const val MOUSE_CLICKED = 0x00
const val MOUSE_RELEASED = 0x01
const val MOUSE_ENTERED = 0x02
const val MOUSE_EXITED = 0x03
const val MOUSE_PRESSED = 0x04

/**
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class OnMouseEvent(
	val x: Double,
	val y: Double,
	val screenX: Double,
	val screenY: Double,
	val isAltDown: Boolean,
	val isCtrlDown: Boolean,
	val isShiftDown: Boolean,
	val isMetaDown: Boolean,
	val type: Int)

fun swingMouse(e: MouseEvent, type: Int) = OnMouseEvent(
	e.x.toDouble(),
	e.y.toDouble(),
	e.xOnScreen.toDouble(),
	e.yOnScreen.toDouble(),
	e.isAltDown,
	e.isControlDown,
	e.isShiftDown,
	e.isMetaDown,
	type)

fun fxMouse(it: JfxMouseEvent, type: Int) = OnMouseEvent(
	it.x,
	it.y,
	it.screenX,
	it.screenY,
	it.isAltDown,
	it.isControlDown,
	it.isShiftDown,
	it.isMetaDown,
	type)
