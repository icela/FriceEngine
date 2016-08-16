package org.frice.game.utils.audio

import java.io.File

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
object AudioManager {
	fun play(file: File) = PlayerThread(file).start()
	fun play(path: String) = PlayerThread(path).start()

	fun getPlayer(file: File) = PlayerThread(file)
	fun getPlayer(path: String) = PlayerThread(path)
}