/**
 * Threw when dimensions for matrices don't match
 * 
 * @author yigitozkavci
 */
public class DimensionMismatchException extends Exception {
	private static final long serialVersionUID = 6252947376504131053L;
	private int leftDim, rightDim;
	public DimensionMismatchException(int leftDim, int rightDim) {
		this.leftDim = leftDim;
		this.rightDim = rightDim;
	}
	
	public String getMessage() {
		return "Dimensions don't match for matrix multiplication: " + leftDim + " and " + rightDim;
	}
}