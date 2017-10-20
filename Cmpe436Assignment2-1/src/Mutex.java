public class Mutex {
	private boolean locked;
	
	public Mutex() {
		this.locked = false;
	}
	
	/**
	 * Try to acquire. If lock is not available, get into wait
	 * queue until someone releases it.
	 */
	public synchronized void P() {
		if(locked) {
			try { wait(); } catch (InterruptedException e) {}
		}
	}
	
	/**
	 * Release the lock.
	 */
	public synchronized void V() {
		this.locked = false;
		notify();
	}
}