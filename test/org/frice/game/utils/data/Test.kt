package org.frice.game.utils.data

import org.frice.game.Game
import org.frice.game.anim.RotateAnim
import org.frice.game.anim.move.AccelerateMove
import org.frice.game.anim.move.SimpleMove
import org.frice.game.anim.scale.SimpleScale
import org.frice.game.event.OnClickEvent
import org.frice.game.obj.button.FButton
import org.frice.game.obj.button.SimpleButton
import org.frice.game.obj.effects.ParticleEffect
import org.frice.game.obj.sub.ShapeObject
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.resource.graphics.ParticleResource
import org.frice.game.utils.graphics.shape.FCircle
import org.frice.game.utils.graphics.shape.FOval
import org.frice.game.utils.graphics.shape.FPoint
import org.frice.game.utils.graphics.shape.FRectangle
import org.frice.game.utils.graphics.utils.ColorUtils.gray
import org.frice.game.utils.message.log.FLog
import org.frice.game.utils.time.FTimeListener
import org.frice.game.utils.time.FTimer
import org.frice.game.utils.time.OnTimeEvent

/**
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */
class Test() : Game() {
	private lateinit var preference: Preference
	private lateinit var xmlPreference: XMLPreference
	private val timer = FTimer(200)

	override fun onInit() {
		super.onInit()
		addTimeListener(FTimeListener(100, object : OnTimeEvent {
			override fun execute() {
				FLog.v("100 ms has passed")
			}
		}))

		addObject(ParticleEffect(ParticleResource(this, width / 2, height / 2), width * 0.25, height * 0.25))
		addObject(SimpleButton(FRectangle(80, 20), "I am a button", 30.0, 30.0, 80.0, 30.0).apply {
			onClickListener = object : FButton.OnClickListener {
				override fun onClick(e: OnClickEvent) {
					addObject(ShapeObject(ColorResource.西木野真姬, FOval(40.0, 20.0), 100.0, 100.0).apply {
						anims.add(SimpleMove(150, 150))
						anims.add(AccelerateMove(-1.0, -1.0))
						anims.add(SimpleScale(1.1, 1.0))
						anims.add(RotateAnim(0.1))
					})
				}
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
		}
	}

	override fun onClick(e: OnClickEvent) {
		super.onClick(e)
		FLog.v(e.toString())
		FLog.v(mousePosition)
	}

	override fun onExit() {
		System.exit(0)
	}
}

fun main(args: Array<String>) {
	Test()
}