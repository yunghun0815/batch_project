package com.kosa.batch.service;

import java.util.List;

import org.quartz.JobKey;

import com.kosa.batch.model.BatchAppVo;
import com.kosa.batch.model.BatchGroupVo;

public interface IBatchService {
	List<BatchGroupVo> getBatchGroupList();

	void insertBatchGroup(BatchGroupVo vo);

	void updateBatchGroup(BatchGroupVo vo);

	void deleteBatchGroup(int batchGroupId);

	List<BatchAppVo> getBatchAppList();

	void insertBatchApp(BatchAppVo vo);

	void updateBatchApp(BatchAppVo vo);

	void deleteBatchApp(int batchAppId);

	void deleteBatchAppByGroupId(int batchGroupId);

	List<BatchGroupVo> getBatchGroupByJobKey(String key);
	
}
