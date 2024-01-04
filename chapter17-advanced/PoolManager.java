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
	
	public static void registByteBufferPool(SelectorPoolIF selectorPool) {
		map.put("ByteBufferPool", selectorPool);
	}
	
	public static ByteBufferPoolIF getByteBufferPool(SelectorPoolIF selectorPool) {
		return (SelectorPoolIF) map.get("ByteBufferPool", selectorPool);
	}
}

	
