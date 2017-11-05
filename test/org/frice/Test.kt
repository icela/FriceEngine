package org.frice

import org.frice.Initializer.launch
import org.frice.obj.SideEffect
import org.frice.resource.graphics.ColorResource
import org.frice.utils.data.Preference
import org.frice.utils.data.XMLPreference
import org.frice.utils.graphics.shape.*
import org.frice.utils.graphics.utils.grayify
import org.frice.utils.message.FLog
import org.frice.utils.time.FTimeListener
import org.frice.utils.time.FTimer
import java.util.*
import kotlin.test.assertEquals

/**
 * Sample
 * Created by ice1000 on 2016/8/21.
 *
 * @author ice1000
 */
class Test : org.frice.Game() {
	private lateinit var preference: Preference
	private lateinit var xmlPreference: XMLPreference
	private val timer = FTimer(200)
	private val objs = LinkedList<org.frice.obj.PhysicalObject>()

	override fun onInit() {
		super.onInit()
		autoGC = true

		addTimeListener(FTimeListener(400, timeUp = { FLog.v("400 ms has passed") }))

		addObject(org.frice.obj.effects.ParticleEffect(org.frice.resource.graphics.ParticleResource(
				this, width / 10, height / 10, 0.01), width * 0.1, height * 0.1))
		addObject(org.frice.obj.button.SimpleButton(
				text = "I am a button",
				x = 30.0,
				y = 30.0,
				width = 100.0,
				height = 30.0).apply {
			onClickListener = {
				val obj = org.frice.obj.sub.ShapeObject(org.frice.resource.graphics.ColorResource.Companion.西木野真姬, FOval(40.0, 30.0), 100.0, 100.0).apply {
					mass = 1.0
					addForce(-1.0, -1.0)
					anims.add(org.frice.anim.move.SimpleMove(400, 400))
					anims.add(org.frice.anim.scale.SimpleScale(1.1, 1.1))
					anims.add(org.frice.anim.RotateAnim(0.1))
				}
				objs.add(obj)
				addObject(obj)
			}
		})
//		getPlayer("1.wav").start()
//		play("1.wav")

//		setCursor(WebImageResource("https://avatars1.githubusercontent.com/u/16477304?v=3&s=84"))

		preference = Preference("settings.properties")
		preference.insert("fuck", "microsoft")

		xmlPreference = XMLPreference("settings.xml")
		xmlPreference.insert("shit", "goddamn it")

		FLog.v(preference.query("fuck", "Apple"))
		FLog.v(xmlPreference.query("shit", "no we don't"))

//		FOval(1.0, 1.0)
		FCircle(1.0)
		FPoint(1, 2)

//		addObject(ImageObject(FileImageResource("1.png"), 10.0, 10.0))

		FLog.v(org.frice.resource.graphics.ColorResource.小泉花阳.color.rgb.grayify())
	}

	override fun onRefresh() {
		super.onRefresh()
		if (timer.ended()) {
			objs.removeAll { o -> o.died }
			addObject(org.frice.obj.sub.ShapeObject(org.frice.resource.graphics.ColorResource.Companion.IntelliJ_IDEA黑, FCircle(10.0),
					mouse.x, mouse.y).apply {
				anims.add(org.frice.anim.move.AccelerateMove.getGravity())
				anims.add(org.frice.anim.move.AccurateMove(random.nextInt(400) - 200.0, 0.0))
				targets.clear()
				objs.forEach { o ->
					addCollider(o, {
						anims.clear()
						targets.clear()
						anims.add(org.frice.anim.move.SimpleMove(0, -300))
						anims.add(org.frice.anim.scale.SimpleScale(1.1, 1.1))
						res = org.frice.resource.graphics.ColorResource.MAGENTA
					})
				}
			})
		}
	}

	override fun onClick(e: org.frice.event.OnClickEvent) {
		super.onClick(e)
		FLog.v(e.toString())
		FLog.v(mouse)
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(Test::class.java)
		}
	}
}

class TestImage : org.frice.Game() {
	override fun onInit() {
		super.onInit()
		addObject(org.frice.obj.sub.ImageObject((org.frice.resource.image.WebImageResource(
				"https://avatars1.githubusercontent.com/u/21008243?v=3&s=200").image as org.frice.platform.adapter.JvmImage).greenify(),
				10.0, 10.0))
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) = launch(TestImage::class.java)
	}
}


/**
 * Created by ice1000 on 2016/9/11.
 *
 * @author ice1000
 */
class Test2 : org.frice.Game() {
	val timer = FTimer(200)
	lateinit var obj: org.frice.obj.sub.ShapeObject
	lateinit var obj2: org.frice.obj.sub.ShapeObject

	@org.junit.Test
	override fun onInit() {
		obj2 = org.frice.obj.sub.ShapeObject(org.frice.resource.graphics.ColorResource.Companion.天依蓝, FRectangle(20, 20), 200.0, 200.0, 233).apply {
			mass = 2.0
		}
		obj = org.frice.obj.sub.ShapeObject(org.frice.resource.graphics.ColorResource.Companion.西木野真姬, FCircle(30.0), 100.0, 100.0, 233).apply {
			mass = 1.0
			anims.add(org.frice.anim.move.SimpleMove(80, 0))
		}
		val text = org.frice.obj.button.SimpleText(org.frice.resource.graphics.ColorResource.BLUE, "this is a text demo", 100.0, 300.0)
		text.textSize = 64
		assertEquals(obj, obj2)
		addObject(obj2, obj, text)
	}

	override fun onRefresh() {
		if (timer.ended()) {
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) = launch(Test2::class.java)
	}
}


/**
 * Created by ice1000 on 2016/10/22.
 *
 * @author ice1000
 */
class Test3 : org.frice.Game() {
	lateinit var a: org.frice.obj.sub.ShapeObject
	val d = 3.14 * 6
	val e = 0.1
	val c = 0.1
	override fun onInit() {
		setSize(1000, 1000)
		a = org.frice.obj.sub.ShapeObject(org.frice.resource.graphics.ColorResource.BLUE, FCircle(10.0), 100.0, 500.0)
		a.anims.add(object : org.frice.anim.move.CustomMove() {
			override fun getXDelta(timeFromBegin: Double) =
					(a.x * c * Math.sin(d) - a.y * c * Math.cos(d)) * e

			override fun getYDelta(timeFromBegin: Double) =
					(a.x * c * Math.cos(d) - a.y * c * Math.sin(d)) * e
		})
		addObject(a)
	}

	override fun onExit() = System.exit(0)

	companion object {
		@JvmStatic
		fun main(args: Array<String>) = launch(Test3::class.java)
	}
}

class Test4 : org.frice.Game(2) {
	override fun onInit() {
		addObject(0, org.frice.obj.effects.FunctionEffect({
			Math.sin(it / 10) * 10 + 100
		}, 10.0, 10.0, 300, 300))
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) = launch(Test4::class.java)
	}
}

