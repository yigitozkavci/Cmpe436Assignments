/**
 * Game of Life algorithm class. This class is being used as a separate thread.
 * 
 * @author yigitozkavci
 */
public class GolNode extends Thread {
	private int M, N;
	private int maxGenerations;
	private int[][] sharedData;
	private CountingSemaphore dataMutex;
	private CountingSemaphore barrier;
	private int generations;
	private MutableInt workCount;
	private CountingSemaphore barrier2;
	private int totalThr;
	
	public GolNode(int M, int N, int[][] sharedData, int maxGenerations, CountingSemaphore dataMutex, MutableInt workCount, CountingSemaphore barrier, CountingSemaphore barrier2, int totalThr) {
		this.M = M;
		this.N = N;
		this.maxGenerations = maxGenerations;
		this.dataMutex = dataMutex;
		this.sharedData = sharedData;
		this.barrier = barrier;
		this.generations = 0;
		this.workCount = workCount;
		this.barrier2 = barrier2;
		this.totalThr = totalThr;
	}

	/**
	 * @param myExistence whether value is 1 or 0 this thread's position
	 * @param population Population around this thread's position
	 * 
	 * @return Value of the cell in the next generation
	 */
	private int decide(int myExistence, int population) {
		return myExistence == 1
			? population == 2 || population == 3 ? 1 : 0
			: population == 3 ? 1 : 0;
	}
	
	/**
	 * Given the position, returns the population around
	 * 
	 * @param i X position
	 * @param j Y position
	 * @return Population around
	 */
	private int getPopulation() {
		int result = 0;
		for(int i = -1; i <= 1; i++) {
			for(int j = -1; j <= 1; j++) {
				if(i == 0 && j == 0) continue;
				result += this.sharedData[M + i][N + j];
			}
		}
		return result;
	}

	@Override
	public void run() {
		// Preparing these values before anyone enters to the critical section,
		// because we need non-updated values here.
		int myExistence = this.sharedData[M][N];
		int population = getPopulation();
		
		// Atomic knot 1
		dataMutex.P();
		workCount.value++;
		if(workCount.value == totalThr) {
			barrier2.P();
			barrier.V();
		}
		dataMutex.V();
		barrier.P();
		barrier.V();
		
		// Critical section
		this.sharedData[M][N] = this.decide(myExistence, population);
		this.generations++;
		if(generations >= maxGenerations) return;

		// Atomic knot 2
		dataMutex.P();
		workCount.value--;
		if(workCount.value == 0) {
			barrier.P();
			barrier2.V();
			Utils.printMtx(sharedData);
		}
		dataMutex.V();
		barrier2.P();
		barrier2.V();
		
		// Recursive call to run
		run();
	}
}