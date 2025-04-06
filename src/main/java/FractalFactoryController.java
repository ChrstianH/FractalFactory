
public class FractalFactoryController {
	private int maxIter;
	private FractalFactoryView ffv;
	private double xMin, xMax, yMin, yMax;

	public FractalFactoryController(FractalFactoryView ffv){
		this.xMin = -2.5;
		this.xMax = 1.5;
		this.yMin = -1.125;
		this.yMax = 1.125;
		this.maxIter = 100;
		this.ffv = ffv;
	}
	
	public int[][][] createColorTable(int[][] iter) {
		int colorSchema = ffv.getColorSchema();
		int[][][] pic = new int[1920][1080][3];
		if (colorSchema == 1) {
			for (int i = 0; i < 1920; i++) {
				for (int j = 0; j < 1080; j++) {
					double color = 767 / (double)this.maxIter * iter[i][j];

					if (color < 256) {
						pic[i][j][0]= (int) color;
						pic[i][j][1] = 0;
						pic[i][j][2] = 0;
					} else if (color >= 256 && color < 512) {
						pic[i][j][0] = 255;
						pic[i][j][1] = (int) color - 256;
						pic[i][j][2] = 0;
					} else if (color >= 512 && color < 768) {
						pic[i][j][0] = 255;
						pic[i][j][1] = 255;
						pic[i][j][2] = (int) color - 512;
					}
				}
			}

		} else if (colorSchema == 2) {
			for (int i = 0; i < 1920; i++) {
				for (int j = 0; j < 1080; j++) {
					if(iter[i][j]==0){
						pic[i][j][0] = 0;
						pic[i][j][1] = 0;
						pic[i][j][2] = 0;						
					}
					else{
						double color = iter[i][j]%1020;
						if (color < 256) {
							pic[i][j][0] = (int)color;
							pic[i][j][1] = (int)color;
							pic[i][j][2] = 255 - (int)color;
						} else if (color >= 256 && color < 511) {
							pic[i][j][0] = 255;
							pic[i][j][1] = 510 - (int)color;
							pic[i][j][2] = 0;
						} else if (color >= 511 && color < 766) {
							pic[i][j][0] = 255;
							pic[i][j][1] = (int)color - 510;
							pic[i][j][2] = (int)color - 510;
						} else if (color >= 766 && color < 1020) {
							pic[i][j][0] = 1019 - (int)color;
							pic[i][j][1] = 1019 - (int)color;
							pic[i][j][2] = 255;
						}
					}
				}
			}

		} else if (colorSchema == 3) {
			for (int i = 0; i < 1920; i++) {
				for (int j = 0; j < 1080; j++) {
					if(iter[i][j]==0){
						pic[i][j][0] = 0;
						pic[i][j][1] = 0;
						pic[i][j][2] = 0;						
					}
					else{
						double color = iter[i][j]%2040;
						if (color < 256) {
							pic[i][j][0] = 255 - (int)color;
							pic[i][j][1] = 0;
							pic[i][j][2] = (int)color;
						} else if (color >= 256 && color < 511) {
							pic[i][j][0] = (int)color - 255;
							pic[i][j][1] = 0;
							pic[i][j][2] = 255;
						} else if (color >= 511 && color < 766) {
							pic[i][j][0] = 765 - (int)color;
							pic[i][j][1] = (int)color - 510;
							pic[i][j][2] = 765 - (int)color;
						} else if (color >= 766 && color < 1021) {
							pic[i][j][0] = (int)color - 765;
							pic[i][j][1] = 255;
							pic[i][j][2] = 0;
						} else if (color >= 1021 && color < 1276) {
							pic[i][j][0] = 1275 - (int)color;
							pic[i][j][1] = 255;
							pic[i][j][2] = (int)color - 1020;
						} else if (color >= 1276 && color < 1531) {
							pic[i][j][0] = (int)color - 1275;
							pic[i][j][1] = 255;
							pic[i][j][2] = 255;
						} else if (color >= 1531 && color < 1786) {
							pic[i][j][0] = 1785 - (int)color;
							pic[i][j][1] = 1785 - (int)color;
							pic[i][j][2] = 1785 - (int)color;
						} else if (color >= 1786 && color < 2040) {
							pic[i][j][0] = (int)color - 1785;
							pic[i][j][1] = 0;
							pic[i][j][2] = 0;
						}
					}
				}
			}
		}
		return pic;
	}

	public int[][] newPic(double xMin, double xMax, double yMin, double yMax, int maxIter) {
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		this.maxIter = maxIter;
		int[][] pic = new int[1920][1080];
		Calculator.resetFinished();
		
		Thread worker1 = new Calculator(this);
		Thread worker2 = new Calculator(this);
		Thread worker3 = new Calculator(this);
		Thread worker4 = new Calculator(this);
		Thread t3 = new ShowTime(ffv);
		
		worker1.start();
		worker2.start();
		worker3.start();
		worker4.start();
		t3.start();
		
		try {
			worker1.join();
			worker2.join();
			worker3.join();
			worker4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t3.interrupt();
		for(int i=0;i<1920;i++){
			for(int j=0;j<1080;j++){
				pic[i][j] = ((Calculator) worker4).getPixel(i, j);
			}
		}
		return pic;
	}

	public FractalFactoryView getFfv(){
		return ffv;
	}
	
	public double getxMin() {
		return xMin;
	}

	public void setxMin(double xMin) {
		this.xMin = xMin;
	}

	public double getxMax() {
		return xMax;
	}

	public void setxMax(double xMax) {
		this.xMax = xMax;
	}

	public double getyMin() {
		return yMin;
	}

	public void setyMin(double yMin) {
		this.yMin = yMin;
	}

	public double getyMax() {
		return yMax;
	}

	public void setyMax(double yMax) {
		this.yMax = yMax;
	}

	public int getMaxIter() {
		return maxIter;
	}

	public void setMaxIter(int maxIter) {
		this.maxIter = maxIter;
	}
}
