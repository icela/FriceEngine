@file:JvmName("AudioManager")

package org.frice.game.utils.audio

import org.frice.game.utils.misc.async
import java.io.File

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
fun play(file: File) = async { getPlayer(file).main() }

fun play(path: String) = async { getPlayer(path).main() }

fun getPlayer(file: File) = AudioPlayer(file)

fun getPlayer(path: String) = AudioPlayer(path)