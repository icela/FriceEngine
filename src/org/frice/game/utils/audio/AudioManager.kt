package org.frice.game.utils.audio

import org.frice.game.utils.misc.async
import java.io.File

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
object AudioManager {
	@JvmStatic
	fun play(file: File) = async { AudioPlayer(file).main() }

	@JvmStatic
	fun play(path: String) = async { AudioPlayer(path).main() }

	@JvmStatic
	fun getPlayer(file: File) = AudioPlayer(file)

	@JvmStatic
	fun getPlayer(path: String) = AudioPlayer(path)
}