import java.io.File;
import java.io.IOException;

public class AdvancedChatServer {
	private Queue queue = null;
	private SelectorPoolIF acceptSelectorPool = null;
	private SelectorPoolIF requestSelectorPool = null;
	private ByteBufferPoolIF byteBufferPool = null;

	ThreadPoolIF acceptThreadPool = null;
	ThreadPoolIF readWriteThreadPool = null;

	public AdvancedChatServer() {
		try {
			initResource();
			startServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void initResource() throws IOException {
		File bufferFile = new File("buffer.tmp");
		if (!bufferFile.exists()) {
			bufferFile.createNewFile();
		}
		bufferFile.deleteOnExit();
		byteBufferPool = new ByteBufferPool(20*1024, 40*2048, bufferFile);

		queue = BlockingEventQueue.getInstance();

		PoolMangager.registByteBufferPool(byteBufferPool);

		acceptThreadPool = new ThreadPool(queue, "AcceptProcessor");
		readWriteThreadPool = new ThreadPool(queue, "ReadWriteProcessor");
		acceptSelectorPool = new AcceptSelectorPool(queue);
		requestSelectorPool = new RequestSelectorPool(queue);

		PoolManager.registAcceptSelectorPool(acceptSelectorPool);
		PoolManager.registRequestSelectorPool(requestSelectorPool);
	}

	private void startServer() {
		acceptThreadPool.startAll();
		readWriteThreadPool.startAll();
		acceptSelectorPool.startAll();
		requestSelectorPool.startAll();
	}

	public static void main(String[] args) {
		AdvancedChatServer server = new AdvancedChatServer();
	}
}
