/**
 * Utility matrix class to handle matrix state & operations
 *  
 * @author yigitozkavci
 */
class Matrix {
	private int rowN;
	private int colN;
	private int[][] data;
	
	/**
	 * Multiply this matrix with m2
	 * 
	 * @param m2 Matrix to multiply with
	 * @return A new matrix that is the result of multiplication
	 * @throws DimensionMismatchException
	 */
	public Matrix multiplyBy(Matrix m2) throws DimensionMismatchException {
		if(colN != m2.rowN) throw new DimensionMismatchException(colN, rowN);
		int[][] result = new int[rowN][m2.colN];
		for(int i = 0; i < rowN; i++) {
			for(int j = 0; j < m2.colN; j++) {
				for(int k = 0; k < colN; k++) {
					result[i][j] += data[i][k] * m2.data[k][j];
				}
			}
		}
		return new Matrix(result);
	}
	
	/**
	 * Only constructor that is allowed
	 * 
	 * @param dat Data to construct this matrix on top of
	 */
	public Matrix(int[][] dat) {
		this.data = dat;
		this.rowN = dat.length;
		this.colN = dat[0].length;
	}
	
	/**
	 * Overriden because matrices have specific way of being represented
	 */
	public String toString() {
		String result = rowN + " " + colN + "\n";
		for(int i = 0; i < rowN; i++) {
			for(int j = 0; j < colN; j++) {
				result += data[i][j] + " ";
			}
			result += "\n";
		}
		return result;
	}
}