@file:JvmName("AudioManager")

package org.frice.utils.audio

import java.io.File
import kotlin.concurrent.thread

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
fun play(file: File) = getPlayer(file).start()

fun play(path: String) = getPlayer(path).start()

fun getPlayer(file: File) = AudioPlayer(file)

fun getPlayer(path: String) = AudioPlayer(path)