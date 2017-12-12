package org.frice.utils.media

import org.frice.utils.forceRun
import java.io.Closeable
import java.io.File
import javax.sound.sampled.*

/**
 * @author ice1000
 * @since v1.7.6
 * @see org.frice.utils.media.AudioPlayer
 */
sealed class AudioPlayerImpl(file: File) : Runnable, Closeable {
	protected var audioInputStream: AudioInputStream
	private var format: AudioFormat
	protected var line: SourceDataLine
	var stopped = false

	init {
		audioInputStream = AudioSystem.getAudioInputStream(file)
		format = audioInputStream.format
		if (format.encoding != AudioFormat.Encoding.PCM_SIGNED) {
			format = AudioFormat(
				AudioFormat.Encoding.PCM_SIGNED,
				format.sampleRate,
				16,
				format.channels,
				format.channels shl 1,
				format.sampleRate,
				false)
			audioInputStream = AudioSystem.getAudioInputStream(format, audioInputStream)
		}
		line = `{-# getLine #-}`(format)
	}

	override fun close() {
		audioInputStream.close()
		line.drain()
		line.close()
	}

	class OnceAudioPlayer(file: File) : AudioPlayerImpl(file) {
		override fun run() {
			forceRun(line::open)
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

	class LoopAudioPlayer(file: File) : AudioPlayerImpl(file) {
		val cache = arrayListOf<ByteArray>()
		override fun run() {
			forceRun(line::open)
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
			run block@ {
				cache.forEachIndexed { index, bytes ->
					if (stopped) return@block
					if (index == cache.size - 1) line.write(bytes, 0, inBytes)
					else line.write(bytes, 0, bytes.size)
				}
			}
			close()
		}
	}
}