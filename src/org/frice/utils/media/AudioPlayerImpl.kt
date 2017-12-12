package org.frice.utils.media

import org.frice.utils.loop
import java.io.File

/**
 * @author ice1000
 * @since v1.7.6
 * @see org.frice.utils.media.AudioPlayer
 */
class OnceAudioPlayer(file: File) : AudioPlayer(file) {
	override fun run() {
		line.open()
		line.start()
		var inBytes: Int
		val audioData = ByteArray(`{-# BUFFER_SIZE #-}`)
		while (true) {
			inBytes = audioInputStream.read(audioData, 0, `{-# BUFFER_SIZE #-}`)
			if (inBytes == -1 || stopped) break
			line.write(audioData, 0, inBytes)
		}
		audioInputStream.close()
	}
}

/**
 * @author ice1000
 * @since v1.7.6
 * @see org.frice.utils.media.AudioPlayer
 */
class LoopAudioPlayer(file: File) : AudioPlayer(file) {
	val cache = arrayListOf<ByteArray>()
	override fun run() {
		line.open()
		line.start()
		var inBytes: Int
		val audioData = ByteArray(`{-# BUFFER_SIZE #-}`)
		while (true) {
			inBytes = audioInputStream.read(audioData, 0, `{-# BUFFER_SIZE #-}`)
			if (inBytes == -1) break
			if (stopped) break
			cache += audioData.clone()
			line.write(audioData, 0, inBytes)
		}
		loop block@ {
			cache.forEachIndexed { index, bytes ->
				if (stopped) return@block
				line.write(bytes, 0, if (index == cache.size) inBytes else `{-# BUFFER_SIZE #-}`)
			}
		}
	}
}