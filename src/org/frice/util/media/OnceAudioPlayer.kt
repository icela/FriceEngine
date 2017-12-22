package org.frice.util.media

import java.io.File

/**
 * @author ice1000
 * @since v1.7.6
 * @see org.frice.util.media.AudioPlayer
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
		close()
	}
}

