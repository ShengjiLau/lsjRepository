package lsj;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.lcdt.warehouse.entity.Apple;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TraverseComparisonTest {
	
	List<Apple> appleList = null;
	List<Integer> traditionList = null;
	List<Integer> forEachList = null;
	List<Integer> streamList = null;
	
	
	@Before
	public void prepareDatabase() {
		
		appleList = Lists.newArrayList();
	
		String str = "这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;" +
	                "这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;" +
	                "这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;这个是干扰字段;" +
	                "这个是干扰字段;这个是干扰字段;这个是干扰字段;";
		
		for (int i = 0; i < 1000000; i++) {
			Apple a = new Apple(i,str);
			appleList.add(a);
		}	
		
		traditionList = Lists.newArrayList(appleList.size());
		forEachList = Lists.newArrayList(appleList.size());
		streamList = Lists.newArrayList(appleList.size());
	}
	
	
	
	public Long traditionalMethod() {
		Long traditionBeginTime = System.currentTimeMillis();
		
		int length = traditionList.size();
		for (int i = 0; i < length; i++) {
			traditionList.add(appleList.get(i).getId());
		}
		
		Long traditionEndTime = System.currentTimeMillis();
		
		return traditionEndTime - traditionBeginTime;	
	}
	
	
	public Long forEachMethod() {
		Long forEachBeginTime = System.currentTimeMillis();
		
		appleList.forEach(x -> forEachList.add(x.getId()));
			
		Long forEachEndTime = System.currentTimeMillis();
		
		return forEachEndTime - forEachBeginTime;
	}
	
	
	public Long streamMethod() {
		Long streamBeginTime = System.currentTimeMillis();
		
		streamList = appleList.stream().map(x -> x.getId()).collect(Collectors.toList());
		
		Long streamEndTime = System.currentTimeMillis();
		
		return streamEndTime - streamBeginTime;
	}
	
	@Test
	public void testMethod() {
		Long time1 = traditionalMethod();
		Long time2 = forEachMethod();
		Long time3 = streamMethod();
		
		log.info("传统for循环比forEach:"+(time2-time1));
		log.info("传统for循环比stream:"+(time3-time1));
		log.info("forEach比stream:"+(time3-time2));	
	}
	
	
	
	
	
	
	
	

}
