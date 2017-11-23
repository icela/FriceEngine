package org.frice.platform

import org.frice.event.OnMouseEvent
import org.frice.obj.*
import org.frice.obj.button.FButton
import org.frice.obj.button.SimpleButton
import org.frice.obj.effects.LineEffect
import org.frice.obj.sub.ShapeObject
import org.frice.platform.adapter.JvmDrawer
import org.frice.platform.owners.*
import org.frice.resource.graphics.ColorResource
import org.frice.utils.unless
import org.frice.utils.shape.FOval
import org.frice.utils.shape.FRectangle

interface FriceGame : TitleOwner, Sized, Resizable {
	val layers: Array<Layer>

	val drawer: FriceDrawer

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

	/** do the delete and add work, to prevent Exceptions */
	fun processBuffer() = layers.forEach(Layer::processBuffer)

	fun onInit() = Unit
	fun onLastInit() = Unit
	fun onRefresh() = Unit
	fun onMouse(e: OnMouseEvent) = Unit
	fun onExit() = Unit
	fun customDraw(g: FriceDrawer) = Unit
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
			it.objects.forEach { o ->
				if (o is FObject) {
					o.runAnims()
					o.checkCollision()
				}
			}

			it.objects.forEach loop@ { o ->
				if (o is ShapeObject && ColorResource.COLORLESS == o.getResource()) return@loop
				if (o is LineEffect && ColorResource.COLORLESS == o.color) return@loop
				bgg.run {
					restore()
					init()
				}
				if (bgg is JvmDrawer) {
					if (o is PhysicalObject) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
					else bgg.rotate(o.rotate, o.x, o.y)
				} else bgg.rotate(o.rotate)
				when (o) {
					is FObject.ImageOwner -> {
						unless(o.x + o.image.width <= 0 ||
							o.x >= width ||
							o.y + o.image.height <= 0 ||
							o.y >= height) {
							bgg.drawImage(o.image, o.x, o.y)
						}
					}
					is ShapeObject -> {
						unless((o.x + o.width) <= 0 ||
							o.x >= width ||
							(o.y + o.height) <= 0 ||
							o.y >= height) {
							bgg.color = o.getResource()
							when (o.collideBox) {
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
				if (autoGC && (o.x < -width || o.x > width + width || o.y < -height || o.y > height + height)) {
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
					newFont(b.fontName, b.textSize)
				}
				if (b is FButton) {
					when (b) {
						is FObject.ImageOwner -> {
							unless(b.x + b.image.width < 0 ||
								b.x > width ||
								b.y + b.image.height < 0 ||
								b.y > height) {
								bgg.drawImage(b.image, b.x, b.y)
							}
						}
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
					bgg.color = b.color
					bgg.drawString(b.text, b.x, b.y)
				}
			}
		}
		customDraw(bgg)
	}
}