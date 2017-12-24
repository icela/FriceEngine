package org.frice.util.media

import org.frice.util.until
import java.io.File

class RandomAudioPlayer(file: File) : LoopAudioPlayer(file) {
	var isPlaying = false

	override fun run() {
		openLine()
		until(stopped) {
			if (isPlaying) {
				isPlaying = false
				if (cache.isEmpty()) firstPlay() else subsequentPlay()
			} else {
				line.write(byteArrayOf(), 0, 0)
			}
		}
		close()
	}
}
