import org.frice.game.Game;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.event.OnWindowEvent;
import org.frice.game.resource.ColorResource;
import org.frice.game.resource.FileImageResource;
import org.frice.game.spirit.FObject;
import org.frice.game.spirit.ImageObject;
import org.frice.game.spirit.ShapedColorObject;
import org.frice.utils.shape.FRectangle;
import org.frice.utils.time.FTimer;

import javax.swing.*;
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

	@Override
	public void onInit() {
		timer = new FTimer(800);
		setBack(ColorResource.Companion.getPINK());
		setBounds(100, 100, 800, 800);
		setTitle("Fuck Fuck Fuck");
		addObject(new ShapedColorObject(ColorResource.Companion.getCYAN(), new FRectangle(50, 50)));
	}

	@Override
	public void onExit() {
	}

	@Override
	public void onRefresh() {
		if (timer.ended()) {
			FObject object;
			if (fuck > 500) mode = 1;
			if (fuck < 1) mode = 0;
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
		JOptionPane.showMessageDialog(this, "fuck!!!!!!");
	}

	@Override
	public void onMouse(OnMouseEvent onFrameMouseEvent) {
	}

	public static void main(String[] args) {
		new Demo1();
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
