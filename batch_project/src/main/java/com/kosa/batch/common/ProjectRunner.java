//package com.kosa.batch.common;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import com.kosa.batch.dao.BatchDao;
//import com.kosa.batch.model.BatchGroupVo;
//import com.kosa.batch.service.impl.JobService;
//
//@Component
//public class ProjectRunner implements ApplicationRunner{
//
//	@Autowired
//	BatchDao batchDao;
//	
//	@Autowired
//	JobService jobService;
//
//	@Override
//	public void run(ApplicationArguments args) throws Exception {
//		List<BatchGroupVo> batchGroupVo = batchDao.getBatchGroupList();
//		
//		for(BatchGroupVo result :  batchGroupVo) {
//			System.out.println(result.getCron());
//			
//			jobService.addJob(result);
//		}	
//	}
//
//}
