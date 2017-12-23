package org.frice.util.media

import org.frice.util.message.FLog
import org.frice.util.unless
import java.io.Closeable
import java.io.File
import javax.sound.sampled.*

/**
 * From https://github.com/ice1000/Dekoder
 *
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 * @see org.frice.util.media.getPlayer
 */
open class AudioPlayer internal constructor(val file: File) : Thread(), Closeable {
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
				format.frameRate,
				false)
			audioInputStream = AudioSystem.getAudioInputStream(format, audioInputStream)
		}
		line = `{-# getLine #-}`(format)
	}

	protected fun openLine() {
		try {
			unless(line.isOpen) {
				line.open()
				line.start()
			}
		} catch (ignored: IllegalStateException) {
			FLog.e("""There're some issues with the audio mixer in your JRE.
Please use `org.frice.util.media.MediaManager instead of AudioManager if you see this message.""")
		}
	}

	/**
	 * It's the safest way to stop playing.
	 * @since v1.7.7
	 */
	fun stopPlaying() {
		stopped = true
	}

	override fun close() {
		line.close()
		audioInputStream.close()
	}
}
