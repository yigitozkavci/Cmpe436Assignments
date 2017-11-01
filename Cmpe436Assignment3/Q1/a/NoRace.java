class Mutex {
  private boolean locked;

  public Mutex(boolean locked) {
    this.locked = locked;
  }

  public synchronized void P() {
    while(locked) try { wait(); } catch(Exception e) { e.printStackTrace(); };
    locked = true;
  }

  public synchronized void V() {
    locked = false;
    notifyAll();
  }
}

class MutableInt {
  private int value;

  public MutableInt(int value) {
    this.value = value;
  }

  public synchronized int getValue() {
    return this.value;
  }

  public synchronized void setValue(int value) {
    this.value = value;
  }
}

/**
 * There is a race but RoadRunner's LS tool cannot find it
 */
public class NoRace extends Thread{
  static final int ITERS = 5000;
  final private Mutex m;
  final private MutableInt mint;

  public NoRace(Mutex m, MutableInt mint) {
    this.m = m;
    this.mint = mint;
  }

  private void inc() {
    this.mint.setValue(this.mint.getValue() + 1);
  }

	@Override
	public void run() {
		for (int i = 0; i < ITERS; i++) {
		    inc();
		}
	}

	public static void main(String args[]) throws Exception {
    Mutex m = new Mutex(false);
    final MutableInt mint = new MutableInt(0);
		final NoRace t1 = new NoRace(m, mint);
		final NoRace t2 = new NoRace(m, mint);
		t1.start();
		t2.start();
		t1.join();
		t2.join();
    System.out.println(mint.getValue());
	}
}
