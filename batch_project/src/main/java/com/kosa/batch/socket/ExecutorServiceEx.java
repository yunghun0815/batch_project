package com.kosa.batch.socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

@Component
public class ExecutorServiceEx {

	public void putTask(Runnable task) {

		// 1000개의 R생성
		String[][] Rs = new String[100][2];
		for(int i=0; i<Rs.length; i++) {
			Rs[i][0] = i + " - IP주소"; 
			Rs[i][1] = "성공";

		}

		// 스레드풀 생성
		ExecutorService executorService = Executors.newFixedThreadPool(10);

		executorService.execute(task);
		// R을 보내는 작업생성과 처리요청
//		for(int i=0; i<1000; i++) {
//					
//		}
		// 스레드 종료
		executorService.shutdownNow();

	}
}