package com.kosa.batch.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.quartz.JobKey;

import com.kosa.batch.model.BatchAppVo;
import com.kosa.batch.model.BatchGroupVo;

@Mapper
public interface BatchDao {
	List<BatchGroupVo> getBatchGroupList();

	void insertBatchGroup(BatchGroupVo vo);

	void updateBatchGroup(BatchGroupVo vo);

	void deleteBatchGroup(int batchGroupId);

	List<BatchAppVo> getBatchAppList();

	void insertBatchApp(BatchAppVo vo);

	void updateBatchApp(BatchAppVo vo);

	void deleteBatchApp(int batchAppId);

	void deleteBatchAppByGroupId(int batchGroupId);

	List<BatchGroupVo> getBatchGroupByJobKey(String jobKey);
}
