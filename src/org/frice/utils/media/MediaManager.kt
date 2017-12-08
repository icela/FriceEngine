@file:JvmName("MediaManager")
package org.frice.utils.media

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.nio.file.Paths


/**
 * @author ice1000
 * @since v1.7.6
 */
fun playMedia(path: String) = getMediaPlayer(path).play()

/**
 * @author ice1000
 * @since v1.7.6
 */
fun getMediaPlayer(path: String) = MediaPlayer(Media(Paths.get(path).toUri().toString()))
