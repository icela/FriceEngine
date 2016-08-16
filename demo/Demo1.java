import org.frice.game.Game;
import org.frice.game.anim.move.SimpleMove;
import org.frice.game.event.OnClickEvent;
import org.frice.game.obj.FObject;
import org.frice.game.obj.ImageObject;
import org.frice.game.obj.ShapeObject;
import org.frice.game.resource.ColorResource;
import org.frice.game.resource.image.FileImageResource;
import org.frice.game.utils.graphics.shape.FOval;
import org.frice.game.utils.message.FDialog;
import org.frice.game.utils.time.FTimeListener;
import org.frice.game.utils.time.FTimer;

import java.util.ArrayList;

/**
 * Demo for the simplest use of Frice Engine
 * Created by ice1000 on 2016/8/13.
 *
 * @author ice1000
 * @since v0.1
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
		switch (dialog.confirm("Choose")) {
			case 0:
				dialog.show("Yes!");
				break;
			case 1:
				dialog.show("No!");
				break;
			case 2:
				dialog.show("Canceled!");
				break;
			default:
				dialog.show("???WTF???");
				break;
		}
	}

	public static void main(String[] args) {
		new Demo1();
	}
}
