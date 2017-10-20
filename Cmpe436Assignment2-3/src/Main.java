/**
 * Yiğit Özkavcı
 * 2013400111
 * yigitozkavci8@gmail.com
 * Cmpe436-Assignment 2
 */
import java.util.ArrayList;

/**
 * Since integers in Java are immutable, we need to create this dummy 
 * object to demonstrate mutations.
 * 
 * @author yigitozkavci
 *
 */
class MutableObj {
	public int value;
	public MutableObj(int value) {
		this.value = value;
	}
}

/**
 * Class for our bad threads.
 * 
 * @author yigitozkavci
 *
 */
class BadThread extends Thread {
	private MutableObj mo;
	
	public BadThread(MutableObj mo) {
		this.mo = mo;
	}
	
	/**
	 * Description is in README.txt
	 */
	public void run() {
		System.out.println("Running");
		int val = this.mo.value; // Read
		if(val % 2 == 0) {
			this.mo.value += 1;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.mo.value += 1;
		} else {
			this.mo.value++;
		}
	}
}

public class Main {
	public static void main(String[] args) {
		ArrayList<Thread> threads = new ArrayList<>();
		
		MutableObj mo = new MutableObj(0);
		for(int i = 0; i < 5; i++) {
			Thread t = new BadThread(mo);
			t.start();
			threads.add(t);
		}
		threads.forEach(t -> {
			try {
				t.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println(mo.value);
	}
}