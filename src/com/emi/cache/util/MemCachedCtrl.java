package com.emi.cache.util;

import java.sql.Date;
import java.util.Map;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.emi.sys.util.SysPropertites;
import net.sf.json.JSON;
import org.apache.commons.lang.StringUtils;

public class MemCachedCtrl {
	protected static MemCachedClient mcc = new MemCachedClient();
	protected static MemCachedCtrl cached = new MemCachedCtrl();
	SockIOPool pool = SockIOPool.getInstance();
	private static String[] servers = {SysPropertites.get("cache.serverIp")+":"+SysPropertites.get("cache.port")};
	//private static String[] servers = {"127.0.0.1:11211"};//ReadXml.getConfiguration();
	private static Integer[] weights = { 1 };
	private static int initialConnections = 1000;
	private static int minSpareConnections = 5000;
	private static int maxSpareConnections = 50000;
	private static long maxIdleTime = 1000 * 60 * 30;
	private static long maxBusyTime = 1000 * 60 * 5;
	private static long maintThreadSleep = 0;
	private static int socketTimeOut = 1000 * 3 * 60;
	private static boolean nagleAlg = false;

	/**
	 * 不允许通过构造方法创建实例
	 */
	protected MemCachedCtrl() {

	}

	public static MemCachedCtrl getInstance() {
		return cached;
	}

	public void init() {
		if (initConfig())
			initPool();
	}

	private boolean initConfig() {
		return true;
	}

	private void initPool() {
		pool.setServers(servers);
		pool.setWeights(weights);
		pool.setInitConn(initialConnections);
		pool.setMinConn(minSpareConnections);
		pool.setMaxConn(maxSpareConnections);
		pool.setMaxIdle(maxIdleTime);
		pool.setMaxBusyTime(maxBusyTime);
		pool.setMaintSleep(maintThreadSleep);
		pool.setSocketTO(socketTimeOut);
		pool.setNagle(nagleAlg);
		pool.setSocketConnectTO(0);
		pool.setFailover(true);
		
		/**
		 * Tcp的规则就是在发送一个包之前，本地机器会等待远程主机 对上一次发送的包的确认信息到来；这个方法就可以关闭套接字的缓存，
		 * 以至这个包准备好了就发；
		 * 设置是否使用Nagle算法，因为我们的通讯数据量通常都比较大（相对TCP控制数据）而且要求响应及时，因此该值需要设置为false
		 */
		pool.setHashingAlg(SockIOPool.NEW_COMPAT_HASH);
		pool.setAliveCheck(false);
		pool.initialize();
		
		// 压缩设置，超过指定大小的数据都会被压缩
		//mcc.setCompressEnable(true);
		//mcc.setCompressThreshold(64 * 1024);
	}

	//是否存在
	public boolean keyExists(String key) {
		return mcc.keyExists(key);
	}

	//缓存一个对象
	public synchronized boolean add(String key, Object value) {
		return mcc.add(key, value);
	}

	//缓存一个带日期的对象
	public synchronized boolean add(String key, Object value, Date expiry) {
		return mcc.add(key, value, expiry);
	}

	//缓存一个
	public synchronized boolean add(String key, Object value, Integer hashCode) {
		return mcc.add(key, value, hashCode);
	}

	//有覆盖，没有增加
	public synchronized boolean set(String key, Object value) {
		return mcc.set(key, value);
	}

	public synchronized boolean set(String key, Object value, Date expiry) {
		return mcc.set(key, value, expiry);
	}

	public synchronized boolean set(String key, Object value, Integer hashCode) {
		return mcc.set(key, value, hashCode);
	}

	public synchronized boolean delete(String key) {
		return mcc.delete(key);
	}

	public synchronized boolean delete(String key, Object value, Date expiry) {
		return mcc.delete(key, expiry);
	}

	public synchronized boolean delete(String key, Integer hashCode, Date expiry) {
		return mcc.delete(key, hashCode, expiry);
	}

	public synchronized boolean flushAll() {
		return mcc.flushAll();
	}

	//删除多台服务器的缓存
	public synchronized boolean flushAll(String[] servers) {
		return mcc.flushAll(servers);
	}

	public synchronized boolean replace(String key, Object value) {
		return mcc.replace(key, value);
	}

	public synchronized boolean replace(String key, Object value, Date expiry) {
		return mcc.replace(key, value, expiry);
	}

	public synchronized boolean replace(String key, Object value,
			Integer hashCode) {
		return mcc.replace(key, value, hashCode);
	}

	public synchronized boolean replace(String key, Object value, Date expiry,
			Integer hashCode) {
		return mcc.replace(key, value, expiry, hashCode);
	}

	//返回服务器状态
	public Map stats() {
		return mcc.stats();
	}

	//返回全部服务器状态
	public Map stats(String[] servers) {
		return mcc.stats(servers);
	}

	public Object get(String key) {
		return mcc.get(key);
	}

	public Object get(String key, Integer hashCode) {
		return mcc.get(key, hashCode);
	}

	public Object get(String key, Integer hashCode, boolean asString) {
		return mcc.get(key, hashCode, asString);
	}

	public Object getMulti(String[] keys) {
		return mcc.getMulti(keys);
	}

	public void shutDown() {
		pool.shutDown();
	}
	
	public static void main(String[] args) {
		MemCachedCtrl cache = MemCachedCtrl.getInstance();
		cache.init();
		boolean k=	cache.set("ok", "ok");
		System.out.print("返回结果: " + k);
	}

}
