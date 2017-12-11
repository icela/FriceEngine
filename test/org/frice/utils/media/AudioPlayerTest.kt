package org.frice.utils.media

import org.frice.GameFX

class AudioPlayerTest : GameFX() {
	val player = getPlayer("../TouhouFrice/res/bgm.mp3", true)
	override fun onLastInit() {
		player.start()
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			launch(AudioPlayerTest::class.java)
		}
	}
}
