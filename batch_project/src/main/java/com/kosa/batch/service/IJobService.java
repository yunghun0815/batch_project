package com.kosa.batch.service;

import com.kosa.batch.model.BatchGroupVo;

public interface IJobService {
	
	void startSchedule();
	
	void shutdownSchedule();
	
	void startJob(BatchGroupVo vo);

	void pauseJob(BatchGroupVo vo);

	void addJob(BatchGroupVo vo);

	void removeJob(BatchGroupVo vo);

	void updateJob(BatchGroupVo vo);

}
