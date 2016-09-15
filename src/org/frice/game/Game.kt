package org.frice.game

import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.event.OnWindowEvent
import org.frice.game.resource.FResource
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.graphics.utils.ColorUtils
import org.frice.game.utils.message.error.FatalError
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.misc.forceRun
import org.frice.game.utils.misc.loop
import org.frice.game.utils.misc.loopIf
import org.frice.game.utils.time.FTimer
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JPanel

/**
 * The base game class.
 * this class do rendering, and something which are invisible to
 * game developer.
 *
 * DO NOT override the constructor.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class Game() : AbstractGame(), Runnable {
	private val refresh = FTimer(3)

	private val buffer: BufferedImage

	private val panel: GamePanel
	private val stableBuffer: BufferedImage

	private val bg: Graphics2D
		get() = buffer.graphics as Graphics2D

	private var fpsCounter = 0
	private var fpsDisplay = 0
	private val fpsTimer: FTimer

	init {
		// set icon
		iconImage = ImageIO.read(javaClass.getResourceAsStream("/icon.png"))

		/// to prevent this engine from the call#cking NPE!!
		panel = GamePanel()
		add(panel, BorderLayout.CENTER)
		bounds = AbstractGame.BIG_SQUARE
		fpsTimer = FTimer(1000)
		onInit()
		buffer = BufferedImage(panel.width, panel.height, BufferedImage.TYPE_INT_ARGB)
		stableBuffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		isVisible = true
		Thread(this).start()
		//		insets.set(0, insets.left, insets.bottom, insets.right)
		FLog.v("Engine start!")
		onLastInit()
	}

	/**
	 * set the frame bounds (size and position)
	 */
	override infix fun setBounds(r: Rectangle) {
		super.setBounds(r)
		panel.bounds = r
	}

	/**
	 * set the frame bounds (size and position)
	 */
	override fun setBounds(x: Int, y: Int, width: Int, height: Int) {
		super.setBounds(x, y, width, height)
		panel.setBounds(x, y, width, height)
	}

	/**
	 * set the frame size
	 */
	override fun setSize(width: Int, height: Int) {
		super.setSize(width, height)
		panel.setSize(width, height)
	}

	/**
	 * set the frame size
	 */
	override infix fun setSize(d: Dimension) {
		super.setSize(d)
		panel.size = d
	}

	/**
	 * get a screenShot.
	 *
	 * @return screen cut as an image
	 */
	fun getScreenCut() = ImageResource.create(stableBuffer)

	/**
	 * this method escaped the error
	 *
	 * @return exact position of the mouse
	 */
	override fun getMousePosition() = panel.mousePosition!!

	override fun run() {
		loopIf(!paused && !stopped && refresh.ended()) {
			forceRun {
				onRefresh()
				timeListeners.forEach { it.check() }
				panel.repaint()
				fpsCounter++
				if (fpsTimer.ended()) {
					fpsDisplay = fpsCounter
					fpsCounter = 0
				}
			}
		}
	}

	private fun drawBackground(back: FResource, g: Graphics2D) {
		when (back) {
			is ImageResource -> g.paint = TexturePaint(back.image, Rectangle(0, 0, width, height))
			is ColorResource -> g.color = back.color
			else -> throw FatalError("Unable to draw background")
		}
		g.fillRect(0, 0, width, height)
	}

	/**
	 * Main game view.
	 * all rendering work and game object calculating are here.
	 *
	 * Created by ice1000 on 2016/8/13.
	 * @author ice1000
	 * @since v0.1
	 */
	inner class GamePanel() : JPanel() {
		init {
			addMouseListener(object : MouseListener {
				override fun mouseClicked(e: MouseEvent) {
					click(OnMouseEvent.create(e, OnMouseEvent.MOUSE_CLICK))
					onClick(OnClickEvent.create(e))
				}

				override fun mouseEntered(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_ENTERED))
				override fun mouseExited(e: MouseEvent) = onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_EXITED))
				override fun mouseReleased(e: MouseEvent) {
					mouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
					onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_RELEASED))
				}

				override fun mousePressed(e: MouseEvent) {
					mouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
					onMouse(OnMouseEvent.create(e, OnMouseEvent.MOUSE_PRESSED))
				}
			})

			addWindowListener(object : WindowListener {
				override fun windowDeiconified(e: WindowEvent) = Unit
				override fun windowActivated(e: WindowEvent) {
					loseFocus = false
					onFocus(OnWindowEvent.create(e))
				}

				override fun windowDeactivated(e: WindowEvent) {
					loseFocus = true
					onLoseFocus(OnWindowEvent.create(e))
				}

				override fun windowIconified(e: WindowEvent) = Unit
				override fun windowClosing(e: WindowEvent) = onExit()
				override fun windowClosed(e: WindowEvent) = Unit
				override fun windowOpened(e: WindowEvent) = Unit
			})
		}

		override fun update(g: Graphics?) = paint(g)
		override fun paintComponent(g: Graphics) {
			drawBackground(back, bg)
			drawEverything({ bg })

			if (loseFocus && loseFocusChangeColor) {
				loop(buffer.width) { x ->
					loop(buffer.height) { y ->
						buffer.setRGB(x, y, ColorUtils.darkerRGB(buffer.getRGB(x, y)))
					}
				}
			}

			if (showFPS) bg.drawString("fps: $fpsDisplay", 30, height - 30)

			/*
			 * 厚颜无耻
			 * bg.drawString("Powered by FriceEngine. ice1000", 5, 20)
			 */

			stableBuffer.graphics.drawImage(buffer, 0, 0, this)
			g.drawImage(buffer, 0, 0, this)
		}
	}
}
