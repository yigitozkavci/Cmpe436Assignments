import java.util.ArrayList;
import java.util.function.Consumer;

/**
 * Game of Life algorithm class. This class is being used because it's convenient
 * to keep `M`, `N` and `data` state variables in logic flow
 * @author yigitozkavci
 *
 */
public class GOL {
	private int M, N, maxGenerations;
	private int currentGen;
	private int[][] lastGenData;
	
	public GOL(int M, int N, int[][] inputData, int maxGenerations) {
		this.M = M;
		this.N = N;
		this.maxGenerations = maxGenerations;
		this.currentGen = 0;
		this.lastGenData = new int[M][N];
		for(int i = 0; i < M; i++)  lastGenData[i] = inputData[i].clone();
	}
	
	/**
	 * Run the Game of Life algorithm
	 */
	public void run(Consumer<Void> c) {
		int[][] temp = new int[M][N];
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < N; j++) {
				temp[i][j] = decideFor(i, j);
				// p(this.lastGenData);
			}
		}
		currentGen++;
		this.lastGenData = temp;
		printM(this.lastGenData);
		if(currentGen < maxGenerations) {
			c.accept(null);
			run(c);
		}
	}

	/**
	 * @param i row of the cell
	 * @param j column of the cell
	 * @return Value of the cell in the next generation
	 */
	private int decideFor(int i, int j) {
		int val = this.lastGenData[i][j];
		long count = getNeighbours(i, j);
		if(val == 1) {
			if(count == 2 || count == 3) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if(count == 3) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	private void printM(int[][] data) {
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}
	/**
	 * Given the position, returns a list of positions of available neighbors
	 * 
	 * @param i X position
	 * @param j Y position
	 * @return List of neighbors available
	 */
	private int getNeighbours(int i, int j) {
		ArrayList<Pair<Integer, Integer>> result = new ArrayList<>();
		
		// Up
		if(i != 0) result.add(new Pair<>(i - 1, j));
		
		// Down
		if(i != M - 1) result.add(new Pair<>(i + 1, j));
		
		// Left
		if(j != 0) result.add(new Pair<>(i, j - 1));
		
		// Right
		if(j != N - 1) result.add(new Pair<>(i, j + 1));
		
		// Up Left
		if(i != 0 && j != 0) result.add(new Pair<>(i - 1, j - 1));
		
		// Down Left
		if(i != M - 1 && j != 0) result.add(new Pair<>(i + 1, j - 1));
		
		// Up Right
		if(i != 0 && j != N - 1) result.add(new Pair<>(i - 1, j + 1));
		
		// Down Right
		if(i != M - 1 && j != N - 1) result.add(new Pair<>(i + 1, j + 1));
		
		return result.stream().map(p -> this.lastGenData[p.left][p.right]).reduce(0, Integer::sum);
	}
	
	public int[][] getLastGenData() {
		return this.lastGenData;
	}
}