package org.frice.platform

import org.frice.obj.AbstractObject
import java.util.*

typealias Layers = Array<Layer>

interface FriceGame : TitleOwner {
	val layers: Layers

	val drawer: FriceDrawer

	/**
	 * not implemented yet.
	 * currently it's same as paused.
	 */
	var stopped: Boolean
	var debug: Boolean

	/** a general purpose instance for generating random numbers */
	val random: Random

	/** if true, the engine will collect all objects which are invisible from game window. */
	var autoGC: Boolean

	/** if true, there will be a fps calculating on the bottom-left side of window. */
	var showFPS: Boolean
	var loseFocus: Boolean
	var loseFocusChangeColor: Boolean
	var millisToRefresh: Int
	var paused: Boolean

	/** do the delete and add work, to prevent Exceptions */
	fun processBuffer() = layers.forEach(Layer::processBuffer)

	fun onInit() = Unit
	fun onLastInit() = Unit
	fun onRefresh() = Unit
	fun onExit()
	fun customDraw(g: FriceDrawer)
	fun onLoseFocus() {
		paused = true
	}

	fun onFocus() {
		paused = false
	}

	/** remove Objects using vararg */
	fun removeObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { removeObject(layer, it) }

	/**
	 * removes single object.
	 * this method is safe.
	 *
	 * @param obj will remove objects which is equal to it.
	 */
	fun removeObject(layer: Int, obj: AbstractObject) = layers[layer].removeObject(obj)

	/**
	 * clears all objects.
	 * this method is safe.
	 */
	fun clearObjects() = layers.forEach(Layer::clearObjects)

	/**
	 * clears all objects in one layer.
	 * this method is safe.
	 */
	fun clearObjects(layer: Int) = layers[layer].clearObjects()

	/** adds an object to game, to be shown on game window. */
	fun addObject(layer: Int, obj: AbstractObject) = layers[layer].addObject(obj)

	/** add Objects using vararg */
	fun addObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { addObject(layer, it) }

	fun addObject(vararg objs: AbstractObject) = addObject(0, *objs)
	fun removeObject(vararg objs: AbstractObject) = removeObject(0, *objs)

	fun clearScreen()
}