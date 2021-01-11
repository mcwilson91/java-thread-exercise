/** maintains its own coordinates as well as a the information necessary 
*	for determining how and in what direction it will move across the grid 
*/

public abstract class Vehicle implements Runnable {

	/** current coordinates of the vehicle */
	protected int [] coordinates;
	/** determines whether the vehicle will move forward 
	*	or back across its current axis
	*/
	protected int modifier;
	/** determines which axis the vehicle will move along */
	protected int axis;
	/** symbol representing a particular vehicle */
	protected char symbol;
	/** intersection to move over */
	protected Intersection intersection;
	/** interface used to update a generators stats once 
	*	a vehicle leaves the intersection 
	*/
	protected GenStats generator;
	/** number of renders completed when car was generated */
	protected int birthTime;
	
	/** constructor */
	public Vehicle(int c, Direction d, Intersection i, GenStats g) {
		modifier = d.getModifier();
		axis = d.getAxis();
		intersection = i;
		
		// determine which axis the given coordinate is for 
		coordinates = new int [2];
		// if vehicle moving forwards vehicle begins off-grid 
		if (modifier == 1) {
			coordinates[axis] = -1;
			coordinates[axis ^ 1] = c;
		}
		else { // if vehicle moving backwards vehicle begins off other side
			coordinates[axis] = intersection.getWidthHeight()[axis];
			coordinates[(axis ^ 1)] = c;
		}
		
		generator = g;
		symbol = 'x'; // default symbol to be overridden
	}
	
	public abstract void run();

	protected abstract void changeDirection(Direction d); // allows vehicle to change direction if necessary
	
	/** get block the vehicle  is currently occupying */
	protected Block getCurrBlock() {
		return intersection.getBlock(coordinates[0], coordinates[1]);
	}
	
	/** get the next block for the vehicle to attempt to occupy */
	protected Block getNextBlock() {
		int [] c = new int [2];
		c[axis] = coordinates[axis];
		c[axis ^ 1] = coordinates[axis ^ 1];
		c[axis] += modifier;
		return intersection.getBlock(c[0], c[1]);
	}
	
	/** update the vehicles current coordinates when entry is successful */
	protected void updatePosition() { 
		coordinates[axis] += modifier;
	}
	
	public char getSymbol() {
		return symbol;
	}
}
