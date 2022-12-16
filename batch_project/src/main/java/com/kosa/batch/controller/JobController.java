package com.kosa.batch.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.batch.model.BatchGroupVo;
import com.kosa.batch.service.IJobService;

@Controller
public class JobController {
	
	@Autowired
	IJobService jobService;

	/** 
	 * Scheudler 시작
	 */
	@ResponseBody
	@PostMapping("/scheduler/start")
	public void startJob(){
		jobService.startSchedule();
	}	
	
	/** 
	 * Scheudler 종료
	 */
	@ResponseBody
	@PostMapping("/scheduler/shutdown")
	public void shutdownScheduler(){
		jobService.shutdownSchedule();
	}	
	
	/** 
	 * JobKey를 통해 정지된 Job을 재시작 하는 컨트롤러
	 * @param jobId
	 * @param jobGroupId
	 */
	@ResponseBody
	@PostMapping("/job/start")
	public void startJob(BatchGroupVo vo){
		
		jobService.startJob(vo);
	}
	
	/** 
	 * JobKey를 통해 해당 Job을 정지시키는 컨트롤러
	 * @param jobId
	 * @param jobGroupId
	 */
	@ResponseBody
	@PostMapping("/job/pause")
	public void pauseJob(BatchGroupVo vo){
		jobService.pauseJob(vo);
	}
	
//	/** 
//	 * Job을 추가하는 컨트롤러
//	 * @param jobId
//	 * @param jobGroupId
//	 * @param triggerId
//	 * @param triggerKey
// 	 * @param cron
//	 * @param ip
//	 * @param port
//	 * @param path
//	 */
//	@ResponseBody
//	@PostMapping("/job/add")
//	public void addJob(BatchGroupVo vo){
//		jobService.addJob(vo);
//	}

//	/** 
//	 * Job을 삭제하는 컨트롤러
//	 * @param jobId
//	 * @param jobGroupId
//	 * @param triggerId
//	 * @param triggerKey
//	 */
//	@ResponseBody
//	@PostMapping("/job/remove")
//	public void removeJob(BatchGroupVo vo){
//		jobService.removeJob(vo);
//	}

//	/** 
//	 * Trigger의 cron을 수정하는 컨트롤러
//	 * @param triggerId
//	 * @param triggerGroupId
//	 * @param cron
//	 */
//	@ResponseBody
//	@PostMapping("/job/update")
//	public void updateJob(BatchGroupVo vo){
//		jobService.updateJob(vo);
//	}

}
