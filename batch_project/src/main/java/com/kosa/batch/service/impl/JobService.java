package com.kosa.batch.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosa.batch.model.BatchGroupVo;
import com.kosa.batch.scheduler.AgentJob;
import com.kosa.batch.service.IJobService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JobService implements IJobService {

	@Autowired
	Scheduler scheduler;

	@Override
	public void startSchedule() {
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException();
		}
	}
	
	@Override
	public void shutdownSchedule() {
		try {
			scheduler.shutdown();
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException();
		}
		
	}
	
	@Override
	public void startJob(BatchGroupVo vo) {
		//jobId와 jobGroupId로 JobKey 생성
		JobKey key = JobKey.jobKey(vo.getJobId(), vo.getJobGroupId());

		try {
			//Job 재시작
			scheduler.resumeJob(key);
			log.info(key + "를 다시 시작합니다");
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public void pauseJob(BatchGroupVo vo) {
		//jobId와 jobGroupId로 JobKey 생성
		JobKey key = JobKey.jobKey(vo.getJobId(), vo.getJobGroupId());

		try {
			//Job 정지
			scheduler.pauseJob(key);
			log.info(key + "를 정지합니다");
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public void addJob(BatchGroupVo vo) {
		//JobDetail 생성, jobDataMap에 ip와 port 추가
		JobDetail job = JobBuilder.newJob(AgentJob.class)
				.withIdentity(vo.getJobId(), vo.getJobGroupId())
				.build();

		//CronTrigger 생성
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity(vo.getTriggerId(), vo.getTriggerGroupId())
				.withSchedule(CronScheduleBuilder.cronSchedule(vo.getCron()))
				.build();

		try {
			scheduler.scheduleJob(job, trigger);
			log.info("Job이 추가되었습니다.");
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public void removeJob(BatchGroupVo vo) {
		try {
			//Trigger 정지
			scheduler.pauseTrigger(new TriggerKey(vo.getTriggerId(), vo.getTriggerGroupId()));
			//스케줄러에 표시된 Trigger 제거
			scheduler.unscheduleJob(new TriggerKey(vo.getTriggerId(), vo.getTriggerGroupId()));
			//스케줄러에서 식별된 Job과 관련 Trigger 모두 제거
			scheduler.deleteJob(new JobKey(vo.getJobId(), vo.getJobGroupId()));
			log.info("삭제가 완료되었습니다.");
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException();
		}
	}

	@Override
	public void updateJob(BatchGroupVo vo) {
		//TriggerKey 생성
		TriggerKey beforeTrigger = TriggerKey.triggerKey(vo.getTriggerId(), vo.getTriggerGroupId());
		//새로운 cron을 받은 Trigger 생성
		CronTrigger newTrigger = (CronTrigger) TriggerBuilder.newTrigger()
				.withSchedule(CronScheduleBuilder.cronSchedule(vo.getCron()))
				.build();
		log.info("업데이트가 완료되었습니다.");
		try {
			//기존 Trigger 새로 정의한 Trigger로 교체
			scheduler.rescheduleJob(beforeTrigger, newTrigger);
		} catch (SchedulerException e) {
			log.error(e.getMessage());
			throw new RuntimeException();			
		}
	}
}
