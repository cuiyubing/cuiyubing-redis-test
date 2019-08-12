package com.cuiyubing.cms.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
 * @ClassName: RedisJSONTest 
 * @Description: 使用JSON系列化方式保存十万个user随机对象到Redis
 * @author:cyb 
 * @date: 2019年8月12日 上午9:30:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-beans2.xml")
public class RedisJSONTest {

		@Resource
		private RedisTemplate<String, Serializable> redisTemplate;
		
		List<User> list = new ArrayList<User>();
		
		@Test
		public void inert_test() {
			//模拟生成十万条数据
			for (int i = 0; i < 100000; i++) {
				list.add(new User(i,StringUtil.generateChineseName()+StringUtil.randomChineseString(2),
						RandomUtil.random(1, 2),"13"+RandomUtil.randomString(9),
						RandomUtil.randomString(3),RandomUtil.random(18, 70)));
			}
			//开始时间
			long startTime = System.currentTimeMillis();
			//保存数据
			for (User user : list) {
				redisTemplate.opsForValue().set("u_"+user.getId(), user);
			}
			//结束时间
			long endTime = System.currentTimeMillis();
			//输出系列化方式、保存数量、所耗时间三项数据，并将这三项数据复制到记事本中
			System.out.println("json序列化保存"+list.size()+"条数据,所消耗时间"+(endTime-startTime));
		}
}
