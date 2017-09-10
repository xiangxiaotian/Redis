package com.lovo.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis���ӳع�����
 * @author Administrator
 *
 */
public class RedisUtils {
	//Redis������IP��ַ
	private static String ADDR = "127.0.0.1";
	
	//Redis�������˿ں�
	private static int PORT = 6379;
	
	//��������
	private static String AUTH = "";
	
	//�������ӵ����������Ĭ��Ϊ8��
	private static int MAX_ACTIVE = 1024;
	
	//���ӳ�������״̬����ʵ��������Ĭ��Ϊ8��
	private static int MAX_IDLE = 200;
	
	//�ȴ��������ӵ�ʱ�������룩
	private static int MAX_WAIT = 10000;
	
	private static JedisPool jedisPool = null;
	/**
	 * ��ʼ��Redis���ӳ�
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
	 * �ͷ�Jedisʵ��
	 * @param jedis
	 */
	public static void delJedis(Jedis jedis){
		if(jedis!=null){
			jedisPool.returnResource(jedis);
		}
	}
}
