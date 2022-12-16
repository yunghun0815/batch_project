package com.kosa.batch.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BatchAppVo {
	private int appId; // app의 Id //시퀀스
	private String batchGroupId; // BatchGroup의 groupId 외래키
	private String appName; // app의 이름
	private String path; // app의 경로
	private String executionOrder; // 실행순번
}
