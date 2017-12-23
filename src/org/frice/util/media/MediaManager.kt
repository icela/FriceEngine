@file:JvmName("MediaManager")

package org.frice.util.media

import javafx.scene.media.*
import org.frice.util.message.FLog
import java.nio.file.Paths


/**
 * For mp3/mp4 on Ubuntu 16.04, it doesn't work.
 * @author ice1000
 * @since v1.7.6
 */
fun playMedia(path: String) {
	getMediaPlayer(path)?.play()
}

/**
 * For mp3/mp4 on Ubuntu 16.04, it doesn't work.
 * @author ice1000
 * @since v1.7.6
 */
fun getMediaPlayer(path: String) = try {
	MediaPlayer(Media(Paths.get(path).toUri().toString()))
} catch (e: MediaException) {
	FLog.e("""MediaPlayer doesn't work with mp3/mp4 on Ubuntu 16.04 with OpenJDK 8.
You may use OpenJDK 9, or switch to other Operating Systems.""")
	null
}
