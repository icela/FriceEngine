package org.frice.utils.media

/**
 * From https://github.com/ice1000/Dekoder
 *
 * Created by ice1000 on 2016/8/16.
 * @author ice1000
 * @since v0.3.1
 * @see org.frice.utils.media.getPlayer
 */
class AudioPlayer internal constructor(val runnable: AudioPlayerRunnable) : Thread(runnable) {

	/**
	 * It's the safest way to stop playing.
	 * @since v1.7.7
	 */
	fun stopPlaying() {
		runnable.stopped = true
	}
}
