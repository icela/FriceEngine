package org.frice

import org.frice.event.OnClickEvent
import org.frice.event.OnMouseEvent
import org.frice.resource.graphics.ColorResource
import org.frice.utils.time.FClock
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JComponent

/**
 * all rendering work and game object calculating are here.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class SwingGamePanel(private val game: Game) : JComponent() {
	init {
		addMouseListener(object : MouseListener {
			override fun mouseClicked(e: MouseEvent) {
				game.click(OnMouseEvent(e, OnMouseEvent.MOUSE_CLICK))
				game.onClick(OnClickEvent(e))
			}

			override fun mouseEntered(e: MouseEvent) = game.onMouse(OnMouseEvent(e, OnMouseEvent.MOUSE_ENTERED))
			override fun mouseExited(e: MouseEvent) = game.onMouse(OnMouseEvent(e, OnMouseEvent.MOUSE_EXITED))
			override fun mouseReleased(e: MouseEvent) {
				game.mouse(OnMouseEvent(e, OnMouseEvent.MOUSE_RELEASED))
				game.onMouse(OnMouseEvent(e, OnMouseEvent.MOUSE_RELEASED))
			}

			override fun mousePressed(e: MouseEvent) {
				game.mouse(OnMouseEvent(e, OnMouseEvent.MOUSE_PRESSED))
				game.onMouse(OnMouseEvent(e, OnMouseEvent.MOUSE_PRESSED))
			}
		})

		game.addWindowListener(object : WindowListener {
			override fun windowDeiconified(e: WindowEvent) = Unit
			override fun windowActivated(e: WindowEvent) {
				game.loseFocus = false
				FClock.resume()
				game.onFocus()
			}

			override fun windowDeactivated(e: WindowEvent) {
				game.loseFocus = true
				FClock.pause()
				game.onLoseFocus()
			}

			override fun windowIconified(e: WindowEvent) = Unit
			override fun windowClosing(e: WindowEvent) = game.onExit()
			override fun windowClosed(e: WindowEvent) = Unit
			override fun windowOpened(e: WindowEvent) = Unit
		})
	}

	override fun update(g: Graphics?) = paint(g)
	override fun paintComponent(g: Graphics) {
		game.clearScreen()
		game.drawEverything(game.drawer)

		if (game.loseFocus and game.loseFocusChangeColor) {
			repeat(game.drawer.friceImage.width) { x: Int ->
				repeat(game.drawer.friceImage.height) { y: Int ->
					game.drawer.friceImage[x, y] = ColorResource(game.drawer
							.friceImage[x, y]
							.color
							.darker())
				}
			}
		}

		game.drawer.init()
		game.drawer.color = ColorResource.DARK_GRAY
		if (game.showFPS) game.drawer.drawString("fps: ${game.fpsDisplay}", 30.0, height - 30.0)

		/*
		 * 厚颜无耻
		 * drawer.drawString("Powered by FriceEngine. ice1000", 5, 20)
		 */
		g.drawImage(game.drawer.friceImage.image, 0, 0, this)
	}
}
