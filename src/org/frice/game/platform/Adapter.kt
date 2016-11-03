package org.frice.game.platform

import org.frice.game.obj.AbstractObject
import org.frice.game.obj.button.FText
import org.frice.game.platform.adapter.JvmDrawer
import org.frice.game.platform.adapter.JvmImage
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import java.util.*

/**
 * Created by ice1000 on 2016/10/31.
 *
 * @author ice1000
 * @param T image type
 */
interface FriceDrawer {
	val friceImage: FriceImage
	var color: ColorResource
	fun init(): Unit
	fun drawOval(x: Double, y: Double, width: Double, height: Double)
	fun drawString(string: String, x: Double, y: Double)
	fun drawImage(image: FriceImage, x: Double, y: Double)
	fun drawRect(x: Double, y: Double, width: Double, height: Double)
	fun drawLine(x: Double, y: Double, width: Double, height: Double)
	fun drawRoundRect(x: Double, y: Double, width: Double, height: Double, arcWidth: Double, arcHeight: Double)
	fun rotate(theta: Double, x: Double, y: Double)
	fun rotate(theta: Double)
	fun restore()
}

interface FriceImage {
	val width: Int
	val height: Int
	operator fun get(x: Int, y: Int): ColorResource
	fun set(x: Int, y: Int, color: ColorResource)
	fun getScaledInstance(x: Double, y: Double): FriceImage
	fun getSubImage(x: Int, y: Int, width: Int, height: Int): JvmImage

	/**
	 * copy a image.
	 * to replace the operation of reading an image from file.
	 * if an image is already read from file, it can be copied from RAM directly.
	 *
	 * classes in this file is to do this job.
	 */
	fun clone(): FriceImage
}


interface FriceClock {
	val current: Long
	fun init()
	fun resume()
	fun pause()
}

interface FriceGame {

	val objects: LinkedList<AbstractObject>
	val objectDeleteBuffer: ArrayList<AbstractObject>
	val objectAddBuffer: ArrayList<AbstractObject>

	val timeListeners: LinkedList<FTimeListener>
	val timeListenerDeleteBuffer: ArrayList<FTimeListener>
	val timeListenerAddBuffer: ArrayList<FTimeListener>

	val texts: LinkedList<FText>
	val textDeleteBuffer: ArrayList<FText>
	val textAddBuffer: ArrayList<FText>

	val drawer: JvmDrawer

	var fpsCounter: Int
	var fpsDisplay: Int
	var fpsTimer: FTimer

	/**
	 * do the delete and add work, to prevent Exceptions
	 */
	fun processBuffer() {
		objects.addAll(objectAddBuffer)
		objects.removeAll(objectDeleteBuffer)
		objectDeleteBuffer.clear()
		objectAddBuffer.clear()

		timeListeners.addAll(timeListenerAddBuffer)
		timeListeners.removeAll(timeListenerDeleteBuffer)
		timeListenerDeleteBuffer.clear()
		timeListenerAddBuffer.clear()

		texts.addAll(textAddBuffer)
		texts.removeAll(textDeleteBuffer)
		textDeleteBuffer.clear()
		textAddBuffer.clear()
	}

	fun onInit()
	fun onLastInit()
	fun onRefresh()
	fun onExit()
	fun customDraw(g: JvmDrawer)
	fun onFocus()
	fun onLoseFocus()

	/**
	 * remove Objects using vararg
	 */
	fun removeObject(vararg objs: AbstractObject)

	/**
	 * add TimeListeners using vararg
	 */
	fun addTimeListener(vararg listeners: FTimeListener)

	/**
	 * add a time listener.
	 *
	 * @param listener time listener to be added
	 */
	infix fun addTimeListener(listener: FTimeListener): Boolean

	/**
	 * removes all auto-executed time listeners
	 */
	fun clearTimeListeners(): Boolean

	/**
	 * remove TimeListeners using vararg
	 */
	fun removeTimeListener(vararg listeners: FTimeListener)

	/**
	 * removes specified listener
	 *
	 * @param listener the listener
	 */
	infix fun removeTimeListener(listener: FTimeListener): Boolean

	/**
	 * removes single object.
	 * this method is safe.
	 *
	 * @param obj will remove objects which is equal to it.
	 */
	infix fun removeObject(obj: AbstractObject)

	/**
	 * clears all objects.
	 * this method is safe.
	 */
	fun clearObjects()

	/**
	 * adds an object to game, to be shown on game window.
	 */
	infix fun addObject(obj: AbstractObject)

	/**
	 * add Objects using vararg
	 */
	fun addObject(vararg objs: AbstractObject)

	fun drawEverything(bgg: JvmDrawer)
	fun clearScreen()

	fun getScreenCut(): ImageResource
}

