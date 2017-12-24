package org.frice.util.media

import org.frice.GameFX
import org.frice.event.MOUSE_CLICKED
import org.frice.event.OnMouseEvent
import org.frice.util.message.FLog

class RandomAudioPlayerTest : GameFX(width = 300, height = 300) {
	val player = getRandomPlayer("../TouhouFrice/res/shake.mp3")
	override fun onLastInit() {
		player.start()
	}

	override fun onMouse(e: OnMouseEvent) {
		if (e.type == MOUSE_CLICKED) {
			player.isPlaying = true
			FLog.v(player.state)
		}
	}

	override fun onExit(): Boolean {
		player.stopPlaying()
		return super.onExit()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(RandomAudioPlayerTest::class.java)
		}
	}
}
