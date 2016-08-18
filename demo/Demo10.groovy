import org.frice.game.Game
import org.frice.game.obj.button.SimpleButton
import org.frice.game.resource.graphics.ColorResource
import org.frice.game.utils.graphics.shape.FRectangle

/**
 * Created by ice1000 on 2016/8/18.
 * @author ice1000
 * @since v0.3.3
 */
class Demo10 extends Game {
	public static void main(String[] args) {
		new Demo10()
	}

	@Override
	protected void onInit() {
		addObject(new SimpleButton(new FRectangle(100, 100),
				ColorResource.getBLUE(), "", 50, 50, 100, 100))
	}
}
