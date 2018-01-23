package com.lcdt.quartz.service.impl;
import com.lcdt.quartz.service.IQuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuartzServiceImpl implements IQuartzService {

	@Autowired
	private Scheduler scheduler;

	/**
	 * 开始一个simpleSchedule()调度(创建一个新的定时任务)
	 * @param jName  job名字(请保证唯一性)
	 * @param jGroup  job组名(请保证唯一性)
	 * @param cron    cron时间表达式
	 * @param tName   trigger名字(请保证唯一性)
	 * @param tGroup  triggerjob组名(请保证唯一性)
	 * @param c  Job任务类
	 */
	public  void startSchedule(String jName,String jGroup,String cron,String tName,String tGroup,Class c) {
		try {
			JobDetail jobDetail = JobBuilder.newJob(c).withIdentity(jName, jGroup).build();
			
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			Trigger trigger = TriggerBuilder.newTrigger()
					.withIdentity(tName, tGroup).startNow()
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startSchedule(JobDetail jobDetail, Trigger trigger) {
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	public void test() {
		System.out.println(3333);
		System.out.println(scheduler);
		System.out.println(11111);
	}


	/**
	 * 开始任务
	 */
	public void start(){
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 暂停Job
	 * @param name job名字
	 * @param group  job组名
	 */
	public void pauseJob(String name, String group){
		JobKey jobKey = JobKey.jobKey(name,group);
		try {
			scheduler.pauseJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 恢复Job
	 * @param name  job名字
	 * @param group  job组名
	 */
	public void resumeJob(String name, String group){
		JobKey jobKey = JobKey.jobKey(name,group);
		try {
			scheduler.resumeJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除Job
	 * @param name  job名字
	 * @param group  job组名
	 */
	public void deleteJob(String name, String group){
		JobKey jobKey = JobKey.jobKey(name,group);
		TriggerKey triggerKey = TriggerKey.triggerKey(name,group);
		 CronTrigger trigger;
		try {
				trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
	            scheduler.pauseTrigger(triggerKey);// 停止触发器
	            scheduler.unscheduleJob(triggerKey);// 移除触发器
	            if (trigger != null) {
	            	 scheduler.deleteJob(trigger.getJobKey());// 删除任务
	            } else {
	            	scheduler.deleteJob(jobKey);
	            }
	           
		
			//scheduler.resumeJob(jobKey);// 删除任务  
			//scheduler.pauseTrigger(triggerKey);
			//scheduler.unscheduleJob(triggerKey);// 移除触发器  
			
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 更新任务表达式
	 * @param name  trigger名字
	 * @param group  trigger组名
	 * @param cron  cron时间表达式
	 */
	public void rescheduleJob(String name, String group,String cron) {
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(name,group);
			// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			// 表达式调度构建器    "0/2 * * * * ?"
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
			// 按新的cronExpression表达式重新构建trigger
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			// 按新的trigger重新设置job执行
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	
}
