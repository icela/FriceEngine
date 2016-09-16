package org.frice.game.utils.message

import java.awt.Frame
import javax.swing.JOptionPane

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 */
class FDialog(val game: Frame?) {
	fun show(msg: String) = JOptionPane.showMessageDialog(game, msg)
	fun input() = input("")
	fun input(msg: String) = JOptionPane.showInputDialog(game, msg)!!
	fun confirm(msg: String) = confirm(msg, "")
	fun confirm(msg: String, title: String) = confirm(msg, title, JOptionPane.YES_NO_CANCEL_OPTION)
	fun confirm(msg: String, title: String, option: Int) = JOptionPane.showConfirmDialog(game, msg, title, option)

//	companion object {
//		internal val TYPE_ERROR = "ERROR_ELDATH"
//		internal val TYPE_INFO = "INFO_ELDATH"
//		internal val TYPE_WARN = "WARN_ELDATH"
//
//		@JvmStatic fun infoDialog(msg: String) = Anonymous().show(TYPE_INFO, msg)
//		@JvmStatic fun warnDialog(doWhat: String, msg: String) = Anonymous().show(TYPE_WARN, doWhat, msg)
//		@JvmStatic fun errorDialog(doWhat: String, msg: String) = Anonymous().show(TYPE_ERROR, doWhat, msg)
//	}

//	internal class Anonymous internal constructor() : Application() {
//		override fun start(primaryStage: Stage?) {
//			when (parameters.raw[0]) {
//				TYPE_INFO -> InfoAlert(parameters.raw[1])
//				TYPE_WARN -> WarnAlert(parameters.raw[1], parameters.raw[2])
//				TYPE_ERROR -> ErrorAlert(parameters.raw[1], parameters.raw[2])
//			}
//		}
//
//		private fun check() = parameters?.let { launch("", "", "") }
//
//		internal fun show(type: String, s1: String) {
//			check()
//			parameters.raw[0] = type
//			parameters.raw[1] = s1
//			start(Stage())
//		}
//
//		internal fun show(type: String, s1: String, s2: String) {
//			check()
//			parameters.raw[0] = type
//			parameters.raw[1] = s1
//			parameters.raw[2] = s2
//			start(Stage())
//		}
//
//		internal fun show(type: String, s1: String, t: Throwable) {
//			check()
//			parameters.raw[0] = type
//			parameters.raw[1] = s1
//			parameters.raw[2] = Coder.parseThrowable(t)
//			start(Stage())
//		}
//
//	}
}