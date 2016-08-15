import org.frice.game.Game;
import org.frice.game.anim.move.ForceMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.event.OnWindowEvent;
import org.frice.game.obj.ImageObject;
import org.frice.game.resource.FileImageResource;

/**
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 * @since v0.2.1
 */
public class Demo2 extends Game {

	public static void main(String[] args) {
		new Demo2();
	}

	@Override
	public void onInit() {
		setSize(800, 800);
		ImageObject object = new ImageObject(new FileImageResource("test.png"), 0, 620);
		object.getAnims().add(new ForceMove(0, 10));
		object.getAnims().add(new SimpleMove(0, -600));
		object.getAnims().add(new SimpleMove(100, 0));
		addObject(object);
		setRefreshPerSecond(40);
	}

	@Override
	public void onExit() {
		System.exit(0);
	}

	@Override
	public void onRefresh() {
	}

	@Override
	public void onClick(OnClickEvent e) {
	}

	@Override
	public void onMouse(OnMouseEvent e) {
	}

	@Override
	public void onLoseFocus(OnWindowEvent e) {
		setPaused(true);
	}

	@Override
	public void onFocus(OnWindowEvent e) {
		setPaused(false);
	}
}
