/** interface allowing vehicle objects to update the generation statistics
* 	for any kind of car generator and for Statistics class to receive data from
*	any generator
*/
import java.util.ArrayList;

public interface GenStats {
	// get list of time taken by every car generated
	public ArrayList<Integer> getVehicleStats();
	// add a car's time taken to the list
	public void updateStats(int timeTaken);
	// get number of vehicles created - FOR TESTING
	public int getVehiclesBorn();
	// get number of vehicles that have left the gird  - FOR TESTING 
	public int getVehiclesDeparted();

}