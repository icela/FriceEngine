package org.frice.utils.media

import java.io.File
import javax.sound.sampled.*

/**
 * From https://github.com/ice1000/Dekoder
 *
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 * @see org.frice.utils.media.getPlayer
 */
open class AudioPlayer internal constructor(val file: File) : Thread() {
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

	/**
	 * It's the safest way to stop playing.
	 * @since v1.7.7
	 */
	fun stopPlaying() {
		stopped = true
	}
}
