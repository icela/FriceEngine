@file:JvmName("AudioManager")

package org.frice.util.media

import java.io.File
import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioSystem

/**
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 */
@JvmOverloads
fun play(file: File, infinite: Boolean = false) = getPlayer(file, infinite).start()

/**
 * @author ice1000
 * @since v0.3.1
 */
@JvmOverloads
fun play(path: String, infinite: Boolean = false) = getPlayer(path, infinite).start()

/**
 * @author ice1000
 * @since v0.3.1
 */
@JvmOverloads
fun getPlayer(file: File, infinite: Boolean = false) = if (infinite) LoopAudioPlayer(file) else OnceAudioPlayer(file)

/**
 * @author ice1000
 * @since v0.3.1
 */
@JvmOverloads
fun getPlayer(path: String, infinite: Boolean = false) = getPlayer(File(path), infinite)

/**
 * @author ice1000
 * @since v1.8.2
 */
fun getRandomPlayer(path: String) = getRandomPlayer(File(path))

fun getRandomPlayer(file: File) = RandomAudioPlayer(file)

internal const val `{-# BUFFER_SIZE #-}` = 4096

@Synchronized
internal fun `{-# getLine #-}`(audioFormat: AudioFormat) = AudioSystem.getSourceDataLine(audioFormat)
