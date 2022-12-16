package com.kosa.batch.scheduler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kosa.batch.model.BatchGroupVo;
import com.kosa.batch.service.IBatchService;
import com.kosa.batch.service.impl.BatchService;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AgentJob implements Job{
	
	@Autowired
	IBatchService batchService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			//잡키로 찾기 path 여러개 + 아이피 + 포트
			log.info("키" + context.getJobDetail().getKey());
			List<BatchGroupVo> batchGroupVo = batchService.getBatchGroupByJobKey(context.getJobDetail().getKey().toString());
			
			log.info("");
			log.info("-----------------------------------");
			log.info(batchGroupVo.get(0).getIp()+":"+batchGroupVo.get(0).getPort()+" 배치 프로그램 실행");
			String result = null;
			
			//배치그룹에 등록된 앱 수만큼 실행
			for(BatchGroupVo vo : batchGroupVo) {
				Process process = Runtime.getRuntime().exec(vo.getPath());
				BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
				
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = new JSONObject();
						
				while((result=br.readLine()) != null) {
					jsonObject = (JSONObject) jsonParser.parse(result);
					log.info("배치 완료 시간 : " + jsonObject.get("date"));
					log.info("실행 결과 : " + jsonObject.get("result"));
				}
			}
				
			log.info("-----------------------------------");

		} catch (Exception e) {
			System.err.println(e);
		}
	}

}
