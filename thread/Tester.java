public class Tester {
	public static void main(String[] args) throws InterruptedException {
		Queue queue = JobQueue.getInstance();

		Thread con1 = new Thread(new Consumer(queue, "1"));
		Thread con2 = new Thread(new Consumer(queue, "2"));
		Thread con3 = new Thread(new Consumer(queue, "3"));
		con1.start();
		con2.start();
		con3.start();

		Thread pro = new Thread(new Producer(queue));
		pro.start();

		Thread.sleep(100);
		pro.interrupt();

		Thread.sleep(400);
		con1.interrupt();
		con2.interrupt();
		con3.interrupt();
	}
}
