import simple.gui.*;
import simple.run.*;
import java.awt.Color;

public class Main extends SimpleGUIApp {
	interface Worker<ResultType> {
		ResultType doWork();
	}
	class WorkerThread<ResultType> implements Runnable {
		private Worker<ResultType> worker;
		private boolean finished;
		private ResultType result;
		
		public WorkerThread(Worker<ResultType> worker) {
			this.worker = worker;
			finished   = false;
		}
		
		public void run() {
			finished = false;
			result = worker.doWork();
			finished = true;
		}
		
		public boolean isFinished() { return finished; }
		public ResultType getResult() { finished = false; return result; }
	}
	
	public static void main(String[]args) { Main.start(new Main(), "Map Generator"); }
	public Main() { super(1025, 1025, 30); }
	
	static final int depth = 13;
	static final int size = (int)Math.pow(2, depth) + 1;
	static final float min = 0;
	static final float max = 255;
	static final float h = 0.5f;
	
	Map map;
	WorkerThread<Map> generatorThread;
	WorkerThread<Image> converterThread;
	Button reset;
	Image image;
	
	boolean loading;
	
	public void setup() {
		reset = new Button(800, 100, 100, 50, "Generating...", null);
		reset.setFillColor(new Color(255, 200, 200));
		
		image = new Image(size, size);
		
		generatorThread = new WorkerThread<Map>(new Worker<Map>() {
				public Map doWork() {
					return new Map(depth, min, max, h);
				}
			});
		converterThread = new WorkerThread<Image>(new Worker<Image>() {
				public Image doWork() {
					Image image = new Image(size, size);
					convertToImage(image);
					return image;
				}
			});
		
		loading = true;
		new Thread(generatorThread).start();
		
	}
	
	public void loop() {
		reset.update();
		if (reset.isClicked() && !loading) {
			loading = true;
			reset.setText("Generating...");
			reset.setFillColor(new Color(255, 200, 200));
			new Thread(generatorThread).start();
		}
		
		if (generatorThread.isFinished()) {
			reset.setText("Converting...");
			map = generatorThread.getResult();
			reset.setFillColor(new Color(255, 255, 200));
			new Thread(converterThread).start();
		}
		if (converterThread.isFinished()) {
			loading = false;
			reset.setText("Reset");
			reset.setFillColor(new Color(255, 255, 255));
			image = converterThread.getResult();
		}
		
		draw.image(image, 0, 0);
		reset.draw();
		updateView();
	}
	
	void convertToImage(Image image) {
		for (int i=0; i<size; i++) {
			for (int j=0; j<size; j++) {
				int val = (int)map.get(i, j);
				image.setColor(i, j, toFireColor(val));
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
