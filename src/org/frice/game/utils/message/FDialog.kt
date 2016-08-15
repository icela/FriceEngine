package org.frice.game.utils.message

import org.frice.game.Game
import javax.swing.JOptionPane

/**
 * Created by ice1000 on 2016/8/14.
 * @author ice1000
 * @since v0.2
 */
class FDialog(val game: Game) {
	fun show(msg: String) = JOptionPane.showMessageDialog(game, msg)
	fun input() = JOptionPane.showInputDialog(game)
	fun confirm(msg: String) = confirm(msg, "", JOptionPane.YES_NO_CANCEL_OPTION)
	fun confirm(msg: String, title: String, option: Int) = JOptionPane.showConfirmDialog(game, msg, title, option)
}