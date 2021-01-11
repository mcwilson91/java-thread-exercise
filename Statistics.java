/**
*	maintains a list of GenStats objects which are used to build a report 
*	detailing the min, max, mean and variance of times for each generator
*/
import java.util.*;

public class Statistics {

	private GenStats [] allGenerators;
	private int index;

	/** constructor */
	public Statistics(int width, int height) {
		allGenerators = new GenStats [width + height];
		index = 0;
	}

	/** add a generator to the array of generators - called by the main */
	public void addGenerator(GenStats g) {
		allGenerators [index] = g;
		index ++;
	}

	/** determine whether the number of cars leaving the grid is differen
	*	to the number of cars being created 
	*/
	private int calcVehicleDiscrepancy(GenStats g) {
		return g.getVehiclesBorn() - g.getVehiclesDeparted();
	}

	/** build report */
	public String getReport() {

		StringBuilder reportBuilder = new StringBuilder();

		// for each generator 
		for (int i = 0; i < index; i ++) {
			ArrayList<Integer> vehicleTimes = allGenerators[i].getVehicleStats();
			// if the generator has a list of times 
			if (vehicleTimes.size() > 0) {
				int sum = 0;
				int max = 0;
				int min = vehicleTimes.get(0);
				// calculate maximum and minimum times 
				for (Integer time : vehicleTimes) {
					sum += time;
					if (time > max)
						max = time;
					if (time < min)
						min = time;
				}
				// calculate mean time
				int mean = sum / vehicleTimes.size();
				
				// calculate the variance 
				int sumOfSquares = 0;
				for (Integer time : vehicleTimes) {
					int difference = time - mean;
					sumOfSquares += (difference * difference);
				}
				int variance = sumOfSquares / vehicleTimes.size();
				
				// convert number of renders completed into number of 
				// miliseconds elapsed
				max *= 20;
				min *= 20;
				mean *= 20;
				variance *= 20;
				
				// create report string 
				reportBuilder.append("Generator " + i + ": Max time: " + max / 1000 + "." + (max % 1000) / 10 
						+ "secs Min time: " + min / 1000 + "." + (min % 1000) / 10 
						+ "secs Mean time: " + mean / 1000 + "." + (mean % 1000) / 10
						+ "secs Variance: " + variance / 1000 + "." + (variance % 1000) / 10 + "secs\n");
				}
		}
		return reportBuilder.toString();
	}


}