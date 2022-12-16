package com.kosa.batch.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosa.batch.model.BatchAppVo;
import com.kosa.batch.model.BatchGroupVo;
import com.kosa.batch.service.IBatchService;
import com.kosa.batch.service.IJobService;

@RequestMapping("/batch")
@Controller
public class BatchController {
	private static String response = "success";
	
	@Autowired
	IBatchService batchService;
	
	@Autowired
	IJobService jobService;
	
	@ResponseBody
	@GetMapping("/group/list")
	public List<BatchGroupVo> getBatchGroupList() {
		
		return batchService.getBatchGroupList(); 
	}
	
	@ResponseBody
	@PostMapping("/group/posts")
	public void insertBatchGroup(BatchGroupVo vo) {
		
		//배치그룹 등록
		batchService.insertBatchGroup(vo);
		//jobStore에 job 등록
		jobService.addJob(vo);
	}
	
	@ResponseBody
	@PostMapping("/group/update")
	public String updateBatchGroup(BatchGroupVo vo) {
		try {
			//배치그룹 수정
			batchService.updateBatchGroup(vo);
			//jobStore에 등록된 트리거 수정
			jobService.updateJob(vo);			
		}catch(Exception e) {
			response = e.getMessage();
			
		}
		return response;
	}
	
	@ResponseBody
	@PostMapping("/group/delete")
	public String deleteBatchGroup(BatchGroupVo vo) {
		try {
			//배치 앱 삭제
			batchService.deleteBatchAppByGroupId(vo.getBatchGroupId());
			//배치그룹 삭제
			batchService.deleteBatchGroup(vo.getBatchGroupId());
			//jobStore에 등록된 잡, 트리거 정지 및 삭제
			jobService.removeJob(vo);
		}catch(Exception e) {
			response = e.getMessage();
		}
		
		return response;
	}

	@ResponseBody
	@GetMapping("/app/list")
	public List<BatchAppVo> getBatchAppList() {
		
		return batchService.getBatchAppList(); 
	}
	
	@ResponseBody
	@PostMapping("/app/posts")
	public String insertBatchApp(BatchAppVo vo) {
		//배치 앱 등록
		try {
			batchService.insertBatchApp(vo);
		}catch(Exception e) {
			response = e.getMessage();
		}
		return response;
	}
	
	@ResponseBody
	@PostMapping("/app/update")
	public String updateBatchApp(BatchAppVo vo) {
		
		//배치 앱 수정
		try {
			batchService.updateBatchApp(vo);			
		}catch(Exception e) {
			response = e.getMessage();
		}
		return response;
	}
	
	@ResponseBody
	@PostMapping("/app/delete")
	public String deleteBatchApp(BatchAppVo vo) {
		try {
			batchService.deleteBatchApp(vo.getAppId());
		}catch(Exception e) {
			response = e.getMessage();
		}
		return response;
	}
}
