package org.frice.game

import org.frice.game.anim.RotateAnim
import org.frice.game.anim.move.AccelerateMove
import org.frice.game.anim.move.AccurateMove
import org.frice.game.anim.move.SimpleMove
import org.frice.game.anim.scale.SimpleScale
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.FObject
import org.frice.game.obj.PhysicalObject
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.effects.ParticleEffect
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.ParticleResource
import org.frice.game.utils.data.Preference
import org.frice.game.utils.data.XMLPreference
import org.frice.game.utils.graphics.shape.FCircle
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FPoint
import org.frice.game.utils.graphics.utils.gray
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import java.util.*

/**
 * Sample
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */
class Test : Game() {
	private lateinit var preference: Preference
	private lateinit var xmlPreference: XMLPreference
	private val timer = FTimer(200)
	private val objs = LinkedList<PhysicalObject>()

	override fun onInit() {
		super.onInit()
		autoGC = true

		addTimeListener(FTimeListener(400, { FLog.v("400 ms has passed") }))

		addObject(ParticleEffect(ParticleResource(this, width / 10, height / 10, 0.01), width * 0.1, height * 0.1))
		addObject(SimpleButton(
				text = "I am a button",
				x = 30.0,
				y = 30.0,
				width = 100.0,
				height = 30.0).apply {
			onClickListener = {
				val obj = ShapeObject(ColorResource.Companion.西木野真姬, FOval(40.0, 30.0), 100.0, 100.0).apply {
					mass = 1.0
					addForce(-1.0, -1.0)
					anims.add(SimpleMove(400, 400))
					anims.add(SimpleScale(1.1, 1.1))
					anims.add(RotateAnim(0.1))
				}
				objs.add(obj)
				addObject(obj)
			}
		})
		//		AudioManager.getPlayer("1.wav").start()
		//		AudioManager.play("1.wav")

		//		setCursor(WebImageResource("https://avatars1.githubusercontent.com/u/16477304?v=3&s=84"))

		preference = Preference("settings.properties")
		preference.insert("fuck", "microsoft")

		xmlPreference = XMLPreference("settings.xml")
		xmlPreference.insert("shit", "goddamn it")

		FLog.v(preference.query("fuck", "Apple"))
		FLog.v(xmlPreference.query("shit", "no we don't"))

		FOval(1.0, 1.0)
		FCircle(1.0)
		FPoint(1, 2)

		//		addObject(ImageObject(FileImageResource("1.png"), 10.0, 10.0))
		//		addObject(ImageObject(WebImageResource("https://avatars1.githubusercontent.com/u/21008243?v=3&s=200"),
		//				10.0, 10.0))

		FLog.v(ColorResource.小泉花阳.color.rgb.gray())
	}

	override fun onRefresh() {
		super.onRefresh()
		if (timer.ended()) {
			objs.removeAll { o -> o.died }
			addObject(ShapeObject(ColorResource.Companion.IntelliJ_IDEA黑, FCircle(10.0),
					mousePosition.x.toDouble(), mousePosition.y.toDouble()).apply {
				anims.add(AccelerateMove.getGravity())
				anims.add(AccurateMove(random.nextInt(400) - 200.0, 0.0))
				targets.clear()
				objs.forEach { o ->
					targets.add(Pair(o, object : FObject.OnCollideEvent {
						override fun handle() {
							anims.clear()
							targets.clear()
							anims.add(SimpleMove(0, -300))
							anims.add(SimpleScale(1.1, 1.1))
							res = ColorResource.MAGENTA
						}
					}))
				}
			})
		}
	}

	override fun onClick(e: OnClickEvent) {
		super.onClick(e)
		FLog.v(e.toString())
		FLog.v(mousePosition)
	}

	override fun onExit() = System.exit(0)
}

fun main(args: Array<String>) {
	Test()
}