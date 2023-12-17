import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleChatServer {
	private static final String HOST = "localhost";
	private static final int PORT = 9090;

	private static FileHandler fileHandler;
	private static Logger logger = Logger.getLogger("nothing");

	private Selector selector = null;
	private ServerSocketChannel serverSocketChannel = null;
	private ServerSocket serverSocket = null;

	private Vector room = new Vector();

	public void initServer() {
		try {
			selector = Selector.open();
			serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			serverSocket = serverSocketChannel.socket();

			InetSocketAddress isa = new InetSocketAddress(HOST, PORT);
			serverSocket.bind(isa);

			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			log(Level.WARNING, "SimpleChatServer.initServer()", e);
		}
	}

	public void startServer() {
		info("Server is started..");
		try {
			while (true) {
				info("요청을 기다리는 중..");
				selector.select();

				Iterator it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					if (key.isAcceptable()) {
						accept(key);
					} else if (key.isReadable()) {
						read(key);
					}
					it.remove();
				}
			}
		} catch (Exception e) {
			log(Level.WARNING, "SimpleChatServer.startServer()", e);
		}
	}

	private void accept(SelectionKey key) {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
		SocketChannel sc;
		try {
			sc = server.accept();
			registerChannel(selector, sc, SelectionKey.OP_READ);
			info(sc.toString() + " 클라이언트가 접속했습니다.");
		} catch(ClosedChannelException e) {
			log(Level.WARNING, "SimpleChatServer.accept()", e);
		} catch (IOException e) {
			log(Level.WARNING, "SimpleChatServer.accept()", e);
		}
	}

	private void registerChannel(Selector selector, SocketChannel sc, int ops) throws ClosedChannelException, IOException {
		if (sc == null) {
			info("Invalid Connection");
			return;
		}
		sc.configureBlocking(false);
		sc.register(selector, ops);

		addUser(sc);
	}

	private void read(SelectionKey key) {
		SocketChannel sc = (SocketChannel) key.channel();
		ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
		try {
			int read = sc.read(buffer);
			info(read + "byte를 읽었습니다.");
		} catch(IOException e) {
			try {
				sc.close();
			} catch (IOException e1) {

			}
			removeUser(sc);
			info(sc.toString() + "클라이언트가 접속을 해제했습니다.");
		}

		try {
			broadcast(buffer);
		} catch(IOException e) {
			log(Level.WARNING, "SimpleChatServer.broadcast()", e);
		}
		clearBuffer(buffer);
	}

	private void broadcast(ByteBuffer buffer) throws IOException {
		buffer.flip();

		Iterator iter = room.iterator();

		while (iter.hasNext()) {
			SocketChannel sc = (SocketChannel) iter.next();
			if (sc != null) {
				sc.write(buffer);
				buffer.rewind();
			}
		}
	}

	private void clearBuffer(ByteBuffer buffer) {
		if (buffer != null) {
			buffer.clear();
			buffer = null;
		}
	}

	private void addUser(SocketChannel sc) {
		room.add(sc);
	}

	private void removeUser(SocketChannel sc) {
		room.remove(sc);
	}

	public void initLog() {
		try {
			fileHandler = new FileHandler("SimpleChatServer.log");
		} catch (IOException e) {}

		logger.addHandler(fileHandler);
		logger.setLevel(Level.ALL);
	}

	public void log(Level level, String msg, Throwable error) {
		logger.log(level, msg, error);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	////// Main/////
	public static void main(String[] args) {
		SimpleChatServer scs = new SimpleChatServer();
		scs.initLog();
		scs.initServer();
		scs.startServer();
	}
}


