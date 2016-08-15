import org.frice.game.Game
import org.frice.game.event.OnClickEvent
import org.frice.game.event.OnMouseEvent
import org.frice.game.utils.data.Preference
import org.frice.game.utils.message.FDialog

/**
 * Demo for database operating
 */
class Demo4 extends Game {

	def fuck = Preference.getPreference("test.xml")
	FDialog dialog

	@Override
	protected void onInit() {
		dialog = new FDialog(this)
	}

	@Override
	protected void onRefresh() {
		switch (dialog.confirm("what do U want?")) {
			case 0:
				fuck.insert(dialog.input("inserting, key?"), dialog.input("inserting, value?"))
				break
			case 1:
				dialog.confirm(fuck.query(
						dialog.input("querying, key?"),
						dialog.input("querying, value?")).toString())
				break
			case 2:
				onExit()
				break
		}
	}

	@Override
	protected void onClick(OnClickEvent e) {
	}

	@Override
	protected void onMouse(OnMouseEvent e) {
	}

	public static void main(String[] args) {
		new Demo4()
	}
}