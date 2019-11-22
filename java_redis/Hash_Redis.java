
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
import redis.clients.jedis.Jedis;
 
public class Hash_Redis {
 
	/**
	 * Redis Redis(2.6版本以上) Hash 常用操作和方法详解
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis je=new Jedis("127.0.0.1",6379);
		je.auth("12345678");
		
		/**
		 * 将哈希表 key 中的域 field 的值设为 value 。
			如果 key 不存在，一个新的哈希表被创建并进行HSET 操作。
			如果域 field 已经存在于哈希表中，旧值将被覆盖。
		 */
		je.hset("hk", "f1", "va1");
		/**
		 * 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在。
			若域 field 已经存在，该操作无效。
			如果 key 不存在，一个新哈希表被创建并执行HSETNX 命令。
		 */
		je.hsetnx("hk", "f2", "va2");
		
		/**
		 * 一次存储多个Key和值
		 */
		Map<String,String> map=new HashMap<String,String>();
		map.put("f8", "v9");
		map.put("f9", "v9");
		je.hmset("hk", map);
		
		
		/** 返回哈希表 key 中所有域的值。 */
		List<String> values=je.hvals("hk");
		System.out.println(values);
		
		/**  删除哈希表 key 中的一个或多个指定域，不存在的域将被忽略。 */
		je.hdel("hk", "f1","f3");
		
		/** 查看哈希表 key 中，给定域 field 是否存在。 */
		boolean existsFile=je.hexists("hk", "f2");
		System.out.println(existsFile);
		
		/** 返回哈希表 key 中给定域 field 的值。 */
		String fe=je.hget("hk", "f2");
		System.out.println(fe);
		
		/**  返回哈希表 key 中，所有的域和值。
		在返回值里，紧跟每个域名 (field name) 之后是域的值 (value)，
		所以返回值的长度是哈希表大小的两倍。 */
		Map<String,String> all=je.hgetAll("hk");
		
		
		/**
		 * 为哈希表 key 中的域 field 的值加上增量 increment 。
			增量也可以为负数，相当于对给定域进行减法操作。
			如果 key 不存在，一个新的哈希表被创建并执行HINCRBY 命令。
			如果域 field 不存在，那么在执行命令前，域的值被初始化为 0 。
			对一个储存字符串值的域 field 执行HINCRBY 命令将造成一个错误。
			本操作的值被限制在 64 位 (bit) 有符号数字表示之内。
		 */
		//je.hincrBy("hk", "inc", 0);
		System.out.println(je.hget("hk", "inc"));
		je.hincrByFloat("hk", "inc", 1.25);
		System.out.println(je.hget("hk", "inc"));
		System.out.println("-------------------------");
		
		/** 返回哈希表 key 中的所有域。 */
		Set<String> keys=je.hkeys("hk");
		System.out.println(keys);
		
		/**返回哈希表 key 中域的数量。  */
		Long hleng=je.hlen("hk");
		System.out.println(hleng);
		
		
		/** 返回哈希表 key 中，一个或多个给定域的值。
		如果给定的域不存在于哈希表，那么返回一个 nil 值。
		因为不存在的 key 被当作一个空哈希表来处理，所以对一个不存在的 key 进行HMGET 操作将返回一个只
		带有 nil 值的表。 */
		List<String> lv=je.hmget("hk", "f1","f2","deef");
		System.out.println(lv);
		
		/** 返回哈希表 key 中所有域的值。 */
		List<String> lvs=je.hvals("hk");
		System.out.println(lvs);
	}
 
}
