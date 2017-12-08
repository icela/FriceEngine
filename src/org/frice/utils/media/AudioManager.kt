@file:JvmName("AudioManager")

package org.frice.utils.media

import java.io.File

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
