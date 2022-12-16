package com.kosa.batch.model;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BatchGroupVo {
	private int batchGroupId; // 그룹 아이디 (pk) 시퀀스
	private String jobId; //잡 아이디
	private String jobGroupId; // 잡 그룹아이디
	private String jobGroupName; // 잡 그룹명
	private String triggerId; // 트리거 아이디
	private String triggerGroupId; // 트리거 그룹명
	private String cron; //크론
	private String description; // 그룹 설명
	private String host; // 호스트 명
	private String ip; // ip 주소
	private int port; // 포트번호
	private String active; // 자동실행여부 default는 'Y'
	private Date startDate; // 시작시간
	private Date endDate; // 종료시간
	
	
	private String path; //경로
	private String jobKey; //잡키
}	