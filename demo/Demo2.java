import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.obj.ImageObject;
import org.frice.game.resource.image.FileImageResource;
import org.frice.game.utils.time.FTimer;

/**
 * Demo for accelerate (AccelerateMove), this is a simple gravity mode.
 *
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 * @since v0.2.1
 */
public class Demo2 extends Game {

	private FTimer timer;
	private ImageObject object1;
	private ImageObject object2;

	public static void main(String[] args) {
		new Demo2();
	}

	@Override
	protected void onInit() {
		setSize(800, 800);
		object1 = new ImageObject(new FileImageResource("test.png"), 0, 620);
		object1.getAnims().add(new AccelerateMove(0, 10));
		object1.getAnims().add(new SimpleMove(0, -600));
		object1.getAnims().add(new SimpleMove(100, 0));
		addObject(object1);
		addObject(object2 = make());
		setRefreshPerSecond(40);
		timer = new FTimer(5000);
	}

	@Override
	protected void onRefresh() {
		if (timer.ended()) {
			removeObject(object1);
			removeObject(object2);
			addObject(object1 = make());
			addObject(object2 = make());
		}
	}

	private ImageObject make() {
		return new ImageObject(new FileImageResource(
				"https://avatars3.githubusercontent.com/u/16398479?v=3&s=40"
//				"test.png"
		), 20, 720) {{
			getAnims().add(new AccelerateMove(0, 10));
			getAnims().add(new SimpleMove(0, -700));
			getAnims().add(new SimpleMove(280, 0));
		}};
	}

	@Override
	protected void onClick(OnClickEvent e) {
	}

	@Override
	protected void onMouse(OnMouseEvent e) {
	}
}
