import org.frice.game.Game;
import org.frice.game.anim.move.AccelerateMove;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.anim.scale.SimpleScale;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.obj.ShapeObject;
import org.frice.game.resource.ColorResource;
import org.frice.game.utils.message.log.FLog;
import org.frice.game.utils.shape.FOval;
import org.frice.game.utils.time.FTimeListener;

/**
 * Created by ice1000 on 2016/8/15.
 *
 * @author ice1000
 */
public class Demo6 extends Game {
	public static void main(String[] args) {
		new Demo6();
	}

	@Override
	protected void onInit() {
		setSize(900, 900);
		addTimeListener(new FTimeListener(2000, () -> addObject(new ShapeObject(ColorResource.Companion.get基佬紫(),
				new FOval(80.0, 120.0), 10, 750) {{
			getAnims().add(new SimpleScale(1.1, 1.1));
			getAnims().add(AccelerateMove.getGravity());
			getAnims().add(new SimpleMove(0, -700));
			getAnims().add(new SimpleMove(100, 0));
		}})));
	}

	@Override
	protected void onRefresh() {
	}

	@Override
	protected void onClick(OnClickEvent e) {
		setPaused(!getPaused());
		FLog.e("paused");
	}

	@Override
	protected void onMouse(OnMouseEvent e) {
	}
}
