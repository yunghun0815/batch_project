package com.kosa.batch.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
//import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

public class QuartzTest {
	public static void main(String[] args) throws Exception {
		
		// Scheduler
		// 스케줄러와 상호 작용하기 위한 기본 API
		
		// Job
		// 스케줄러에 의해 실행되기를 원하는 구현 될 인터페이스
		
		// JobDetail
		// 작업 인스턴스를 정의하는데 사용
		
		// Trigger
		// 지정된 작업이 실행되는 일정을 정의하는 구성 요소
		
		// JobBuilder
		// 작업을 정의하는 인스턴스(JobDetail)을 빌드하는데 사용 

		// TriggerBuilder
		// 트리거 인스턴스를 정의/빌드 하는데 사용
		
		/**
			SCheduler의 생명주기는 SchedulerFactory 및 해당 shutdown() 메서드를 통해 제한 됨, 
			생성된 Scheduler 인터페이스는 Job과 Trigger를 추가, 제거 및 나열하고 기타 일정 관련 작업(트리거 일시 정지 등)을 수행하는데 사용할 수 있음
		**/
		
		//Scheduler는 start 메소드로 시작될 때까지 어떠한 트리거에도 작동하지 않음
		
		
		//스케줄러를 사용하기 위해 인스턴스화
		SchedulerFactory schedFact = new StdSchedulerFactory();
		
		Scheduler sched = schedFact.getScheduler();
		
		//작업 정의, 'HelloJob' 클래스 연결
		JobDetail job = JobBuilder.newJob(HelloJob.class)
				//이름과 그룹을 정의
				.withIdentity("myjob","group1")
				.build();
		
		// 5초 마다 지금 실행되도록 한 작업을 트리거 함
		Trigger trigger = newTrigger()
				.withIdentity("myTrigger", "group1")
				.startNow()
				.withSchedule(simpleSchedule()
						.withIntervalInSeconds(5)
						.repeatForever())
				.build();
		
		// 트리거를 사용하여 작업을 예약 스케줄링 하도록 Quartz에게 지시
		sched.start();
		
		sched.scheduleJob(job, trigger);
	}
}
