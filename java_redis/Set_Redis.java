
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
 
import redis.clients.jedis.Jedis;
 
public class Set_Redis {
 
	/**
	 * Redis Redis(2.6版本以上) Set 常用操作和方法详解
	 * @param args
	 */
	public static void main(String[] args) {
		Jedis jedis=new Jedis('127.0.0.1',6379);
		jedis.auth('12345678');
		
		//sadd  去除重复的元素
		jedis.sadd('jedisSet', '1 2 3 4 5 5'.split(' '));
		Set<String> s = jedis.smembers('jedisSet'); // 返回集合中的所有元素
		System.out.println('sadd 返回值：'+s);//返回值：[1, 2, 3, 4, 5]
		
		//scard 返回集合中元素的个数 当key 不存在时返回 0 
		long n = jedis.scard('jedisSet');// 返回集合中元素的个数 当key 不存在时返回 0 
		System.out.println('jedisSet 集合中元素的个数：'+n);
		
		//sdiff  返回给定的几个集合的差集
		jedis.sadd('jedisSet1', '1 2 3 4 5 5'.split(' '));
		jedis.sadd('jedisSet2', '5 6 7 8'.split(' '));
		s = jedis.sdiff('jedisSet1 jedisSet2'.split(' '));
		System.out.println('返回给定的几个集合的差集: '+s);
		
		//sdiffstore 类似于 sdiff命令，将结果集放入了给定的集合中，返回给定集合的长度 如果给定集合已经存在则对其进行覆盖
		n = jedis.sdiffstore('jedisSet3', 'jedisSet1 jedisSet2'.split(' '));
		s = jedis.smembers('jedisSet3'); // 返回集合中的所有元素
		System.out.println('存放差集集合的长度：'+n + '差集结果集：'+s);
		
		//sinter 返回所有给定集合的交集
		s= jedis.sinter('jedisSet1 jedisSet2'.split(' '));
		System.out.println('返回给定的几个集合的交集: '+s);
		
		//sinterstore 类似于 sinter命令，将结果集放入了给定的集合中，返回给定集合的长度 如果给定集合已经存在则对其进行覆盖
		n = jedis.sinterstore('jedisSet4', 'jedisSet1 jedisSet2'.split(' '));
		s = jedis.smembers('jedisSet4'); // 返回集合中的所有元素
		System.out.println('存放交集集合的长度：'+n + '交集结果集：'+s);
		
		//sismember 判断 某个 成员是否在 某个key中  返回 true 或 false
		jedis.sadd('jedisSet5', 'r t d s e t'.split(' '));
		Boolean flag = jedis.sismember('jedisSet5', 'r');
		System.out.println('判断 某个 成员是否在 某个key中'+flag);
		
		//smvoe  将  集合srckey中的成员 移动到dstkey  原则：集合中的元素不重复
		jedis.sadd('jedisSet6', '测 试'.split(' '));
		jedis.sadd('jedisSet7', 'l d e q f j'.split(' '));
		n = jedis.smove('jedisSet6', 'jedisSet7', '啊');
		Set<String> sSet = jedis.smembers('jedisSet6'); // 返回集合中的所有元素
		Set<String> dSet = jedis.smembers('jedisSet7'); // 返回集合中的所有元素
		System.out.println('成功1 失败0 '+n+' 源集合 '+sSet+' 目标集合'+dSet);//源集合 [测,试] 目标集合[d, e, 啊, l, j, q, f]
		
		//spop 随机移除集合中的元素并返回
		jedis.sadd('jedisSet8', '测 试'.split(' '));
		String s8 = jedis.spop('jedisSet8');//移除并返回集合中的一个随机元素。
		Set<String> jedisSet8 = jedis.smembers('jedisSet8'); // 返回集合中的所有元素
		System.out.println(s8+' '+jedisSet8);
	 
		//srandmember(key) 随机返回集合中的一个元素，不删除
		jedis.sadd('jedisSet9', '测 试'.split(' '));
		String s9 = jedis.srandmember('jedisSet9');
		Set<String> jedisSet9 = jedis.smembers('jedisSet9'); // 返回集合中的所有元素
		System.out.println(s9+' '+jedisSet9);
		
		//srandmember(key,count) 随机返回集合中的一个元素，不删除,count > 0 返回指定个数的集合元素，不重复，如果count 大于集合的长度，那只返回整个集合
		jedis.sadd('jedisSet10', '测 试'.split(' '));
		List<String> setList = jedis.srandmember('jedisSet10',10);
		Set<String> jedisSet10 = jedis.smembers('jedisSet10'); // 返回集合中的所有元素
		System.out.println('集合中的值：'+jedisSet10+' 随机返回的集合： '+setList);//集合中的值：[测,试] 随机返回的集合： [测,试]
		
		jedis.sadd('jedisSet11', '测 试'.split(' '));
		List<String> setListf = jedis.srandmember('jedisSet11',-10);//count < 0 随机返回 指定count 绝对值个元素，如果大于集合长度，则继续返回，允许重复，
		Set<String> jedisSet11 = jedis.smembers('jedisSet11'); // 返回集合中的所有元素
		System.out.println('集合中的值：'+jedisSet11+' 随机返回的集合： '+setListf);//集合中的值：[测,试] 随机返回的集合： [测,试]
		
		//srem  移除集合key 中的一个或多个member 元素，不存在的member 元素会被忽略。当key 不是集合类型，返回一个错误。
		jedis.sadd('jedisSet12', '测 试'.split(' '));
		Set<String> jedisSet12bef = jedis.smembers('jedisSet12'); // 返回集合中的所有元素
		n = jedis.srem('jedisSet12', '测 试'.split(' '));// n = 4 表示移除成功元素的个数
		Set<String> jedisSet12after = jedis.smembers('jedisSet12'); // 返回集合中的所有元素
		System.out.println(n+' jedisSet12 移除前key中的元素： '+jedisSet12bef+'jedisSet12 移除后key中的元素： '+jedisSet12after);//4 jedisSet12 移除前key中的元素： [测,试]jedisSet12 移除后key中的元素： [测]
		
		//SUNION 返回几个集合的并集
		jedis.sadd('jedisSet13', '测 试'.split(' '));
		jedis.sadd('jedisSet14', '1 2 3 4'.split(' '));
		Set<String> sunionSet = jedis.sunion('jedisSet13 jedisSet14'.split(' '));
		System.out.println('jedisSet13  jedisSet14并集：'+sunionSet);
		
		//sunionstore
		n = jedis.sunionstore('jedisSet15', 'jedisSet13 jedisSet14'.split(' '));// 返回并集集合的长度
		sunionSet = jedis.smembers('jedisSet15');
		System.out.println(n+ ' jedisSet13  jedisSet14并集：'+sunionSet);
	}
 
}
