/** defines the directions in which a car can run and contains attributes
*	which determine how a vehicle moves across the grid
*/
public enum Direction {
	
	NORTH (1, -1, '^'),
	EAST (0, 1, '>'),
	SOUTH (1, 1, 'V'),
	WEST (0, -1, '<');
	
	/** axis is either 0 (representing x-axis) or 1 (representing y-axis) */
	private final int axis;
	/** modifier determines whether the vehicle moves forward (1) or back (-1) */
	private final int modifier;
	/** default symbol */ 
	private final char symbol;
	
	Direction (int a, int m, char s) {
		axis = a;
		modifier = m;
		symbol = s;
	}
	
	public int getAxis() {
		return axis;
	}
	
	public int getModifier() {
		return modifier;
	}

	public char getSymbol() {
		return symbol;
	}
}
