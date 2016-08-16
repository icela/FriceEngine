package org.frice.game.utils.audio

import org.frice.game.utils.message.log.FLog
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.SourceDataLine

/**
 * From https://github.com/ice1000/Dekoder
 *
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class PlayerThread internal constructor(file: File) : Thread() {
	internal constructor(path: String) : this(File(path))

	companion object {
		val BUFFER_SIZE = 2048

	}
	private var ais: AudioInputStream
	private var line: SourceDataLine

	private var format: AudioFormat
	var paused: Boolean = false

	var exited: Boolean = false

	init {
		ais = AudioSystem.getAudioInputStream(file)
		format = ais.format
		line = SourceDataLineFactory.getLine(format)
	}

	override fun run() {
		line.open()
		line.start()
		var inBytes = 0
		var cnt = 0
		while (!exited && inBytes != -1) if (!paused) {
//			FLog.debug("loop ${cnt++}")
			val audioData = ByteArray(BUFFER_SIZE)
			inBytes = ais.read(audioData, 0, BUFFER_SIZE)
			if (inBytes >= 0) line.write(audioData, 0, inBytes)
		}
		line.drain()
		line.close()
		FLog.info("Ended playing")
	}
}
