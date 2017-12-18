package org.frice.utils.media

import org.frice.GameFX
import org.frice.event.MOUSE_CLICKED
import org.frice.event.OnMouseEvent
import org.frice.utils.message.FLog

class AudioPlayerTest : GameFX(300, 300) {
	val player = getPlayer("../TouhouFrice/res/shake.mp3", true)
	val player2 = getPlayer("shake.wav", true)
	override fun onLastInit() {
		player.start()
	}

	override fun onMouse(e: OnMouseEvent) {
		if (e.type == MOUSE_CLICKED) {
			if (!player.stopped) {
				player.stopPlaying()
				player.close()
				player2.start()
			} else if (!player2.stopped) player2.stopPlaying()
		}
		FLog.v("${player.state}, ${player2.state}")
	}

	override fun onExit(): Boolean {
		player.stopPlaying()
		player2.stopPlaying()
		return super.onExit()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(AudioPlayerTest::class.java)
		}
	}
}
