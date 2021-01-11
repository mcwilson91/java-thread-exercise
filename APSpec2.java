public class APSpec2 {

	/** size of the intersection */
	private static final int X_DIMENSION = 20;
	private static final int Y_DIMENSION = 10;
	/** number of times to print the intersection */
	private static final int NUMBER_OF_RENDERS = 2000;

	public static void main(String[] args) {

		//initialise and set the intersection running 
		Intersection intersection = new Intersection(X_DIMENSION, Y_DIMENSION, NUMBER_OF_RENDERS);
		Thread interThread = new Thread(intersection);
		interThread.start();

		// initialise statistics class for report building
		Statistics stats = new Statistics(X_DIMENSION, Y_DIMENSION);

		// initialise a single lane generator per lane for each direction - can be either quiet
		// or busy traffic generator
		for (int i = 0; i < X_DIMENSION / 2; i ++) {
			OneLaneGenerator south = new BusyLaneGenerator(i, Direction.SOUTH, intersection);
			Thread genThread = new Thread(south);
			genThread.start();
			stats.addGenerator(south);
		}
		for (int i = X_DIMENSION / 2; i < X_DIMENSION; i ++) {
			OneLaneGenerator north = new QuietLaneGenerator(i, Direction.NORTH, intersection);
			Thread genThread = new Thread(north);
			genThread.start();
			stats.addGenerator(north);
		}
		for (int i = 0; i < Y_DIMENSION / 2; i ++) {
			OneLaneGenerator east = new QuietLaneGenerator(i, Direction.EAST, intersection);
			Thread genThread = new Thread(east);
			genThread.start();
			stats.addGenerator(east);
		}
		for (int i = Y_DIMENSION / 2; i < Y_DIMENSION; i ++) {
			OneLaneGenerator west = new BusyLaneGenerator(i, Direction.WEST, intersection);
			Thread genThread = new Thread(west);
			genThread.start();
			stats.addGenerator(west);
		}

		try { // wait for intersection thread to finish 
			interThread .join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} // print report and close the program
		System.out.println(stats.getReport());
		System.exit(0);
	}
}