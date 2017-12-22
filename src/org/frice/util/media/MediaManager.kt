@file:JvmName("MediaManager")
package org.frice.util.media

import javafx.scene.media.Media
import javafx.scene.media.MediaPlayer
import java.nio.file.Paths


/**
 * @exception com.sun.media.jfxmedia.MediaException for mp3/mp4 on Ubuntu 16.04, it doesn't work.
 * @author ice1000
 * @since v1.7.6
 */
fun playMedia(path: String) = getMediaPlayer(path).play()

/**
 * @exception com.sun.media.jfxmedia.MediaException for mp3/mp4 on Ubuntu 16.04, it doesn't work.
 * @author ice1000
 * @since v1.7.6
 */
fun getMediaPlayer(path: String) = MediaPlayer(Media(Paths.get(path).toUri().toString()))
