@file:JvmName("AudioManager")

package org.frice.utils.audio

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.io.File
import java.nio.file.Paths

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
fun play(file: File) = getPlayer(file).start()

/**
 * @author ice1000
 * @since v0.3.1
 */
fun play(path: String) = getPlayer(path).start()

/**
 * @author ice1000
 * @since v0.3.1
 */
fun getPlayer(file: File) = AudioPlayer(file)

/**
 * @author ice1000
 * @since v0.3.1
 */
fun getPlayer(path: String) = AudioPlayer(path)

/**
 * @author ice1000
 * @since v1.7.6
 */
fun playSoundFX(path: String) {
	getPlayerFX(path).play()
}

/**
 * @author ice1000
 * @since v1.7.6
 */
fun getPlayerFX(path: String) = MediaPlayer(Media(Paths.get(path).toUri().toString()))
