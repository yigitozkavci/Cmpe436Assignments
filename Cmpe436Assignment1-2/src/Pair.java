/**
 * A utility class to keep a generic tuple
 * @author yigitozkavci
 *
 * @param <L> Left value
 * @param <R> Right value
 */
public class Pair<L, R> {
	public L left;
	public R right;
	public Pair(L left, R right) {
		this.left = left;
		this.right = right;
	}
}