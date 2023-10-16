import java.util.LinkedList;
import java.util.NoSuchElementException;

public class JobQueue implements Queue {
	private static final String NAME = "JOB QUEUE";
	private static final Object monitor = new Object();

	private LinkedList jobs = new LinkedList();

	private static JobQueue instance = new JobQueue();
	private JobQueue() {}

	public static JobQueue getInstance() {
		if (instance == null) {
			instance = new JobQueue();
		}
		return instance;
	}
	
	public String getName() {
		return NAME;
	}

	public LinkedList getLinkedList() {
		return jobs;
	}

	public void clear() {
		synchronized (monitor) {
			jobs.clear();
		}
	}

	public void put(Object o) {
		synchronized (monitor) {
			jobs.addLast(o);
		}
	}

	public Object pop() throws InterruptedException, NoSuchElementException {
		Object o = null;
		if (jobs.isEmpty()) {
			return "empty";
		}	
		o = jobs.removeFirst();
		
		if (o == null) throw new NoSuchElementException();
		return o;
	}

	public int size() {
		return jobs.size();
	}
}
