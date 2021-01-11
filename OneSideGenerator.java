/** generates vehicles for an entire side of the intersection */
import java.util.*;
import java.util.concurrent.locks.*;

public class OneSideGenerator implements Runnable, GenStats {
	
	/** direction in which this generator's cars will run */
	private Direction direction;
	/** intersection to generate cars for*/
	private Intersection intersection;
	private int vehiclesBorn;
	private int vehiclesDeparted;
	private ReentrantLock generatorLock;
	/** list of times taken for cars to leave the grid */
	private ArrayList<Integer> vehicleStats;
	/** integer that represents the maximum coordinate for a given axis 
	*	used for generating a random x or y coordinate 
	*/
	private int coordinateBound;
	
	/** constructor */
	public OneSideGenerator(Direction d, Intersection i) {
		intersection = i;
		direction = d;
		vehicleStats = new ArrayList<Integer>();
		generatorLock = new ReentrantLock();
		coordinateBound = intersection.getWidthHeight()[d.getAxis() ^ 1];
	}
	
	/** generates cars at random intervals with randomly assigned speeds
	*	until the intersection has stopped rendering 
	*/
	public void run() {
		Random random = new Random();
		while(intersection.isRunning()) {
			// wait random length of time before creating new car
			int randomSleep = random.nextInt(1000) + 100;
			try {
				Thread.sleep(randomSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// assign random speed to the car
			int vehicleSpeed = random.nextInt(500) + 50;
			// assign random coordinate value for vehicle to interpret 
			int coordinate = random.nextInt(coordinateBound);
			Vehicle v = new BasicCar(coordinate, direction, vehicleSpeed, intersection, this);
			Thread t = new Thread(v);
			t.start();
			vehiclesBorn ++;
		}
	}

	public ArrayList<Integer> getVehicleStats() {
		return vehicleStats;
	}

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
