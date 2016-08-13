import org.frice.game.Game;
import org.frice.game.event.OnClickEvent;
import org.frice.game.event.OnMouseEvent;
import org.frice.game.spirit.ImageObject;
import org.frice.game.resource.FileImageResource;
import org.frice.utils.time.Timer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by ice1000 on 2016/8/13.
 *
 * @author ice1000
 */
public class Demo1 extends Game {

	private ArrayList<ImageObject> objects = new ArrayList<>();
	private Timer timer;
	private int fuck = 0;
	private int mode = 0;

	@Override
	public void onInit() {
		timer = new Timer(800);
		setBackground(Color.CYAN);
		setBounds(100, 100, 800, 800);
		setTitle("Fuck Fuck Fuck");
	}

	@Override
	public void onExit() {
	}

	@Override
	public void onRefresh() {
		if (timer.ended()) {
			ImageObject object;
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
}
