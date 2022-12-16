package com.kosa.batch;

import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kosa.batch.dao.BatchDao;
import com.kosa.batch.model.BatchGroupVo;
import com.kosa.batch.service.impl.JobService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BatchProjectApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(BatchProjectApplication.class, args);
		
	}
	
	@Bean
	public CommandLineRunner run(JobService jobService, BatchDao batchDao) {
		
		return args -> {
			List<BatchGroupVo> batchGroupVo = batchDao.getBatchGroupList();
			
			for(BatchGroupVo result :  batchGroupVo) {
				
				jobService.addJob(result);
			}	
		};
	}

}
