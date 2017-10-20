public class CountingSemaphore {
	private int initValue;
	
	public CountingSemaphore(int initValue) {
		this.initValue = initValue;
	}
	
	/**
	 * Try to acquire. If lock is not available, get into wait
	 * queue until someone releases it.
	 */
	public synchronized void P() {
		while(initValue == 0) {
			try { wait(); } catch (InterruptedException e) {}
		}
		initValue--;
	}
	
	/**
	 * Release the lock.
	 */
	public synchronized void V() {
		this.initValue++	;
		notifyAll();
	}
}