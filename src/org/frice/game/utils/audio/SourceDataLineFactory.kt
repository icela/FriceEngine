package org.frice.game.utils.audio

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine
import javax.sound.sampled.SourceDataLine

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
object SourceDataLineFactory {
	fun getLine(audioFormat: AudioFormat): SourceDataLine {
		val info = DataLine.Info(
				SourceDataLine::class.java,
				audioFormat
		)
		val res = AudioSystem.getLine(info) as SourceDataLine
		res.open(audioFormat)
		return res
	}
}