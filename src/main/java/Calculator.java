import java.awt.Frame;
// import java.util.*;


public class Calculator extends Thread {
	private int maxIter;
	private double xMin, xMax, yMin, yMax;
	private double xStep, yStep;
	private static boolean[] finished = new boolean[1920]; 
	private static int[][] pic = new int[1920][1080];
	private static Frame myFrame;

	public Calculator(FractalFactoryController ffc){
		xMin = ffc.getxMin();
		xMax = ffc.getxMax();
		yMin = ffc.getyMin();
		yMax = ffc.getyMax();
		maxIter = ffc.getMaxIter();
		xStep = (xMax - xMin) / 1919;
		yStep = (yMax - yMin) / 1079;
		myFrame = ffc.getFfv().getMyFrame();
	}
	
	public void run() {
		for(int col = 0;col<1920;col++){
			if(!finished[col]){
				finished[col] = true;
				
				myFrame.setTitle("Fractal Factory HD - "+(int)(col/19.2)+"% fertig");
				int iter = 0;
				double x = xMin - xStep + col * xStep;
				double y, im, re, reNeu;
			
				y = yMax + yStep;
				x += xStep;
				for (int j = 0; j < 1080; j++) {
					y -= yStep;

					re = 0;
					im = 0;
					
					iter = 0;
					do {
						reNeu = re * re - im * im + x;
						im = 2 * re * im + y;
						re = reNeu;
						iter++;
					} while (re * re + im * im <= 4 && iter < this.maxIter);

					if (iter < this.maxIter) {
						pic[col][j] = iter;
					} else {
						pic[col][j] = 0;
					}
				}
			}
		}
	}
	
	public int getPixel(int i, int j){
		return pic[i][j];
	}
	
	public static void resetFinished(){
		for(int i=0;i<1920;i++){
			finished[i] = false;
		}
	}
}
