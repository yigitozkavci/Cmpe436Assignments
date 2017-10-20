/**
 * Yiğit Özkavcı
 * 2013400111
 * yigitozkavci8@gmail.com
 * Cmpe436-Assignment 2
 */
public class Main {
	public static void main(String[] args) {
		Mutex m1 = new Mutex();
		Mutex m2 = new Mutex();
		
		Thread t1 = new Thread() {
			public void run() {
				m2.P();
				try {
					Thread.sleep(3); // Represents a long computation
				} catch (InterruptedException e) { e.printStackTrace(); }
				m1.P();
				m1.V();
				m2.V();
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				m1.P();
				try {
					Thread.sleep(3); // Represents a long computation
				} catch (InterruptedException e) { e.printStackTrace(); }
				m2.P();
				m2.V();
				m1.V();
			}
		};
		t1.start(); t2.start();
		try {
			t1.join(); t2.join();
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}