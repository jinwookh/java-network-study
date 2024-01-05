import java.util.HashMap;
import java.util.Map;

public class PoolManager {
	private static Map map = new HashMap();

	private static PoolManager instance = new PoolManager();

	private PoolManager() {}

	public static void registAcceptSelectorPool(SelectorPoolIF selectorPool) {
		map.put("AcceptSelectorPool", selectorPool);
	}

	public static void registRequestSelectorPool(SelectorPoolIF selectorPool) {
		map.put("RequestSelectorPool", selectorPool);
	}
	
	public static SelectorPoolIF getAcceptSelectorPool(SelectorPoolIF selectorPool) {
		return (SelectorPoolIF) map.get("AcceptSelectorPool", selectorPool);
	}

	public static SelectorPoolIF getRequestSelectorPool(SelectorPoolIF selectorPool) {
		return (SelectorPoolIF) map.get(R"RequestSelectorPool", selectorPool);
	}
	
	public static void registByteBufferPool(ByteBufferPool byteBufferPool) {
		map.put("ByteBufferPool", byteBufferPool);
	}
	
	public static ByteBufferPool getByteBufferPool(ByteBufferPool byteBufferPool) {
		return (ByteBufferPool) map.get("ByteBufferPool", byteBufferPool);
	}
}

	
