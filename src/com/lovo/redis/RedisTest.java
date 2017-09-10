package com.lovo.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * Redis������
 * @author Administrator
 *
 */
public class RedisTest {
	private Jedis jedis;
	
	//����Redis���ݿ�
	public void connectRedis(){
		jedis = new Jedis("127.0.0.1",6379);
		//Ȩ����֤
		//jedis.auth("admin");
	}
	//����Redis�����ַ���
	public void testString(){
		//�������
		jedis.set("lovo", "12345");
		System.out.println(jedis.get("lovo"));
		
		//ƴ���ַ���
		jedis.append("lovo", "6789");
		System.out.println(jedis.get("lovo"));
		String[] keys = {"lovo"};
		//ɾ������
		jedis.del(keys);
		String[] keys2 = {"name","zhangsan","age","23","qq","545323545"};
		jedis.mset(keys2);
		jedis.incr("age");
		System.out.println(jedis.get("name")+"-"+jedis.get("age")+"-"+jedis.get("qq"));
	}
	
	//����redis����Map
	public void testMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", "lisi");
		map.put("age", "20");
		map.put("qq", "434564");
		jedis.hmset("user", map);
		
		//��һ��������map������������Ĳ�����map����Ԫ��key
		List<String> rsmap = jedis.hmget("user", "name","age","qq");
		System.out.println(rsmap);
		
		//ɾ��mapԪ��
		jedis.hdel("user", "name","age");
		System.out.println(jedis.hmget("user", "qq"));
		System.out.println(jedis.hlen("user"));
		System.out.println(jedis.exists("user"));
		System.out.println(jedis.hkeys("user"));
		System.out.println(jedis.hvals("user"));
		
		//����map
		Iterator<String> iterator = jedis.hkeys("user").iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key+":"+jedis.hmget("user", key));
		}
	}
	//����Redis����list
	public void testList(){
		//ɾ������
		jedis.del("java");
		jedis.lpush("java", "spring");
		jedis.lpush("java", "springmvc");
		jedis.lpush("java", "mybatis");
		
		System.out.println(jedis.lrange("java", 0, -1));
		
		//��������
		jedis.rpush("java", "spring");
		
		System.out.println(jedis.lrange("java", 0, -1));
	}
	//����Redis����set����
	public void testSet(){
		//�������
		jedis.sadd("users", "xinxin");
		jedis.sadd("users", "hellowolrd");
		
		//ɾ������
		jedis.srem("users", "xinxin");
		
		System.out.println(jedis.smembers("users"));
		System.out.println(jedis.scard("users"));
		
	}
	//redis���ӳ�
	public void testRedisPool(){
		RedisUtils.getJedis().set("pool", "test");
		System.out.println(RedisUtils.getJedis().get("pool"));
	}
	
	public static void main(String[] args) {
		RedisTest test = new RedisTest();
		test.connectRedis();
//		test.testString();
//		test.testMap();
//		test.testList();
//		test.testSet();
		test.testRedisPool();
	}
}
