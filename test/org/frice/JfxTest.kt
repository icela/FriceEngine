package org.frice

import org.frice.anim.move.*
import org.frice.anim.scale.SimpleScale
import org.frice.event.OnMouseEvent
import org.frice.obj.PhysicalObject
import org.frice.obj.button.SimpleButton
import org.frice.obj.button.SimpleText
import org.frice.obj.sub.ShapeObject
import org.frice.resource.graphics.ColorResource
import org.frice.utils.graphics.greyify
import org.frice.utils.message.FLog
import org.frice.utils.shape.*
import org.frice.utils.time.FTimer
import java.util.*
import java.util.function.Consumer
import kotlin.test.assertEquals

fun main(args: Array<String>) {
	val obj = O()
	launch(obj)
}

class TestFx : GameFX(width = 600, height = 600) {
	private val timer = FTimer(200)
	private val objs = LinkedList<PhysicalObject>()

	override fun onExit() {
		System.exit(0)
	}

	override fun onInit() {
		super.onInit()
		autoGC = true

		addObject(SimpleButton(
			text = "I am a button",
			x = 30.0,
			y = 30.0,
			width = 100.0,
			height = 30.0).apply {
			onMouseListener = Consumer {
				ShapeObject(ColorResource.西木野真姬, FOval(40.0, 30.0), 100.0, 100.0).run {
					mass = 1.0
					addForce(-1.0, -1.0)
					anims.add(SimpleMove(200, 200))
					anims.add(SimpleScale(1.1, 1.1))
					objs += this
					addObject(this)
				}
			}
		})

		FLog.v(ColorResource.小泉花阳.color.rgb.greyify())
	}

	private val random = Random(System.currentTimeMillis())

	override fun onMouse(e: OnMouseEvent) {
		super.onMouse(e)
		if (timer.ended()) {
			objs.removeAll { o -> o.died }
			addObject(ShapeObject(ColorResource.IntelliJ_IDEA黑, FCircle(10.0), e.x, e.y).apply {
				anims.add(AccelerateMove.getGravity())
				anims.add(AccurateMove(random.nextInt(400) - 200.0, 0.0))
				targets.clear()
				objs.forEach { o ->
					addCollider(o, {
						anims.clear()
						targets.clear()
						anims.add(SimpleMove(0, -300))
						anims.add(SimpleScale(1.1, 1.1))
						res = ColorResource.MAGENTA
					})
				}
			})
		}

	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(TestFx::class.java)
		}
	}
}

class O : GameFX() {
	val timer = FTimer(200)
	lateinit var obj: ShapeObject
	lateinit var obj2: ShapeObject

	override fun onInit() {
		obj2 = ShapeObject(ColorResource.天依蓝, FRectangle(20, 20), 200.0, 200.0, 233)
		obj = ShapeObject(ColorResource.西木野真姬, FCircle(30.0), 100.0, 100.0, 233).apply {
			mass = 1.0
			addAnim(SimpleMove(80, 0))
		}
		val text = SimpleText(ColorResource.BLUE, "this is a text demo", 100.0, 300.0)
		text.textSize = 64.0
		assertEquals(obj, obj2)
		addObject(obj2, obj, text)
	}

	override fun onRefresh() {
		if (timer.ended()) {
		}
	}

	override fun onLastInit() {
		// title = "Your awesome title"
		// not allowed
	}
}
