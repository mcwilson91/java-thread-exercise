/** generates BasicCar threads for a single row or column of 
* 	the intersection with a higher frequency and assigns a 
*	slightly slowly average speed than the other generators
*/
import java.util.*;
import java.util.concurrent.locks.*;

public class BusyLaneGenerator extends OneLaneGenerator implements Runnable, GenStats {
	
	public BusyLaneGenerator(int c, Direction d, Intersection i) {
		super(c, d, i);
	}
	
	public void run() {
		Random random = new Random();
		while(intersection.isRunning()) {
			// wait a random length of time before generating new car
			int randomSleep = random.nextInt(5000) + 50;
			try {
				Thread.sleep(randomSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// assign vehicle a random speed
			int vehicleSpeed = random.nextInt(400) + 100;
			Vehicle v = new BasicCar(coordinate, direction, vehicleSpeed, intersection, this);
			Thread t = new Thread(v);
			t.start();
			vehiclesBorn ++;
		}
	}

}
