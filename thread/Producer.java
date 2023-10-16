public class Producer implements Runnable {
	private Queue queue = null;

	public Producer(Queue queue) {
		this.queue = queue;
	}

	public void run() {
		System.out.println("[ Start Producer.. ]");
		try {
			int i = 0;
			while (!Thread.currentThread().isInterrupted() && i < 40000) {
				queue.put(Integer.toString(i++));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("[ End Producer.. ]");
		}
	}
}
