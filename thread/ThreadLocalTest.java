import java.util.Random;

public class ThreadLocalTest {
	static volatile int counter = 0;
	static Random random = new Random();

	private static class ThreadLocalObject extends ThreadLocal {
		Random random = new Random();
		protected Object initialValue() {
			return new Integer(random.nextInt(1000));
		}
	}

	static ThreadLocal threadLocal = new ThreadLocalObject();

	private static void displayValues() {
		System.out.println("Thread Name:" + Thread.currentThread().getName() + ", initialValue:" + threadLocal.get() +", counter:" + counter);
	}

	public static void main(String args[]) {
		displayValues();

		Runnable runner = new Runnable() {
			public void run() {
				synchronized (ThreadLocalTest.class) {
					counter++;
				}
				displayValues();
				try {
					Thread.sleep(((Integer)threadLocal.get()).intValue());
					displayValues();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};

		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(runner);
			t.start();
		}
	}
}
