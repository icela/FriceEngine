package org.frice.platform

import org.frice.event.DelayedEvent
import org.frice.event.OnMouseEvent
import org.frice.obj.*
import org.frice.obj.button.*
import org.frice.obj.effects.LineEffect
import org.frice.obj.sub.ImageObject
import org.frice.obj.sub.ShapeObject
import org.frice.platform.adapter.JvmImage
import org.frice.platform.owner.*
import org.frice.resource.graphics.ColorResource
import org.frice.resource.image.ImageResource
import org.frice.util.EventManager
import org.frice.util.shape.*

interface FriceGame : TitleOwner, Sized, Resizable, Collidable {
	val layers: Array<Layer>
	val eventManager: EventManager

	var activeArea: FShapeQuad?
	val defaultActiveArea
		get() = object : FShapeQuad {
			override val x get() = 0.0
			override val y get() = 0.0
			override val width get() = this@FriceGame.width.toDouble()
			override val height get() = this@FriceGame.height.toDouble()
		}

	override val box: FShapeQuad
		get() = activeArea ?: defaultActiveArea

	/**
	 * not implemented yet.
	 * currently it's same as paused.
	 */
	var stopped: Boolean
	var debug: Boolean

	/** if true, the engine will collect all objects which are invisible from game window. */
	var autoGC: Boolean
		get() = layers.all(Layer::autoGC)
		set(value) {
			layers.forEach { it.autoGC = value }
		}

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

	fun getLayers(int: Int) = layers[int]

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
	fun removeObject(layer: Int, vararg objects: AbstractObject) = objects.forEach { removeObject(layer, it) }

	/** remove objects unsafely using vararg */
	fun instantRemoveObject(layer: Int, vararg objects: AbstractObject) =
		objects.forEach { instantRemoveObject(layer, it) }

	/** remove objects */
	fun removeObject(layer: Int, objects: AttachedObjects) = objects.objs.forEach { removeObject(layer, it) }

	/** remove objects unsafely */
	fun instantRemoveObject(layer: Int, objects: AttachedObjects) =
		objects.objs.forEach { instantRemoveObject(layer, it) }

	/** remove objects */
	fun removeObject(layer: Int, objects: AttachedAbstractObjects) = objects.objs.forEach { removeObject(layer, it) }

	/** remove objects unsafely */
	fun instantRemoveObject(layer: Int, objects: AttachedAbstractObjects) =
		objects.objs.forEach { instantRemoveObject(layer, it) }

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
	fun addObject(layer: Int, vararg objects: AbstractObject) = objects.forEach { addObject(layer, it) }

	/** add objects */
	fun addObject(layer: Int, objects: AttachedObjects) = objects.objs.forEach { addObject(layer, it) }

	/** add objects */
	fun addObject(layer: Int, objects: AttachedAbstractObjects) = objects.objs.forEach { addObject(layer, it) }

	fun addObject(vararg objects: AbstractObject) = addObject(0, *objects)
	fun removeObject(vararg objects: AbstractObject) = removeObject(0, *objects)

	/** adds an object to game unsafely, to be shown on game window. */
	fun instantAddObject(layer: Int, obj: AbstractObject) = layers[layer].instantAddObject(obj)

	/** add objects unsafely using vararg */
	fun instantAddObject(layer: Int, vararg objects: AbstractObject) = objects.forEach { instantAddObject(layer, it) }

	/** add objects unsafely using vararg */
	fun instantAddObject(layer: Int, objects: AttachedObjects) = objects.objs.forEach { instantAddObject(layer, it) }

	/** add objects unsafely using vararg */
	fun instantAddObject(layer: Int, objects: AttachedAbstractObjects) =
		objects.objs.forEach { instantAddObject(layer, it) }

	/** add objects unsafely using vararg */
	fun instantAddObject(vararg objects: AbstractObject) = instantAddObject(0, *objects)

	/** remove objects unsafely using vararg */
	fun instantRemoveObject(vararg objects: AbstractObject) = instantRemoveObject(0, *objects)

	/** draw a white square */
	fun clearScreen(drawer: FriceDrawer) {
		drawer.color = ColorResource.WHITE
		drawer.drawRect(0.0, 0.0, width.toDouble(), height.toDouble())
		drawer.restore()
	}

	/** check buttons' events */
	fun mouse(e: OnMouseEvent) = layers.forEach {
		it.buttons.forEach { b -> if (b.containsPoint(e.x, e.y)) b onMouse e }
	}

	fun runLater(delayedEvent: DelayedEvent) = eventManager.insert(delayedEvent)
	fun runLater(millisFromNow: Long, event: SideEffect) = runLater(DelayedEvent.millisFromNow(millisFromNow, event))
	fun runFromStart(millisFromStart: Long, event: SideEffect) = runLater(DelayedEvent(millisFromStart, event))

	/** calling onRefresh and do checking */
	fun adjust() {
		onRefresh()
		eventManager.check()
	}

	/**
	 * Doing everything related to game objects:
	 * removing died objects, deal with animations, draw objects on screen
	 *
	 * @author ice1000
	 * @param bgg the drawer used to draw
	 */
	fun dealWithObjects(bgg: FriceDrawer) {
		processBuffer()
		layers.forEach {
			it.objects.removeIf removing@ { o ->
				if (it.autoGC) {
					if ((o is PhysicalObject && !box.collides(o)) || (o.x > width || o.y > height)) {
						o.died = true
						return@removing true
					}
				}
				(o as? FObject)?.`{-# runAnims #-}`()
				return@removing o.died
			}

			it.texts.removeIf removing@ { o ->
				if (it.autoGC) {
					if (o.x > width || o.y > height) {
						o.died = true
						return@removing true
					}
				}
				return@removing o.died
			}

			it.objects.forEach loop@ { o ->
				if (!o.isVisible) return@loop
				if (o is ColorOwner && ColorResource.COLORLESS == o.color) return@loop
				bgg.init()
				if (o is FContainer) bgg.rotate(o.rotate, o.x + o.width / 2, o.y + o.height / 2)
				else bgg.rotate(o.rotate, o.x, o.y)
				when (o) {
					is ImageOwner -> if (defaultActiveArea.collides(o)) bgg.drawImage(o.image, o.x, o.y)
					is ShapeObject -> {
						if (defaultActiveArea.collides(o)) {
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
				bgg.restore()
			}

			it.texts.forEach loop@ { b ->
				if (!b.isVisible) return@loop
				if (b.color == ColorResource.COLORLESS) return@loop
				bgg.init()
				bgg.rotate(b.rotate)
				bgg.useFont(b)
				if (b is FButton) {
					when (b) {
						is ImageOwner -> if (defaultActiveArea.collides(b)) bgg.drawImage(b.image, b.x, b.y)
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
				bgg.restore()
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
