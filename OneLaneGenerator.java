/** generates vehicle threads for single row or column of the intersection */
import java.util.*;
import java.util.concurrent.locks.*;

public class OneLaneGenerator implements Runnable, GenStats {
	
	/** single coordinate determining which lane this generator will generate for */
	protected int coordinate;
	/** direction in which the vehicle will move to begin */
	protected Direction direction;
	/** intersection the vehicle will move across */
	protected Intersection intersection;
	/** number of vehicles created */
	protected int vehiclesBorn;
	/** number of thie genetor's cars that have left the grid */
	protected int vehiclesDeparted;
	protected ReentrantLock generatorLock;
	/** list of the time taken by all this generators cars to leave */
	protected ArrayList<Integer> vehicleStats;
	
	/** constructor */
	public OneLaneGenerator(int c, Direction d, Intersection i) {
		coordinate = c;
		intersection = i;
		direction = d;
		vehicleStats = new ArrayList<Integer>();
		generatorLock = new ReentrantLock();
	}
	
	/** generates cars at random intervals with randomly assigned speeds
	*	until the intersection has stopped rendering 
	*/
	public void run() {
		Random random = new Random();
		while(intersection.isRunning()) {
			// wait random length of time before creating new car
			int randomSleep = random.nextInt(5000) + 300;
			try {
				Thread.sleep(randomSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}	// assign random speed to the car
			int vehicleSpeed = random.nextInt(250) + 100;
			Vehicle v = new BasicCar(coordinate, direction, vehicleSpeed, intersection, this);
			Thread t = new Thread(v);
			t.start();
			vehiclesBorn ++;
		}
	}

	/** get list of times taken to cross the grid by this generator's cars */
	public ArrayList<Integer> getVehicleStats() {
		return vehicleStats;
	}

	/** add time to the list */
	public void updateStats(int timeTaken) {
		generatorLock.lock();
		try {
			vehicleStats.add(timeTaken);
			vehiclesDeparted ++;
		}
		finally {
			generatorLock.unlock();
		}
	}
	
	public int getVehiclesBorn() {
		return vehiclesBorn;
	}

	public int getVehiclesDeparted() {
		return vehiclesDeparted;
	}

}
