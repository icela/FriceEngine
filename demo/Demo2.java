import org.frice.game.Game;
import org.frice.game.anim.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.event.OnWindowEvent;
import org.frice.game.obj.ImageObject;
import org.frice.game.resource.FileImageResource;
import org.frice.game.utils.time.FTimer;

/**
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 * @since v0.2.1
 */
public class Demo2 extends Game {

	private FTimer timer = new FTimer(100);

	public static void main(String[] args) {
		new Demo2();
	}

	@Override
	public void onInit() {
		addObject(new ImageObject(new FileImageResource("test.png"), 20, 20) {{
			getAnims().add(new SimpleMove(10, 10));
		}});
//		setBack(new FileImageResource("test.png"));
//		setRefreshPerSecond(1);
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
