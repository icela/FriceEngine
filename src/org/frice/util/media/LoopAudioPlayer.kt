package org.frice.util.media

import org.frice.util.until
import java.io.File

/**
 * @author ice1000
 * @since v1.7.6
 * @see org.frice.util.media.AudioPlayer
 */
open class LoopAudioPlayer(file: File) : AudioPlayer(file) {
	@JvmField protected val cache = arrayListOf<ByteArray>()

	protected fun firstPlay() {
		var inBytes: Int
		val audioData = ByteArray(`{-# BUFFER_SIZE #-}`)
		while (true) {
			inBytes = audioInputStream.read(audioData, 0, `{-# BUFFER_SIZE #-}`)
			if (inBytes == -1) break
			if (stopped) break
			cache += audioData.clone()
			line.write(audioData, 0, inBytes)
		}
	}

	protected fun subsequentPlay() {
		cache.indices.forEach { index ->
			val bytes = cache[index]
			if (stopped) return
			line.write(bytes, 0, if (index == cache.size) -1 else `{-# BUFFER_SIZE #-}`)
		}
	}

	override fun run() {
		openLine()
		firstPlay()
		until(stopped, ::subsequentPlay)
		close()
	}
}