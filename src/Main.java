import simple.gui.*;
import simple.run.*;
import java.awt.Color;

public class Main extends SimpleGUIApp {
	public static void main(String[]args) { Main.start(new Main(), "Map Generator"); }
	public Main() { super(1025, 1025, 30); }
	
	static final int depth = 5;
	static final int size = (int)Math.pow(2, depth) + 1;
	static final float min = 0;
	static final float max = 255;
	static final float h = 0.5f;
	
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
		} else {
			/*
			Map.shiftMap(depth, min, max, h, map.heightMap);
			convertToImage(); */
		}
		
		draw.image(image, 0, 0);
		reset.draw();
		updateView();
	}
	
	void convertToImage() {
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				int val = (int)map.get(i, j);
				image.set(i, j, toFireColor(val));
			}
		}
	}
	
	Color toFireColor(int val) {
		if (val < 85) {
			return new Color(val*3, 0, 0);
		} else if (val < 170) {
			return new Color(255, (val-85)*2, 0);
		} else {
			return new Color(255, val, val/4);
		}
	}

}
