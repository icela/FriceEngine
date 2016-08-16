package org.frice.game.utils.audio.threads

import org.frice.game.utils.audio.SourceDataLineFactory
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
open class PlayerThread(file: File) : Thread() {

	companion object {
		val BUFFER_SIZE = 2048
	}

	protected var ais: AudioInputStream
	protected var line: SourceDataLine
	protected var format: AudioFormat

	val playData = PlayData()

	init {
		ais = AudioSystem.getAudioInputStream(file)
		format = ais.format
		line = SourceDataLineFactory.getLine(format)
	}

	constructor(path: String) : this(File(path))

	override fun run() {
		line.open()
		line.start()
		var inBytes = 0
		var cnt = 0
		while (inBytes != -1 && !playData.threadExit) if (!playData.isPaused) {
			FLog.debug("loop ${cnt++}")
			val audioData = ByteArray(BUFFER_SIZE)
			inBytes = ais.read(audioData, 0, BUFFER_SIZE)
			if (inBytes >= 0) line.write(audioData, 0, inBytes)
		}
		line.drain()
		line.close()
		FLog.info("Ended playing")
	}

	data class PlayData(
			var isPaused: Boolean = false,
			var threadExit: Boolean = false
	)
}
