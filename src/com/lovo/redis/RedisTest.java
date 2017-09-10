package com.lovo.redis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * Redis测试类
 * @author Administrator
 *
 */
public class RedisTest {
	private Jedis jedis;
	
	//连接Redis数据库
	public void connectRedis(){
		jedis = new Jedis("127.0.0.1",6379);
		//权限认证
		//jedis.auth("admin");
	}
	//测试Redis操作字符串
	public void testString(){
		//添加数据
		jedis.set("lovo", "12345");
		System.out.println(jedis.get("lovo"));
		
		//拼接字符串
		jedis.append("lovo", "6789");
		System.out.println(jedis.get("lovo"));
		String[] keys = {"lovo"};
		//删除数据
		jedis.del(keys);
		String[] keys2 = {"name","zhangsan","age","23","qq","545323545"};
		jedis.mset(keys2);
		jedis.incr("age");
		System.out.println(jedis.get("name")+"-"+jedis.get("age")+"-"+jedis.get("qq"));
	}
	
	//测试redis操作Map
	public void testMap(){
		Map<String,String> map = new HashMap<String,String>();
		map.put("name", "lisi");
		map.put("age", "20");
		map.put("qq", "434564");
		jedis.hmset("user", map);
		
		//第一个参数，map对象名，后面的参数，map对象元素key
		List<String> rsmap = jedis.hmget("user", "name","age","qq");
		System.out.println(rsmap);
		
		//删除map元素
		jedis.hdel("user", "name","age");
		System.out.println(jedis.hmget("user", "qq"));
		System.out.println(jedis.hlen("user"));
		System.out.println(jedis.exists("user"));
		System.out.println(jedis.hkeys("user"));
		System.out.println(jedis.hvals("user"));
		
		//遍历map
		Iterator<String> iterator = jedis.hkeys("user").iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			System.out.println(key+":"+jedis.hmget("user", key));
		}
	}
	//测试Redis操作list
	public void testList(){
		//删除内容
		jedis.del("java");
		jedis.lpush("java", "spring");
		jedis.lpush("java", "springmvc");
		jedis.lpush("java", "mybatis");
		
		System.out.println(jedis.lrange("java", 0, -1));
		
		//增加数据
		jedis.rpush("java", "spring");
		
		System.out.println(jedis.lrange("java", 0, -1));
	}
	//测试Redis操作set集合
	public void testSet(){
		//添加数据
		jedis.sadd("users", "xinxin");
		jedis.sadd("users", "hellowolrd");
		
		//删除数据
		jedis.srem("users", "xinxin");
		
		System.out.println(jedis.smembers("users"));
		System.out.println(jedis.scard("users"));
		
	}
	//redis连接池
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
