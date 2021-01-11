/** Block represents each square of the intersection and 
*	contains methods to allow each vehiclethread to update the block
*	to contain its own vehicle object. Reentrant lock and condition ensures
*	that threads wait before updating the occupant attribute
*/
import java.util.concurrent.locks.*;

public class Block {
		/** whether the block contains a vehicle */
		private boolean occupied;
		/** the vehicle object occupying the block*/
		private Vehicle occupant;

		private ReentrantLock blockLock;
		private Condition blockCondition;

		/** constructor */
		public Block() {
			occupied = false;
			occupant = null;
			blockLock = new ReentrantLock();
			blockCondition = blockLock.newCondition();
		}

		/** called by a vehicle thread to update occupant */
		public void enterBlock(Vehicle o) {
			blockLock.lock();
			try { // wait until this block is vacated before updating 
				while (this.isOccupied())
					blockCondition.await();
				this.occupant = o;
				this.occupied = true;
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally {
				blockLock.unlock();
			}
		}

		/** checks if the block is occupied */
		public boolean isOccupied() {
			return occupied;
		}
		
		/** returns the char representation of the current vehicle */
		public char getCurrentVehicleSymbol() {
			return occupant.getSymbol();
		}

		/** called by the vehicle thread to leave */
		public void leaveBlock() {
			blockLock.lock();
			this.occupied = false;
			this.occupant = null;
			// wakes all threads currently waiting to enter this block 
			blockCondition.signalAll();
			blockLock.unlock();

	}
}