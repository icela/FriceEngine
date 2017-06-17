package org.frice.game.utils.audio

import org.frice.game.utils.misc.forceRun
import org.frice.game.utils.misc.until
import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine

/**
 * From https://github.com/ice1000/Dekoder
 *
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
class AudioPlayer internal constructor(file: File) {
	internal constructor(path: String) : this(File(path))

	internal fun main() {
		forceRun { line.open() }
		line.start()
		var inBytes = 0
		val audioData = ByteArray(BUFFER_SIZE)
		until(inBytes == -1 || exited) {
			inBytes = audioInputStream.read(audioData, 0, BUFFER_SIZE)
			if (inBytes >= 0) line.write(audioData, 0, inBytes)
		}
		line.drain()
		line.close()
	}

	private val thread = Thread { main() }

	companion object {
		@JvmField val BUFFER_SIZE = 2048

		private fun getLine(audioFormat: AudioFormat) = (AudioSystem.getLine(DataLine.Info(SourceDataLine::class.java,
				audioFormat)) as SourceDataLine).apply { open(audioFormat) }
	}

	private var audioInputStream = AudioSystem.getAudioInputStream(file)
	private var format = audioInputStream.format
	private var line: SourceDataLine = getLine(format)

	private var exited = false

	fun exit() {
		exited = true
		thread.join()
	}

	fun start() = thread.start()
}
