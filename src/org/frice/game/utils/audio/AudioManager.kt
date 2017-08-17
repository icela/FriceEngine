@file:JvmName("AudioManager")

package org.frice.game.utils.audio

import java.io.File
import kotlin.concurrent.thread

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
fun play(file: File) = thread { getPlayer(file).main() }

fun play(path: String) = thread { getPlayer(path).main() }

fun getPlayer(file: File) = AudioPlayer(file)

fun getPlayer(path: String) = AudioPlayer(path)