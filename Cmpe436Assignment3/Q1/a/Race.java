class MutableInt {
  private int value;

  public MutableInt(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}

/**
 * There is a race and RoadRunner's LS tool finds it
 */
public class Race extends Thread{
  static final int ITERS = 5000;
  private MutableInt mint;

  public Race(MutableInt mint) {
    this.mint = mint;
  }

  public void inc() {
    this.mint.setValue(this.mint.getValue() + 1);
  }

	@Override
	public void run() {
		for (int i = 0; i < ITERS; i++) {
		    inc();
		}
	}

	public static void main(String args[]) throws Exception {
    MutableInt mint = new MutableInt(0);
		final Race t1 = new Race(mint);
		final Race t2 = new Race(mint);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
    System.out.println(mint.getValue());
	}
}
