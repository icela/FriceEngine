package org.frice.util.media

import java.io.File

/**
 * @author ice1000
 * @since v1.7.6
 * @see org.frice.util.media.AudioPlayer
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
		out@ while (true) {
			for ((index, bytes) in cache.withIndex()) {
				if (stopped) break@out
				line.write(bytes, 0, if (index == cache.size) inBytes else `{-# BUFFER_SIZE #-}`)
			}
		}
		close()
	}
}