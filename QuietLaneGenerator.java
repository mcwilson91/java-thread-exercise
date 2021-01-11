/** generates BasicCar threads for a single row or column of 
* 	the intersection with a lower frequency and assigns a 
*	slightly higher average speed than the other generators
*/
import java.util.*;
import java.util.concurrent.locks.*;

public class QuietLaneGenerator extends OneLaneGenerator implements Runnable, GenStats {
	
	public QuietLaneGenerator(int c, Direction d, Intersection i) {
		super(c, d, i);
	}
	
	public void run() {
		Random random = new Random();
		while(intersection.isRunning()) {
			int randomSleep = random.nextInt(10000) + 1000;
			try {
				Thread.sleep(randomSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			int vehicleSpeed = random.nextInt(500) + 10;
			Vehicle v = new BasicCar(coordinate, direction, vehicleSpeed, intersection, this);
			Thread t = new Thread(v);
			t.start();
			vehiclesBorn ++;
		}
	}

}
