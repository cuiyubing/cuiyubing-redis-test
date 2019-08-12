package com.cuiyubing.cms.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cuiyubing.cms.domain.User;
import com.cuiyubing.common.utils.RandomUtil;
import com.cuiyubing.common.utils.StringUtil;
/**
 * 
 * @ClassName: RedisHashTest 
 * @Description: 使用Redis的Hash类型保存十万个user随机对象到Redis
 * @author:cyb 
 * @date: 2019年8月12日 上午9:30:37
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-beans.xml")
public class RedisHashTest {
		@Resource
		private RedisTemplate<String, Serializable> redisTemplate;
		
		Map<String,User> users = new HashMap<String,User>();
		
		@Test
		public void inert_test() {
			//模拟生成十万条数据
			for (int i = 0; i < 100000; i++) {
				users.put("u_"+i, (new User(i,StringUtil.generateChineseName()+StringUtil.randomChineseString(2),
						RandomUtil.random(1, 2),"13"+RandomUtil.randomString(9),
						RandomUtil.randomString(3),RandomUtil.random(18, 70))));
			}
			//开始时间
			long startTime = System.currentTimeMillis();
			//保存数据
				redisTemplate.opsForHash().putAll("keys", users);
			//结束时间
			long endTime = System.currentTimeMillis();
			//输出系列化方式、保存数量、所耗时间三项数据，并将这三项数据复制到记事本中
			System.out.println("Hash,jdk序列化保存十万条数据,所消耗时间"+(endTime-startTime));
		}
}
