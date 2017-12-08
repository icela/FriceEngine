package org.frice.utils.media

import org.frice.utils.forceRun
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
class AudioPlayer internal constructor(file: File) : Thread() {
	internal constructor(path: String) : this(File(path))
	val runnable = AudioPlayerRunnable(file)

	override fun run() = runnable.run()

	companion object LineGetter {
		const val BUFFER_SIZE = 1024

		fun getLine(audioFormat: AudioFormat): SourceDataLine {
			val res = AudioSystem.getLine(DataLine.Info(SourceDataLine::class.java, audioFormat)) as SourceDataLine
			res.open(audioFormat)
			return res
		}
	}

	/**
	 * @author ice1000
	 * @since v1.7.6
	 * @see AudioPlayer
	 */
	class AudioPlayerRunnable(file: File) : Runnable {
		private var audioInputStream: AudioInputStream
		private var format: AudioFormat
		private var line: SourceDataLine

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
			line = getLine(format)
		}

		override fun run() {
			forceRun(line::open)
			line.start()
			var inBytes: Int
			val audioData = ByteArray(BUFFER_SIZE)
			while(true) {
				inBytes = audioInputStream.read(audioData, 0, BUFFER_SIZE)
				if (inBytes == -1) break
				line.write(audioData, 0, inBytes)
			}
			line.drain()
			line.close()
		}
	}
}
