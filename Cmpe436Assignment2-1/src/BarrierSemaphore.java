public class BarrierSemaphore {
	private int count;
	private int M;
	private int N;
	private int limit;
	
	public BarrierSemaphore(int M, int N) {
		this.count = 0;
		this.M = M;
		this.N = N;
		this.limit = M * N;
	}
	
	/**
	 * When any thread finishes their job, they call this function with the state
	 * they see.
	 * If the caller is the last thread to finish, we accept its state as the
	 * completed valid state, since state transitions are synchronized on this `finish` function.
	 * 
	 * @param stepData   Data that threads see and reads
	 * @param sharedData Data that is globally shared across threads
	 */
	public synchronized void finish(int[][] stepData, int[][] sharedData) {
		count++;
		if(count == limit) {
			count = 0;
			for(int i = 0; i < M; i++) {
				for(int j = 0; j < N; j++) {
					stepData[i][j] = sharedData[i][j];
				}
			}
			
			Utils.printMtx(sharedData);
			
			notifyAll();
		} else {
			try { wait(); } catch (InterruptedException e) {}
		}
	}
}