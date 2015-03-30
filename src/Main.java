import java.awt.Color;

import simple.gui.*;
import simple.run.*;

public class Main extends SimpleGUIApp {
	public static void main(String[]args) { Main.start(new Main(), "Map Generator"); }
	public Main() { super(1000, 600, 30); }
	
	static final int depth = 8;
	static final int size = (int)Math.pow(2, depth) + 1;
	static final float min = 0;
	static final float max = 255;
	static final float h = 0.4f;
	
	Map map;
	Button reset;
	Image image;
	
	public void setup() {
		image = new Image(size, size);
		map = new Map(depth, min, max, h); 
		reset = new Button(800, 100, 100, 50, "Reset", null);
		convertToImage();
	}
	
	public void loop() {
		reset.update();
		if (reset.isClicked()) {
			map.reset(depth, min, max, h); 
			convertToImage();
		}
		
		draw.image(image, 0, 0);
		reset.draw();
		updateView();
	}
	
	void convertToImage() {
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				int val = (int)map.get(i, j);
				image.set(i, j, new Color(val, val, val));
			}
		}
	}

}
