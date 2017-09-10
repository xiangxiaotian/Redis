package com.lovo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池工具类
 * @author Administrator
 *
 */
public class RedisUtils {
	//Redis服务器IP地址
	private static String ADDR = "127.0.0.1";
	
	//Redis服务器端口号
	private static int PORT = 6379;
	
	//访问密码
	private static String AUTH = "";
	
	//可用连接的最大数量（默认为8）
	private static int MAX_ACTIVE = 1024;
	
	//连接池最大空闲状态对象实例数量（默认为8）
	private static int MAX_IDLE = 200;
	
	//等待可用连接的时长（毫秒）
	private static int MAX_WAIT = 10000;
	
	private static JedisPool jedisPool = null;
	/**
	 * 初始化Redis连接池
	 */
	static{
		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxActive(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWait(MAX_WAIT);
			config.setTestOnBorrow(true);
			jedisPool = new JedisPool(config,ADDR,PORT,10000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @return
	 */
	public static Jedis getJedis(){
		Jedis jedis = null;
		try {
			if(jedisPool !=null){
				jedis = jedisPool.getResource();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return jedis;
	}
	/**
	 * 释放Jedis实例
	 * @param jedis
	 */
	public static void delJedis(Jedis jedis){
		if(jedis!=null){
			jedisPool.returnResource(jedis);
		}
	}
}
