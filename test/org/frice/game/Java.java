package org.frice.game;

import org.frice.game.resource.image.ImageResource;
import org.frice.game.utils.graphics.utils.ColorUtils;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by ice1000 on 2016/8/29.
 *
 * @author ice1000
 */
public class Java extends Game {
	public static void main(String[] args) throws FileNotFoundException {
		BufferedImage image = ImageResource.fromPath("D:\\图片\\aniki.jpg").getImage();
		PrintWriter writer = new PrintWriter("test.txt");
		for (int i = 0; i < image.getWidth(); i += 4) {
			for (int j = 0; j < image.getHeight(); j += 1)
				writer.print(ColorUtils.toAscii(image.getRGB(i, j)));
			writer.println();
		}
	}
}
