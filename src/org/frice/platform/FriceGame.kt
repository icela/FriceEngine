package org.frice.platform

import org.frice.event.OnMouseEvent
import org.frice.obj.*
import org.frice.obj.button.*
import org.frice.obj.effects.LineEffect
import org.frice.obj.sub.ImageObject
import org.frice.obj.sub.ShapeObject
import org.frice.platform.adapter.JvmDrawer
import org.frice.platform.adapter.JvmImage
import org.frice.platform.owners.*
import org.frice.resource.graphics.ColorResource
import org.frice.resource.image.ImageResource
import org.frice.utils.shape.*

interface FriceGame : TitleOwner, Sized, Resizable, Collidable {
	val layers: Array<Layer>
	val drawer: FriceDrawer

	override val box get() = object : FShapeQuad {
		override val x get() = 0.0
		override val y get() = 0.0
		override val width get() = this@FriceGame.width.toDouble()
		override val height get() = this@FriceGame.height.toDouble()
	}

	/**
	 * not implemented yet.
	 * currently it's same as paused.
	 */
	var stopped: Boolean
	var debug: Boolean

	/** if true, the engine will collect all objects which are invisible from game window. */
	var autoGC: Boolean

	/** if true, there will be a fps calculating on the bottom-left side of window. */
	var showFPS: Boolean
	var loseFocus: Boolean
	var loseFocusChangeColor: Boolean
	var millisToRefresh: Int
	var paused: Boolean
	var isFullScreen: Boolean
	var isAlwaysTop: Boolean

	/** do the delete and add work, to prevent Exceptions */
	fun processBuffer() = layers.forEach(Layer::processBuffer)

	fun onInit() = Unit
	fun onLastInit() = Unit
	fun onRefresh() = Unit
	fun onMouse(e: OnMouseEvent) = Unit
	fun customDraw(g: FriceDrawer) = Unit
	fun onLoseFocus() {
		paused = true
	}

	fun onFocus() {
		paused = false
	}

	/** remove objects using vararg */
	fun removeObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { removeObject(layer, it) }

	/** remove objects unsafely using vararg */
	fun instantRemoveObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { instantRemoveObject(layer, it) }

	/**
	 * removes single object.
	 * this method is safe.
	 *
	 * @param obj will remove objects which is equal to it.
	 */
	fun removeObject(layer: Int, obj: AbstractObject) = layers[layer].removeObject(obj)

	/** remove objects unsafely */
	fun instantRemoveObject(layer: Int, obj: AbstractObject) = layers[layer].instantRemoveObject(obj)

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

	/** add objects using vararg */
	fun addObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { addObject(layer, it) }

	fun addObject(vararg objs: AbstractObject) = addObject(0, *objs)
	fun removeObject(vararg objs: AbstractObject) = removeObject(0, *objs)

	/** adds an object to game unsafely, to be shown on game window. */
	fun instantAddObject(layer: Int, obj: AbstractObject) = layers[layer].instantAddObject(obj)

	/** add objects unsafely using vararg */
	fun instantAddObject(layer: Int, vararg objs: AbstractObject) = objs.forEach { instantAddObject(layer, it) }

	/** add objects unsafely using vararg */
	fun instantAddObject(vararg objs: AbstractObject) = instantAddObject(0, *objs)

	/** remove objects unsafely using vararg */
	fun instantRemoveObject(vararg objs: AbstractObject) = instantRemoveObject(0, *objs)

	fun clearScreen() {
		drawer.color = ColorResource.WHITE
		drawer.drawRect(0.0, 0.0, width.toDouble(), height.toDouble())
		drawer.restore()
	}

	fun mouse(e: OnMouseEvent) = layers.forEach {
		it.texts.forEach { b ->
			if (b is FButton && b.containsPoint(e.x.toInt(), e.y.toInt())) b onMouse e
		}
	}

	fun drawEverything(bgg: FriceDrawer) {
		processBuffer()
		layers.forEach {
			it.objects.removeIf { o ->
				if (o is FObject) {
					o.`{-# runAnims #-}`()
					return@removeIf o.died
				}
				false
			}

			it.objects.forEach loop@ { o ->
				if (o is ShapeObject && ColorResource.COLORLESS == o.resource) return@loop
				if (o is LineEffect && ColorResource.COLORLESS == o.color) return@loop
				bgg.restore()
				bgg.init()
				if (bgg is JvmDrawer) {
					if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
					else bgg.rotate(o.rotate, o.x, o.y)
				} else bgg.rotate(o.rotate)
				when (o) {
					is FObject.ImageOwner -> if (collides(o)) bgg.drawImage(o.image, o.x, o.y)
					is ShapeObject -> {
						if (collides(o)) {
							bgg.color = o.resource
							when (o.shape) {
								is FRectangle -> bgg.drawRect(o.x, o.y, o.width, o.height)
								is FOval -> bgg.drawOval(o.x, o.y, o.width, o.height)
							}
						}
					}
					is LineEffect -> {
						bgg.color = o.color
						bgg.drawLine(o.x, o.y, o.x2, o.y2)
					}
				}
				if (autoGC and (o.x < -width || o.x > width + width || o.y < -height || o.y > height + height)) {
					if (o is PhysicalObject) o.died = true
					it.removeObject(o)
				}
			}

			it.texts.forEach loop@ { b ->
				if (b.color == ColorResource.COLORLESS) return@loop
				bgg.run {
					restore()
					init()
					rotate(b.rotate)
					useFont(b)
				}
				if (b is FButton) {
					when (b) {
						is FObject.ImageOwner -> if (collides(b)) bgg.drawImage(b.image, b.x, b.y)
						is SimpleButton -> {
							bgg.color = b.color
							bgg.drawRoundRect(b.x, b.y,
								b.width, b.height,
								Math.min(b.width * 0.5, 10.0),
								Math.min(b.height * 0.5, 10.0))
							bgg.color = ColorResource.DARK_GRAY
							bgg.drawString(b.text, b.x + 10, b.y + b.height / 2)
						}
					}
				} else {
					if (b.x < width && b.y < height) {
						bgg.color = b.color
						bgg.drawString(b.text, b.x, b.y)
					}
				}
			}
		}
		customDraw(bgg)
	}

	fun setCursor(o: FriceImage)

	fun setCursor(o: ImageObject) = setCursor(o.image)
	fun setCursor(o: ImageResource) = setCursor(o.image)

	/**
	 * get a screenShot.
	 *
	 * @return screen cut as an image
	 */
	val screenCut: JvmImage

	fun dialogConfirmYesNo(msg: String) = dialogConfirmYesNo(msg, "Confirm")
	fun dialogConfirmYesNo(msg: String, title: String): Boolean
	fun dialogShow(msg: String) = dialogShow(msg, "Information")
	fun dialogShow(msg: String, title: String)
	fun dialogInput(msg: String) = dialogInput(msg, "Input")
	fun dialogInput(msg: String, title: String): String

	/**
	 * measures the width and height of the text.
	 * @see measureTextWidth if you only care about the width
	 * @param text the text
	 * @return the width and height of the text, according to it's font
	 */
	fun measureText(text: FText): FRectangle

	/**
	 * if you only care about width, choose this.
	 * @param text the text
	 * @return the width of the text, according to it's font
	 */
	fun measureTextWidth(text: FText): Int

	fun measureTextHeight(text: FText): Int = measureText(text).height
}
