/** contains the run method for the vehicle and manages
*	the speed at which the car moves across the grid
*/

public class BasicCar extends Vehicle {

	/** the amount of time the car waits before attempting to enter the next block */
	private int speed;

	/** constructor */
	public BasicCar(int c, Direction d, int s, Intersection i, GenStats g) {
		super(c, d, i, g);
		speed = s;
		symbol = d.getSymbol();
	}

	/** runnable method */
	public void run() {

		try { // enter the first block of the grid 
		getNextBlock().enterBlock(this);
		updatePosition();
		birthTime = intersection.getCurrTime();
			try {	// wait before attempting next entry
					Thread.sleep(speed);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}

			// continue entering and leaving blocks
			while (intersection.isRunning()) {
				Block next = getNextBlock();
				next.enterBlock(this);
				getCurrBlock().leaveBlock();
				updatePosition();
				try {	// wait before attempting next entry
					Thread.sleep(speed);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}	
		}
		catch (ArrayIndexOutOfBoundsException e) { // if there are no blocks left cease running
			getCurrBlock().leaveBlock();
			// update stats on cars generated and left
			generator.updateStats(intersection.getCurrTime() - birthTime);
		}
	}

	protected void changeDirection(Direction d) {
		// axis = d.getAxis();
		// modifier = d.getModifier();
	}
	


}
