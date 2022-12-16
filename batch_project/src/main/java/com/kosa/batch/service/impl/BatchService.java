package com.kosa.batch.service.impl;

import java.util.List;

import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.kosa.batch.dao.BatchDao;
import com.kosa.batch.model.BatchAppVo;
import com.kosa.batch.model.BatchGroupVo;
import com.kosa.batch.service.IBatchService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BatchService implements IBatchService{

	@Autowired
	BatchDao batchDao;
	
	@Override
	public List<BatchGroupVo> getBatchGroupList() {
		
		return batchDao.getBatchGroupList();
	}

	@Override
	public void insertBatchGroup(BatchGroupVo vo) {
		String jobKey = vo.getJobGroupId() + "." + vo.getJobId();
		vo.setJobKey(jobKey);
		batchDao.insertBatchGroup(vo);
	}

	@Override
	public void updateBatchGroup(BatchGroupVo vo) {
		String jobKey = vo.getJobGroupId() + "." + vo.getJobId();
		vo.setJobKey(jobKey);
		batchDao.updateBatchGroup(vo);
	}

	@Override
	public void deleteBatchGroup(int batchGroupId) {
		batchDao.deleteBatchGroup(batchGroupId);
	}

	@Override
	public List<BatchAppVo> getBatchAppList() {
		
		return batchDao.getBatchAppList();
	}

	@Override
	public void insertBatchApp(BatchAppVo vo) {
		batchDao.insertBatchApp(vo);
	}

	@Override
	public void updateBatchApp(BatchAppVo vo) {
		batchDao.updateBatchApp(vo);
	}

	@Override
	public void deleteBatchApp(int batchAppId) {
		batchDao.deleteBatchApp(batchAppId);
	}

	@Override
	public void deleteBatchAppByGroupId(int batchGroupId) {
		batchDao.deleteBatchAppByGroupId(batchGroupId);
	}

	@Override
	public List<BatchGroupVo> getBatchGroupByJobKey(String key) {
		return batchDao.getBatchGroupByJobKey(key);
	}

}
