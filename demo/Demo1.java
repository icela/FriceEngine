import org.frice.game.Game;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.event.OnWindowEvent;
import org.frice.game.obj.FObject;
import org.frice.game.obj.ImageObject;
import org.frice.game.obj.ShapeObject;
import org.frice.game.resource.ColorResource;
import org.frice.game.resource.FileImageResource;
import org.frice.game.utils.message.FDialog;
import org.frice.game.utils.message.log.FLog;
import org.frice.game.utils.shape.FOval;
import org.frice.game.utils.time.FTimeListener;
import org.frice.game.utils.time.FTimer;

import java.util.ArrayList;

/**
 * Created by ice1000 on 2016/8/13.
 *
 * @author ice1000
 */
public class Demo1 extends Game {

	private ArrayList<FObject> objects = new ArrayList<>();
	private FTimer timer;
	private int fuck = 0;
	private int mode = 0;
	private FDialog dialog = new FDialog(this);

	@Override
	public void onInit() {
		timer = new FTimer(800);
		setBack(ColorResource.Companion.getPINK());
		setBounds(100, 100, 800, 800);
		setTitle("Fuck Fuck Fuck");
		addObject(new ShapeObject(ColorResource.Companion.getDARK_GRAY(), new FOval(50, 50)) {{
			getAnims().add(new SimpleMove(10, 20));
		}});
		addTimeListener(new FTimeListener(200, () -> {
			if (fuck > 500) mode = 1;
			if (fuck < 1) mode = 0;
		}));
	}

	@Override
	public void onExit() {
		System.exit(0);
	}

	@Override
	public void onRefresh() {
		if (timer.ended()) {
			FObject object;
			switch (mode) {
				case 1:
					object = objects.get(objects.size() - 1);
					removeObject(object);
					objects.remove(object);
					fuck -= 100;
					break;
				case 0:
					fuck += 100;
					object = new ImageObject(new FileImageResource("test.png"), fuck, fuck);
					addObject(object);
					objects.add(object);
					break;
			}
		}
	}

	@Override
	public void onClick(OnClickEvent onClickEvent) {
//		dialog.show("fuck!!!!!!");
		FLog.i(dialog.confirm("Choose"));
	}

	@Override
	public void onMouse(OnMouseEvent onFrameMouseEvent) {
	}

	@Override
	public void onLoseFocus(OnWindowEvent e) {
		setPaused(true);
	}

	@Override
	public void onFocus(OnWindowEvent e) {
		setPaused(false);
	}

	public static void main(String[] args) {
		new Demo1();
	}
}
