/** maintains a matrix of block objects that each car thread can move into,
*	as well as keeping a record of the time elapsed since intialisation for 
*	use in the statistics class */
public class Intersection implements Runnable {
	/** all the blocks a vehicle can move into */
	private Block [][] grid;
	/** size of the intersection */
	private int height;
	private int width;
	/** number of times to render the grid */
	private int timeToRun;
	/** number of timea the grid has been rendered */
	private int timeSoFar;
	/** whether the rendering has finished or not */
	private boolean isRunning;

	/** constructor */
	public Intersection(int x, int y, int z) {
		height = y;
		width = x;
		timeToRun = z;
		grid = new Block [height][width];
		for (int i = 0; i < y; i++)
			for (int j = 0; j < x; j ++)
				grid [i][j] = new Block();
	}

	/** return a block for a given set of coordinates 
	*	throw exception if a block does not exist so 
	*	the vehicle thread can leave the grid 
	*/
	public Block getBlock(int x, int y) throws ArrayIndexOutOfBoundsException {
		return grid[y][x];
	}
	
	/** return an array containing the dimensions of the 
	*	grid - x is 0 and y is 1 so the vehicle can compare to 
	*	its own coordinate array 
	*/
	public int [] getWidthHeight() {
		int [] size = new int [2];
		size [0] = width;
		size [1] = height;
		return size;
	}

	/** get number of renders (representing 20 miliseconds) elapsed */
	public int getCurrTime() {
		return timeSoFar;
	}

	/** draw the grid with the char representations of each vehicle z times */
	public void run() {
		isRunning = true;
		for (timeSoFar = 0; timeSoFar < timeToRun; timeSoFar ++) {
			// draw top line
			for (int i = 0; i < (grid[0].length * 2) + 1; i ++)
				System.out.print('-');
			System.out.println();
			// draw the lanes of the gris
			for (int i = 0; i < grid.length; i ++ ) {
				for (int j = 0; j < (grid[i].length * 2) + 1; j ++) {
					if ((j & 1) == 0)
						System.out.print('|');
					else { 	// if the block contains a vehicle print its symbol
						if (grid[i][j/2].isOccupied()) 
							System.out.print(grid[i][j/2].getCurrentVehicleSymbol());
						else
							System.out.print(' ');
					}
				}
				System.out.println();
			}
			// draw the bottom line
			for (int i = 0; i < (grid[0].length * 2) + 1; i ++)
				System.out.print('-');
			System.out.println();
			// wait 20 miliseconds before rendering again 
			try {
				Thread.sleep(20);
			}
			catch(InterruptedException e) {}
		}
		isRunning = false;
	}

	/** checks if the intersection is still being rendered */
	public boolean isRunning() {
		return isRunning;
	}



}