import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.obj.ImageObject;
import org.frice.game.resource.FileImageResource;

/**
 * Demo for accelerate (AccelerateMove), this is a simple gravity mode.
 *
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
	protected void onInit() {
		setSize(800, 800);
		ImageObject object = new ImageObject(new FileImageResource("test.png"), 0, 620);
		object.getAnims().add(new AccelerateMove(0, 10));
		object.getAnims().add(new SimpleMove(0, -600));
		object.getAnims().add(new SimpleMove(100, 0));
		addObject(object);
		setRefreshPerSecond(40);
	}

	@Override
	protected void onRefresh() {
	}

	@Override
	protected void onClick(OnClickEvent e) {
	}

	@Override
	protected void onMouse(OnMouseEvent e) {
	}
}
