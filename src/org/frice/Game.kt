@file:Suppress("unused")

package org.frice

import org.frice.event.OnMouseEvent
import org.frice.obj.*
import org.frice.obj.button.FButton
import org.frice.obj.button.SimpleButton
import org.frice.obj.effects.LineEffect
import org.frice.obj.sub.ImageObject
import org.frice.obj.sub.ShapeObject
import org.frice.platform.*
import org.frice.platform.adapter.JvmDrawer
import org.frice.platform.adapter.JvmImage
import org.frice.resource.graphics.ColorResource
import org.frice.resource.image.ImageResource
import org.frice.utils.message.FDialog
import org.frice.utils.message.FLog
import org.frice.utils.misc.unless
import org.frice.utils.shape.FOval
import org.frice.utils.shape.FRectangle
import org.frice.utils.time.FClock
import org.frice.utils.time.FTimer
import java.awt.BorderLayout
import java.awt.Point
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.function.Consumer
import javax.imageio.ImageIO.read
import javax.swing.JFrame

/**
 * First game class(not for you)
 *
 * Standard library, mainly for GUI.
 * some other library is in @see
 * The base game class.
 * this class do rendering, and something which are invisible to
 * game developer.
 *
 * DO NOT override the constructor.
 *
 * Created by ice1000 on 2016/8/15.
 * @author ice1000
 * @since v0.2.3
 */
@Suppress("LeakingThis")
open class Game
@JvmOverloads
constructor(layerCount: Int = 1) : JFrame(), FriceGame {
	override val layers = Array<Layer>(layerCount) { Layer() }

	override var paused = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override var stopped = false
		set(value) {
			if (value) FClock.pause() else FClock.resume()
			field = value
		}

	override var debug = true
	override var autoGC = true
	override var showFPS = true

	override var loseFocus = false
		set(value) = Unit

	override var loseFocusChangeColor = true

	@get:JvmName(" refresh")
	internal val refresh = FTimer(4)
	override var millisToRefresh: Int
		get () = refresh.time
		set (value) {
			refresh.time = value
		}

	internal val panel: SwingGamePanel = SwingGamePanel(this)

	override val drawer: JvmDrawer

	var fpsCounter = 0
	var fpsDisplay = 0
	var fpsTimer = FTimer(1000)

	/**
	 * represent the mouse as an object
	 */
	@JvmField
	val mouse = object : AbstractObject {
		override var x: Double
			get() = panel.mousePosition?.getX() ?: -1.0
			set(value) {
				panel.mousePosition?.setLocation(value, y)
			}

		override var y: Double
			get() = panel.mousePosition?.getY() ?: -1.0
			set(value) {
				panel.mousePosition?.setLocation(x, value)
			}

		override var rotate = 0.0
		override fun toString() = "($x, $y)"
	}

	init {
		isResizable = false
		layout = BorderLayout()
		// set icon
		iconImage = read(javaClass.getResourceAsStream("/icon.png"))

		/// to prevent this engine from the call#cking NPE!!
		this.add(panel, BorderLayout.CENTER)
		bounds = BIG_SQUARE
		onInit()
		drawer = JvmDrawer(this)
		drawer.init()
		isVisible = true
		FLog.i("If the window doesn't appear, please call `launch(YourGameClass.class)` instead of the constructor.")
	}

	override fun onExit() {
		if (FDialog(this).confirm("Are you sure to exit?", "Ensuring", FDialog.YES_NO_OPTION) == FDialog.YES_OPTION) {
			dispose()
			System.exit(0)
		} else return
	}

	/**
	 * for kotlin only
	 * add keyboard listeners with lambda
	 */
	fun addKeyListener(
		typed: Consumer<KeyEvent>? = null,
		pressed: Consumer<KeyEvent>? = null,
		released: Consumer<KeyEvent>? = null) {
		addKeyListener(object : KeyListener {
			override fun keyPressed(e: KeyEvent) {
				pressed?.accept(e)
			}

			override fun keyReleased(e: KeyEvent) {
				released?.accept(e)
			}

			override fun keyTyped(e: KeyEvent) {
				typed?.accept(e)
			}
		})
	}

	/**
	 * get a screenShot.
	 *
	 * @return screen cut as an image
	 */
	fun getScreenCut() = ImageResource(drawer.friceImage)

	fun addKeyTypedEvent(keyCode: Int, key: Consumer<KeyEvent>)
		= addKeyListener(typed = Consumer { e -> if (e.keyCode == keyCode) key.accept(e) })

	fun addKeyPressedEvent(keyCode: Int, key: Consumer<KeyEvent>)
		= addKeyListener(pressed = Consumer { e -> if (e.keyCode == keyCode) key.accept(e) })

	fun addKeyReleasedEvent(keyCode: Int, key: Consumer<KeyEvent>)
		= addKeyListener(released = Consumer { e -> if (e.keyCode == keyCode) key.accept(e) })

	infix fun setCursor(o: ImageResource) = setCursor(ImageObject(o))
	infix fun setCursor(o: ImageObject) {
		cursor = toolkit.createCustomCursor((o.image as JvmImage).image, Point(0, 0), "cursor")
	}
}
