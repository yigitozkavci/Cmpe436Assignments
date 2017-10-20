
public class Utils {
	/**
	 * Pretty-prints the given matrix. This is synchronized just in case
	 * of a calling within non thread-safe code, since this function does UI.
	 * 
	 * @param mtx Matrix to print
	 */
	public synchronized static void printMtx(int[][] mtx) {
		for(int i = 1; i < mtx.length - 2; i++) {
			for(int j = 1; j < mtx[0].length - 2; j++) {
				System.out.print(mtx[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("==============");
	}
	
	/**
	 * @param m Matrix data
	 * @param M Width
	 * @param N Height
	 * 
	 * @return A **new** M to N matrix filled with provided data
	 */
	public static int[][] copyMtx(int[][] m, int M, int N) {
		int[][] copied = new int[M + 2][N + 2];
		for(int i = 0; i < M + 2; i++)  copied[i] = m[i].clone();
		return copied;
	}
}
