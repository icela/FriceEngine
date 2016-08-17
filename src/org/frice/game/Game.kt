package org.frice.game

import org.frice.game.obj.FObject
import org.frice.game.obj.effects.ParticleEffect
import org.frice.game.obj.sub.ImageObject
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.FResource
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.image.ImageResource
import org.frice.game.utils.forceRun
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.loopIf
import org.frice.game.utils.message.error.FatalError
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.pause
import org.frice.game.utils.time.FTimeListener
import java.awt.*
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JPanel

/**
 * Do not override the constructor.
 *
 * Created by ice1000 on 2016/8/13.
 * @author ice1000
 * @since v0.1
 */
open class Game() : AbstractGame(), Runnable {
	private val panel = GamePanel()
	private val objects = ArrayList<FObject>()
	private val timeListeners = ArrayList<FTimeListener>()
	private val buffer: BufferedImage
	private val bg: Graphics
		get() = buffer.graphics

	init {
		isResizable = false
		add(panel, BorderLayout.CENTER)
		setBounds(200, 200, 640, 480)
		onInit()
		buffer = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
		Thread(this).start()
		isVisible = true
		insets.set(0, insets.left, insets.bottom, insets.right)
		FLog.v("Engine start!")
	}

	protected fun addObject(obj: FObject) = objects.add(obj)
	protected fun addObjects(objs: Array<FObject>) = objects.addAll(objs)
	protected fun clearObjects() = objects.clear()
	protected fun removeObject(obj: FObject) = objects.remove(obj)
	protected fun removeObjects(objs: Array<FObject>) = objects.removeAll(objs)

	fun addTimeListener(listener: FTimeListener) = timeListeners.add(listener)
	fun addTimeListeners(listeners: Array<FTimeListener>) = timeListeners.addAll(listeners)
	fun removeTimeListener(listener: FTimeListener) = timeListeners.remove(listener)
	fun removeTimeListeners(listeners: Array<FTimeListener>) = timeListeners.removeAll(listeners)

	override fun setBounds(r: Rectangle) {
//		r.height += insets.top
		super.setBounds(r)
		panel.bounds = r
	}

	override fun setBounds(x: Int, y: Int, width: Int, height: Int) {
//		FLog.debug("insets.top = ${insets.top}")
		super.setBounds(x, y, width, height)
		panel.setBounds(x, y, width, height)
	}

	override fun setSize(width: Int, height: Int) {
		super.setSize(width, height)
		panel.setSize(width, height)
	}

	override fun setSize(d: Dimension) {
		super.setSize(d)
		panel.size = d
	}

	protected fun setCursor(o: ImageResource) = setCursor(ImageObject(o))
	protected fun setCursor(o: ImageObject) {
		cursor = toolkit.createCustomCursor(o.getImage(), Point(0, 0), "cursor")
	}

	protected fun getScreenCut() = ImageResource.create(BufferedImage(width, height,
			BufferedImage.TYPE_INT_ARGB).apply {
		graphics.drawImage(buffer, width, height, this@Game)
	})

	override fun run() {
		loopIf({ !paused && !stopped }) {
			forceRun { onRefresh() }
			timeListeners.forEach { it.check() }
			panel.repaint()
			pause(1000.0 / refreshPerSecond)
		}
	}

	private fun drawBackground(back: FResource) {
		when (back) {
			is ImageResource -> bg.drawImage(back.image.getScaledInstance(width, height, 0), 0, 0, this)
			is ColorResource -> {
				val g = bg
				g.color = back.color
				g.fillRect(0, 0, width, height)
			}
			else -> throw FatalError("Unable to draw background")
		}
	}

	/**
	 * Created by ice1000 on 2016/8/13.
	 * @author ice1000
	 * @since v0.1
	 */
	inner class GamePanel : JPanel() {

		override fun update(g: Graphics?) = paint(g)
		override fun paint(g: Graphics) {
			drawBackground(back)
			objects.forEach { o ->
				o.runAnims()
				o.checkCollision()
			}
			g.drawImage(buffer, 0, 0, this)
			objects.forEach { o ->
				when (o) {
					is ParticleEffect ->
						bg.drawImage(o.getResource().getResource(), o.x.toInt(), o.y.toInt(), this)
					is ImageObject ->
						bg.drawImage(o.getImage(), o.x.toInt(), o.y.toInt(), this)
					is ShapeObject -> {
						val bgg = bg
						bgg.color = o.getResource().color
						when (o.collideBox) {
							is FRectangle -> bgg.fillRect(
									o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt())
							is FOval -> bgg.fillOval(
									o.x.toInt(),
									o.y.toInt(),
									o.width.toInt(),
									o.height.toInt())
						}
					}
				}
			}
			g.drawImage(buffer, 0, 0, this)
		}
	}
}
