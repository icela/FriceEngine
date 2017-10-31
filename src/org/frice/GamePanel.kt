package org.frice

import org.frice.utils.time.FClock
import java.awt.Graphics
import java.awt.event.*
import javax.swing.JPanel

/**
 * all rendering work and game object calculating are here.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
class GamePanel(private val game: org.frice.Game) : JPanel() {
	init {
		addMouseListener(object : MouseListener {
			override fun mouseClicked(e: MouseEvent) {
				click(org.frice.event.OnMouseEvent(e, org.frice.event.OnMouseEvent.MOUSE_CLICK))
				onClick(org.frice.event.OnClickEvent(e))
			}

			override fun mouseEntered(e: MouseEvent) = onMouse(org.frice.event.OnMouseEvent(e, org.frice.event.OnMouseEvent.MOUSE_ENTERED))
			override fun mouseExited(e: MouseEvent) = onMouse(org.frice.event.OnMouseEvent(e, org.frice.event.OnMouseEvent.MOUSE_EXITED))
			override fun mouseReleased(e: MouseEvent) {
				mouse(org.frice.event.OnMouseEvent(e, org.frice.event.OnMouseEvent.MOUSE_RELEASED))
				onMouse(org.frice.event.OnMouseEvent(e, org.frice.event.OnMouseEvent.MOUSE_RELEASED))
			}

			override fun mousePressed(e: MouseEvent) {
				mouse(org.frice.event.OnMouseEvent(e, org.frice.event.OnMouseEvent.MOUSE_PRESSED))
				onMouse(org.frice.event.OnMouseEvent(e, org.frice.event.OnMouseEvent.MOUSE_PRESSED))
			}
		})

		addWindowListener(object : WindowListener {
			override fun windowDeiconified(e: WindowEvent) = Unit
			override fun windowActivated(e: WindowEvent) {
				loseFocus = false
				FClock.resume()
				onFocus()
			}

			override fun windowDeactivated(e: WindowEvent) {
				loseFocus = true
				FClock.pause()
				onLoseFocus()
			}

			override fun windowIconified(e: WindowEvent) = Unit
			override fun windowClosing(e: WindowEvent) = onExit()
			override fun windowClosed(e: WindowEvent) = Unit
			override fun windowOpened(e: WindowEvent) = Unit
		})
	}

	override fun update(g: Graphics?) = paint(g)
	override fun paintComponent(g: Graphics) {
		clearScreen()
		drawEverything(drawer)

		if (loseFocus and loseFocusChangeColor) {
			repeat(drawer.friceImage.width) { x: Int ->
				repeat(drawer.friceImage.height) { y: Int ->
					drawer.friceImage[x, y] = org.frice.resource.graphics.ColorResource(drawer
							.friceImage[x, y]
							.color
							.darker())
				}
			}
		}

		drawer.init()
		drawer.color = org.frice.resource.graphics.ColorResource.DARK_GRAY
		if (showFPS) drawer.drawString("fps: ${fpsDisplay}", 30.0, height - 30.0)

		/*
		 * 厚颜无耻
		 * drawer.drawString("Powered by FriceEngine. ice1000", 5, 20)
		 */
		g.drawImage(drawer.friceImage.image, 0, 0, this)
	}
}
