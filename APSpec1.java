/** main class */
public class APSpec1 {

	/** size of the intersection */
	private static final int X_DIMENSION = 20;
	private static final int Y_DIMENSION = 10;
	/** number of times to print the intersection */ 
	private static final int NUMBER_OF_RENDERS = 2000;

	public static void main(String[] args) {

		// initialise the intersection and set thread running
		Intersection intersection = new Intersection(X_DIMENSION, Y_DIMENSION, NUMBER_OF_RENDERS);
		Thread interThread = new Thread(intersection);
		interThread.start();

		// intialise and run generator for south-bound cars
		OneSideGenerator south = new OneSideGenerator(Direction.SOUTH, intersection);
		Thread southThread = new Thread (south);
		southThread.start();

		// intiialise and run generator for east-bound cars
		OneSideGenerator east = new OneSideGenerator(Direction.EAST, intersection);
		Thread eastThread = new Thread (east);
		eastThread.start();

		try { // wait for intersection thread to finish 
			interThread .join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
		// quit
		System.exit(0);
	}
}